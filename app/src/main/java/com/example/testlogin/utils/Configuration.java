package com.example.testlogin.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Configuration {

    public static final String API_ENVIRONMENT = "TEST";
    public static final int COMMISSION = 3900;
    public static final int GROUP = 4;

    public static final String API_BASE_URL = "http://so-unlam.net.ar/api/api/";
    public static final int REQUEST_READ_TIMEOUT = 10000;
    public static final int REQUEST_CONNECTION_TIMEOUT = 20000;

    public static boolean checkPermission(Context c, String permission) {
        int check = ContextCompat.checkSelfPermission(c, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    public static String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(30, random).toString(32);
    }
}
