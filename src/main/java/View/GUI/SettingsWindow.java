package View.GUI;

import Controller.Controller;
import Model.Tasks.ProcessTask;
import View.View;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import org.controlsfx.tools.Borders;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static javafx.stage.Modality.APPLICATION_MODAL;

public class SettingsWindow {
    private Controller controller;

    Stage settingsStage; TaskTable table; EditPane pane; MenuOnClick contextMenu; DownMenu downMenu;

    public SettingsWindow(Controller controller){
        this.controller = controller;
        table = new TaskTable();
        pane = new EditPane();
        contextMenu = new MenuOnClick();
        downMenu = new DownMenu();
    }

    public void create(List<ProcessTask> tasks){
        settingsStage = new Stage();
        Pane root = new VBox();
        root.setPadding(new Insets(25));
        Pane wrappedRoot = (Pane) Borders.wrap(root).lineBorder().color(Color.BLACK).title("Process list").buildAll();
        table.create(tasks);
        pane.create();
        downMenu.create();
        root.getChildren().addAll(pane.box, table.taskTableView, downMenu.box);
        Scene scene = new Scene(wrappedRoot, 600,600);
        settingsStage.setTitle("Processing task settings");
        settingsStage.initModality(APPLICATION_MODAL);
        settingsStage.setScene(scene);
        settingsStage.show();
    }

    public void update(List<ProcessTask> tasks){
        table.update(tasks);

    }

    class DownMenu{
        HBox box;

        void create(){
            this.box = new HBox();
            Button okButton = new Button("OK");
            okButton.setMinWidth(100);
            okButton.setOnAction(evt->{
                controller.saveSettings();
                settingsStage.close();
            });
            Button appButton = new Button("Apply");
            appButton.setMinWidth(100);
            appButton.setOnAction(evt -> {
                controller.saveSettings();
            });
            Button cnlButton = new Button("Cancel");
            cnlButton.setMinWidth(100);
            cnlButton.setOnAction(evt -> {
                controller.restoreSettings();
                settingsStage.close();
            });
            box.getChildren().addAll(okButton, appButton, cnlButton);
            box.setSpacing(50);
            box.setAlignment(Pos.CENTER_RIGHT);
            box.setPadding(new Insets(25));

        }
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
            taskTableView.setContextMenu(contextMenu.create());
            taskTableView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
                selected = newValue;
                contextMenu.selTask = newValue;
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
        GridPane box;

        ProcessTask selTask;
        TextField taskNameVal; TextField taskPathVal;
        Button okTaskButton; Button cancelButton;

        void create(){
            Label taskNameLab = new Label("Task name: ");
            taskNameVal = new TextField();
            Label taskPathLab = new Label("Path to the task script: ");
            taskPathVal = new TextField();
            okTaskButton = new Button("OK");
            okTaskButton.setOnAction(evt -> {
                int ind = Optional.ofNullable(selTask)
                        .map(e -> controller.remDefinedTask(e))
                        .orElse(-1);
                try{
                    controller.addDefinedTask(taskNameVal.getText(),
                                              taskPathVal.getText(),
                                              ind);
                } catch (IllegalArgumentException e){
                    View.alertDialog(e.getMessage());
                }
                table.taskTableView.setMouseTransparent(false);
                selTask = null;
                clearAndDisable();
            });
            cancelButton = new Button("Cancel");
            cancelButton.setOnAction(evt -> {
                table.taskTableView.setMouseTransparent(false);
                clearAndDisable();
                selTask = null;
            });
            clearAndDisable();
            okTaskButton.setMinWidth(100);
            cancelButton.setMinWidth(100);
            List<Node> nodes = Arrays.asList(taskNameLab, taskNameVal, okTaskButton,
                                             taskPathLab, taskPathVal, cancelButton);
            this.box = new GridPane();
            box.getChildren().addAll(nodes);
            for (int i = 0, row = 0, col = 0; i < nodes.size(); i++, row = i / 3, col = i % 3) {
                GridPane.setConstraints(nodes.get(i), col, row);
            }

        }

        private void clearAndDisable(){
            okTaskButton.setDisable(true);
            cancelButton.setDisable(true);
            taskPathVal.setText("");
            taskNameVal.setText("");
            taskNameVal.setDisable(true);
            taskPathVal.setDisable(true);
        }
    }

    class MenuOnClick{
        MenuItem addDefinedTask = new MenuItem("Add task");
        MenuItem remDefinedTask = new MenuItem("Remove task");
        MenuItem edtDefinedTask = new MenuItem("Edit task");
        ProcessTask selTask;

        ContextMenu create(){
            addDefinedTask.setOnAction(e-> {
                pane.okTaskButton.setDisable(false);
                pane.cancelButton.setDisable(false);
                pane.taskNameVal.setDisable(false);
                pane.taskPathVal.setDisable(false);
                pane.taskNameVal.setText("");
                pane.taskPathVal.setText("");
                table.taskTableView.setMouseTransparent(true);
            });
            remDefinedTask.setOnAction(e ->{
                controller.remDefinedTask(selTask);
            });
            edtDefinedTask.setOnAction(e -> {
                pane.okTaskButton.setDisable(false);
                pane.cancelButton.setDisable(false);
                pane.taskPathVal.setDisable(false);
                pane.taskNameVal.setDisable(false);
                pane.taskNameVal.setText(selTask.getLabel());
                pane.taskPathVal.setText(selTask.getScriptPath().toString());
                pane.selTask = selTask;
                table.taskTableView.setMouseTransparent(true);
            });
            ContextMenu menuOnClick = new ContextMenu();
            menuOnClick.getItems().addAll(addDefinedTask, remDefinedTask, edtDefinedTask);
            return menuOnClick;
        }
    }
}