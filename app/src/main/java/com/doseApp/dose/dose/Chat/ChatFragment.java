package com.doseApp.dose.dose.Chat;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.doseApp.dose.dose.Common;
import com.doseApp.dose.dose.Models.MessageModel;
import com.doseApp.dose.dose.Models.User;
import com.doseApp.dose.dose.R;
import com.doseApp.dose.dose.SendNotificationPack.APIService;
import com.doseApp.dose.dose.SendNotificationPack.Client;
import com.doseApp.dose.dose.SendNotificationPack.Data;
import com.doseApp.dose.dose.SendNotificationPack.MyResponse;
import com.doseApp.dose.dose.SendNotificationPack.NotificationSender;
import com.doseApp.dose.dose.User.DisplayPharma;
import com.doseApp.dose.dose.databinding.FragmentChatBinding;
import com.doseApp.dose.dose.pharmaceutical.DisplayUsers;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatFragment extends Fragment {
    private FragmentChatBinding mBinding;
    private DatabaseReference Database;
    private ArrayList<MessageModel> message;
    private ChatAdapter adapter;
    private String receiverId,userId;
    private User user;
    private APIService apiService;
    private String userName;
    private int mess=0;
    private Map<String ,String> profile=new HashMap<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentChatBinding.inflate(inflater,container,false);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        user=getArguments().getParcelable("user");
        Database= FirebaseDatabase.getInstance().getReference("chat");
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        receiverId=user.getId();
        if (! user.getImage().equals("")) {
            Glide.with(this).load(user.getImage()).into(mBinding.profileImage);
        }
        if (Common.type==1) {
            getUserName("pharma",userId);
        }
        else {
            getUserName("users",userId);
        }
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
                    //mBinding.chat.scrollToPosition(adapter.getItemCount()-1);
                    removeNew();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void removeNew() {
        FirebaseDatabase.getInstance().getReference("messageNum").child(userId).child(receiverId).child("num").setValue(0);
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
                    getToken(receiverId,userName,messageModel.getMessage());
                    addToNotification();
                    mess=1;
                    addToNew();
                }
            }
        });
    }

    private void addToNew() {
        FirebaseDatabase.getInstance().getReference("messageNum").child(receiverId).child(userId)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() ) {
                    if (mess == 1) {
                        int num = Integer.parseInt(snapshot.child("num").getValue().toString());
                        num++;
                        FirebaseDatabase.getInstance().getReference("messageNum").child(receiverId).child(userId)
                                .child("num").setValue(num);
                        mess=0;
                    }
                }
                else {
                    FirebaseDatabase.getInstance().getReference("messageNum").child(receiverId).child(userId)
                            .child("num").setValue(1);
                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addToNotification()
    {
        String noty=userName+" Send new message";
        FirebaseDatabase.getInstance().getReference("notification").child(receiverId).push().child("notify").setValue(noty);
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
    private void getUserName(String table,String id)
    {
        FirebaseDatabase.getInstance().getReference(table).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName=snapshot.child("userName").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getToken(String userID, String title, String message) {
        FirebaseDatabase.getInstance().getReference("tokens").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String token=snapshot.child("token").getValue().toString();
                    sendNotifications(token,title,message);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void sendNotifications(String usertoken, String title, String message) {
        Data data = new Data(title, message);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
            @SuppressLint("ShowToast")
            @Override
            public void onResponse(@NonNull Call<MyResponse> call, @NonNull Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body() != null && response.body().success != 1) {
                        Toast.makeText(getActivity(), "Failed ", Toast.LENGTH_LONG);
                    } else {
                        Log.e("success", response.code() + " success ya Fashel " + response.body().success + " Token " + usertoken);
                    }
                } else {
                    Log.e("send Notifications", "Failed ya Fashel: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MyResponse> call, @NonNull Throwable t) {
            }
        });
    }
}