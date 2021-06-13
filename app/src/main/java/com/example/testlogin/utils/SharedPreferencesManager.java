package com.example.testlogin.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.testlogin.R;
import com.example.testlogin.models.EmergencyContact;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * SharedPreferencesManager
 *
 * La manejamos como un Singleton de acceso a las preferences con métodos sincronizados
 * para evitar fallos al guardar
 */
public class SharedPreferencesManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPrefEditor;
    private static SharedPreferencesManager singletonInstance;

    private static final String SP_NAMESPACE = "FasTEST-SharedPreferences";
    private static final String SP_DEFAULT_VALUE = "";
    private static final String SP_VERIFICATION_CODE = "currentVerificationCode";
    private static final String SP_EMERGENCY_CONTACT_LIST = "emergencyContacts";
    private static final String SP_LAST_LOGIN_DATE = "lastLoginDate";

    @SuppressLint("CommitPrefEdits")
    private SharedPreferencesManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SP_NAMESPACE, Context.MODE_PRIVATE);
        this.sharedPrefEditor = sharedPreferences.edit();
    }

    public static SharedPreferencesManager getInstance(Context context) {
        if (singletonInstance == null)
            singletonInstance = new SharedPreferencesManager(context);

        return singletonInstance;
    }

    public synchronized void saveCurrentVerificationCode(String currentVerificationCode) {
        sharedPrefEditor.putString(SP_VERIFICATION_CODE, currentVerificationCode);
        sharedPrefEditor.apply();
    }

    public String getCurrentVerificationCode() {
        return sharedPreferences.getString(SP_VERIFICATION_CODE, SP_DEFAULT_VALUE);
    }

    public synchronized void saveEmergencyContactList(List<EmergencyContact> emergencyContactList) {

        JSONArray jsonArray = new JSONArray();

        for(EmergencyContact ec : emergencyContactList)
            jsonArray.put(ec.toJSON());

        sharedPrefEditor.putString(SP_EMERGENCY_CONTACT_LIST, jsonArray.toString());
        sharedPrefEditor.apply();
    }

    public List<EmergencyContact> getEmergencyContactList() throws JSONException {
        JSONArray jsonArray = new JSONArray(sharedPreferences.getString(SP_EMERGENCY_CONTACT_LIST, new JSONArray().toString()));
        List<EmergencyContact> list = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++) {
            EmergencyContact ec = new EmergencyContact();
            ec.getFromJSON(jsonArray.getJSONObject(i));
            list.add(ec);
        }

        return list;
    }

    public void sendMessageToEmergencyContactList(Context context) throws JSONException{
        JSONArray jsonArray = new JSONArray(sharedPreferences.getString(SP_EMERGENCY_CONTACT_LIST, new JSONArray().toString()));
        Toast.makeText(context, "Contacts: "+jsonArray.length(), Toast.LENGTH_SHORT).show();

        for(int i = 0; i < jsonArray.length(); i++) {
            EmergencyContact ec = new EmergencyContact();
            ec.getFromJSON(jsonArray.getJSONObject(i));
            Toast.makeText(context, "Nombre: "+ec.getName()+" Tel: "+ec.getPhoneNumber(), Toast.LENGTH_SHORT).show();
        }
    }

    public synchronized void saveLastLoginDate(long dateInMillis) {
        sharedPrefEditor.putLong(SP_LAST_LOGIN_DATE, dateInMillis);
        sharedPrefEditor.apply();
    }

    public void delete() {
        sharedPrefEditor.clear();
        sharedPrefEditor.apply();
    }
}
