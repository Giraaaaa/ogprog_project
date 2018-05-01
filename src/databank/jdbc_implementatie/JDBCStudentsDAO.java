/*
@author Sieben Veldeman
 */

package databank.jdbc_implementatie;

import databank.db_objects.Students;
import databank.database_algemeen.StudentsDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCStudentsDAO extends JDBCAbstractDAO implements StudentsDAO {

    public JDBCStudentsDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Students> getStudents() throws SQLException {
        try (PreparedStatement stmnt = prepare("SELECT * FROM students")) {
            try (ResultSet set =  stmnt.executeQuery()) {
                List<Students> students = new ArrayList<>();
                while (set.next()) {
                    students.add(new Students(
                            set.getInt("id"),
                            set.getString("name")));
                }
                return students;
            }
        } catch (SQLException ex) {
            throw new SQLException("Could not retrieve students from database.");
        }
    }

    public Students findStudentsByName(String name) throws SQLException {
        // Deze methode gaat ervan uit dat alle studentengroepen een unieke naam hebben.
        try (PreparedStatement stmnt = prepare("SELECT * FROM students WHERE name = ?")) {
            stmnt.setString(1, name);
            try (ResultSet result = stmnt.executeQuery()) {
                Students opgevraagd = new Students(result.getInt("id"), result.getString("name"));
                return opgevraagd;
            }
        } catch (SQLException ex) {
            throw new SQLException("Could not retrieve group, perhaps you gave a wrong name.");
        }

    }

    public void updateStudents(String name, int id) throws SQLException {
        try (PreparedStatement stmnt = prepare("UPDATE students SET name = ? WHERE id = ?")) {
            stmnt.setString(1, name);
            stmnt.setInt(2, id);
            stmnt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Could not update this student group.");
        }
    }

    public int createStudents(String name) throws SQLException {
        try (PreparedStatement stmnt = prepare("INSERT INTO students(name) VALUES (?)")) {
            stmnt.setString(1, name);
            stmnt.executeUpdate();
            try {
                ResultSet keys = stmnt.getGeneratedKeys();
                return keys.getInt(1);
            } catch (SQLException ex) {
                throw new SQLException("Invalid generated key.");
            }
        } catch (SQLException ex) {
            throw new SQLException("Could not create student group.");
        }
    }

}

