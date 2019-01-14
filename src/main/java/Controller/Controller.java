package Controller;import Model.Model;import Model.Projects.Publications.Book;import Model.Projects.Publications.Publication;import Model.Projects.Project;import Model.Tasks.ProcessTask;import View.View;import javafx.stage.Stage;import java.io.File;import java.nio.file.Path;import java.nio.file.Paths;import java.util.Optional;import static View.ViewElement.CENTER_PANE;public class Controller {    Model model;    View view;    public Controller(Model model, Stage stage){        this.model = model;        this.view = new View(model, this, stage);        view.create();    }    public void addProject(File file){        if (file != null){            Project project = new Project(                    file.getName(),                    Paths.get(file.getPath()));            model.addProject(project);        }    }    public void remProject(Project project){        model.remProject(project);    }    public void selProject(Project project){        view.update(CENTER_PANE, project);    }    public void selPub(Publication pub) {        view.update(CENTER_PANE, pub);    }    public void addPub(Project project, File file) throws IllegalArgumentException {        if (file != null){            Publication pub = new Book(                    file.getName(),                    Paths.get(file.getPath()),                    project);            model.addPub(project, pub);        }    }    public void remPub(Project project, Publication publication){        model.remPub(project,publication);    }    public void setTargetPath(Project project, File file){        Path path = Paths.get(file.getPath());        model.setTargetPath(project, path);    }    public void addTask(Class<ProcessTask> clazz, Publication pub) throws Exception {        ProcessTask task = clazz.getConstructor(Publication.class).newInstance(pub);        model.addTask(task);    }    public File chooseDir(){        return Optional                .ofNullable(view.chooseDir())                .orElseThrow(() -> new IllegalArgumentException(""));    }    public File chooseDir(Path initialPath){        return Optional                .ofNullable(view.chooseDir(initialPath))                .orElseThrow(() -> new IllegalArgumentException(""));    }}