package com.doseApp.dose.dose;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ViewModelData extends ViewModel {
    public MutableLiveData<ArrayList<String>> repeat=new MutableLiveData<>();
     public MutableLiveData<ArrayList<String>> getRepeat() {
        return repeat;
    }
}
