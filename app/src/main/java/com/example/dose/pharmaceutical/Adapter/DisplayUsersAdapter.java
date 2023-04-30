package com.example.dose.pharmaceutical.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dose.Models.User;
import com.example.dose.R;

import java.util.ArrayList;

public class DisplayUsersAdapter extends RecyclerView.Adapter<DisplayUsersAdapter.help>{
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onclick(int position,int type);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
    private ArrayList<User> users;

    public DisplayUsersAdapter(ArrayList<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_display_users,parent,false);
        return new help(v);
    }

    @Override
    public void onBindViewHolder(@NonNull help holder, int position) {
        holder.name.setText(users.get(position).getUserName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class help extends RecyclerView.ViewHolder
    {
        TextView name;
        public help(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.userName);
        }
    }
}
