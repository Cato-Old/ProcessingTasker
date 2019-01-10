package View.GUI;import Controller.Controller;import Model.Projects.Project;import View.View;import javafx.geometry.HPos;import javafx.geometry.VPos;import javafx.scene.control.Button;import javafx.scene.layout.*;import javafx.scene.control.Label;import javafx.stage.Stage;import java.nio.file.Path;import java.util.Objects;import java.util.Optional;public class ProjectPane {    private Controller controller;    private Stage stage; private GridPane publicPane;    public ProjectPane(Controller controller, Stage stage){        this.controller = controller;        this.stage = stage;    }    public Pane create(){        publicPane = new GridPane();        createEmptyProjectView();        return publicPane;    }    public void update(Project project){        if (project != null){            Label sourcePathLab = new Label("Path to source directory: ");            Label sourcePathVal = new Label(project.getSourcePath().toString());            Label targetPathLab = new Label("Path to target directory: ");            Label targetPathVal = new Label(Optional.ofNullable(project.getTargetPath())                                                    .map(Path::toString)                                                    .orElse("..."));            Button targetPathSet = new Button("Set catalog");            targetPathSet.setOnAction(evt -> {                try {                    controller.setTargetPath(project, controller.chooseDir());                } catch (IllegalArgumentException e) {                    View.alertDialog(e.getMessage());                }            });            publicPane.getChildren().removeIf(Objects::nonNull);            publicPane.getColumnConstraints().removeIf(Objects::nonNull);            publicPane.getRowConstraints().removeIf(Objects::nonNull);            ColumnConstraints col1 = new ColumnConstraints();            ColumnConstraints col2 = new ColumnConstraints();            ColumnConstraints col3 = new ColumnConstraints();            col1.setPercentWidth(40);            col2.setPercentWidth(40);            col3.setPercentWidth(20);            GridPane.setConstraints(sourcePathLab,0,0,1,1);            GridPane.setConstraints(sourcePathVal,1,0,1,1);            GridPane.setConstraints(targetPathLab,0,1,1,1);            GridPane.setConstraints(targetPathVal,1,1,1,1);            GridPane.setConstraints(targetPathSet,2,1,1,1);            publicPane.getColumnConstraints().addAll(col1, col2, col3);            publicPane.getChildren().addAll(sourcePathLab, sourcePathVal, targetPathLab, targetPathVal, targetPathSet);        } else {            publicPane.getChildren().removeIf(Objects::nonNull);            publicPane.getColumnConstraints().removeIf(Objects::nonNull);            publicPane.getRowConstraints().removeIf(Objects::nonNull);            createEmptyProjectView();        }    }    private void createEmptyProjectView(){        Label noProjectLab = new Label("There is no selected project");        ColumnConstraints col = new ColumnConstraints();        RowConstraints row = new RowConstraints();        col.setPercentWidth(100);        row.setPercentHeight(100);        publicPane.getColumnConstraints().add(col);        publicPane.getRowConstraints().add(row);        GridPane.setConstraints(noProjectLab,0,0,1,1,HPos.CENTER,VPos.CENTER);        publicPane.getChildren().add(noProjectLab);    }}