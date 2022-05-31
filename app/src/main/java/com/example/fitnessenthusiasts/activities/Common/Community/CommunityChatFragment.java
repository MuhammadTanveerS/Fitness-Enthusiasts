package com.example.fitnessenthusiasts.activities.Common.Community;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.Session;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.CommunityChatAdapter;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.MessageAdapter;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.MessageModel;
import com.example.fitnessenthusiasts.databinding.FragmentCommunityChatBinding;
import com.example.fitnessenthusiasts.databinding.FragmentCommunityDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;


public class CommunityChatFragment extends Fragment {

    FragmentCommunityChatBinding binding;
    FirebaseDatabase database;
    ArrayList<MessageModel> messageModels;
    CommunityChatAdapter adapter;
    String key;
    Session session;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCommunityChatBinding.inflate(inflater,container, false);

        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));
        key=((CommunityMainActivity)getActivity()).key;
        session = new Session(getContext());

        messageModels = new ArrayList<>();
        adapter = new CommunityChatAdapter(getContext(),messageModels);
        binding.chatRV.setAdapter(adapter);
        getMessages();
        sendMsg();

        return binding.getRoot();
    }

    private void getMessages() {
        database.getReference().child("Communities").child(key).child("chat")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            messageModels.clear();
                            for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                MessageModel model = dataSnapshot.getValue(MessageModel.class);
                                messageModels.add(model);
                            }
                            adapter.notifyDataSetChanged();
                            binding.chatRV.scrollToPosition(messageModels.size() - 1);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void sendMsg() {
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = binding.msgContent.getText().toString();
                Date date = new Date();

                MessageModel model = new MessageModel(content, FirebaseAuth.getInstance().getUid(), date.getTime());
                model.setSenderName(session.getName());
                binding.msgContent.setText("");
                database.getReference().child("Communities").child(key).child("chat").push().setValue(model);

            }
        });
    }
}