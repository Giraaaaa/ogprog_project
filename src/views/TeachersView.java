package views;

import databank.db_objects.Teacher;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.ListView;
import models.TeachersModel;


public class TeachersView extends ListView<Teacher> implements InvalidationListener {

    private TeachersModel model;

    public TeachersModel getModel() {
        return model;
    }

    public void setModel(TeachersModel model) {
        this.model = model;
        model.addListener(this);
        setItems(model.getTeachers());
        // Make the listview 600 pixels long
        setPrefHeight(605);
    }

    public void invalidated(Observable o) {
        setItems(model.getTeachers());
    }

}
