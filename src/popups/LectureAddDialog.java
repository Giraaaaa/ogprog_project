/*
@author Sieben Veldeman
 */

package popups;

import databank.db_objects.*;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import models.Model;

import java.sql.SQLException;

public class LectureAddDialog extends Dialog {

    private Model model;
    private ComboBox<Teacher> teacherComboBox;
    private ComboBox<Students> studentsComboBox;
    private ComboBox<Location> locationComboBox;
    private ComboBox<Period> periodComboBox;
    private TextField coursefield;
    private ComboBox<String> dayComboBox;
    private Spinner<Integer> durationspinner;
    private Button addknop;

    public LectureAddDialog(Model model, ObservableList<Period> periodes) {
        this.model = model;
        ObservableList<Teacher> teacherkeuzes = model.getTeachers();
        ObservableList<Students> studentskeuzes = model.getStudents();
        ObservableList<Location> locationkeuzes = model.getLocations();
        // Voegt een verborgen close-button toe, zodat de dialog wel kan gesloten worden via het kruisje.
        // Dit komt door slecht design in javafx?
        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        // We maken nu telkens comboboxes en labels om de gebruiker de eigenschappen van de lessen te laten kiezen
        studentsComboBox = new ComboBox<>(studentskeuzes);
        Label studentslabel = new Label("Choose student group:");
        grid.add(studentslabel, 0, 0);
        grid.add(studentsComboBox, 1, 0);
        teacherComboBox = new ComboBox<>(teacherkeuzes);
        Label teacherlabel = new Label("Choose a teacher:");
        grid.add(teacherlabel, 0, 1);
        grid.add(teacherComboBox, 1, 1);
        locationComboBox = new ComboBox<>(locationkeuzes);
        Label locationlabel = new Label("Choose location:");
        grid.add(locationlabel, 0, 2);
        grid.add(locationComboBox, 1, 2);
        periodComboBox = new ComboBox<>(periodes);
        Label periodlabel = new Label("Choose period");
        grid.add(periodlabel, 0, 3);
        grid.add(periodComboBox, 1, 3);
        coursefield = new TextField();
        Label courselabel = new Label("Enter course:");
        grid.add(courselabel, 0, 4);
        grid.add(coursefield, 1, 4);
        durationspinner = new Spinner<>(1, periodes.size(), 1);
        Label durationlabel = new Label("Choose duration: ");
        grid.add(durationlabel, 0, 5);
        grid.add(durationspinner, 1, 5);
        dayComboBox = new ComboBox<>();
        dayComboBox.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        Label daylabel = new Label("Choose day");
        grid.add(daylabel, 0, 6);
        grid.add(dayComboBox, 1, 6);
        addknop = new Button("Add");
        addknop.setOnAction(ae -> pressed());
        grid.add(addknop,1, 7);
        getDialogPane().setContent(grid);
        showAndWait();
    }

    public void pressed() {
        // Deze alert staat er voor het geval dat de gebruiker geen complete input geeft.
        Alert alert = new Alert(Alert.AlertType.ERROR, "One or more properties were not specified", ButtonType.CLOSE);
        alert.setHeaderText("Cannot add lecture");
        // De les die we gaan toevoegen
        Lecture les;
        try {
            int studentsid = studentsComboBox.getSelectionModel().getSelectedItem().getId();
            int teacherid = teacherComboBox.getSelectionModel().getSelectedItem().getId();
            int locationid = locationComboBox.getSelectionModel().getSelectedItem().getId();
            String course = coursefield.getText();
            int day = dayComboBox.getSelectionModel().getSelectedIndex() + 1;
            int firstblock = periodComboBox.getSelectionModel().getSelectedItem().getId();
            int duration = durationspinner.getValue();
            les = new Lecture(studentsid, teacherid, locationid, course, day, firstblock, duration);
            if (course.isEmpty() || day == 0) {
                alert.show();
            }
            else if (periodComboBox.getItems().indexOf(periodComboBox.getSelectionModel().getSelectedItem()) + duration > periodComboBox.getItems().size()) {
                // In dit geval heeft de gebruiker een onmogelijke duration ogegeven.
                Alert telang = new Alert(Alert.AlertType.ERROR, "Duration cannot be this long", ButtonType.CLOSE);
                alert.setHeaderText("Cannot add lecture");
                telang.show();
            }
            // Kijken of deze les nog niet in de db zit.
            else if (model.uniquelecture(les)) {
                    model.addLecture(les);
                    close();
            }
            else {
                Alert nietuniek = new Alert(Alert.AlertType.ERROR, "Deze les is zit al in de databank", ButtonType.CLOSE);
                nietuniek.show();
            }

        } catch (NullPointerException ex) {
            alert.show();
        }
    }
}
