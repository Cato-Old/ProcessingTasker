package Controller;import Model.Model;import Model.Projects.Publications.Book;import Model.Projects.Publications.Publication;import Model.Projects.Project;import Model.Tasks.ProcessTask;import View.View;import javafx.concurrent.Task;import javafx.stage.Stage;import java.io.File;import java.nio.file.Path;import java.nio.file.Paths;import java.util.List;import java.util.Optional;import java.util.concurrent.ExecutorService;import java.util.concurrent.Executors;import static View.ViewElement.CENTER_PANE;public class Controller {    Model model;    View view;    public Controller(Model model, Stage stage){        this.model = model;        this.view = new View(model, this, stage);        view.create();    }    public void addProject(File file){        if (file != null){            Project project = new Project(                    file.getName(),                    Paths.get(file.getPath()));            model.addProject(project);        }    }    public void remProject(Project project){        model.remProject(project);    }    public void selProject(Project project){        view.update(CENTER_PANE, project);    }    public void selPub(Publication pub) {        view.update(CENTER_PANE, pub);    }    public void addPub(Project project, File file) throws IllegalArgumentException {        if (file != null){            Publication pub = new Book(                    file.getName(),                    Paths.get(file.getPath()),                    project);            model.addPub(project, pub);        }    }    public void remPub(Project project, Publication publication){        model.remPub(project,publication);    }    public void setTargetPath(Project project, File file){        Path path = Paths.get(file.getPath());        model.setTargetPath(project, path);    }    public void addTask(ProcessTask process, Publication pub){        ProcessTask task = process.setPublication(pub);        model.addTask(task);    }    public void runTaskQueue(){        List<ProcessTask> tasks = model.getTasks();        ExecutorService executor = Executors.newSingleThreadExecutor();        tasks.forEach(el -> {            Task task = el.getTask();            executor.execute(task);            task.setOnSucceeded(evt -> model.remTask(el));        });    }    public void addDefinedTask(String label, String path, int ind) throws IllegalArgumentException {        if (new File(path).exists()){            model.addDefinedTask(new ProcessTask(label, Paths.get(path)), ind);        } else {            throw new IllegalArgumentException("Given path does not exists.");        }    }    public int remDefinedTask(ProcessTask task){        return model.remDefinedTask(task);    }    public File chooseDir(){        return Optional                .ofNullable(view.chooseDir())                .orElseThrow(() -> new IllegalArgumentException(""));    }    public File chooseDir(Path initialPath){        return Optional                .ofNullable(view.chooseDir(initialPath))                .orElseThrow(() -> new IllegalArgumentException(""));    }}