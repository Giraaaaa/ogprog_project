package models;

import databank.database_algemeen.*;
import databank.db_objects.*;
import databank.jdbc_implementatie.JDBCDataAccessProvider;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Model implements Observable {

    private ObservableList<Location> locations = FXCollections.observableArrayList();
    private ObservableList<Students> students = FXCollections.observableArrayList();
    private ObservableList<Teacher> teachers = FXCollections.observableArrayList();
    private DataAccessProvider dap = new JDBCDataAccessProvider();
    private List<InvalidationListener> listenerList = new ArrayList<>();
    // Deze lijst bevat de lessen die momenteel zichtbaar zijn.
    private List<Lecture> lessen;


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
    public void populateLocation() throws SQLException {
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

    public void addLecture(Lecture les) {
        // Voegt een nieuwe les toe aan de db.
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            LectureDAO dao = dac.getLectureDAO();
            dao.createLecture(les.getStudent_id(), les.getTeacher_id(), les.getLocation_id(), les.getCourse(), les.getDay(), les.getFirst_block(), les.getDuration());
        } catch (SQLException ex) {
            throw new RuntimeException("Could not add lecture");
        }

    }


    // Wanneer we een bestaande db openen, wordt deze methode opgeroepen om de studenten in te laden.
    public void populateStudents() throws SQLException {
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
    public void populateTeacher() throws SQLException {
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            TeacherDAO dao = dac.getTeacherDAO();
            Iterable<Teacher> leerkrachten = dao.getTeachers();
            for (Teacher teacher : leerkrachten) {
                teachers.add(teacher);
            }
        }
        fireInvalidationEvent();
    }

    public void updateTeacher(Teacher teacher) {
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            TeacherDAO dao = dac.getTeacherDAO();
            dao.updateTeacher(teacher.getName(), teacher.getId());
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to update teachers.");
        }
        fireInvalidationEvent();
    }

    public void populateLecture() throws SQLException {
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            LectureDAO dao = dac.getLectureDAO();
            Iterable<Lecture> lectures = dao.getLectures();
            for (Lecture lecture : lectures) {
                lessen.add(lecture);
            }
        }
    }

    public boolean uniquelecture(Lecture lecture) {
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            LectureDAO dao = dac.getLectureDAO();
            return ! dao.findLecture(lecture.getStudent_id(), lecture.getTeacher_id(), lecture.getLocation_id(), lecture.getCourse(), lecture.getDay(), lecture.getFirst_block(), lecture.getDuration());
        } catch (SQLException ex) {
            return true;
        }
    }

    public List<Period> getPeriods() throws SQLException {
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            PeriodDAO dao = dac.getPeriodDAO();
            List<Period> periods = dao.getPeriods();
            return periods;
        }
    }

    // De volgende methodes vragen de betrokken lessen aan de DAO's aan de hand van de id's
    public List<Lecture> giveLectureListByteacherid(int id) throws SQLException {
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            LectureDAO dao = dac.getLectureDAO();
            List<Lecture> lessen = dao.getLecturesByTeacherid(id);
            return lessen;
        }
    }

    public List<Lecture> giveLectureListbyStudentsid(int id) throws SQLException {
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            LectureDAO dao = dac.getLectureDAO();
            List<Lecture> lessen = dao.getLecturesByStudentsid(id);
            return lessen;
        }
    }

    public List<Lecture> giveLectureListbyLocationid(int id) throws SQLException {
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            LectureDAO dao = dac.getLectureDAO();
            List<Lecture> lessen = dao.getLectureByLocationid(id);
            return lessen;
        }
    }

    public ObservableList<Lecture> getCurrentLectures() {return FXCollections.observableArrayList(lessen); }


    public void removeLecture(Lecture lecture) {
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            LectureDAO dao = dac.getLectureDAO();
            dao.removeLecture(lecture.getStudent_id(), lecture.getTeacher_id(), lecture.getLocation_id(), lecture.getCourse(), lecture.getDay(), lecture.getFirst_block(), lecture.getDuration());
        } catch (SQLException ex) {
            throw new RuntimeException("Unable to remove lecture");
        }
    }

    // Deze methode update de huidige lectures, wordt opgeroepen wanneer er een nieuwe teachers/location/students wordt geselecteerd
    public void updateLectures(List<Lecture> lectures) {
        lessen = new ArrayList<>(lectures);
    }

    public void editURL(String path) {
        dap.editURL(path);
    }
}
