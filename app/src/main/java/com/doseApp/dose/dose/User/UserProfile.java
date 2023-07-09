package com.doseApp.dose.dose.User;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.doseApp.dose.dose.Models.User;
import com.doseApp.dose.dose.databinding.FragmentUserProfileBinding;
import com.doseApp.dose.dose.SweetDialog;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UserProfile extends Fragment {

    private FragmentUserProfileBinding mBinding;
    private String userId,name,email,type,image="";
    private DatabaseReference database;
    private SweetAlertDialog loading;
    private UploadTask uploadTask;
    private StorageReference storageReference;
    public static final int PICK_IMAGE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentUserProfileBinding.inflate(inflater,container,false);
        startLoading();
        initFirebase();
        getUserData();
        selectImage();
        updateUserData();
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
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
    }
    private void getUserData()
    {
        database.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 name=snapshot.child("userName").getValue().toString();
                 email=snapshot.child("email").getValue().toString();
                 type=snapshot.child("type").getValue().toString();
                 if (snapshot.child("image").exists()) {
                     image = snapshot.child("image").getValue().toString();
                 }
                 loading.dismiss();
                 addDataView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void addDataView()
    {
        mBinding.userName.setText(name);
        mBinding.email.setText(email);
        if (! image.equals(""))
        {
            Glide.with(this).load(image).into(mBinding.profile);
        }
    }
    private void selectImage()
    {
        mBinding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&& resultCode== Activity.RESULT_OK)
        {
            if (data !=null)
            {
                Glide.with(this).load(data.getData().toString()).into(mBinding.profile);
                startLoading();
                saveImage(data.getData());
            }
        }
    }
    private void saveImage(Uri data) {
        storageReference= FirebaseStorage.getInstance().getReference("images");
        StorageReference reference=storageReference.child(UUID.randomUUID().toString());
        uploadTask= reference.putFile(data);

        Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful())
                {
                    throw task.getException();
                }
                return reference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri=task.getResult();
                    image=downloadUri.toString();
                    loading.dismiss();
                }
            }
        });

    }
    private void updateUserData()
    {
        mBinding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u =new User(mBinding.userName.getText().toString(),email,userId,type,image);
                addToDatabase(u);
            }
        });
    }
    private void addToDatabase(User p)
    {
        database.child(p.getId()).setValue(p).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    successSweetDialog();
                }
            }
        });
    }
    private void successSweetDialog()
    {
        SweetAlertDialog success = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
        success.getProgressHelper().setBarColor(Color.parseColor("#1f8d28"));
        success.setTitleText("update profile successfully ");
        success.show();
    }

}