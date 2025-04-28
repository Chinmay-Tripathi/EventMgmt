package com.example.eventmgmt;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.example.eventmgmt.data.Event;
import com.example.eventmgmt.ui.EventViewModel;
import com.google.android.material.button.MaterialButton;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EventDetailsActivity extends AppCompatActivity {
    private EventViewModel eventViewModel;
    private Event currentEvent;
    private ImageView imageEvent;
    private TextView textEventName;
    private TextView textEventDateTime;
    private TextView textEventLocation;
    private TextView textEventDescription;
    private MaterialButton buttonViewOnMap;
    private MaterialButton buttonManageAttendees;
    private MaterialButton buttonSetReminder;
    private MaterialButton buttonShareEvent;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        initializeViews();
        setupClickListeners();

        long eventId = getIntent().getLongExtra("event_id", -1);
        if (eventId != -1) {
            eventViewModel.getEventById(eventId).observe(this, this::updateUI);
        }
    }

    private void initializeViews() {
        imageEvent = findViewById(R.id.imageEvent);
        textEventName = findViewById(R.id.textEventName);
        textEventDateTime = findViewById(R.id.textEventDateTime);
        textEventLocation = findViewById(R.id.textEventLocation);
        textEventDescription = findViewById(R.id.textEventDescription);
        buttonViewOnMap = findViewById(R.id.buttonViewOnMap);
        buttonManageAttendees = findViewById(R.id.buttonManageAttendees);
        buttonSetReminder = findViewById(R.id.buttonSetReminder);
        buttonShareEvent = findViewById(R.id.buttonShareEvent);
    }

    private void setupClickListeners() {
        buttonViewOnMap.setOnClickListener(v -> viewOnMap());
        buttonManageAttendees.setOnClickListener(v -> manageAttendees());
        buttonSetReminder.setOnClickListener(v -> setReminder());
        buttonShareEvent.setOnClickListener(v -> shareEvent());
    }

    private void updateUI(Event event) {
        if (event == null) return;
        currentEvent = event;

        textEventName.setText(event.getName());
        textEventDateTime.setText(dateFormat.format(event.getDateTime()));
        textEventLocation.setText(event.getLocation());
        textEventDescription.setText(event.getDescription());

        if (event.getImagePath() != null) {
            Glide.with(this)
                    .load(Uri.parse(event.getImagePath()))
                    .into(imageEvent);
        }
    }

    private void viewOnMap() {
        if (currentEvent == null || currentEvent.getLatitude() == null || currentEvent.getLongitude() == null) {
            Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show();
            return;
        }

        String uri = String.format(Locale.ENGLISH, "geo:%f,%f?q=%f,%f(%s)",
                currentEvent.getLatitude(), currentEvent.getLongitude(),
                currentEvent.getLatitude(), currentEvent.getLongitude(),
                currentEvent.getName());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    private void manageAttendees() {
        if (currentEvent == null) return;
        Intent intent = new Intent(this, ManageAttendeesActivity.class);
        intent.putExtra("event_id", currentEvent.getId());
        startActivity(intent);
    }

    private void setReminder() {
        if (currentEvent == null) return;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentEvent.getDateTime());
        calendar.add(Calendar.HOUR, -1); // Reminder 1 hour before event

        Intent intent = new Intent(this, EventReminderReceiver.class);
        intent.putExtra("event_id", currentEvent.getId());
        intent.putExtra("event_name", currentEvent.getName());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Toast.makeText(this, "Reminder set for 1 hour before the event", Toast.LENGTH_SHORT).show();
    }

    private void shareEvent() {
        if (currentEvent == null) return;

        String shareText = String.format("%s\nDate: %s\nLocation: %s\nDescription: %s",
                currentEvent.getName(),
                dateFormat.format(currentEvent.getDateTime()),
                currentEvent.getLocation(),
                currentEvent.getDescription());

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(shareIntent, "Share Event"));
    }
} 