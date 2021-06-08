package com.example.testlogin.models;

public class Token {

    private String activeToken;
    private String refreshToken;

    public Token() {}

    public Token(String activeToken, String refreshToken) {
        this.activeToken = activeToken;
        this.refreshToken = refreshToken;
    }

    public String getActiveToken() {
        return activeToken;
    }

    public void setActiveToken(String activeToken) {
        this.activeToken = activeToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
