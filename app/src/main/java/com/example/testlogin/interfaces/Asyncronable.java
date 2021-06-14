package com.example.testlogin.interfaces;

public interface Asyncronable<T> {

    void showProgress(String msg);
    void hideProgress();
    void afterRequest(T response);

}
