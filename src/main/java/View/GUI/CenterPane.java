package View.GUI;

import Controller.Controller;
import Model.Projects.Project;
import Model.Projects.Publications.Publication;
import Model.Tasks.ProcessTask;
import Model.Tasks.ProcessTaskSettings;
import View.GUI.StringConverters.ComboBoxStringConverter;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import java.nio.file.Path;
import java.util.*;

public class CenterPane {
    Controller controller;

    GridPane centerPane; TaskComboBox taskComboBox;

    public CenterPane(Controller controller) {
        this.controller = controller;
    }

    class TaskComboBox{
        ComboBox<ProcessTask> view;
        ProcessTask selected;

        void create(List<ProcessTask> taskList){
            view = new ComboBox<>();
            taskList.forEach(e -> view.getItems().add(e));
            view.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                selected = newValue;
                });
            view.setConverter(new ComboBoxStringConverter());
        }

        void update(List<ProcessTask> taskList){
            view.getItems().removeIf(Objects::nonNull);
            taskList.forEach(e -> view.getItems().add(e));
        }
    }

    public Pane create() {
        centerPane = new GridPane();
        createEmptyProjectView();
        return centerPane;
    }

    public void update(Project project) {
        Label sourcePathLab = new Label("Path to source directory: ");
        Label sourcePathVal = new Label(project.getSourcePath().toString());
        Label targetPathLab = new Label("Path to target directory: ");
        Label targetPathVal = new Label(Optional.ofNullable(project.getTargetPath())
                                                .map(Path::toString)
                                                .orElse("..."));
        Button targetPathSet = new Button("Set catalog");
        targetPathSet.setOnAction(evt -> {
            try {
                controller.setTargetPath(project, controller.chooseDir());
            } catch (IllegalArgumentException ignore) {
            }
        });
        centerPane.getChildren().removeIf(Objects::nonNull);
        centerPane.getColumnConstraints().removeIf(Objects::nonNull);
        centerPane.getRowConstraints().removeIf(Objects::nonNull);
        GridPane.setConstraints(sourcePathLab, 0, 0);
        GridPane.setConstraints(sourcePathVal, 1, 0);
        GridPane.setConstraints(targetPathLab, 0, 1);
        GridPane.setConstraints(targetPathVal, 1, 1);
        GridPane.setConstraints(targetPathSet, 2, 1, 1, 1);
        centerPane.getColumnConstraints().addAll(getColumnConstraintsList(40, 40, 20));
        centerPane.getChildren().addAll(sourcePathLab, sourcePathVal, targetPathLab, targetPathVal, targetPathSet);
    }

    public void update(Publication pub, List<ProcessTask> taskList){
        taskComboBox = new TaskComboBox();
        taskComboBox.create(taskList);
        List<Node> nodes = Arrays.asList(
                new Label("Path to source directory: "), new Label(pub.getSourcePath().toString()),
                new Label("Path to target directory: "), new Label(Optional.ofNullable(pub.getTargetPath())
                                                                           .map(Path::toString)
                                                                           .orElse("...")),
                new Label("Current processing status: "), new Label(pub.getState().getLabel()),
                new Label("Select task to carry out: "), taskComboBox.view);
        Button selTaskButton = new Button("Select");
        selTaskButton.setOnAction(evt -> {
            try {
                controller.addTask(taskComboBox.selected, pub);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        centerPane.getChildren().removeIf(Objects::nonNull);
        centerPane.getColumnConstraints().removeIf(Objects::nonNull);
        centerPane.getRowConstraints().removeIf(Objects::nonNull);
        for (int i = 0, row = 0, col = 0; i < nodes.size(); i++, row = i / 2, col = i % 2) {
            GridPane.setConstraints(nodes.get(i), col, row);
        }
        GridPane.setConstraints(selTaskButton, 2,3);
        centerPane.getColumnConstraints().addAll(getColumnConstraintsList(40, 40, 20));
        centerPane.getChildren().addAll(nodes);
        centerPane.getChildren().add(selTaskButton);
    }

    public void update(List<ProcessTask> taskList){
        Optional.ofNullable(taskComboBox)
                .ifPresent(e -> e.update(taskList));
    }

    public void update(){
        centerPane.getChildren().removeIf(Objects::nonNull);
        centerPane.getColumnConstraints().removeIf(Objects::nonNull);
        centerPane.getRowConstraints().removeIf(Objects::nonNull);
        createEmptyProjectView();
    }

    private void createEmptyProjectView() {
        Label noProjectLab = new Label("There is no selected project or publication");
        ColumnConstraints col = new ColumnConstraints();
        RowConstraints row = new RowConstraints();
        col.setPercentWidth(100);
        row.setPercentHeight(100);
        centerPane.getColumnConstraints().add(col);
        centerPane.getRowConstraints().add(row);
        GridPane.setConstraints(noProjectLab, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER);
        centerPane.getChildren().add(noProjectLab);
    }

    private List<ColumnConstraints> getColumnConstraintsList(int...args){
        ColumnConstraints[] colConstraintsArr = new ColumnConstraints[args.length];
        for (int i = 0; i < args.length; i++){
            colConstraintsArr[i] = new ColumnConstraints();
        }
        List<ColumnConstraints> colConstsraintsList = Arrays.asList(colConstraintsArr);
        for (int i = 0; i < args.length; i++) {
            colConstsraintsList.get(i).setPercentWidth(args[i]);
        }
        return colConstsraintsList;
    }
}