/*
@author Sieben Veldeman
 */

package timetable;

import databank.db_objects.Lecture;
import databank.db_objects.Period;
import databank.jdbc_implementatie.JDBCDataAccessProvider;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.LocationsModel;
import models.StudentsModel;
import models.TeachersModel;
import popups.PeriodsDialog;
import views.LocationsView;
import views.StudentsView;
import views.TeachersView;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Companion {
    public GridPane grid;
    public Accordion acc;
    public TeachersView teachers;
    private TeachersModel teachersmodel;
    public StudentsView students;
    private StudentsModel studentsmodel;
    public LocationsView locations;
    private LocationsModel locationsmodel;
    public MenuItem choose;
    private Keuzelijsten keuzes = new Keuzelijsten();
    private int rows;
    private int columns;
    // This list contains all of the lectures that are in the grid at the moment
    private List<Label> currentlist;
    //This boolean indicates if there is already a file selected, if not, we cannot add any teachers, ... to the file.
    private boolean fileselected;

    public void initialize() {
        grid.setVisible(false);
        currentlist = new ArrayList<>();
        teachers.getSelectionModel().selectedItemProperty().addListener(
                obj -> teacherChange() //Listener
        );
        teachersmodel = new TeachersModel();
        teachers.setModel(teachersmodel);
        locations.getSelectionModel().selectedItemProperty().addListener(
                obj -> locationChange() //Listener
        );
        locationsmodel = new LocationsModel();
        locations.setModel(locationsmodel);
        students.getSelectionModel().selectedItemProperty().addListener(
                obj -> studentsChange() // Listener
        );
        studentsmodel = new StudentsModel();
        students.setModel(studentsmodel);

    }

    private void teacherChange() {
        try {
            List<Lecture> lessen = keuzes.giveLectureListByteacherid(teachers.getSelectionModel().getSelectedItem().getId());
            updateRooster(lessen);
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong");
        }
    }

    private void locationChange() {
        try {
            List<Lecture> lessen = keuzes.giveLectureListbyLocationid(locations.getSelectionModel().getSelectedItem().getId());
            updateRooster(lessen);
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong");
        }
    }

    private void studentsChange() {
        try {
            List<Lecture> lessen = keuzes.giveLectureListbyStudentsid(students.getSelectionModel().getSelectedItem().getId());
            updateRooster(lessen);
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong");
        }
    }

    public void initializeRooster() throws SQLException {
        grid.setVisible(true);
        List<Period> periodes = keuzes.givePeriodsList();
        rows = periodes.size();
        // We have 5 days in a schoolweek + 1 column for the times
        columns = 6;
        // This for-loop adds all the rows to our grid, and also adds labels to display the times
        for (int i = 0; i < rows; i += 1) {
            RowConstraints rowc = new RowConstraints();
            rowc.setPercentHeight(95.0 / rows);
            grid.getRowConstraints().add(rowc);
            Label label = new Label(periodes.get(i).toString());
            label.setFont(new Font(16.0));
            label.setStyle("-fx-border-color: white;" + "-fx-background-color: gainsboro;");
            label.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
            label.setAlignment(Pos.CENTER);
            grid.add(label, 0, i + 1);
        }


    }

    public void updateRooster(List<Lecture> lijst) {
        // First we remove all lessons that are currently in the grid.
        for (Label label : currentlist) {
            grid.getChildren().remove(label);
        }
        currentlist = new ArrayList<>();
        // Add the lectures to be added to the current list, and then to the grid
        for (int i = 1; i < columns; i += 1) {
            for (int j = 1; j < rows; j += 1) {
                int lessons_on_this_hour = 0;
                String labelstring = "";
                for (Lecture lecture : lijst) {
                    if (lecture.getDay() == i && (lecture.getFirst_block() == j || lecture.getFirst_block() + lecture.getDuration() - 1 == j)) {
                        labelstring = labelstring + lecture + "\n";
                        lessons_on_this_hour += 1;
                    }
                }
                Label label = new Label(labelstring);
                if (lessons_on_this_hour == 1) {
                    label.setStyle("-fx-background-color: green;" + "-fx-text-fill: white;" + "-fx-border-color: white;");
                    label.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    label.setFont(new Font("Verdana", 13.0));
                    grid.add(label, i, j);
                    currentlist.add(label);
                } else if (lessons_on_this_hour > 1) {
                    label.setStyle("-fx-background-color: red;" + "-fx-text-fill: white;" + "-fx-border-color: white");
                    label.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    label.setFont(new Font("Verdana", 13.0));
                    grid.add(label, i, j);
                    currentlist.add(label);
                }
            }
        }
    }

    public void kiesFileActie() throws SQLException {
        FileChooser kiezer = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("DataBase files (*.db)", "*.db");
        kiezer.getExtensionFilters().add(extFilter);
        Stage kiesvenster = new Stage();
        kiesvenster.setX(300);
        kiesvenster.setY(300);
        File file = kiezer.showOpenDialog(kiesvenster);
        //Als de gebruiker op cancel drukt, heeft hij geen file geselecteerd en hoeven we niets te doen.
        if (file != null) {

            // We passen de JDBC_connectiestring aan zodat we met de geselecteerde databank kunnen verbinden.
            new JDBCDataAccessProvider().editURL(file.getAbsolutePath());
            teachersmodel.populate();
            locationsmodel.populate();
            studentsmodel.populate();
            initializeRooster();
            fileselected = true;
        }
    }

    public void nieuwFileActie() throws SQLException {
        FileChooser kiezer = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("DataBase files (*.db)", "*.db");
        kiezer.getExtensionFilters().add(extFilter);
        Stage kiesvenster = new Stage();
        kiesvenster.setX(300);
        kiesvenster.setY(300);
        File file = kiezer.showSaveDialog(kiesvenster);
        if (file != null) {
            grid.setVisible(true);
            // We passen de JDBC_connectiestring aan zodat we met de geselecteerde databank kunnen verbinden.
            new JDBCDataAccessProvider().editURL(file.getAbsolutePath());
            // We initialiseren alle nodige tables in de databank.
            new JDBCDataAccessProvider().createDataBase();
            new PeriodsDialog();
            initializeRooster();
            fileselected = true;
        }
    }

    public void studentsPopup() throws SQLException {
        if (fileselected) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add student group");
            dialog.setHeaderText("Enter the name of the group");
            dialog.setContentText("Name:");
            Optional<String> naam = dialog.showAndWait();
            if (naam.isPresent()) {
                studentsmodel.addStudents(naam.get());
            }
        }
    }

    public void teacherPopup() throws SQLException {
        if (fileselected) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add teacher");
            dialog.setHeaderText("Enter the name of the teacher");
            dialog.setContentText("Name: ");
            Optional<String> naam = dialog.showAndWait();
            if (naam.isPresent()) {
                teachersmodel.addTeacher(naam.get());
            }
        }
    }

    public void locationPopup() throws SQLException {
        if (fileselected) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add location");
            dialog.setHeaderText("Enter the name of the location");
            dialog.setContentText("Name: ");
            Optional<String> naam = dialog.showAndWait();
            if (naam.isPresent()) {
                locationsmodel.addLocation(naam.get());
            }
        }
    }
}
