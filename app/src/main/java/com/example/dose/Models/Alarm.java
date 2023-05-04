package com.example.dose.Models;

import java.util.ArrayList;

public class Alarm {
    private String name,time,status,alarmId;
    private ArrayList<String> repeat;

    public Alarm(String name, String time, String status, String alarmId, ArrayList<String> repeat) {
        this.name = name;
        this.time = time;
        this.status = status;
        this.alarmId = alarmId;
        this.repeat = repeat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public ArrayList<String> getRepeat() {
        return repeat;
    }

    public void setRepeat(ArrayList<String> repeat) {
        this.repeat = repeat;
    }
}
