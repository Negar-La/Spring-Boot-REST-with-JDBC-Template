package com.sg.jdbctcomplexexample.dao;

import com.sg.jdbctcomplexexample.TestApplicationConfiguration;
import com.sg.jdbctcomplexexample.entity.Employee;
import com.sg.jdbctcomplexexample.entity.Meeting;
import com.sg.jdbctcomplexexample.entity.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class MeetingDaoDBTest {

    @Autowired
    RoomDao roomDao;

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    MeetingDao meetingDao;

    @BeforeEach
    public void setUp() {
        List<Room> rooms = roomDao.getAllRooms();
        for (Room room : rooms) {
            roomDao.deleteRoomById(room.getId());
        }

        List<Employee> employees = employeeDao.getAllEmployees();
        for (Employee employee : employees) {
            employeeDao.deleteEmployeeById(employee.getId());
        }

        List<Meeting> meetings = meetingDao.getAllMeetings();
        for (Meeting meeting : meetings) {
            meetingDao.deleteMeetingById(meeting.getId());
        }
    }

    @Test
    public void testAddGetMeeting() {
        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);

        Employee employee = new Employee();
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        employee = employeeDao.addEmployee(employee);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        Meeting meeting = new Meeting();
        meeting.setName("Test Meeting");
        meeting.setTime(LocalDateTime.now().withNano(0));
        meeting.setRoom(room);
        meeting.setAttendees(employees);
        meeting = meetingDao.addMeeting(meeting);

        Meeting fromDao = meetingDao.getMeetingByid(meeting.getId());

        assertEquals(meeting, fromDao);
    }

    @Test
    public void testGetAllMeetings() {
        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);

        Employee employee = new Employee();
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        employee = employeeDao.addEmployee(employee);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        Meeting meeting = new Meeting();
        meeting.setName("Test Meeting");
        meeting.setTime(LocalDateTime.now().withNano(0));
        meeting.setRoom(room);
        meeting.setAttendees(employees);
        meeting = meetingDao.addMeeting(meeting);

        Meeting meeting2 = new Meeting();
        meeting2.setName("Test Meeting 2");
        meeting2.setTime(LocalDateTime.now().withNano(0));
        meeting2.setRoom(room);
        meeting2.setAttendees(employees);
        meeting2 = meetingDao.addMeeting(meeting2);

        List<Meeting> meetings = meetingDao.getAllMeetings();

        assertEquals(2, meetings.size());
        assertTrue(meetings.contains(meeting));
        assertTrue(meetings.contains(meeting2));
    }

    public void testUpdateMeeting() {
        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);

        Employee employee = new Employee();
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        employee = employeeDao.addEmployee(employee);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        Meeting meeting = new Meeting();
        meeting.setName("Test Meeting");
        meeting.setTime(LocalDateTime.now().withNano(0));
        meeting.setRoom(room);
        meeting.setAttendees(employees);
        meeting = meetingDao.addMeeting(meeting);

        Meeting fromDao = meetingDao.getMeetingByid(meeting.getId());

        assertEquals(meeting, fromDao);

        meeting.setName("Test Meeting 2");

        Employee employee2 = new Employee();
        employee2.setFirstName("Test First 2");
        employee2.setLastName("Test Last 2");
        employee2 = employeeDao.addEmployee(employee2);

        employees.add(employee2);

        meeting.setAttendees(employees);

        meetingDao.updateMeeting(meeting);   // We call "updateMeeting" to save it in the database.

        assertNotEquals(meeting, fromDao);   //  the local Meeting does not match the Meeting we previously retrieved from the database

        fromDao = meetingDao.getMeetingByid(meeting.getId());   // Finally, we re-retrieve the Meeting from the database

        assertEquals(meeting, fromDao);
    }

    @Test
    public void testDeleteMeeting() {
        Employee employee = new Employee();   //1- We start by creating our Employee
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        employee = employeeDao.addEmployee(employee);  //2- saving it to the database

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);    //3-  adding it to a List to use later.

        Room room = new Room();   // create our Room
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);   // and add it to the database.

        Meeting meeting = new Meeting();
        meeting.setName("Test Meeting");
        meeting.setTime(LocalDateTime.now().withNano(0));
        meeting.setRoom(room);
        meeting.setAttendees(employees);
        meeting = meetingDao.addMeeting(meeting);

        meetingDao.deleteMeetingById(meeting.getId());

        Meeting fromDao = meetingDao.getMeetingByid(meeting.getId());

        assertNull(fromDao);
    }

    @Test
    public void testGetMeetingsForRoom() {
        Employee employee = new Employee();
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        employee = employeeDao.addEmployee(employee);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);

        Room room2 = new Room();
        room2.setName("Test Room 2");
        room2.setDescription("Test Room Description 2");
        room2 = roomDao.addRoom(room2);

        Meeting meeting = new Meeting();
        meeting.setName("Test Meeting");
        meeting.setTime(LocalDateTime.now().withNano(0));
        meeting.setRoom(room);
        meeting.setAttendees(employees);
        meeting = meetingDao.addMeeting(meeting);

        Meeting meeting2 = new Meeting();
        meeting2.setName("Test Meeting");
        meeting2.setTime(LocalDateTime.now().withNano(0));
        meeting2.setRoom(room2);
        meeting2.setAttendees(employees);
        meeting2 = meetingDao.addMeeting(meeting2);

        Meeting meeting3 = new Meeting();
        meeting3.setName("Test Meeting");
        meeting3.setTime(LocalDateTime.now().withNano(0));
        meeting3.setRoom(room);
        meeting3.setAttendees(employees);
        meeting3 = meetingDao.addMeeting(meeting3);

        List<Meeting> meetingsForRoom = meetingDao.getMeetingsForRoom(room);

        assertEquals(2, meetingsForRoom.size());
        assertTrue(meetingsForRoom.contains(meeting));
        assertTrue(meetingsForRoom.contains(meeting3));
        assertFalse(meetingsForRoom.contains(meeting2));
    }

    @Test
    public void testGetMeetingsForEmployee() {

        Employee employee = new Employee();
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        employee = employeeDao.addEmployee(employee);

        Employee employee2 = new Employee();
        employee2.setFirstName("Test First 2");
        employee2.setLastName("Test Last 2");
        employee2 = employeeDao.addEmployee(employee2);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        employees.add(employee2);

        List<Employee> employees2 = new ArrayList<>();
        employees2.add(employee2);

        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);

        Meeting meeting = new Meeting();
        meeting.setName("Test Meeting");
        meeting.setTime(LocalDateTime.now().withNano(0));
        meeting.setRoom(room);
        meeting.setAttendees(employees);
        meeting = meetingDao.addMeeting(meeting);

        Meeting meeting2 = new Meeting();
        meeting2.setName("Test Meeting");
        meeting2.setTime(LocalDateTime.now().withNano(0));
        meeting2.setRoom(room);
        meeting2.setAttendees(employees2);
        meeting2 = meetingDao.addMeeting(meeting2);

        Meeting meeting3 = new Meeting();
        meeting3.setName("Test Meeting");
        meeting3.setTime(LocalDateTime.now().withNano(0));
        meeting3.setRoom(room);
        meeting3.setAttendees(employees);
        meeting3 = meetingDao.addMeeting(meeting3);

        List<Meeting> meetingsForEmployee = meetingDao.getMeetingsForEmployee(employee);

        assertEquals(2, meetingsForEmployee.size());
        assertTrue(meetingsForEmployee.contains(meeting));
        assertTrue(meetingsForEmployee.contains(meeting3));
        assertFalse(meetingsForEmployee.contains(meeting2));
    }
}