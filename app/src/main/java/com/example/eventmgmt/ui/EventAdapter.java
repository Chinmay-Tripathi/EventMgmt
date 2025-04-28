package com.example.eventmgmt.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eventmgmt.R;
import com.example.eventmgmt.data.Event;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private List<Event> events;
    private final OnEventClickListener listener;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());

    public interface OnEventClickListener {
        void onEventClick(Event event);
    }

    public EventAdapter(OnEventClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);
        holder.bind(event);
    }

    @Override
    public int getItemCount() {
        return events == null ? 0 : events.size();
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        private final TextView textEventName;
        private final TextView textEventDateTime;
        private final TextView textEventLocation;
        private final TextView textEventDescription;

        EventViewHolder(View itemView) {
            super(itemView);
            textEventName = itemView.findViewById(R.id.textEventName);
            textEventDateTime = itemView.findViewById(R.id.textEventDateTime);
            textEventLocation = itemView.findViewById(R.id.textEventLocation);
            textEventDescription = itemView.findViewById(R.id.textEventDescription);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onEventClick(events.get(position));
                }
            });
        }

        void bind(Event event) {
            textEventName.setText(event.getName());
            textEventDateTime.setText(dateFormat.format(event.getDateTime()));
            textEventLocation.setText(event.getLocation());
            textEventDescription.setText(event.getDescription());
        }
    }
} 