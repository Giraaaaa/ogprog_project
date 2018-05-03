/*
@author Sieben Veldeman
 */

package databank.database_algemeen;

import databank.db_objects.Lecture;

import java.sql.SQLException;
import java.util.List;

public interface LectureDAO {

    List<Lecture> getLectures() throws SQLException;

    List<Lecture> getLecturesByStudentsid(int id) throws SQLException;

    List<Lecture> getLecturesByTeacherid(int id) throws SQLException;

    List<Lecture> getLectureByLocationid(int id) throws SQLException;

    void createLecture(int students_id, int teacher_id, int location_id, String course, int day, int first_block, int duration) throws SQLException;

    boolean findLecture(int students_id, int teacher_id, int location_id, String course, int day, int first_block, int duration) throws SQLException;

    void removeLecture(int students_id, int teacher_id, int location_id, String course, int day, int first_block, int duration) throws SQLException;

    
}
