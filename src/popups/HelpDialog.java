package popups;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HelpDialog extends Dialog {

    public HelpDialog() {
        VBox box = new VBox();
        box.setSpacing(15);
        Label doel = new Label("This program is designed to give a graphical representation of databases following the lectures scheme, " +
                "it does not offer support for other database schemes.");
        doel.setStyle("-fx-font-style: italic");
        Label usage = new Label("A single click on a teacher/students/location, gives the associate lectures. Editing can be done by clicking twice.");
        Label otherusage = new Label("All other functionality is provided in the menubar at the top");
        Label credits = new Label("Written by Sieben Veldeman - 2018");
        credits.setStyle("-fx-font-style: italic;" + "-fx-text-fill: black");
        box.getChildren().add(doel);
        box.getChildren().add(usage);
        box.getChildren().add(otherusage);
        box.getChildren().add(credits);
        getDialogPane().setContent(box);
        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        showAndWait();
    }
}
