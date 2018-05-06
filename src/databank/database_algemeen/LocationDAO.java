/*
@author Sieben Veldeman
 */

package databank.database_algemeen;

import databank.DataAccessException;
import databank.db_objects.Location;

import java.util.List;

public interface LocationDAO {

    List<Location> getLocations() throws DataAccessException;

    Location findLocationByName(String name) throws DataAccessException;

    int createLocation(String name) throws DataAccessException;

    void updateLocation(String name, int id) throws DataAccessException;

}
