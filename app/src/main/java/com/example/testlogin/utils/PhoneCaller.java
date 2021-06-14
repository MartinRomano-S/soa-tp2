package com.example.testlogin.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.core.app.ActivityCompat;

public class PhoneCaller {
    public static void makePhoneCall(Activity activity, String number){
        Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));

        if(!Configuration.checkPermission(activity, Manifest.permission.CALL_PHONE))
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.CALL_PHONE}, 1);

        if(Configuration.checkPermission(activity, Manifest.permission.CALL_PHONE))
            activity.startActivity(call);
    }
}
