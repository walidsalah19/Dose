package com.example.dose.User;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dose.Chat.ChatFragment;
import com.example.dose.Common;
import com.example.dose.Models.User;
import com.example.dose.R;
import com.example.dose.SweetDialog;
import com.example.dose.User.Adapter.DisplayPharmaAdapter;
import com.example.dose.databinding.FragmentDisplayPharmaBinding;
import com.example.dose.pharmaceutical.Adapter.DisplayUsersAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DisplayPharma extends Fragment {

   private FragmentDisplayPharmaBinding mBinding;
    private DatabaseReference database;
    private DisplayPharmaAdapter adapter;
    private ArrayList<User> users;
    private SweetAlertDialog loading;
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
    }
    private void init()
    {
        users=new ArrayList<>();
        adapter=new DisplayPharmaAdapter(users,getContext());
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
                    adapter.notifyDataSetChanged();
                }
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