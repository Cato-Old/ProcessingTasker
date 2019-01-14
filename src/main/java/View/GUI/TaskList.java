package View.GUI;

import Controller.Controller;
import Model.Tasks.ProcessTask;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;

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
            HBox.setHgrow(runningTaskQueue, ALWAYS);
            HBox.setHgrow(stopingTaskQueue, ALWAYS);
            this.box = new HBox(runningTaskQueue, stopingTaskQueue);
        }


    }

    public Pane create(List<ProcessTask> tasks) {
        menu.create();
        list = new ListView<>();
        list.getItems().addAll(tasks);
        VBox.setVgrow(list, ALWAYS);
        return new VBox(list,menu.box);
    }

    public void update(List<ProcessTask> tasks) {
        list.getItems().addAll(tasks);
    }
}
