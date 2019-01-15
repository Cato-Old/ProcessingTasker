package Model.Tasks;import Model.Projects.Publications.Publication;import javafx.concurrent.Task;import javafx.scene.control.TextArea;public abstract class ProcessTask {    Publication publication;    TextArea consoleTextArea;    public ProcessTask(){}    public ProcessTask(Publication publication){        this.publication = publication;    }    public abstract String getLabel();    public abstract Task task();    public void setPublication(Publication publication) {        this.publication = publication;    }    public Publication getPublication(){        return publication;    }    public void run() throws InterruptedException {        Thread th = new Thread(task());        th.setDaemon(true);        th.start();    }}