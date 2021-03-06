package Model.Tasks;

import Model.Projects.Publications.Publication;
import View.View;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ProcessTask implements Serializable {
    String label;
    transient Path scriptPath;
    transient Publication publication;

    public ProcessTask(String label, Path scriptPath){
        this.label = label;
        this.scriptPath = scriptPath;
    }

    public String getLabel(){
        return label;
    }

    public Path getScriptPath() {
        return scriptPath;
    }

    public Task task(){
        return new Task() {
            @Override
            protected Object call() throws Exception {
                String line;
                Process proc = new ProcessBuilder()
                        .command("python",
                                 scriptPath.toString(),
                                 publication.getTargetPath().toString(),
                                 publication.getSourcePath().toString())
                        .start();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
                    while((line = reader.readLine()) != null){
                        final String ln = "\n" + line;
                        Platform.runLater(()-> {
                            View.logView.appendText(ln);
                        });
                    }
                } catch (Exception e){e.printStackTrace();}
                return null;
            }
        };
    }

    public ProcessTask setPublication(Publication publication){
        ProcessTask newProcessTask = new ProcessTask(this.label, this.scriptPath);
        newProcessTask.publication = publication;
        return newProcessTask;
    }

    public Publication getPublication(){
        return publication;
    }


    public Task getTask() {
        Task task = task();
        Thread th = new Thread(task);
        th.setDaemon(true);
        return task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessTask that = (ProcessTask) o;
        return Objects.equals(scriptPath, that.scriptPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scriptPath);
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(this.scriptPath.toString());
    }
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        scriptPath = Paths.get((String) ois.readObject());
    }
}