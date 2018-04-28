/*
@author Sieben Veldeman
 */

package databank.database_algemeen;

import databank.db_objects.Lecture;

import java.sql.SQLException;

public interface LectureDAO {

    Iterable<Lecture> getLectures() throws SQLException;

    Iterable<Lecture> getLecturesByStudentsid(int id) throws SQLException;

    Iterable<Lecture> getLecturesByTeacherid(int id) throws SQLException;

    Iterable<Lecture> getLectureByLocationid(int id) throws SQLException;

    void createLecture(int students_id, int teacher_id, int location_id, String course, int day, int first_block, int duration) throws SQLException;

    
}
