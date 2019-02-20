package View.GUI;

import Controller.Controller;
import Model.Tasks.ProcessTask;
import View.GUI.StringConverters.TaskListViewStringConverter;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Objects;

import static javafx.scene.layout.Priority.ALWAYS;

public class TaskList {
    Controller controller;

    private Menu menu; ListView<ProcessTask> list;

    public TaskList(Controller controller){
        this.controller = controller;
        menu = new Menu();
    }



    class Menu{
        HBox box;
        Button runningTaskQueue = new Button("Run queue");
        Button stopingTaskQueue = new Button("Stop queue");

        void create(){
            runningTaskQueue.setPrefWidth(200);
            stopingTaskQueue.setPrefWidth(200);
            runningTaskQueue.setOnAction(e ->{
                controller.runTaskQueue();
                runningTaskQueue.setDisable(true);
            });
            stopingTaskQueue.setOnAction(e -> {
                controller.stopTaskQueue();
            });
            HBox.setHgrow(runningTaskQueue, ALWAYS);
            HBox.setHgrow(stopingTaskQueue, ALWAYS);
            this.box = new HBox(runningTaskQueue, stopingTaskQueue);
        }


    }

    public Pane create(List<ProcessTask> tasks) {
        menu.create();
        list = new ListView<>();
        list.getItems().addAll(tasks);
        list.setCellFactory(l -> {
            TextFieldListCell<ProcessTask> lc = new TextFieldListCell<>();
            lc.setConverter(new TaskListViewStringConverter());
            return lc;});
        VBox.setVgrow(list, ALWAYS);
        return new VBox(list,menu.box);
    }

    public void update(List<ProcessTask> tasks) {
        if (tasks.size() == 0) {
            menu.runningTaskQueue.setDisable(false);
        }
        list.getItems().removeIf(Objects::nonNull);
        list.getItems().addAll(tasks);
    }
}
