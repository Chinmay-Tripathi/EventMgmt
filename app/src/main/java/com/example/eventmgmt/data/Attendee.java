package com.example.eventmgmt.data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "attendees",
        foreignKeys = @ForeignKey(entity = Event.class,
                                parentColumns = "id",
                                childColumns = "eventId",
                                onDelete = ForeignKey.CASCADE))
public class Attendee {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long eventId;
    private String name;
    private String email;
    private String phoneNumber;

    public Attendee(long eventId, String name, String email, String phoneNumber) {
        this.eventId = eventId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
} 