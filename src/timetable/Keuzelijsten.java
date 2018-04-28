/*
@author Sieben Veldeman
 */

package timetable;


import databank.database_algemeen.*;
import databank.db_objects.*;
import databank.jdbc_implementatie.JDBCDataAccessProvider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Keuzelijsten {

    private DataAccessProvider dap;

    public Keuzelijsten() {
        dap = new JDBCDataAccessProvider();
    }

    public List<Teacher> giveTeacherList() throws SQLException {
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            TeacherDAO dao = dac.getTeacherDAO();
            Iterable<Teacher> teachers = dao.getTeachers();
            List<Teacher> leerkrachten = new ArrayList<>();
            for (Teacher teacher : teachers) {
                leerkrachten.add(teacher);
            }
            return leerkrachten;
        }
    }

    public List<Location> giveLocationList() throws SQLException {
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            LocationDAO dao = dac.getLocationDAO();
            Iterable<Location>  locations = dao.getLocations();
            List<Location> locaties = new ArrayList<>();
            for (Location locatie: locations) {
                locaties.add(locatie);
            }
            return locaties;
        }
    }

    public List<Students> giveStudentsList() throws SQLException {
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            StudentsDAO dao = dac.getStudentsDAO();
            Iterable<Students>  students = dao.getStudents();
            List<Students> studenten = new ArrayList<>();
            for (Students student: students) {
                studenten.add(student);
            }
            return studenten;
        }

    }

    public List<Period> givePeriodsList() throws SQLException {
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            PeriodDAO dao = dac.getPeriodDAO();
            Iterable<Period> periods = dao.getPeriods();
            List<Period> periodes = new ArrayList<>();
            for (Period periode: periods) {
                periodes.add(periode);
            }
            return periodes;
        }
    }

    public List<Lecture> giveLectureListByteacherid(int id) throws SQLException {
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            LectureDAO dao = dac.getLectureDAO();
            Iterable<Lecture> lectures = dao.getLecturesByTeacherid(id);
            List<Lecture> lessen = new ArrayList<>();
            for (Lecture lecture: lectures) {
                lessen.add(lecture);
            }
            return lessen;
        }
    }

    public List<Lecture> giveLectureListbyStudentsid(int id) throws SQLException {
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            LectureDAO dao = dac.getLectureDAO();
            Iterable<Lecture> lectures = dao.getLecturesByStudentsid(id);
            List<Lecture> lessen = new ArrayList<>();
            for (Lecture lecture: lectures) {
                lessen.add(lecture);
            }
            return lessen;
        }
    }

    public List<Lecture> giveLectureListbyLocationid(int id) throws SQLException {
        try (DataAccessContext dac = dap.getDataAccessContext()) {
            LectureDAO dao = dac.getLectureDAO();
            Iterable<Lecture> lectures = dao.getLectureByLocationid(id);
            List<Lecture> lessen = new ArrayList<>();
            for (Lecture lecture: lectures) {
                lessen.add(lecture);
            }
            return lessen;
        }
    }
}
