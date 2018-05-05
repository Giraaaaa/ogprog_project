package views;

import databank.db_objects.Location;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import models.Model;

public class LocationsView extends ListView<Location> implements InvalidationListener {

    private Model model;

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addListener(this);
        setItems(model.getLocations());
        setPrefHeight(605);
        setEditable(true);
         // Deze factory zorgt ervoor dat we door te dubbelklikken op cellen, locaties kunnen updaten.
        setCellFactory(lv -> {
            TextFieldListCell<Location> cell = new TextFieldListCell<>();
            StringConverter<Location> converter = new StringConverter<Location>() {
                @Override
                public String toString(Location object) {
                    return object.toString();
                }

                @Override
                public Location fromString(String string) {
                    // We moeten enkel de naam aanpassen als de nieuwe naam uniek is.
                    boolean uniek = true;
                    for (Location location : model.getLocations()) {
                        if (string.equals(location.getName()) && location.getId() != cell.getItem().getId()) {
                            uniek = false;
                        }
                    }
                    if (cell.getItem() != null && uniek && !string.equals("")) {
                        cell.getItem().setName(string);
                    }
                    return cell.getItem();
                }
            };
            cell.setConverter(converter);
            return cell;
        });
        setOnEditCommit(new EventHandler<EditEvent<Location>>() {
            @Override
            public void handle(EditEvent<Location> event) {
                model.updateLocation(event.getNewValue());
            }
        });
    }
    @Override
    public void invalidated(Observable observable) {
        setItems(model.getLocations());
    }
}
