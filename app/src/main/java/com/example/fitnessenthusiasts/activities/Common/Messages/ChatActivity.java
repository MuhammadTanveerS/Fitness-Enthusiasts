package com.example.fitnessenthusiasts.activities.Common.Messages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    Intent intent;
    String userId, userFName, userPhoto;
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
        binding.chatRV.setLayoutManager(new LinearLayoutManager(this));
        binding.chatRV.setAdapter(adapter);

        receiverUid = userId;
        senderUid = FirebaseAuth.getInstance().getUid();
        senderRoom = senderUid + receiverUid;
        receiverRoom = receiverUid + senderUid;

        getMessages();

    }
    private void getDataFromIntent() {
        userId =    intent.getStringExtra("userId");
        userFName = intent.getStringExtra("userFName");
        userPhoto = intent.getStringExtra("userPhoto");


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
                                .child("messages").push().setValue(model);

                            }
                });
    }
}