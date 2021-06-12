package com.example.testlogin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testlogin.adapters.EmergencyContactsListAdapter;
import com.example.testlogin.adapters.EventsListAdapter;
import com.example.testlogin.models.EmergencyContact;
import com.example.testlogin.models.Event;
import com.example.testlogin.utils.Configuration;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class EventsActivity extends AppCompatActivity {

    RecyclerView listEvents;
    List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        listEvents = findViewById(R.id.listEvents);

        listEvents.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(getDrawable(R.drawable.divider)));
        listEvents.addItemDecoration(divider);
        events = new ArrayList<>();

        events.add(new Event(new Date(), "Shake", "Desc 1"));
        events.add(new Event(new Date(), "Shake", "Desc 2"));
        events.add(new Event(new Date(), "Shake", "Desc 3"));
        events.add(new Event(new Date(), "Shake", "Desc 4"));
        events.add(new Event(new Date(), "Shake", "Desc 5"));
        //Configuration.removeEmergencyContactList(this);



        EventsListAdapter evAdapter = new EventsListAdapter(events);
        listEvents.setAdapter(evAdapter);
    }
}
