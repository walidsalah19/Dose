package com.doseApp.dose.dose.Models;

public class HealthRecords {
    private String height,weight,age,diabetic,pressure,penicillinAllergy,UserId;

    public HealthRecords(String height, String weight, String age, String diabetic, String pressure, String penicillinAllergy, String userId) {
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.diabetic = diabetic;
        this.pressure = pressure;
        this.penicillinAllergy = penicillinAllergy;
        UserId = userId;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDiabetic() {
        return diabetic;
    }

    public void setDiabetic(String diabetic) {
        this.diabetic = diabetic;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getPenicillinAllergy() {
        return penicillinAllergy;
    }

    public void setPenicillinAllergy(String penicillinAllergy) {
        this.penicillinAllergy = penicillinAllergy;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
