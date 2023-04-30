package com.example.dose;

import android.content.Context;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SweetDialog {
    public static SweetAlertDialog success(Context context,String title)
    {
        SweetAlertDialog success = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        success.getProgressHelper().setBarColor(Color.parseColor("#1f8d28"));
        success.setTitleText(title);
        success.setConfirmText("ok");
        success.setCancelable(false);
        return success;
    }
    public static SweetAlertDialog failed(Context context, String title)
    {
        SweetAlertDialog failed = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        failed.getProgressHelper().setBarColor(Color.parseColor("#1f8d28"));
        failed.setTitleText(title);
        failed.setConfirmText("ok");
        failed.setCancelable(false);
        return failed;
    }
    public static SweetAlertDialog loading(Context context)
    {
        SweetAlertDialog success = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        success.getProgressHelper().setBarColor(Color.parseColor("#1f8d28"));
        success.setCancelable(false);
        return success;
    }
    public static SweetAlertDialog warning(Context context, String title)
    {
        SweetAlertDialog warning = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        warning.getProgressHelper().setBarColor(Color.parseColor("#1f8d28"));
        warning.setTitleText(title);
        warning.setConfirmText("ok");
        warning.setCancelText("No");
        warning.setCancelable(false);
        return warning;
    }
    public static SweetAlertDialog choose(Context context, String title)
    {
        SweetAlertDialog choose = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        choose.getProgressHelper().setBarColor(Color.parseColor("#1f8d28"));
        choose.setTitleText(title);
        choose.setConfirmText("ok");
        choose.setCancelText("No");
        choose.setCancelable(false);
        return choose;
    }

}
