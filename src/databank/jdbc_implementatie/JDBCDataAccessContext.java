/*
@author Sieben Veldeman
 */

package databank.jdbc_implementatie;

import databank.database_algemeen.*;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDataAccessContext implements DataAccessContext {

    private Connection connection;

    public JDBCDataAccessContext(Connection connection) {
        this.connection = connection;
    }

    @Override
    public LectureDAO getLectureDAO() {
        return new JDBCLectureDAO(connection);
    }

    @Override
    public LocationDAO getLocationDAO() {
        return new JDBCLocationDAO(connection);
    }

    @Override
    public PeriodDAO getPeriodDAO() {
        return new JDBCPeriodDAO(connection);
    }

    @Override
    public StudentsDAO getStudentsDAO() {
        return new JDBCStudentsDAO(connection);
    }

    @Override
    public TeacherDAO getTeacherDAO() {
        return new JDBCTeacherDAO(connection);
    }

    @Override
    public void close() throws SQLException {
        try {
            connection.close();
        }
        catch (SQLException ex) {
            throw new SQLException("Could not close this context.");
        }
    }
}
