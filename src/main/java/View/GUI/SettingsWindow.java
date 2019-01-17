package View.GUI;

import Model.Tasks.ProcessTask;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static javafx.stage.Modality.APPLICATION_MODAL;

public class SettingsWindow {

    Stage settingsStage; TaskTable table; EditPane pane;

    public SettingsWindow(){
        table = new TaskTable();
        pane = new EditPane();
    }

    public void create(List<ProcessTask> tasks){
        settingsStage = new Stage();
        Pane root = new VBox();
        table.create(tasks);
        pane.create();
        root.getChildren().addAll(pane.box, table.taskTableView);
        Scene scene = new Scene(root, 400,400);
        settingsStage.setTitle("Processing task settings");
        settingsStage.initModality(APPLICATION_MODAL);
        settingsStage.setScene(scene);
        settingsStage.show();
    }

    public void update(List<ProcessTask> tasks){

    }

    class TaskTable{
        TableView<ProcessTask> taskTableView;
        ProcessTask selected;

        void create(List<ProcessTask> tasks){
            taskTableView = new TableView<>();
            TableColumn<ProcessTask, String> c1 = new TableColumn<>("Name");
            c1.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue().getLabel()));
            TableColumn<ProcessTask, String> c2 = new TableColumn<>("Path");
            c2.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue().getScriptPath().toString()));
            taskTableView.getItems().addAll(tasks);
            taskTableView.getColumns().addAll(c1, c2);
            taskTableView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
                selected = newValue;
                pane.update();
            }));
        }

    }

    class EditPane{
        HBox box;

        void create(){
            Label taskNameLab = new Label("Task name: ");
            TextField taskNameVal = new TextField();
            this.box = new HBox(taskNameLab,taskNameVal);
        }

        void update(){
            Label taskNameLab = new Label("Task name: ");
            TextField taskNameVal = new TextField();
            taskNameVal.setText(Optional.ofNullable(table.selected)
                                        .map(ProcessTask::getLabel)
                                        .orElse(""));
            box.getChildren().removeIf(Objects::nonNull);
            box.getChildren().addAll(taskNameLab,taskNameVal);

        }
    }
}
