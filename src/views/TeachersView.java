package views;

import databank.db_objects.Teacher;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import models.Model;


public class TeachersView extends ListView<Teacher> implements InvalidationListener {

    private Model model;

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addListener(this);
        setItems(model.getTeachers());
        // Make the listview 600 pixels long
        setPrefHeight(605);
        setEditable(true);
        setCellFactory(lv -> {
            TextFieldListCell<Teacher> cell = new TextFieldListCell<>();
            StringConverter<Teacher> converter = new StringConverter<Teacher>() {
                @Override
                public String toString(Teacher object) {
                    return object.toString();
                }

                @Override
                public Teacher fromString(String string) {
                    // We moeten enkel de naam aanpassen als de nieuwe naam uniek is.
                    boolean uniek = true;
                    for (Teacher teacher : model.getTeachers()) {
                        if (string.equals(teacher.getName()) && teacher.getId() != cell.getItem().getId()) {
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
        setOnEditCommit(new EventHandler<EditEvent<Teacher>>() {
            @Override
            public void handle(EditEvent<Teacher> event) {
                model.updateTeacher(event.getNewValue());

            }
        });
    }

    public void invalidated(Observable o) {
        setItems(model.getTeachers());
    }

}
