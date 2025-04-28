package com.example.eventmgmt;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eventmgmt.data.Attendee;
import com.example.eventmgmt.ui.AttendeeAdapter;
import com.example.eventmgmt.ui.EventViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ManageAttendeesActivity extends AppCompatActivity implements AttendeeAdapter.OnAttendeeActionListener {
    private EventViewModel eventViewModel;
    private AttendeeAdapter adapter;
    private long eventId;
    private TextInputEditText editAttendeeName;
    private TextInputEditText editAttendeeEmail;
    private TextInputEditText editAttendeePhone;
    private MaterialButton buttonAddAttendee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_attendees);

        eventId = getIntent().getLongExtra("event_id", -1);
        if (eventId == -1) {
            Toast.makeText(this, "Invalid event ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        initializeViews();
        setupRecyclerView();
        setupClickListeners();
        observeAttendees();
    }

    private void initializeViews() {
        editAttendeeName = findViewById(R.id.editAttendeeName);
        editAttendeeEmail = findViewById(R.id.editAttendeeEmail);
        editAttendeePhone = findViewById(R.id.editAttendeePhone);
        buttonAddAttendee = findViewById(R.id.buttonAddAttendee);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewAttendees);
        adapter = new AttendeeAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupClickListeners() {
        buttonAddAttendee.setOnClickListener(v -> addAttendee());
    }

    private void observeAttendees() {
        eventViewModel.getAttendeesForEvent(eventId).observe(this, attendees -> {
            adapter.setAttendees(attendees);
        });
    }

    private void addAttendee() {
        String name = editAttendeeName.getText().toString().trim();
        String email = editAttendeeEmail.getText().toString().trim();
        String phone = editAttendeePhone.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Attendee attendee = new Attendee(eventId, name, email, phone);
        eventViewModel.insertAttendee(attendee);

        // Clear input fields
        editAttendeeName.setText("");
        editAttendeeEmail.setText("");
        editAttendeePhone.setText("");
    }

    @Override
    public void onEditAttendee(Attendee attendee) {
        // TODO: Implement edit functionality
        Toast.makeText(this, "Edit functionality coming soon", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteAttendee(Attendee attendee) {
        eventViewModel.deleteAttendee(attendee);
    }
} 