package com.sg.jdbctcomplexexample.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kylerudy
 */
public class Meeting {
    int id;
    String name;
    LocalDateTime time;
    Room room;
    List<Employee> attendees = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Employee> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Employee> attendees) {
        this.attendees = attendees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meeting meeting = (Meeting) o;

        if (id != meeting.id) return false;
        if (!name.equals(meeting.name)) return false;
        if (!time.equals(meeting.time)) return false;
        if (!room.equals(meeting.room)) return false;
        return attendees.equals(meeting.attendees);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + time.hashCode();
        result = 31 * result + room.hashCode();
        result = 31 * result + attendees.hashCode();
        return result;
    }
}
