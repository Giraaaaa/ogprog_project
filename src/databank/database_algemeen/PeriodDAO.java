/*
@author Sieben Veldeman
 */

package databank.database_algemeen;

import databank.db_objects.Period;

import java.sql.SQLException;

public interface PeriodDAO {

    Iterable<Period> getPeriods() throws SQLException;

    int createPeriod(int hour, int minute) throws SQLException;

}
