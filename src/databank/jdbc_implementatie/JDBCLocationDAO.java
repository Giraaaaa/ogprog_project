/*
@author Sieben Veldeman
 */

package databank.jdbc_implementatie;

import databank.DataAccessException;
import databank.database_algemeen.LocationDAO;
import databank.db_objects.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCLocationDAO extends JDBCAbstractDAO implements LocationDAO {

    public JDBCLocationDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Location> getLocations() throws DataAccessException {
        try (PreparedStatement stmnt = prepare("SELECT * FROM location")) {
            try (ResultSet set =  stmnt.executeQuery()) {
                List<Location> locations = new ArrayList<>();
                while (set.next()) {
                   locations.add(new Location(
                                        set.getInt("id"),
                                        set.getString("name")));
                }
                return locations;
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Could not retrieve locations from database", ex);
        }
    }

    public Location findLocationByName(String name) throws DataAccessException {
        // Deze methode gaat ervan uit dat alle studentengroepen een unieke naam hebben.
        try (PreparedStatement stmnt = prepare("SELECT * FROM location WHERE name = ?")) {
            stmnt.setString(1, name);
            try (ResultSet result = stmnt.executeQuery()) {
                Location opgevraagd = new Location(result.getInt("id"), result.getString("name"));
                return opgevraagd;
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Could not retrieve location", ex);
        }

    }

    //Returns the id of the created person
    public int createLocation(String name) throws DataAccessException {
        try (PreparedStatement stmnt = prepare("INSERT INTO location(name) VALUES (?)")) {
            stmnt.setString(1, name);
            stmnt.executeUpdate();
            try {
                ResultSet keys = stmnt.getGeneratedKeys();
                return keys.getInt(1);
            } catch (SQLException ex) {
                throw new SQLException("Invalid generated key.");
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Could not create location", ex);
        }
    }

    public void updateLocation(String name, int id) throws DataAccessException {
        try (PreparedStatement stmnt = prepare("UPDATE location SET name = ? WHERE id = ?")) {
            stmnt.setString(1, name);
            stmnt.setInt(2, id);
            stmnt.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Could not update this location", ex);
        }
    }
}
