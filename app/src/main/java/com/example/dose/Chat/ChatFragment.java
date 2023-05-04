package com.example.dose.Chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.dose.Common;
import com.example.dose.Models.User;
import com.example.dose.R;
import com.example.dose.User.DisplayPharma;
import com.example.dose.databinding.FragmentChatBinding;
import com.example.dose.pharmaceutical.DisplayUsers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class ChatFragment extends Fragment {
    private FragmentChatBinding mBinding;
    private DatabaseReference Database;
    private ArrayList<MessageModel> message;
    private ChatAdapter adapter;
    private String receiverId,userId;
    private User user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentChatBinding.inflate(inflater,container,false);
        user=getArguments().getParcelable("user");
        Database= FirebaseDatabase.getInstance().getReference("chat");
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        receiverId=user.getId();
        Glide.with(this).load(user.getImage()).into(mBinding.profileImage);
        recyclerViewComponent();
        back();
        sendMessage();
        return mBinding.getRoot();
    }
    private void recyclerViewComponent()
    {
        message=new ArrayList<>();
        adapter=new ChatAdapter(message,userId);
        mBinding.chat.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.chat.setAdapter(adapter);

        getMessages();
    }

    private void getMessages() {
        Database.child(userId).child(receiverId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    message.clear();
                    for (DataSnapshot data:snapshot.getChildren())
                    {
                        String text=data.child("message").getValue().toString();
                        String time=data.child("time").getValue().toString();
                        String id=data.child("userId").getValue().toString();
                        MessageModel model=new MessageModel(text,id,time);
                        message.add(model);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void sendMessage()
    {
        mBinding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mBinding.sendMessageText.getText().toString()))
                {
                    mBinding.sendMessageText.setError("write your message");
                }
                else
                {
                    sendMessageToDatabase();
                }
            }
        });
    }

    private void sendMessageToDatabase() {
        MessageModel messageModel=new MessageModel(mBinding.sendMessageText.getText().toString(),userId,getDateTime());
        Database.child(userId).child(receiverId).push().setValue(messageModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Database.child(receiverId).child(userId).push().setValue(messageModel);
                    mBinding.sendMessageText.setText("");
                }
            }
        });
    }
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH.mm", Locale.US);
        Date date = new Date();
        return dateFormat.format(date);
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.type==1) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.pharma, new DisplayUsers()).commit();
                }
                else {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.userFrameLayout, new DisplayPharma()).commit();

                }
            }
        });
    }
}