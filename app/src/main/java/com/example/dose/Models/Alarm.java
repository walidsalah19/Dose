package com.example.dose.Models;

import java.util.ArrayList;

public class Alarm {
    private String name,hours,minute,status,alarmId,type;
    private ArrayList<String> repeat;

    public Alarm(String name, String hours, String minute, String status, String alarmId, String type, ArrayList<String> repeat) {
        this.name = name;
        this.hours = hours;
        this.minute = minute;
        this.status = status;
        this.alarmId = alarmId;
        this.type = type;
        this.repeat = repeat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getRepeat() {
        return repeat;
    }

    public void setRepeat(ArrayList<String> repeat) {
        this.repeat = repeat;
    }
}
