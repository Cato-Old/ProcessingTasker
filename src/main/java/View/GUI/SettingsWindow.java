package View.GUI;

import Controller.Controller;
import Model.Tasks.ProcessTask;
import View.View;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static javafx.stage.Modality.APPLICATION_MODAL;

public class SettingsWindow {
    private Controller controller;

    Stage settingsStage; TaskTable table; EditPane pane;

    public SettingsWindow(Controller controller){
        this.controller = controller;
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
        table.update(tasks);

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

        void update(List<ProcessTask> tasks) {
            TableColumn<ProcessTask, String> c1 = new TableColumn<>("Name");
            c1.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue().getLabel()));
            TableColumn<ProcessTask, String> c2 = new TableColumn<>("Path");
            c2.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue().getScriptPath().toString()));
            taskTableView.getItems().removeIf(Objects::nonNull);
            taskTableView.getColumns().removeIf(Objects::nonNull);
            taskTableView.getItems().addAll(tasks);
            taskTableView.getColumns().addAll(c1, c2);

        }

    }

    class EditPane{
        HBox box;

        void create(){
            Label taskNameLab = new Label("Task name: ");
            TextField taskNameVal = new TextField();
            Label taskPathLab = new Label("Path to the task script: ");
            TextField taskPathVal = new TextField();
            Button addTaskButton = new Button("Add task");
            addTaskButton.setDisable(true);
            this.box = new HBox(taskNameLab, taskNameVal,
                                taskPathLab, taskPathVal,
                                addTaskButton);
        }

        void update(){
            Label taskNameLab = new Label("Task name: ");
            System.out.println(table.selected);
            TextField taskNameVal = new TextField(Optional.ofNullable(table.selected)
                                                          .map(ProcessTask::getLabel)
                                                          .orElse(""));
            Label taskPathLab = new Label("Path to the task script: ");
            TextField taskPathVal = new TextField(Optional.ofNullable(table.selected)
                                                          .map(e -> e.getScriptPath().toString())
                                                          .orElse(""));
            Button addTaskButton = new Button("Add task");
            addTaskButton.setOnAction(e-> {
                taskNameVal.setText("");
                taskPathVal.setText("");
                table.taskTableView.setMouseTransparent(true);
            });
            Button confirmAddButton = new Button("Confirm");
            confirmAddButton.setOnAction(evt -> {
                try{
                    controller.addDefinedTask(taskNameVal.getText(), taskPathVal.getText());
                } catch (IllegalArgumentException e){
                    View.alertDialog(e.getMessage());
                }
                table.taskTableView.setMouseTransparent(false);
            });
            box.getChildren().removeIf(Objects::nonNull);
            box.getChildren().addAll(taskNameLab, taskNameVal,
                                     taskPathLab, taskPathVal,
                                     addTaskButton, confirmAddButton);

        }
    }
}
