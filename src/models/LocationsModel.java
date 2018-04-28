package models;

import databank.database_algemeen.DataAccessContext;
import databank.database_algemeen.DataAccessProvider;
import databank.database_algemeen.LocationDAO;
import databank.db_objects.Location;
import databank.jdbc_implementatie.JDBCDataAccessProvider;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationsModel implements Observable {

    private ObservableList<Location> locations = FXCollections.observableArrayList();
    private DataAccessProvider dap = new JDBCDataAccessProvider();
    private List<InvalidationListener> listenerList = new ArrayList<>();

    @Override
    public void addListener(InvalidationListener listener) {
        listenerList.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        listenerList.remove(listener);
    }

    private void fireInvalidationEvent() {
        for (InvalidationListener listener : listenerList) {
            listener.invalidated(this);
        }
    }

    public ObservableList<Location> getLocations() {
        return locations;
    }


    // Wanneer we een bestaande db openen, wordt deze methode opgeroepen om de locaties in te laden.
    public void populate() throws SQLException {
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            LocationDAO dao = dac.getLocationDAO();
            Iterable<Location> locaties = dao.getLocations();
            for (Location location : locaties) {
                locations.add(location);
            }
        }
        fireInvalidationEvent();
    }


    // Methode om een locatie aan te passen in de databank.
    public void updateLocation(Location loc) {
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            LocationDAO dao = dac.getLocationDAO();
            dao.updateLocation(loc.getName(), loc.getId());
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to update location.");
        }
        fireInvalidationEvent();
    }

    // Methode om een locatie toe te voegen aan de databank, en aan het model.
    public void addLocation(String name) throws SQLException {
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            LocationDAO dao = dac.getLocationDAO();
            int id = dao.createLocation(name);
            locations.add(new Location(id, name));
        }
        fireInvalidationEvent();
    }

}
