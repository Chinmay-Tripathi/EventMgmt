package com.example.eventmgmt.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface AttendeeDao {
    @Insert
    long insert(Attendee attendee);

    @Update
    void update(Attendee attendee);

    @Delete
    void delete(Attendee attendee);

    @Query("SELECT * FROM attendees WHERE eventId = :eventId")
    LiveData<List<Attendee>> getAttendeesForEvent(long eventId);

    @Query("SELECT * FROM attendees WHERE id = :attendeeId")
    LiveData<Attendee> getAttendeeById(long attendeeId);
} 