package com.example.fitnessenthusiasts.activities.HelperClasses.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.MessageModel;
import com.example.fitnessenthusiasts.databinding.MessageRecieveLayoutBinding;
import com.example.fitnessenthusiasts.databinding.MessageSentLayoutBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter{

    Context context;
    ArrayList<MessageModel> messages;
    final int ITEM_SENT =1;
    final int ITEM_RECEIVE = 2;

    public MessageAdapter(Context context, ArrayList<MessageModel> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == ITEM_SENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.message_sent_layout, parent, false);
            return new SentViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.message_recieve_layout, parent, false);
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
        }else{
            ReceiverViewHolder viewHolder = (ReceiverViewHolder)holder;
            viewHolder.binding.msgContent.setText(model.getContent());
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class SentViewHolder extends RecyclerView.ViewHolder {

        MessageSentLayoutBinding binding;
        public SentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MessageSentLayoutBinding.bind(itemView);
        }
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {

        MessageRecieveLayoutBinding binding;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MessageRecieveLayoutBinding.bind(itemView);
        }
    }

}
