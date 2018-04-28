/*
@author Sieben Veldeman
 */

package databank.database_algemeen;

import databank.db_objects.Students;

import java.sql.SQLException;

public interface StudentsDAO {

    Iterable<Students> getStudents() throws SQLException;

    Students findStudentsByName(String name) throws SQLException;

    int createStudents(String name) throws SQLException;

    void updateStudents(String name, int id) throws SQLException;

}
