package com.example.eventmgmt.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eventmgmt.R;
import com.example.eventmgmt.data.Attendee;
import com.google.android.material.button.MaterialButton;
import java.util.List;

public class AttendeeAdapter extends RecyclerView.Adapter<AttendeeAdapter.AttendeeViewHolder> {
    private List<Attendee> attendees;
    private final OnAttendeeActionListener listener;

    public interface OnAttendeeActionListener {
        void onEditAttendee(Attendee attendee);
        void onDeleteAttendee(Attendee attendee);
    }

    public AttendeeAdapter(OnAttendeeActionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AttendeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attendee, parent, false);
        return new AttendeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendeeViewHolder holder, int position) {
        Attendee attendee = attendees.get(position);
        holder.bind(attendee);
    }

    @Override
    public int getItemCount() {
        return attendees == null ? 0 : attendees.size();
    }

    public void setAttendees(List<Attendee> attendees) {
        this.attendees = attendees;
        notifyDataSetChanged();
    }

    class AttendeeViewHolder extends RecyclerView.ViewHolder {
        private final TextView textAttendeeName;
        private final TextView textAttendeeEmail;
        private final TextView textAttendeePhone;
        private final MaterialButton buttonEditAttendee;
        private final MaterialButton buttonDeleteAttendee;

        AttendeeViewHolder(View itemView) {
            super(itemView);
            textAttendeeName = itemView.findViewById(R.id.textAttendeeName);
            textAttendeeEmail = itemView.findViewById(R.id.textAttendeeEmail);
            textAttendeePhone = itemView.findViewById(R.id.textAttendeePhone);
            buttonEditAttendee = itemView.findViewById(R.id.buttonEditAttendee);
            buttonDeleteAttendee = itemView.findViewById(R.id.buttonDeleteAttendee);
        }

        void bind(Attendee attendee) {
            textAttendeeName.setText(attendee.getName());
            textAttendeeEmail.setText(attendee.getEmail());
            textAttendeePhone.setText(attendee.getPhoneNumber());

            buttonEditAttendee.setOnClickListener(v -> listener.onEditAttendee(attendee));
            buttonDeleteAttendee.setOnClickListener(v -> listener.onDeleteAttendee(attendee));
        }
    }
} 