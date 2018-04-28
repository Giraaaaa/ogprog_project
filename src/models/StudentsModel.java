package models;

import databank.database_algemeen.DataAccessContext;
import databank.database_algemeen.DataAccessProvider;
import databank.database_algemeen.StudentsDAO;
import databank.db_objects.Students;
import databank.jdbc_implementatie.JDBCDataAccessProvider;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentsModel implements Observable {

    private ObservableList<Students> students = FXCollections.observableArrayList();
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

    public ObservableList<Students> getStudents() {
        return students;
    }

    public void addStudents(String name) throws SQLException {
        // Opdracht aan databank om een teacher toe te voegen, die methode geeft automatisch het gecreerde ID terug, waarmee
        // we een nieuw teacher object aan de observablelist kunnen toevoegen.
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            StudentsDAO dao = dac.getStudentsDAO();
            int id = dao.createStudents(name);
            students.add(new Students(id, name));
        }
        fireInvalidationEvent();
    }
    // Wanneer we een bestaande db openen, wordt deze methode opgeroepen om de studenten in te laden.
    public void populate() throws SQLException {
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            StudentsDAO dao = dac.getStudentsDAO();
            Iterable<Students> studenten = dao.getStudents();
            for (Students student : studenten) {
                students.add(student);
            }
        }
        fireInvalidationEvent();
    }

    public void updateStudents(Students students) {
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            StudentsDAO dao = dac.getStudentsDAO();
            dao.updateStudents(students.getName(), students.getId());
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to update location.");
        }
        fireInvalidationEvent();
    }
}
