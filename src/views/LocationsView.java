package views;

import databank.db_objects.Location;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
                    if (cell.getItem() != null) {
                        cell.getItem().setName(string);
                    }
                    model.updateLocation(cell.getItem());
                    return cell.getItem();
                }
            };
            cell.setConverter(converter);
            return cell;
        });
    }

    @Override
    public void invalidated(Observable observable) {
        setItems(model.getLocations());
    }
}
