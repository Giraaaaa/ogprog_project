/*
@author Sieben Veldeman
 */

package databank.database_algemeen;

import databank.DataAccessException;
import databank.db_objects.Period;

import java.util.List;

public interface PeriodDAO {

    List<Period> getPeriods() throws DataAccessException;

    int createPeriod(int hour, int minute) throws DataAccessException;

}
