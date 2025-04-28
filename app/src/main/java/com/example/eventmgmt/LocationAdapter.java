package com.example.eventmgmt;

import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eventmgmt.R;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    private final List<Address> locations;
    private final OnLocationClickListener listener;

    public interface OnLocationClickListener {
        void onLocationClick(Address address);
    }

    public LocationAdapter(List<Address> locations, OnLocationClickListener listener) {
        this.locations = locations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Address address = locations.get(position);
        holder.bind(address);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {
        private final TextView textLocationName;
        private final TextView textLocationAddress;

        LocationViewHolder(View itemView) {
            super(itemView);
            textLocationName = itemView.findViewById(R.id.textLocationName);
            textLocationAddress = itemView.findViewById(R.id.textLocationAddress);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onLocationClick(locations.get(position));
                }
            });
        }

        void bind(Address address) {
            String locationName = address.getFeatureName();
            if (locationName == null || locationName.isEmpty()) {
                locationName = address.getLocality();
            }
            textLocationName.setText(locationName);
            textLocationAddress.setText(address.getAddressLine(0));
        }
    }
} 