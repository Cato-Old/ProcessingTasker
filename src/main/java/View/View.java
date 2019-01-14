package View;import Controller.Controller;import Model.Model;import Model.Projects.Project;import Model.Projects.Publications.Publication;import View.GUI.CenterPane;import View.GUI.PublicationTree;import View.GUI.TaskList;import javafx.geometry.Pos;import javafx.scene.Scene;import javafx.scene.control.Alert;import javafx.scene.control.Button;import javafx.scene.control.TextArea;import javafx.scene.layout.BorderPane;import javafx.scene.layout.Pane;import javafx.stage.DirectoryChooser;import javafx.stage.Stage;import java.io.File;import java.nio.file.Path;import java.util.Observable;import java.util.Observer;import static View.ViewElement.*;import static javafx.scene.control.Alert.AlertType.ERROR;public class View implements Observer {    Model model;    Controller controller;    Stage primaryStage;    PublicationTree pubTree; CenterPane proPane; TaskList taskLst;    public View(Model model, Controller controller, Stage stage){        this.model = model;        this.controller = controller;        primaryStage = stage;        model.addObserver(this);    }    public static void alertDialog(String exMessage){        Alert dialog = new Alert(ERROR, exMessage);        dialog.showAndWait();    }    public File chooseDir(){        DirectoryChooser dirChooser = new DirectoryChooser();        return dirChooser.showDialog(primaryStage);    }    public File chooseDir(Path initialPath){        DirectoryChooser dirChooser = new DirectoryChooser();        dirChooser.setInitialDirectory(initialPath.toFile());        return dirChooser.showDialog(primaryStage);    }    @Override    public void update(Observable o, Object arg) {        ViewElement message = arg instanceof ViewElement ? (ViewElement) arg : null;        if (message == PUBLICATION_TREE){            pubTree.update(model.getProjects());        } else if (message == CENTER_PANE) {            Project project = (Project) pubTree                    .getTreeView()                    .getSelectionModel()                    .getSelectedItem()                    .getValue();            proPane.update(project);        } else if (message == TASK_LIST){            taskLst.update(model.getTasks());        }    }    public void update(ViewElement message, Object arg) {        if (message == CENTER_PANE && arg == null){            proPane.update();        } else if (message == CENTER_PANE && arg instanceof Project) {            proPane.update((Project) arg);        } else if (message == CENTER_PANE && arg instanceof Publication){            proPane.update((Publication) arg);        }    }    public void create(){        pubTree = new PublicationTree(controller);        proPane = new CenterPane(controller);        taskLst = new TaskList(controller);        Pane pubTreeView = pubTree.create(model.getProjects());        Pane proPaneView = proPane.create();        Pane taskLstView = taskLst.create(model.getTasks());        pubTreeView.setPrefWidth(300);        taskLstView.setPrefWidth(300);        TextArea logView = new TextArea("abrakadabra\nabrakadabra");        logView.setEditable(false);        Button addText = new Button("Add text");        Button getText = new Button("Get text");        Button clearText = new Button ("Clear");        getText.setOnAction(e -> System.out.println(logView.getText()));        addText.setOnAction(e -> {            logView.setText(logView.getText() + "\nabrakadabra");            logView.selectPositionCaret(logView.getLength());            logView.deselect();        });        BorderPane root = new BorderPane(proPaneView,                                         clearText,                                         taskLstView,                                         logView,                                         pubTreeView);        System.out.println(proPaneView.isResizable());        BorderPane.setAlignment(proPaneView, Pos.CENTER);        Scene scene = new Scene(root, 800, 600);        primaryStage.setTitle("Moje okno");        primaryStage.setScene(scene);        primaryStage.show();    }}