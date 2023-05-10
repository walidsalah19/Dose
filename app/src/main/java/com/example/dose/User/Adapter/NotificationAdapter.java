package com.example.dose.User.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dose.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.help>{
    private ArrayList<String> noty;

    public NotificationAdapter(ArrayList<String> noty) {
        this.noty = noty;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_layout,parent,false);
        return new help(v);
    }

    @Override
    public void onBindViewHolder(@NonNull help holder, int position) {
        holder.notify.setText(noty.get(position));
    }

    @Override
    public int getItemCount() {
        return noty.size();
    }

    public class help extends RecyclerView.ViewHolder
    {

        TextView notify;
        public help(@NonNull View itemView) {
            super(itemView);
            notify=itemView.findViewById(R.id.notify);
        }
    }
}
