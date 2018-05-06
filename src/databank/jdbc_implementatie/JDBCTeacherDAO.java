/*
@author Sieben Veldeman
 */

package databank.jdbc_implementatie;

import databank.DataAccessException;
import databank.db_objects.Teacher;
import databank.database_algemeen.TeacherDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCTeacherDAO extends JDBCAbstractDAO implements TeacherDAO {

    public JDBCTeacherDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Iterable<Teacher> getTeachers() throws DataAccessException {
        try (PreparedStatement stmnt = prepare("SELECT * FROM teacher")) {
            try (ResultSet set = stmnt.executeQuery()) {
                List<Teacher> teachers = new ArrayList<>();
                while (set.next()) {
                    teachers.add(new Teacher(
                            set.getInt("id"),
                            set.getString("name")));
                }
                return teachers;
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Could not retrieve teachers from database", ex);
        }
    }

    public Teacher findByName(String naam) throws DataAccessException {
        try (PreparedStatement stmnt = prepare("SELECT * FROM teacher WHERE name = ?")) {
            stmnt.setString(1, naam);
            try (ResultSet set = stmnt.executeQuery()) {
                Teacher teacher = new Teacher(set.getInt("id"), set.getString("name"));
                return teacher;
            }
            } catch (SQLException ex) {
            throw new DataAccessException("Could not retrieve teacher from database", ex);
        }
    }

    public void updateTeacher(String name, int id) throws DataAccessException {
        try (PreparedStatement stmnt = prepare("UPDATE teacher SET name = ? WHERE id = ?")) {
            stmnt.setString(1, name);
            stmnt.setInt(2, id);
            stmnt.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Could not update this teacher", ex);
        }
    }

    //Returns the id of the created person
    public int createTeacher(String name) throws DataAccessException {
        try (PreparedStatement stmnt = prepare("INSERT INTO teacher(name) VALUES (?)")) {
            stmnt.setString(1, name);
            stmnt.executeUpdate();
            try {
                ResultSet keys = stmnt.getGeneratedKeys();
                return keys.getInt(1);
            } catch (SQLException ex) {
                throw new SQLException("Invalid generated key.");
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Could not create teacher", ex);
        }
    }
}
