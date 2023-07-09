package com.doseApp.dose.dose.User;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doseApp.dose.dose.Models.User;
import com.doseApp.dose.dose.R;
import com.doseApp.dose.dose.SweetDialog;
import com.doseApp.dose.dose.Chat.ChatFragment;
import com.doseApp.dose.dose.Common;
import com.doseApp.dose.dose.databinding.FragmentDisplayPharmaBinding;
import com.doseApp.dose.dose.User.Adapter.DisplayPharmaAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DisplayPharma extends Fragment {

   private FragmentDisplayPharmaBinding mBinding;
    private DatabaseReference database;
    private DisplayPharmaAdapter adapter;
    private ArrayList<User> users;
    private SweetAlertDialog loading;
    private HashMap<String,String> messNum;
    private String userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentDisplayPharmaBinding.inflate(inflater,container,false);
        startLoading();
        initFirebase();
        init();
        chat();
        getUsers();
        back();
        return mBinding.getRoot();
    }
    private void startLoading()
    {
        loading= SweetDialog.loading(getActivity());
        loading.show();
    }
    private void initFirebase()
    {
        database= FirebaseDatabase.getInstance().getReference("pharma");
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
    }
    private void init()
    {
        messNum=new HashMap<>();
        users=new ArrayList<>();
        adapter=new DisplayPharmaAdapter(users,messNum,getContext());
        mBinding.users.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.users.setAdapter(adapter);
    }
    private void getUsers()
    {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for (DataSnapshot data:snapshot.getChildren())
                    {
                        String name=data.child("userName").getValue().toString();
                        String email=data.child("email").getValue().toString();
                        String id=data.child("id").getValue().toString();
                        String type=data.child("type").getValue().toString();
                        String image=data.child("image").getValue().toString();
                        users.add(new User(name,email,id,type,image));
                    }
                    getMessageNum();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getMessageNum()
    {
        FirebaseDatabase.getInstance().getReference("messageNum").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messNum.clear();
                if (snapshot.exists())
                {
                    for (DataSnapshot snap:snapshot.getChildren())
                    {
                        String key = snap.getKey();
                        String num = snap.child("num").getValue().toString();
                        messNum.put(key,num);
                    }
                    adapter.notifyDataSetChanged();

                }
                adapter.notifyDataSetChanged();

                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
      private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.userFrameLayout, new UserHome()).commit();
            }
        });
    }
    private void chat()
    {
        adapter.setOnItemClickListener(new DisplayPharmaAdapter.OnItemClickListener() {
            @Override
            public void onclick(int position) {
                Bundle b=new Bundle();
                b.putParcelable("user",users.get(position));
                ChatFragment c=new ChatFragment();
                c.setArguments(b);
                Common.type=0;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.userFrameLayout,c).addToBackStack(null).commit();
            }
        });
    }
}