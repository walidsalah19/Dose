package com.doseApp.dose.dose.Models;

public class Treatment {
    private String treatmentName,age,diabetic,pressure,penicillinAllergy,type,dose;

    public Treatment(String treatmentName, String age, String diabetic, String pressure, String penicillinAllergy, String type, String dose) {
        this.treatmentName = treatmentName;
        this.age = age;
        this.diabetic = diabetic;
        this.pressure = pressure;
        this.penicillinAllergy = penicillinAllergy;
        this.type = type;
        this.dose = dose;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }
}
