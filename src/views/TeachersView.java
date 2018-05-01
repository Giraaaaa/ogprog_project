package views;

import databank.db_objects.Teacher;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
                    if (cell.getItem() != null) {
                        cell.getItem().setName(string);
                    }
                    model.updateTeacher(cell.getItem());
                    return cell.getItem();
                }
            };
            cell.setConverter(converter);
            return cell;
        });
    }

    public void invalidated(Observable o) {
        setItems(model.getTeachers());
    }

}
