package com.sg.jdbctcomplexexample.dao;

import com.sg.jdbctcomplexexample.TestApplicationConfiguration;
import com.sg.jdbctcomplexexample.entity.Employee;
import com.sg.jdbctcomplexexample.entity.Meeting;
import com.sg.jdbctcomplexexample.entity.Room;
import org.junit.jupiter.api.AfterEach;
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
public class EmployeeDaoDBTest {

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
    public void testAddGetEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        employee = employeeDao.addEmployee(employee);

        Employee fromDao = employeeDao.getEmployeeById(employee.getId());

        assertEquals(employee, fromDao);
    }

    @Test
    public void testGetAllEmployees() {
        Employee employee = new Employee();
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        employee = employeeDao.addEmployee(employee);

        Employee employee2 = new Employee();
        employee2.setFirstName("Test First 2");
        employee2.setLastName("Test Last 2");
        employee2 = employeeDao.addEmployee(employee2);

        List<Employee> employees = employeeDao.getAllEmployees();

        assertEquals(2, employees.size());
        assertTrue(employees.contains(employee));
        assertTrue(employees.contains(employee2));
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        employee = employeeDao.addEmployee(employee);

        Employee fromDao = employeeDao.getEmployeeById(employee.getId());

        assertEquals(employee, fromDao);

        employee.setFirstName("Another Test First");

        employeeDao.updateEmployee(employee);

        assertNotEquals(employee, fromDao);

        fromDao = employeeDao.getEmployeeById(employee.getId());   // retrieve it a second time

        assertEquals(employee, fromDao);
    }

    @Test
    public void testDeleteEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        employee = employeeDao.addEmployee(employee);

        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);

        Meeting meeting = new Meeting();
        meeting.setName("Test Meeting");
        meeting.setTime(LocalDateTime.now());
        meeting.setRoom(room);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        meeting.setAttendees(employees);
        meeting = meetingDao.addMeeting(meeting);

        employeeDao.deleteEmployeeById(employee.getId());

        Employee fromDao = employeeDao.getEmployeeById(employee.getId());

        assertNull(fromDao);
    }
}