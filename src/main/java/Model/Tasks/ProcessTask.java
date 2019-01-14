package Model.Tasks;import Model.Projects.Publications.Publication;import javafx.concurrent.Task;import javafx.scene.control.TextArea;import javafx.util.StringConverter;import java.util.HashMap;import java.util.Map;public abstract class ProcessTask {    Publication publication;    TextArea consoleTextArea;    public ProcessTask(){}    public ProcessTask(Publication publication){        this.publication = publication;    }    public abstract Task task();    public void setPublication(Publication publication) {        this.publication = publication;    }    public void run(){        Thread th = new Thread(task());        th.setDaemon(true);        th.start();    }}