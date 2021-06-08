package com.example.testlogin.interfaces;

import org.json.JSONObject;

public interface Asyncronable<T> {

    void showProgress(String msg);
    void hideProgress();
    void afterRequest(T response);

}