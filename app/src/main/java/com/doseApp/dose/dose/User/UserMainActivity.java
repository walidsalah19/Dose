package com.doseApp.dose.dose.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.doseApp.dose.dose.R;
import com.doseApp.dose.dose.SendNotificationPack.APIService;
import com.doseApp.dose.dose.databinding.ActivityUserMainBinding;
import com.doseApp.dose.dose.SendNotificationPack.Client;
import com.doseApp.dose.dose.UserAccess.Login;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class UserMainActivity extends AppCompatActivity {
    private ActivityUserMainBinding mBinding;
    private APIService apiService;
    private String userToken;
    private Map<String ,String> profile=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=ActivityUserMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        getSupportFragmentManager().beginTransaction().replace(R.id.userFrameLayout, new UserHome()).commit();

        bottomNavigationAction();
        updateToken();

    }
    private void updateToken()
    {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    // Get new FCM registration token
                    userToken = task.getResult();
                    System.out.println(userToken);
                    profile.put("token",userToken);
                    //database.collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(profile);
                     FirebaseDatabase.getInstance().getReference("tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("token").setValue(userToken);
                });
    }
    private void bottomNavigationAction() {
        mBinding.doctorNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.logout) {
                    startActivity(new Intent(UserMainActivity.this, Login.class));
                } else if (item.getItemId() == R.id.home)
                    moveToFragment(new UserHome());
                else if (item.getItemId() == R.id.profile)
                    moveToFragment(new UserProfile());
                return false;
            }
        });

    }

    private void moveToFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.userFrameLayout, fragment).addToBackStack(null).commit();
    }
}