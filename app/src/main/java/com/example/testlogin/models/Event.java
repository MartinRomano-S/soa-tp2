package com.example.testlogin.models;

import java.util.Date;

public class Event {
    private Date eventDate;
    private String type;
    private String description;

    public Event(){}

    public Event(Date eventDate, String type, String description) {
        this.eventDate = eventDate;
        this.type = type;
        this.description = description;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
