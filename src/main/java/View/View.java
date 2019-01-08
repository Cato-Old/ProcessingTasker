package View;import Controller.Controler;import Projects.Projects;import View.GUI.PublicationTree;import javafx.scene.Scene;import javafx.scene.control.Button;import javafx.scene.control.TextArea;import javafx.scene.layout.BorderPane;import javafx.stage.Stage;public class View {    public void create(Stage primaryStage){        TextArea logView = new TextArea("abrakadabra\nabrakadabra");        logView.setEditable(false);        Button addText = new Button("Add text");        Button getText = new Button("Get text");        Button clearText = new Button ("Clear");        getText.setOnAction(e -> System.out.println(logView.getText()));        addText.setOnAction(e -> {            logView.setText(logView.getText() + "\nabrakadabra");            logView.selectPositionCaret(logView.getLength());            logView.deselect();        });        clearText.setOnAction(e -> new Tasks.CopyAndRenumberTask(logView).run());        Projects projects = new Projects();        Controler controler = new Controler(projects, primaryStage);        PublicationTree pubTree = controler.getPublicationTree();        BorderPane root = new BorderPane(logView,                                         addText,                                         clearText,                                         getText,                                         pubTree.get());        Scene scene = new Scene(root, 300, 300);        primaryStage.setTitle("Moje okno");        primaryStage.setScene(scene);        primaryStage.show();    }}