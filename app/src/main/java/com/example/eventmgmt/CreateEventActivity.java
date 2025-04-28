package com.example.eventmgmt;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.eventmgmt.data.Event;
import com.example.eventmgmt.ui.EventViewModel;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;
import java.util.Date;

public class CreateEventActivity extends AppCompatActivity {
    private EventViewModel eventViewModel;
    private TextInputEditText editEventName;
    private TextInputEditText editEventDescription;
    private TextInputEditText editEventLocation;
    private Button buttonSelectDateTime;
    private Button buttonSelectLocation;
    private Button buttonAddImage;
    private ImageView imageEvent;
    private Button buttonSaveEvent;
    private Date selectedDateTime;
    private Double selectedLatitude;
    private Double selectedLongitude;
    private String imagePath;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MAP_SELECTION_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        editEventName = findViewById(R.id.editEventName);
        editEventDescription = findViewById(R.id.editEventDescription);
        editEventLocation = findViewById(R.id.editEventLocation);
        buttonSelectDateTime = findViewById(R.id.buttonSelectDateTime);
        buttonSelectLocation = findViewById(R.id.buttonSelectLocation);
        buttonAddImage = findViewById(R.id.buttonAddImage);
        imageEvent = findViewById(R.id.imageEvent);
        buttonSaveEvent = findViewById(R.id.buttonSaveEvent);
    }

    private void setupClickListeners() {
        buttonSelectDateTime.setOnClickListener(v -> showDateTimePicker());
        buttonSelectLocation.setOnClickListener(v -> openMapForLocationSelection());
        buttonAddImage.setOnClickListener(v -> openImagePicker());
        buttonSaveEvent.setOnClickListener(v -> saveEvent());
    }

    private void showDateTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    calendar.set(year1, month1, dayOfMonth);
                    showTimePicker(calendar);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePicker(Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute1) -> {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute1);
                    selectedDateTime = calendar.getTime();
                    buttonSelectDateTime.setText(selectedDateTime.toString());
                }, hour, minute, false);
        timePickerDialog.show();
    }

    private void openMapForLocationSelection() {
        Intent intent = new Intent(this, MapSelectionActivity.class);
        startActivityForResult(intent, MAP_SELECTION_REQUEST);
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            imagePath = imageUri.toString();
            imageEvent.setImageURI(imageUri);
            imageEvent.setVisibility(View.VISIBLE);
        } else if (requestCode == MAP_SELECTION_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedLatitude = data.getDoubleExtra("latitude", 0);
            selectedLongitude = data.getDoubleExtra("longitude", 0);
            String locationName = data.getStringExtra("location_name");
            editEventLocation.setText(locationName);
        }
    }

    private void saveEvent() {
        String name = editEventName.getText().toString().trim();
        String description = editEventDescription.getText().toString().trim();
        String location = editEventLocation.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty() || location.isEmpty() || selectedDateTime == null) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Event event = new Event(name, description, selectedDateTime, location,
                selectedLatitude, selectedLongitude, imagePath);
        eventViewModel.insertEvent(event);
        finish();
    }
} 