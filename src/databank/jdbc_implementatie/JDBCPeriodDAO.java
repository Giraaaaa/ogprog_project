/*
@author Sieben Veldeman
 */

package databank.jdbc_implementatie;

import databank.DataAccessException;
import databank.database_algemeen.PeriodDAO;
import databank.db_objects.Period;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCPeriodDAO extends JDBCAbstractDAO implements PeriodDAO {

    public JDBCPeriodDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Period> getPeriods() throws DataAccessException {
        try (PreparedStatement stmnt = prepare("SELECT * FROM period ORDER BY hour, minute ASC")) {
            try (ResultSet set =  stmnt.executeQuery()) {
                List<Period> periods = new ArrayList<>();
                while (set.next()) {
                    periods.add(new Period(
                                        set.getInt("id"),
                                        set.getInt("hour"),
                                        set.getInt("minute")));
                }

                return periods;
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Could not retrieve periods from database", ex);
        }
    }

        // Returns the id of the created period
    public int createPeriod(int hour, int minute) throws DataAccessException {
        try (PreparedStatement stmnt = prepare("INSERT INTO period(hour, minute) VALUES (?, ?)")) {
            stmnt.setInt(1, hour);
            stmnt.setInt(2, minute);
            stmnt.executeUpdate();
            try {
                ResultSet keys = stmnt.getGeneratedKeys();
                return keys.getInt(1);
            } catch (SQLException ex) {
                throw new DataAccessException("Invalid generated key", ex);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Could not create student group.", ex);
        }
    }
}
