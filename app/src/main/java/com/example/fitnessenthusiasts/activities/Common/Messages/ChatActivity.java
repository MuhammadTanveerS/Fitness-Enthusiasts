package com.example.fitnessenthusiasts.activities.Common.Messages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.MessageAdapter;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.MessageModel;
import com.example.fitnessenthusiasts.databinding.ActivityChatBinding;
import com.example.fitnessenthusiasts.databinding.ActivityUserProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    Intent intent;
    String userId, userFName, userPhoto, token;
    String senderUid,receiverUid,senderRoom,receiverRoom;
    MessageAdapter adapter;
    FirebaseDatabase database;
    ArrayList<MessageModel> messageModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));

        intent = getIntent();
        getDataFromIntent();

        messageModels = new ArrayList<>();
        adapter = new MessageAdapter(this,messageModels);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setReverseLayout(true);
        binding.chatRV.setLayoutManager(linearLayoutManager);
        binding.chatRV.setAdapter(adapter);
//        binding.chatRV.scrollToPosition(items.size() - 1);

        receiverUid = userId;
        senderUid = FirebaseAuth.getInstance().getUid();
        senderRoom = senderUid + receiverUid;
        receiverRoom = receiverUid + senderUid;

        getMessages();
        checkUserStatus();
        listenText();

    }


    private void getDataFromIntent() {
        userId =    intent.getStringExtra("userId");
        userFName = intent.getStringExtra("userFName");
        userPhoto = intent.getStringExtra("userPhoto");
        token = intent.getStringExtra("token");

        binding.chatName.setText(userFName);
        Picasso.get()
                .load(userPhoto)
                .placeholder(R.drawable.placeholder_avatar)
                .into(binding.chatImage);
    }

    private void getMessages() {
        database.getReference().child("Chats")
                .child(senderRoom).child("messages")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageModels.clear();
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            MessageModel model = dataSnapshot.getValue(MessageModel.class);
                            messageModels.add(model);
                        }
                        adapter.notifyDataSetChanged();
                        binding.chatRV.scrollToPosition(messageModels.size() - 1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    public void finish(View view) {
        finish();
    }

    public void sendMsg(View view) {
        String content = binding.msgContent.getText().toString();
        Date date = new Date();

        MessageModel model = new MessageModel(content,senderUid,date.getTime());
        binding.msgContent.setText("");

        HashMap<String,Object> lastMsgMap = new HashMap<>();
        lastMsgMap.put("lastMsg",model.getContent());
        lastMsgMap.put("lastMsgTime",date.getTime());
        database.getReference().child("Chats").child(senderRoom).updateChildren(lastMsgMap);
        database.getReference().child("Chats").child(receiverRoom).updateChildren(lastMsgMap);


        database.getReference().child("Chats").child(senderRoom)
                .child("messages").push().setValue(model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        database.getReference().child("Chats").child(receiverRoom)
                                .child("messages").push().setValue(model)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        sendNotification(userFName,model.getContent(),token);
                                    }
                                });

                            }
                });
    }


    private void checkUserStatus(){
        database.getReference().child("UserStatus").child(receiverUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String status = snapshot.getValue(String.class);
                    if(!status.isEmpty()){
                        if(status.equals("Offline")){
                            binding.chatStatus.setVisibility(View.GONE);
                        }else{
                            binding.chatStatus.setText(status);
                            binding.chatStatus.setVisibility(View.VISIBLE);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void listenText() {
        final Handler handler = new Handler();

        binding.msgContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                database.getReference().child("UserStatus").child(FirebaseAuth.getInstance().getUid()).setValue("typing....");
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(typingStopped,1000);
            }
            Runnable typingStopped = new Runnable() {
                @Override
                public void run() {
                    database.getReference().child("UserStatus").child(FirebaseAuth.getInstance().getUid()).setValue("Online");
                }
            };
        });
    }

    private void sendNotification(String name, String content, String token){
        try {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://fcm.googleapis.com/fcm/send";

            JSONObject data = new JSONObject();
            data.put("title", name);
            data.put("body", content);

            JSONObject notificationData = new JSONObject();
            notificationData.put("notification",data);
            notificationData.put("to",token);

            JsonObjectRequest request = new JsonObjectRequest(url, notificationData
                    , new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ChatActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> map = new HashMap<>();
                    String key = "Key=AAAA_Vfot6Q:APA91bGwel3qlwaZaDZ7oM77N9XQjMOwyzAm5pR7kcB4K63D-nt_gtsrUnBwcpe37qSCNA0nX0BmJNaHV0lVE_mHxKEGZu2xWTWwekPIlAmrEtvMcP7FLGeHhgQQUi2FQruAl671CdNA";
                    map.put("Authorization",key);
                    map.put("Content-Type","application/json");
                    return map;
                }
            };
            queue.add(request);
        } catch (Exception ex){

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String currentId = FirebaseAuth.getInstance().getUid();
        database.getReference().child("UserStatus").child(currentId).setValue("Online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        String currentId = FirebaseAuth.getInstance().getUid();
        database.getReference().child("UserStatus").child(currentId).setValue("Offline");
    }
}