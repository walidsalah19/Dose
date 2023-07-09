package com.doseApp.dose.dose.User;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doseApp.dose.dose.Models.Treatment;
import com.doseApp.dose.dose.R;
import com.doseApp.dose.dose.SweetDialog;
import com.doseApp.dose.dose.databinding.FragmentTreatmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TreatmentFragment extends Fragment {
    private FragmentTreatmentBinding mBinding;
    private SweetAlertDialog loading;
    private String userId;
    private ArrayList<Treatment> treatments;
    private DatabaseReference database;
    private String height,weight="1",age="18",diabetic="",pressure="",penicillinAllergy="",type="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentTreatmentBinding.inflate(inflater,container,false);
        treatments=new ArrayList<>();
        initFirebase();
        startLoading();
        getHealthRecords();
        getTreatment();
        checkTreatment();
        back();
        return mBinding.getRoot();
    }
    private void initFirebase()
    {
        database= FirebaseDatabase.getInstance().getReference();
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
    }
    private void startLoading()
    {
        loading= SweetDialog.loading(getActivity());
        loading.show();
    }
    private void getTreatment()
    {
        database.child("treatment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for (DataSnapshot data: snapshot.getChildren()) {
                        String treatmentName = data.child("treatmentName").getValue().toString();
                        String age = data.child("age").getValue().toString();
                        String diabetic = data.child("diabetic").getValue().toString();
                        String pressure = data.child("pressure").getValue().toString();
                        String penicillinAllergy = data.child("penicillinAllergy").getValue().toString();
                        String type = data.child("type").getValue().toString();
                        String dose = data.child("dose").getValue().toString();
                        treatments.add(new Treatment(treatmentName,age,diabetic,pressure,penicillinAllergy,type,dose));
                    }
                }
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getHealthRecords()
    {
        database.child("healthRecords").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    height = snapshot.child("height").getValue().toString();
                    weight = snapshot.child("weight").getValue().toString();
                    age = snapshot.child("age").getValue().toString();
                    diabetic = snapshot.child("diabetic").getValue().toString();
                    pressure = snapshot.child("pressure").getValue().toString();
                    penicillinAllergy = snapshot.child("penicillinAllergy").getValue().toString();
                }
                else {
                        funFailed("you need to add your health record");
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.userFrameLayout, new UserHome()).commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void funFailed(String title)
    {
        SweetAlertDialog fail=SweetDialog.failed(getContext(),title);
        fail.show();
        fail.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                fail.dismiss();
            }
        });
    }
    private void checkTreatment()
    {
        mBinding.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tName=mBinding.treatment.getText().toString();
                if (TextUtils.isEmpty(tName))
                {
                    mBinding.treatment.setError("please enter the treatment name");
                }
                else {
                    checkUserHealth(tName);
                }
            }
        });
    }

    private void checkUserHealth(String tName) {
        Treatment t = null;
        String tType=mBinding.type.getSelectedItem().toString();
        for (Treatment tr:treatments)
        {
            if (tr.getTreatmentName().toLowerCase().equals(tName.toLowerCase()))
            {
                t=tr;
            }
        }

        if (t==null)
        {
            mBinding.treatment.setError("please enter correct treatment name");
        }
        else if (Integer.parseInt(age)<18 && tType.equals("pills"))
        {
            mBinding.result.setTextColor(getResources().getColor(R.color.red));
            mBinding.result.setText("Wrong because his age does not allow him to take pills, he must take drink");
        }
        else if (Integer.parseInt(age)>=18 && tType.equals("drink"))
        {
            mBinding.result.setTextColor(getResources().getColor(R.color.red));
            mBinding.result.setText("This adult should take pills");
        }
        else if (t.getDiabetic().toLowerCase().equals("yes") && diabetic.toLowerCase().equals("yes"))
        {
            mBinding.result.setTextColor(getResources().getColor(R.color.red));
            mBinding.result.setText("Bisoprolol should not be used if the patient has diabetes.\n");
        }
        else if (t.getPressure().toLowerCase().equals("yes") && pressure.toLowerCase().equals("yes"))
        {
            mBinding.result.setTextColor(getResources().getColor(R.color.red));
            mBinding.result.setText("Ibuprofen should not be used as an analgesic in patients with high blood pressure.\n");
        }
        else if (t.getPenicillinAllergy().toLowerCase().equals("yes")&&penicillinAllergy.toLowerCase().equals("yes"))
        {
            mBinding.result.setTextColor(getResources().getColor(R.color.red));
            mBinding.result.setText("Augmentin should not be used if the patient is allergic to penicillin.\n");
        }
        else
        {
            mBinding.result.setTextColor(getResources().getColor(R.color.green));
            if(t.getTreatmentName().toLowerCase().equals("ibuprofen"))
            {
                calculateIbuprofen();
            }
            else if (t.getTreatmentName().toLowerCase().equals("fevadol"))
            {
                calculateFevadol();
            }
            else if (t.getTreatmentName().toLowerCase().equals("concor"))
            {
                mBinding.result.setText("5 mg orally once a day /\n" +
                        "5 mg مره واحدة عن طريق الفم");
            }
            else if (t.getTreatmentName().toLowerCase().equals("brufen"))
            {
                mBinding.result.setText("200-400 mg orally every 4-6 hours / \n" +
                        "200-400 mg كل ٤-٦ ساعات عن طريق الفم");
            }
            else if (t.getTreatmentName().toLowerCase().equals("augmentin"))
            {
                mBinding.result.setText("875/125 mg orally every 12 hours  875/125 mg\n" +
                        "كل ١٢ ساعة عن طريق الفم");
            }
        }
    }

    private void calculateFevadol() {
        if (Integer.parseInt(age)>=18)
        {
            mBinding.result.setText("650 mg every 6h max 3250 mg daily ");
        }
        else {
            int num=10*Integer.parseInt(weight);
            int dose=num/4;
            dose=dose/4;
            mBinding.result.setText(dose+" mg every 6h max 1625 mg daily ");
        }
    }

    private void calculateIbuprofen() {
        if (Integer.parseInt(age)>=18)
        {
            mBinding.result.setText("200 to 400 mg every 6h max 1200 mg daily ");
        }
        else {
            int num=5*Integer.parseInt(weight);
            int dose=num/3;
            dose=dose/4;
            mBinding.result.setText(dose+" mg every 6h ");
        }
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