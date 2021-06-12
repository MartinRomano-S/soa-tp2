package com.example.testlogin.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class PhoneCaller {
    public static void makePhoneCall(Context context, String number){
        Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        context.startActivity(call);
    }
}
