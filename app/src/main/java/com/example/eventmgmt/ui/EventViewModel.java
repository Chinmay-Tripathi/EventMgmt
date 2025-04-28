package com.example.eventmgmt.ui;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.eventmgmt.data.Event;
import com.example.eventmgmt.data.EventRepository;
import com.example.eventmgmt.data.Attendee;
import java.util.List;

public class EventViewModel extends AndroidViewModel {
    private final EventRepository repository;
    private final LiveData<List<Event>> allEvents;

    public EventViewModel(Application application) {
        super(application);
        repository = new EventRepository(application);
        allEvents = repository.getAllEvents();
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    public LiveData<Event> getEventById(long eventId) {
        return repository.getEventById(eventId);
    }

    public void insertEvent(Event event) {
        repository.insertEvent(event);
    }

    public void updateEvent(Event event) {
        repository.updateEvent(event);
    }

    public void deleteEvent(Event event) {
        repository.deleteEvent(event);
    }

    public LiveData<List<Attendee>> getAttendeesForEvent(long eventId) {
        return repository.getAttendeesForEvent(eventId);
    }

    public void insertAttendee(Attendee attendee) {
        repository.insertAttendee(attendee);
    }

    public void updateAttendee(Attendee attendee) {
        repository.updateAttendee(attendee);
    }

    public void deleteAttendee(Attendee attendee) {
        repository.deleteAttendee(attendee);
    }
} 