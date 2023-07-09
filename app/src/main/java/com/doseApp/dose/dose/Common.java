package com.doseApp.dose.dose;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Common {
    public static int type;
    public static ArrayList<String> repeat=new ArrayList<>();
    public static long calculateDelay(String currentTime, String time) {
        try {

            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            Date date1 = format.parse(currentTime);
            Date date2 = format.parse(time);

            return (date2.getTime() - date1.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }

    }
}
