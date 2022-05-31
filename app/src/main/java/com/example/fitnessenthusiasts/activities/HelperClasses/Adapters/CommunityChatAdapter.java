package com.example.fitnessenthusiasts.activities.HelperClasses.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.UserHelperClass;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.MessageModel;
import com.example.fitnessenthusiasts.databinding.CommunityMessageRecieveLayoutBinding;
import com.example.fitnessenthusiasts.databinding.CommunityMessageSendLayoutBinding;
import com.example.fitnessenthusiasts.databinding.MessageRecieveLayoutBinding;
import com.example.fitnessenthusiasts.databinding.MessageSentLayoutBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CommunityChatAdapter extends RecyclerView.Adapter{

    Context context;
    ArrayList<MessageModel> messages;
    final int ITEM_SENT =1;
    final int ITEM_RECEIVE = 2;

    public CommunityChatAdapter(Context context, ArrayList<MessageModel> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == ITEM_SENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.community_message_send_layout, parent, false);
            return new SentViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.community_message_recieve_layout, parent, false);
            return new ReceiverViewHolder(view);
        }

    }

    @Override
    public int getItemViewType(int position) {
        MessageModel messageModel = messages.get(position);
        if(FirebaseAuth.getInstance().getUid().equals(messageModel.getSenderID())) {
            return ITEM_SENT;
        } else {
            return ITEM_RECEIVE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageModel model = messages.get(position);


        if(holder.getClass() == SentViewHolder.class) {
            SentViewHolder viewHolder = (SentViewHolder)holder;
            viewHolder.binding.msgContent.setText(model.getContent());
            viewHolder.binding.senderName.setText(model.getSenderName());

        }else{
            ReceiverViewHolder viewHolder = (ReceiverViewHolder)holder;
            viewHolder.binding.msgContent.setText(model.getContent());
            viewHolder.binding.recieverName.setText(model.getSenderName());
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class SentViewHolder extends RecyclerView.ViewHolder {

        CommunityMessageSendLayoutBinding binding;
        public SentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CommunityMessageSendLayoutBinding.bind(itemView);
        }
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {

        CommunityMessageRecieveLayoutBinding binding;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CommunityMessageRecieveLayoutBinding.bind(itemView);
        }
    }

}
