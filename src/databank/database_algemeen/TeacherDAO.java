/*
@author Sieben Veldeman
 */

package databank.database_algemeen;

import databank.db_objects.Teacher;

import java.sql.SQLException;

public interface TeacherDAO {

    Iterable<Teacher> getTeachers() throws SQLException;

    Teacher findByName(String name) throws SQLException;

    int createTeacher(String name) throws SQLException;

    void updateTeacher(String name, int id) throws SQLException;


}
