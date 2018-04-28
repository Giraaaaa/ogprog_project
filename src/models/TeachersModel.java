package models;

import databank.database_algemeen.DataAccessContext;
import databank.database_algemeen.DataAccessProvider;
import databank.database_algemeen.TeacherDAO;
import databank.db_objects.Teacher;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import databank.jdbc_implementatie.JDBCDataAccessProvider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeachersModel implements Observable {

    private ObservableList<Teacher> teachers = FXCollections.observableArrayList();
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

    public ObservableList<Teacher> getTeachers() {
        return teachers;
    }

    public void addTeacher(String name) throws SQLException {
        // Opdracht aan databank om een teacher toe te voegen, die methode geeft automatisch het gecreerde ID terug, waarmee
        // we een nieuw teacher object aan de observablelist kunnen toevoegen.
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            TeacherDAO dao = dac.getTeacherDAO();
            int id = dao.createTeacher(name);
            teachers.add(new Teacher(id, name));
        }
        fireInvalidationEvent();
    }

    // Wanneer we een bestaande db openen, wordt deze methode opgeroepen om de teachers in te laden.
    public void populate() throws SQLException {
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            TeacherDAO dao = dac.getTeacherDAO();
            Iterable<Teacher> leerkrachten = dao.getTeachers();
            for (Teacher teacher : leerkrachten) {
                teachers.add(teacher);
            }
        }
        fireInvalidationEvent();
    }
}
