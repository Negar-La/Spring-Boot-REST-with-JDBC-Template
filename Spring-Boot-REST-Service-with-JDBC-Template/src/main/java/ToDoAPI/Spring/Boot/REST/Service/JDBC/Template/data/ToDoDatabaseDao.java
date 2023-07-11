package ToDoAPI.Spring.Boot.REST.Service.JDBC.Template.data;

import ToDoAPI.Spring.Boot.REST.Service.JDBC.Template.models.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class ToDoDatabaseDao implements ToDoDao{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ToDoDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ToDo add(ToDo todo) {
        final String sql = "INSERT INTO todo(todo, note) VALUES(?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, todo.getTodo());
            statement.setString(2, todo.getNote());
            return statement;

        }, keyHolder);

        todo.setId(keyHolder.getKey().intValue());

        return todo;
    }

    @Override
    public List<ToDo> getAll() {
        final String sql = "SELECT * FROM todo;";
        return jdbcTemplate.query(sql, new ToDoMapper());
    }

    @Override
    public ToDo findById(int id) {
        final String sql = "SELECT id, todo, note, finished "
                + "FROM todo WHERE id = ?;";

        return jdbcTemplate.queryForObject(sql, new ToDoMapper(), id);
    }

    @Override
    public boolean update(ToDo todo) {
        final String sql = "UPDATE todo SET "
                + "todo = ?, "
                + "note = ?, "
                + "finished = ? "
                + "WHERE id = ?;";

        return jdbcTemplate.update(sql,
                todo.getTodo(),
                todo.getNote(),
                todo.isFinished(),
                todo.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        final String sql = "DELETE FROM todo WHERE id = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private static final class ToDoMapper implements RowMapper<ToDo> {

        @Override
        public ToDo mapRow(ResultSet rs, int index) throws SQLException {
            ToDo td = new ToDo();
            td.setId(rs.getInt("id"));
            td.setTodo(rs.getString("todo"));
            td.setNote(rs.getString("note"));
            td.setFinished(rs.getBoolean("finished"));
            return td;
        }
    }
}
