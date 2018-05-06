/*
@author Sieben Veldeman
 */

package databank.database_algemeen;

import databank.DataAccessException;

public interface DataAccessContext extends AutoCloseable {

    LectureDAO getLectureDAO();

    LocationDAO getLocationDAO();

    PeriodDAO getPeriodDAO();

    StudentsDAO getStudentsDAO();

    TeacherDAO getTeacherDAO();

    void close() throws DataAccessException;

}
