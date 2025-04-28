package com.example.eventmgmt;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapSelectionActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private TextInputEditText editSearchLocation;
    private RecyclerView recyclerViewLocations;
    private MaterialButton buttonUseCurrentLocation;
    private LocationAdapter locationAdapter;
    private List<Address> searchResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_selection);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        initializeViews();
        setupRecyclerView();
        setupClickListeners();
    }

    private void initializeViews() {
        editSearchLocation = findViewById(R.id.editSearchLocation);
        recyclerViewLocations = findViewById(R.id.recyclerViewLocations);
        buttonUseCurrentLocation = findViewById(R.id.buttonUseCurrentLocation);

        editSearchLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchLocations(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupRecyclerView() {
        locationAdapter = new LocationAdapter(searchResults, this::onLocationSelected);
        recyclerViewLocations.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLocations.setAdapter(locationAdapter);
    }

    private void setupClickListeners() {
        buttonUseCurrentLocation.setOnClickListener(v -> getCurrentLocation());
    }

    private void searchLocations(String query) {
        if (query.isEmpty()) {
            searchResults.clear();
            locationAdapter.notifyDataSetChanged();
            return;
        }

        new Thread(() -> {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocationName(query, 5);
                
                runOnUiThread(() -> {
                    searchResults.clear();
                    if (addresses != null) {
                        searchResults.addAll(addresses);
                    }
                    locationAdapter.notifyDataSetChanged();
                });
            } catch (IOException e) {
                runOnUiThread(() -> 
                    Toast.makeText(this, "Error searching locations", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        getAddressFromLocation(location);
                    } else {
                        Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getAddressFromLocation(Location location) {
        new Thread(() -> {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(
                        location.getLatitude(),
                        location.getLongitude(),
                        1);

                if (addresses != null && !addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    runOnUiThread(() -> returnLocationResult(address));
                }
            } catch (IOException e) {
                runOnUiThread(() -> 
                    Toast.makeText(this, "Error getting address", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void onLocationSelected(Address address) {
        returnLocationResult(address);
    }

    private void returnLocationResult(Address address) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("latitude", address.getLatitude());
        resultIntent.putExtra("longitude", address.getLongitude());
        resultIntent.putExtra("location_name", address.getAddressLine(0));
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
} 