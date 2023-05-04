package com.example.dose.Chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dose.Models.MessageModel;
import com.example.dose.R;

import java.util.ArrayList;

public class ChatAdapter extends  RecyclerView.Adapter<ChatAdapter.help> {
  private ArrayList<MessageModel> message ;
  private String userId;

    public ChatAdapter(ArrayList<MessageModel> message, String userId) {
        this.message = message;
        this.userId = userId;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_content_chat,parent,false);
        return new help(v);
    }

    @Override
    public void onBindViewHolder(@NonNull help holder, int position) {

        RelativeLayout.LayoutParams params2= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        if (message.get(position).getUserId().equals(userId))
        {
            holder.receiver_layout.setVisibility(View.GONE);
            holder.sender_layout.setLayoutParams(params2);
            holder.sender_message.setText(message.get(position).getMessage());
            holder.sender_time.setText(message.get(position).getTime());
        }
        else
        {
            holder.receiver_layout.setLayoutParams(params2);
            holder.sender_layout.setVisibility(View.GONE);
            holder.receiver_message.setText(message.get(position).getMessage());
            holder.receiver_time.setText(message.get(position).getTime());
        }
    }

    @Override
    public int getItemCount() {
        return message.size();
    }

    public class help extends RecyclerView.ViewHolder
    {
        TextView sender_message,sender_time,receiver_message,receiver_time;
        LinearLayout sender_layout,receiver_layout;
        public help(@NonNull View itemView) {
            super(itemView);
            sender_message=(TextView)itemView.findViewById(R.id.sender_message);
            sender_time=(TextView)itemView.findViewById(R.id.sender_time);
            sender_layout= (LinearLayout) itemView.findViewById(R.id.sender_layout);


            receiver_message=(TextView)itemView.findViewById(R.id.receiver_message);
            receiver_time=(TextView)itemView.findViewById(R.id.receiver_time);
            receiver_layout= (LinearLayout) itemView.findViewById(R.id.receiver_layout);
        }
    }
}
