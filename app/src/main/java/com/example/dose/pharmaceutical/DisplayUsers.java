package com.example.dose.pharmaceutical;

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
import com.example.dose.databinding.FragmentDisplayUsersBinding;
import com.example.dose.pharmaceutical.Adapter.DisplayUsersAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DisplayUsers extends Fragment {

    private FragmentDisplayUsersBinding mBinding;
    private DatabaseReference database;
    private DisplayUsersAdapter adapter;
    private ArrayList<User> users;
    private SweetAlertDialog loading;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentDisplayUsersBinding.inflate(inflater,container,false);
        startLoading();
        initFirebase();
        chat();
        init();
        getUsers();
        return mBinding.getRoot();
    }
    private void startLoading()
    {
        loading= SweetDialog.loading(getActivity());
        loading.show();
    }
    private void initFirebase()
    {
        database= FirebaseDatabase.getInstance().getReference("users");
    }
    private void init()
    {
        users=new ArrayList<>();
        adapter=new DisplayUsersAdapter(users);
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
                   users.add(new User(name,email,id,type));
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
    private void chat()
    {
        adapter.setOnItemClickListener(new DisplayUsersAdapter.OnItemClickListener() {
            @Override
            public void onclick(int position, int type) {
                Bundle b=new Bundle();
                b.putParcelable("user",users.get(position));
                ChatFragment c=new ChatFragment();
                c.setArguments(b);
                Common.type=1;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.pharma, c).addToBackStack(null).commit();
            }
        });
    }
}