package com.example.eventmgmt.data;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventRepository {
    private final EventDao eventDao;
    private final AttendeeDao attendeeDao;
    private final ExecutorService executorService;

    public EventRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        eventDao = db.eventDao();
        attendeeDao = db.attendeeDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Event operations
    public LiveData<List<Event>> getAllEvents() {
        return eventDao.getAllEvents();
    }

    public LiveData<Event> getEventById(long eventId) {
        return eventDao.getEventById(eventId);
    }

    public void insertEvent(Event event) {
        executorService.execute(() -> eventDao.insert(event));
    }

    public void updateEvent(Event event) {
        executorService.execute(() -> eventDao.update(event));
    }

    public void deleteEvent(Event event) {
        executorService.execute(() -> eventDao.delete(event));
    }

    // Attendee operations
    public LiveData<List<Attendee>> getAttendeesForEvent(long eventId) {
        return attendeeDao.getAttendeesForEvent(eventId);
    }

    public void insertAttendee(Attendee attendee) {
        executorService.execute(() -> attendeeDao.insert(attendee));
    }

    public void updateAttendee(Attendee attendee) {
        executorService.execute(() -> attendeeDao.update(attendee));
    }

    public void deleteAttendee(Attendee attendee) {
        executorService.execute(() -> attendeeDao.delete(attendee));
    }
} 