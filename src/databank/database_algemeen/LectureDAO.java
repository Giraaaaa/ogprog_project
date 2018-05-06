/*
@author Sieben Veldeman
 */

package databank.database_algemeen;

import databank.DataAccessException;
import databank.db_objects.Lecture;

import java.util.List;

public interface LectureDAO {

    List<Lecture> getLectures() throws DataAccessException;

    List<Lecture> getLecturesByStudentsid(int id) throws DataAccessException;

    List<Lecture> getLecturesByTeacherid(int id) throws DataAccessException;

    List<Lecture> getLectureByLocationid(int id) throws DataAccessException;

    void createLecture(int students_id, int teacher_id, int location_id, String course, int day, int first_block, int duration) throws DataAccessException;

    boolean findLecture(int students_id, int teacher_id, int location_id, String course, int day, int first_block, int duration) throws DataAccessException;

    void removeLecture(int students_id, int teacher_id, int location_id, String course, int day, int first_block, int duration) throws DataAccessException;

    
}
