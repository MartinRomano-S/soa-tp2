package com.example.testlogin.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;

import androidx.core.content.ContextCompat;

import com.example.testlogin.R;

import org.json.JSONException;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Configuration {

    public static final String API_ENVIRONMENT = "TEST";
    public static final int COMMISSION = 3900;
    public static final int GROUP = 4;

    public static final String VERIFICATION_EMAIL = "fastestappsoa@gmail.com";
    public static final String VERIFICATION_PASSWORD = "prueba123";

    public static final String API_BASE_URL = "http://so-unlam.net.ar/api/api/";
    public static final int REQUEST_READ_TIMEOUT = 10000;
    public static final int REQUEST_CONNECTION_TIMEOUT = 20000;
    public static final int MINIMUM_PASSWORD_LENGTH = 8;
    public static final int MAX_DNI_LENGTH = 8;

    public static boolean checkPermission(Context c, String permission) {
        int check = ContextCompat.checkSelfPermission(c, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean isNetworkConnected(Context c) {
        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null) {
            Network network = connectivityManager.getActiveNetwork();

            if (network == null) return false;

            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
            return networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        }
        return false;
    }

    public static void showModalMessage(Context c, String title, String msg) {
        AlertDialog.Builder dialog;
        dialog = new AlertDialog.Builder(c);
        dialog.setTitle(title);
        dialog.setMessage(msg);

        dialog.setPositiveButton(c.getString(R.string.acceptButton), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });

        dialog.create().show();
    }

    public static String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(30, random).toString(32);
    }

    public static void setCurrentVerificationCode(Activity activity, String currentVerificationCode) {
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putString(activity.getString(R.string.currentVerificationCode), currentVerificationCode);
        sharedPrefEditor.apply();
    }

    public static String getCurrentVerificationCode(Activity activity) {
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getString(activity.getString(R.string.currentVerificationCode), "");
    }
}
