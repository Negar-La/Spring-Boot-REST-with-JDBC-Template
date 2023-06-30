package com.sg.jdbctcomplexexample.dao;

import com.sg.jdbctcomplexexample.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RoomDAODB implements RoomDao{
    @Autowired
    JdbcTemplate jdbc;

    public static final class RoomMapper implements RowMapper<Room> {
        @Override
        public Room mapRow(ResultSet rs, int index) throws SQLException {
            Room rm = new Room();
            rm.setId(rs.getInt("id"));
            rm.setName(rs.getString("name"));
            rm.setDescription(rs.getString("description"));
            return rm;
        }
    }

    @Override
    public List<Room> getAllRooms() {
        return null;
    }

    @Override
    public Room getRoomById(int id) {
        return null;
    }

    @Override
    public Room addRoom(Room room) {
        return null;
    }

    @Override
    public void updateRoom(Room room) {

    }

    @Override
    public void deleteRoomById(int id) {

    }
}
