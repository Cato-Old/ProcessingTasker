package Model.Tasks;

import Model.Projects.Publications.Publication;
import javafx.application.Platform;
import javafx.concurrent.Task;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CopyAndRenumberTask extends ProcessTask {
    public static String label = "Copy and Renumber";

    public CopyAndRenumberTask(){
        super();
    }

    public CopyAndRenumberTask(Publication publication) {
        super(publication);
    }

    @Override
    public Task task() {
        return new Task() {
            @Override
            protected Void call() throws Exception {
                String line;
                Runtime rt = Runtime.getRuntime();
                Process proc = rt.exec("python C:\\Java\\projects\\ProcessingTasker\\script.py");
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
                    while((line = reader.readLine()) != null){
                        final String ln = "\n" + line;
                        Platform.runLater(()->consoleTextArea.appendText(ln));
                    }
                } catch (Exception e){e.printStackTrace();}
                return null;
            }
        };
    }
}
