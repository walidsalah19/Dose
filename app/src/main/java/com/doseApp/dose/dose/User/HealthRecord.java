package com.doseApp.dose.dose.User;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.doseApp.dose.dose.Models.HealthRecords;
import com.doseApp.dose.dose.R;
import com.doseApp.dose.dose.databinding.FragmentHealthRecordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HealthRecord extends Fragment {
    private FragmentHealthRecordBinding mBinding;
    private DatabaseReference database;
    private String userId,height,weight,age,diabetic,pressure,penicillinAllergy;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentHealthRecordBinding.inflate(inflater,container,false);
        initFirebase();
        clickAddRecord();
        getHealthRecords();
        back();
        return mBinding.getRoot();
    }
    private void initFirebase()
    {
        database= FirebaseDatabase.getInstance().getReference("healthRecords");
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
    }
    private void getHealthRecords()
    {
        database.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    height = snapshot.child("height").getValue().toString();
                    weight = snapshot.child("weight").getValue().toString();
                    age = snapshot.child("age").getValue().toString();
                    diabetic = snapshot.child("diabetic").getValue().toString();
                    pressure = snapshot.child("pressure").getValue().toString();
                    penicillinAllergy = snapshot.child("penicillinAllergy").getValue().toString();
                    addToView();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void addToView()
    {
        mBinding.weight.setText(weight);
        mBinding.height.setText(height);
        mBinding.age.setText(age);
        selectValue(mBinding.Diabetic,diabetic);
        selectValue(mBinding.onsellinSpinner,penicillinAllergy);
        selectValue(mBinding.pressureSpinner,pressure);
    }
    private void selectValue(Spinner spinner, Object value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }
    private void clickAddRecord()
    {
        mBinding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                height=mBinding.height.getText().toString();
                weight=mBinding.weight.getText().toString();
                age=mBinding.age.getText().toString();
                diabetic=mBinding.Diabetic.getSelectedItem().toString();
                pressure=mBinding.pressureSpinner.getSelectedItem().toString();
                penicillinAllergy=mBinding.onsellinSpinner.getSelectedItem().toString();
                if (TextUtils.isEmpty(height))
                {
                    mBinding.height.setError("please enter your height");
                }
                else if (TextUtils.isEmpty(weight))
                {
                    mBinding.weight.setError("please enter your weight");
                }
                else if (TextUtils.isEmpty(age))
                {
                    mBinding.age.setError("please enter your age");
                }
                else if (diabetic.equals("Diabetic"))
                {
                    Toast.makeText(getActivity(), "Click Yes if you are diabetic", Toast.LENGTH_SHORT).show();
                }
                else if (pressure.equals("Pressure"))
                {
                    Toast.makeText(getActivity(), "Click Yes if you are hypertensive", Toast.LENGTH_SHORT).show();
                }
                else if (penicillinAllergy.equals("Penicillin Allergy"))
                {
                    Toast.makeText(getActivity(), "Click Yes if you suffer from Penicillin Allergy", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    HealthRecords h=new HealthRecords(height,weight,age,diabetic,pressure,penicillinAllergy,userId);
                    addToDatabase(h);
                }

            }
        });
    }

    private void addToDatabase(HealthRecords h) {
        database.child(userId).setValue(h).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        success.setTitleText("add Health record successfully ");
        success.show();
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
}