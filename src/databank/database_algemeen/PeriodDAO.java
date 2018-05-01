/*
@author Sieben Veldeman
 */

package databank.database_algemeen;

import databank.db_objects.Period;

import java.sql.SQLException;
import java.util.List;

public interface PeriodDAO {

    List<Period> getPeriods() throws SQLException;

    int createPeriod(int hour, int minute) throws SQLException;

}
