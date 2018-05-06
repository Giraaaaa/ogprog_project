/*
@author Sieben Veldeman
 */

package databank.database_algemeen;

import databank.DataAccessException;
import databank.db_objects.Teacher;

public interface TeacherDAO {

    Iterable<Teacher> getTeachers() throws DataAccessException;

    Teacher findByName(String name) throws DataAccessException;

    int createTeacher(String name) throws DataAccessException;

    void updateTeacher(String name, int id) throws DataAccessException;


}
