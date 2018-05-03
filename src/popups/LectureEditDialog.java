package popups;


import databank.db_objects.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import models.Model;

import java.sql.SQLException;

// Lijkt op de lectureadddialog
public class LectureEditDialog extends Dialog {

    private Model model;
    ListView<Lecture> lectures;
    private ObservableList<Teacher> teacherkeuzes;
    private ObservableList<Location> locationkeuzes;
    private ObservableList<Students> studentskeuzes;
    private ObservableList<Period> periodekeuzes;
    private ComboBox<Teacher> teacherComboBox;
    private ComboBox<Students> studentsComboBox;
    private ComboBox<Location> locationComboBox;
    private ComboBox<Period> periodComboBox;
    private TextField coursefield;
    private ComboBox<String> dayComboBox;
    private Spinner<Integer> durationspinner;
    private Button editknop;
    private Button removeknop;


    public LectureEditDialog(Model model) throws SQLException {
        try {
            this.model = model;
            teacherkeuzes = model.getTeachers();
            studentskeuzes = model.getStudents();
            locationkeuzes = model.getLocations();
            periodekeuzes = FXCollections.observableArrayList(model.getPeriods());
            teacherComboBox = new ComboBox<>(teacherkeuzes);
            studentsComboBox = new ComboBox<>(studentskeuzes);
            locationComboBox = new ComboBox<>(locationkeuzes);
            // Voegt een verborgen close-button toe, zodat de dialog wel kan gesloten worden via het kruisje.
            // Dit komt door slecht design in javafx?
            getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            Node closeButton = getDialogPane().lookupButton(ButtonType.CLOSE);
            closeButton.managedProperty().bind(closeButton.visibleProperty());
            closeButton.setVisible(false);
            lectures = new ListView<>(model.getCurrentLectures());
            lectures.setPrefHeight(300);
            lectures.getSelectionModel().selectedItemProperty().addListener(select -> selected());
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            Label studentslabel = new Label("Edit student group:");
            grid.add(studentslabel, 0, 0);
            grid.add(studentsComboBox, 1, 0);
            Label teacherlabel = new Label("Edit teacher:");
            grid.add(teacherlabel, 0, 1);
            grid.add(teacherComboBox, 1, 1);
            Label locationlabel = new Label("Choose location:");
            grid.add(locationlabel, 0, 2);
            grid.add(locationComboBox, 1, 2);
            Label periodlabel = new Label("Choose period");
            periodComboBox = new ComboBox<>(periodekeuzes);
            grid.add(periodlabel, 0, 3);
            grid.add(periodComboBox, 1, 3);
            coursefield = new TextField();
            Label courselabel = new Label("Enter course:");
            grid.add(courselabel, 0, 4);
            grid.add(coursefield, 1, 4);
            durationspinner = new Spinner<>(1, model.getPeriods().size(), 1);
            Label durationlabel = new Label("Choose duration: ");
            grid.add(durationlabel, 0, 5);
            grid.add(durationspinner, 1, 5);
            dayComboBox = new ComboBox<>();
            dayComboBox.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
            Label daylabel = new Label("Choose day");
            grid.add(daylabel, 0, 6);
            grid.add(dayComboBox, 1, 6);
            editknop = new Button("Edit");
            editknop.setOnAction(ae -> edited());
            grid.add(editknop, 1, 7);
            removeknop = new Button(("Remove"));
            removeknop.setOnAction(ae -> remove());
            grid.add(removeknop, 0, 7);
            VBox vbox = new VBox(lectures, grid);
            vbox.setSpacing(10);
            getDialogPane().setContent(vbox);
            showAndWait();
        } catch (NullPointerException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No lectures selected");
            alert.show();
        }
    }


    public void edited() {
        // We verwijderen de lecture en voegen hem dan weer toe met de updated data,
        // dit voorkomt problemen met primary keys
        model.removeLecture(lectures.getSelectionModel().getSelectedItem());
        model.addLecture(new Lecture(studentsComboBox.getSelectionModel().getSelectedItem().getId(), teacherComboBox.getSelectionModel().getSelectedItem().getId(), locationComboBox.getSelectionModel().getSelectedItem().getId(), coursefield.getText(), dayComboBox.getSelectionModel().getSelectedIndex() + 1, periodComboBox.getSelectionModel().getSelectedItem().getId(), durationspinner.getValue()));

    }

    public void remove() {
        model.removeLecture(lectures.getSelectionModel().getSelectedItem());
        close();
    }

    public void selected() {

        // Stel de huidige waarden in van de lecture, zodat de gebruiker kan zien welke hij/zij selecteerde.
        Lecture selectedlecture = lectures.getSelectionModel().getSelectedItem();
        for (Students students : studentskeuzes) {
            if (selectedlecture.getStudent_id() == students.getId()) {
                studentsComboBox.getSelectionModel().select(students);
            }
        }
        for (Teacher teacher : teacherkeuzes) {
            if (selectedlecture.getTeacher_id() == teacher.getId()) {
                teacherComboBox.getSelectionModel().select(teacher);
            }
        }
        for (Location location : locationkeuzes) {
            if (selectedlecture.getLocation_id() == location.getId()) {
                locationComboBox.getSelectionModel().select(location);
            }
        }
        for (Period period : periodekeuzes) {
            if (selectedlecture.getFirst_block() == period.getId()) {
                periodComboBox.getSelectionModel().select(period);
            }
        }
        coursefield.setText(selectedlecture.getCourse());
        durationspinner.getValueFactory().setValue(selectedlecture.getDuration());
        dayComboBox.getSelectionModel().select(selectedlecture.getDay() - 1);
    }
    
}
