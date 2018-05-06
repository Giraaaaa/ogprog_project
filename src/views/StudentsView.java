/*
@author Sieben Veldeman
 */

package views;

import databank.db_objects.Location;
import databank.db_objects.Students;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import models.Model;

public class StudentsView extends ListView<Students> implements InvalidationListener {

    private Model model;

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addListener(this);
        setItems(model.getStudents());
        // Make the listview 605 pixels long
        setPrefHeight(605);
        setEditable(true);
        // Deze factory zorgt ervoor dat we door te dubbelklikken op cellen in de studentslijst, students kunnen updaten.
        setCellFactory(lv -> {
            TextFieldListCell<Students> cell = new TextFieldListCell<>();
            StringConverter<Students> converter = new StringConverter<Students>() {
                @Override
                public String toString(Students object) {
                    return object.toString();
                }

                @Override
                public Students fromString(String string) {
                    // We moeten enkel de naam aanpassen als de nieuwe naam uniek is.
                    boolean uniek = true;
                    for (Students students : model.getStudents()) {
                        if (string.equals(students.getName()) && students.getId() != cell.getItem().getId()) {
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
        setOnEditCommit(new EventHandler<EditEvent<Students>>() {
            @Override
            public void handle(EditEvent<Students> event) {
                model.updateStudents(event.getNewValue());
            }
        });
    }

    public void invalidated(Observable o) {
        setItems(model.getStudents());
    }

}

