/*
@author Sieben Veldeman
 */

package databank.database_algemeen;

import databank.db_objects.Location;

import java.sql.SQLException;

public interface LocationDAO {

    Iterable<Location> getLocations() throws SQLException;

    Location findLocationByName(String name) throws SQLException;

    int createLocation(String name) throws SQLException;

    void updateLocation(String name, int id) throws SQLException;

}
