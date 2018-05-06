/*
@author Sieben Veldeman
 */

package databank.database_algemeen;

import databank.DataAccessException;
import databank.db_objects.Students;

import java.util.List;

public interface StudentsDAO {

    List<Students> getStudents() throws DataAccessException;

    Students findStudentsByName(String name) throws DataAccessException;

    int createStudents(String name) throws DataAccessException;

    void updateStudents(String name, int id) throws DataAccessException;

}
