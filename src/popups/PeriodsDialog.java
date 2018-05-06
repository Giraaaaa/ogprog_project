/*
@author Sieben Veldeman
 */

package popups;


import databank.database_algemeen.DataAccessContext;
import databank.database_algemeen.DataAccessProvider;
import databank.database_algemeen.PeriodDAO;
import databank.db_objects.Period;
import databank.jdbc_implementatie.JDBCDataAccessProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import models.Model;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class PeriodsDialog extends Dialog {

    private Model model;

    private TextField uurveld;
    private Label uurlabel;
    private TextField minutenveld;
    private Label minutenlabel;
    private Button knop;
    // Dit label toont een bericht wanneer de user een onmogelijke tijd ingeeft.
    private Label warning;
    // In deze hbox komen alle fields en de knop.
    private HBox box;
    // Deze listview toont de periodes die zullen worden toegevoegd, zodat de gebruiker een overview heeft.
    private ListView<Period> periodesview;
    // De lijst waarin de periodes komen die worden toegevoegd.
    private ObservableList<Period> periodes;

    public PeriodsDialog(Model model) {
        this.model = model;
        setTitle("Add a period");
        // Label en een textfields toevoegen, om periodes te inputten
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        uurveld = new TextField();
        uurveld.setPrefWidth(50);
        uurlabel = new Label("Hour:");
        minutenlabel = new Label("Minutes:");
        minutenveld = new TextField();
        minutenveld.setPrefWidth(50);
        warning = new Label("onmogelijk tijdstip");
        warning.setFont(new Font("Verdana", 16.0));
        warning.setStyle("-fx-text-fill: red");
        warning.setVisible(false);
        knop = new Button("Add");
        knop.setOnAction(event -> pressed());
        box = new HBox(5, uurlabel, uurveld, minutenlabel, minutenveld, knop);
        periodesview = new ListView<>();
        periodes = FXCollections.observableArrayList();
        grid.add(box, 0, 1);
        grid.add(periodesview, 0, 0);
        grid.add(warning, 0, 2);
        Button closeknop = new Button();
        closeknop.setText("Close");
        closeknop.setOnMouseClicked(eh -> sluit());
        grid.add(closeknop, 0, 3);
        getDialogPane().setContent(grid);
        showAndWait();
    }

    private void pressed() {
        if (!uurveld.getText().matches("-?\\d+") || ! minutenveld.getText().matches("-?\\d+")) {
            uurveld.setStyle("-fx-text-inner-color: red");
            minutenveld.setStyle("-fx-text-inner-color: red");
            warning.setVisible(true);
        }
        else {
            int uur = Integer.parseInt(uurveld.getText());
            int minuten = Integer.parseInt(minutenveld.getText());
            if (uur > 23 || minuten > 59) {
                uurveld.setStyle("-fx-text-inner-color: red");
                minutenveld.setStyle("-fx-text-inner-color: red");
                warning.setVisible(true);
            } else {
                // Voor we de periode kunnen toevoegen moeten we eerst checken of ze uniek is.
                for (Period periode : periodes) {
                    // In dit geval is de periode niet uniek
                    if (uur == periode.getHour() && minuten == periode.getMinute()) {
                        warning.setText("Periode bestaat al");
                        warning.setVisible(true);
                        return;
                    }
                }

                int id = model.createPeriod(uur, minuten);
                periodes.add(new Period(id, uur, minuten));
                uurveld.setStyle(null);
                minutenveld.setStyle(null);
                warning.setVisible(false);
                // In het geval dat de text op de warning veranderd was
                warning.setText("onmogelijk tijdstip.");
                periodesview.setItems(periodes);
            }
        }
    }

    private void sluit() {
        if (periodes.size() < 1) {
            Alert nietgenoeg = new Alert(Alert.AlertType.ERROR);
            nietgenoeg.setHeaderText("No periods specified");
            nietgenoeg.show();
        }
        else {
            // We moeten een dummy closebutton toevoegen omdat javafx design dit vraagt, we kunnen anders niet programmatisch
            // de dialoog sluiten.
            getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            close();
        }
    }
}
