/*
@author Sieben Veldeman
 */

package databank.database_algemeen;

import java.sql.SQLException;

public interface DataAccessContext extends AutoCloseable {

    LectureDAO getLectureDAO();

    LocationDAO getLocationDAO();

    PeriodDAO getPeriodDAO();

    StudentsDAO getStudentsDAO();

    TeacherDAO getTeacherDAO();

    void close() throws SQLException;

}
