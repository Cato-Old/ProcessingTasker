package View.GUI;

import Controller.Controller;
import Model.Projects.Project;
import Model.Projects.Publications.Publication;
import Model.Tasks.CopyAndRenumberTask;
import Model.Tasks.ProcessTask;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.util.StringConverter;

import java.nio.file.Path;
import java.util.*;

public class CenterPane {
    Controller controller;

    GridPane centerPane; TaskComboBox taskComboBox;

    public CenterPane(Controller controller) {
        this.controller = controller;
    }

    class TaskComboBox{
        ComboBox<Class<ProcessTask>> view;
        ProcessTask selected;

        void create(){
            view = new ComboBox<>();
            List<Class> taskListClass = Arrays.asList(CopyAndRenumberTask.class);
            taskListClass.stream()
                    .forEach(e -> view.getItems().add(e));
            view.setConverter(new StringConverter<Class<ProcessTask>>() {
                @Override
                public String toString(Class<ProcessTask> object) {
                    String result;
                    try {
                        result = (String) object.getDeclaredField("label").get(null);
                    } catch (Exception e) {
                        result = "To be implemented";
                    }
                    return result;
                }

                @Override
                public Class<ProcessTask> fromString(String string) {
                    return null;
                }
            });
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
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        col1.setPercentWidth(40);
        col2.setPercentWidth(40);
        col3.setPercentWidth(20);
        GridPane.setConstraints(sourcePathLab, 0, 0, 1, 1);
        GridPane.setConstraints(sourcePathVal, 1, 0, 1, 1);
        GridPane.setConstraints(targetPathLab, 0, 1, 1, 1);
        GridPane.setConstraints(targetPathVal, 1, 1, 1, 1);
        GridPane.setConstraints(targetPathSet, 2, 1, 1, 1);
        centerPane.getColumnConstraints().addAll(col1, col2, col3);
        centerPane.getChildren().addAll(sourcePathLab, sourcePathVal, targetPathLab, targetPathVal, targetPathSet);
    }

    public void update(Publication pub){
        taskComboBox = new TaskComboBox();
        taskComboBox.create();
        Label sourcePathLab = new Label("Path to source directory: ");
        Label sourcePathVal = new Label(pub.getSourcePath().toString());
        Label targetPathLab = new Label("Path to target directory: ");
        Label targetPathVal = new Label(Optional.ofNullable(pub.getTargetPath())
                                                .map(Path::toString)
                                                .orElse("..."));
        Label currStatusLab = new Label("Current processing status: ");
        Label currStatusVal = new Label(pub.getState().getLabel());
        Label procTaskerLab = new Label("Select task to carry out: ");
        centerPane.getChildren().removeIf(Objects::nonNull);
        centerPane.getColumnConstraints().removeIf(Objects::nonNull);
        centerPane.getRowConstraints().removeIf(Objects::nonNull);
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        col1.setPercentWidth(40);
        col2.setPercentWidth(40);
        col3.setPercentWidth(20);
        GridPane.setConstraints(sourcePathLab, 0, 0, 1, 1);
        GridPane.setConstraints(sourcePathVal, 1, 0, 1, 1);
        GridPane.setConstraints(targetPathLab, 0, 1, 1, 1);
        GridPane.setConstraints(targetPathVal, 1, 1, 1, 1);
        GridPane.setConstraints(currStatusLab, 0, 2, 1, 1);
        GridPane.setConstraints(currStatusVal, 1, 2, 1, 1);
        GridPane.setConstraints(procTaskerLab, 0, 3, 1, 1);
        GridPane.setConstraints(taskComboBox.view, 1,3,1,1);
        centerPane.getColumnConstraints().addAll(col1, col2, col3);
        centerPane.getChildren().addAll(sourcePathLab, sourcePathVal,
                                        targetPathLab, targetPathVal,
                                        currStatusLab, currStatusVal,
                                        procTaskerLab, taskComboBox.view);
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
}