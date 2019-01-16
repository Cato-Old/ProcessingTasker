package View.GUI;

import Model.Tasks.ProcessTask;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

import static javafx.stage.Modality.APPLICATION_MODAL;

public class SettingsWindow {

    TaskTable taskTable;

    public SettingsWindow(){
        taskTable = new TaskTable();
    }

    public void create(List<ProcessTask> tasks){
        Stage settingsStage = new Stage();
        Pane root = new VBox();
        taskTable.create(tasks);
        root.getChildren().addAll(new Label("Hello world"), taskTable.taskTableView);
        Scene scene = new Scene(root, 400,400);
        settingsStage.setTitle("Processing task settings");
        settingsStage.initModality(APPLICATION_MODAL);
        settingsStage.setScene(scene);
        settingsStage.show();
    }
    class TaskTable{
        TableView<ProcessTask> taskTableView;

        void create(List<ProcessTask> tasks){
            taskTableView = new TableView<>();
            TableColumn<ProcessTask, String> c1 = new TableColumn<>("Name");
            c1.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue().getLabel()));
            TableColumn<ProcessTask, String> c2 = new TableColumn<>("Path");
            c2.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue().getScriptPath().toString()));
            taskTableView.getItems().addAll(tasks);
            taskTableView.getColumns().addAll(c1, c2);
        }
    }
}
