/*
@author Sieben Veldeman
 */

package databank.jdbc_implementatie;

import databank.db_objects.Lecture;
import databank.database_algemeen.LectureDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCLectureDAO extends JDBCAbstractDAO implements LectureDAO {

    public JDBCLectureDAO(Connection connection) {
        super(connection);
    }


    @Override
    public List<Lecture> getLectures() throws SQLException {
        try (PreparedStatement stmnt = prepare("SELECT * FROM lecture")) {
            try (ResultSet set =  stmnt.executeQuery()) {
                List<Lecture> lectures = new ArrayList<>();
                while (set.next()) {
                    lectures.add(new Lecture(
                                        set.getInt("students_id"),
                                        set.getInt("teacher_id"),
                                        set.getInt("location_id"),
                                        set.getString("course"),
                                        set.getInt("day"),
                                        set.getInt("first_block"),
                                        set.getInt("duration")));
                }
                return lectures;
            }
        } catch (SQLException ex) {
            throw new SQLException("Could not retrieve lectures from database.");
        }
    }

    @Override
    public List<Lecture> getLectureByLocationid(int id) throws SQLException {
        try (PreparedStatement stmnt = prepare("SELECT * FROM lecture WHERE location_id = ?")) {
            stmnt.setInt(1, id);
            ResultSet result = stmnt.executeQuery();
            return resultSetToList(result);
        }
    }

    @Override
    public List<Lecture> getLecturesByStudentsid(int id) throws SQLException {
        try (PreparedStatement stmnt = prepare("SELECT * FROM lecture WHERE students_id = ?")) {
            stmnt.setInt(1, id);
            ResultSet result = stmnt.executeQuery();
            return resultSetToList(result);
        }
    }

    @Override
    public List<Lecture> getLecturesByTeacherid(int id) throws SQLException {
        try(PreparedStatement stmnt = prepare("SELECT * FROM lecture WHERE teacher_id = ?")) {
            stmnt.setInt(1, id);
            ResultSet result = stmnt.executeQuery();
            return resultSetToList(result);
        }
    }
    public List<Lecture> resultSetToList(ResultSet result) throws SQLException {
        List<Lecture> lessen = new ArrayList<>();
        while (result.next()) {
            lessen.add(new Lecture(
                    result.getInt("students_id"),
                    result.getInt("teacher_id"),
                    result.getInt("location_id"),
                    result.getString("course"),
                    result.getInt("day"),
                    result.getInt("first_block"),
                    result.getInt("duration")));
        }
        return lessen;
    }

    @Override
    public void createLecture(int students_id, int teacher_id, int location_id, String course, int day, int first_block, int duration) throws SQLException {
        try (PreparedStatement stmnt = prepare("INSERT INTO lecture(students_id, teacher_id, location_id, course, day, first_block, duration) VALUES (?,?,?,?,?,?,?)")) {
            stmnt.setInt(1, students_id);
            stmnt.setInt(2, teacher_id);
            stmnt.setInt(3, location_id);
            stmnt.setString(4, course);
            stmnt.setInt(5, day);
            stmnt.setInt(6, first_block);
            stmnt.setInt(7, duration);
            stmnt.executeUpdate();
        }
    }

    // Wordt gebruikt om te checken of lectures uniek zijn
    public boolean findLecture(int students_id, int teacher_id, int location_id, String course, int day, int first_block, int duration) throws SQLException {
        try (PreparedStatement stmnt = prepare("SELECT * FROM lecture WHERE students_id = ? AND teacher_id = ? AND location_id = ? AND course = ? AND day = ? AND first_block = ? AND duration = ?")) {
            stmnt.setInt(1, students_id);
            stmnt.setInt(2, teacher_id);
            stmnt.setInt(3, location_id);
            stmnt.setString(4, course);
            stmnt.setInt(5, day);
            stmnt.setInt(6, first_block);
            stmnt.setInt(7, duration);
            ResultSet rs = stmnt.executeQuery();
            // Return false wanneer er geen rijen zitten in de resultset en true wanneer er wel rijen/ of 1 rij inzitten.
            return rs.isBeforeFirst();
        }
    }

    public void removeLecture(int students_id, int teacher_id, int location_id, String course, int day, int first_block, int duration) throws SQLException {
        try (PreparedStatement stmnt = prepare("DELETE FROM lecture WHERE students_id = ? AND teacher_id = ? AND location_id = ? AND course = ? AND day = ? AND first_block = ? AND duration = ?")) {
            stmnt.setInt(1, students_id);
            stmnt.setInt(2, teacher_id);
            stmnt.setInt(3, location_id);
            stmnt.setString(4, course);
            stmnt.setInt(5, day);
            stmnt.setInt(6, first_block);
            stmnt.setInt(7, duration);
            stmnt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Could not remove lecture");
        }
    }
}
