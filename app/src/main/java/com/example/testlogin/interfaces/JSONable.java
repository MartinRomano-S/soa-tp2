package com.example.testlogin.interfaces;

import org.json.JSONObject;

public interface JSONable {
    JSONObject toJSON();
    void getFromJSON(JSONObject jsonObject);
}
