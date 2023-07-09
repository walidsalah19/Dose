package com.doseApp.dose.dose.User.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doseApp.dose.dose.Models.User;
import com.doseApp.dose.dose.R;

import java.util.ArrayList;
import java.util.HashMap;

public class DisplayPharmaAdapter extends RecyclerView.Adapter<DisplayPharmaAdapter.help>{
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onclick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
    private ArrayList<User> users;
    HashMap<String, String> messNum;
    Context context;
    public DisplayPharmaAdapter(ArrayList<User> users, HashMap<String, String> messNum, Context context) {
        this.users = users;
        this.context=context;
        this.messNum=messNum;
    }
    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_display_users,parent,false);
        return new help(v);
    }

    @Override
    public void onBindViewHolder(@NonNull help holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(users.get(position).getUserName());
        if (messNum.containsKey(users.get(position).getId())) {
            String num=messNum.get(users.get(position).getId());
            if (! num.equals("0")){
                holder.num.setText(messNum.get(users.get(position).getId()));
            }
            else
            {
                holder.num.setVisibility(View.GONE);
            }
        }
        else
        {
            holder.num.setVisibility(View.GONE);
        }
        if (! users.get(position).getImage().equals(""))
        {
            Glide.with(context).load(users.get(position).getImage()).into(holder.profile_image);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class help extends RecyclerView.ViewHolder
    {
        TextView name,num;
        ImageView profile_image;
        public help(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.userName);
            num=itemView.findViewById(R.id.newMess);
            profile_image=itemView.findViewById(R.id.profile_image);
        }
    }
}
