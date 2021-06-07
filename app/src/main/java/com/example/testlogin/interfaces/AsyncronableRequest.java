package com.example.testlogin.interfaces;

import org.json.JSONObject;

public interface AsyncronableRequest {
    void toggleProgressBar(boolean status);
    void showResponseMessage(JSONObject msg);
    void afterRequest(JSONObject msg);
   // void executeActivity(Class<?> activity);
    //void showMessage(String msg);
}
