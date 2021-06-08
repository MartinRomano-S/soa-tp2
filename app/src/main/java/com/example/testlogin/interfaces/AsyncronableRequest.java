package com.example.testlogin.interfaces;

import org.json.JSONObject;

public interface AsyncronableRequest {
    void toggleProgressBar(boolean status);
    void afterRequest(JSONObject response);
}
