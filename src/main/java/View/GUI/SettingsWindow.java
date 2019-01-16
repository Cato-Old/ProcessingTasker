package View.GUI;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static javafx.stage.Modality.APPLICATION_MODAL;

public class SettingsWindow {

    public void create(){
        Stage settingsStage = new Stage();
        Pane root = new StackPane();
        root.getChildren().add(new Label("Hello world"));
        Scene scene = new Scene(root, 400,400);
        settingsStage.setTitle("Processing task settings");
        settingsStage.initModality(APPLICATION_MODAL);
        settingsStage.setScene(scene);
        settingsStage.show();
    }
}
