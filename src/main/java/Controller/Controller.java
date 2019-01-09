package Controller;import Model.Model;import Model.Projects.Publications.Book;import Model.Projects.Publications.Publication;import View.GUI.PublicationTree;import Model.Projects.Project;import View.View;import javafx.stage.Stage;public class Controller {    Model model;    View view;    private PublicationTree publicationTree;    public Controller(Model model, Stage stage){        this.model = model;        this.view = new View(model, this, stage);        view.create();    }    public PublicationTree getPublicationTree() {        return publicationTree;    }    public void addProject(String name){        Project project = new Project(name);        model.addProject(project);    }    public void remProject(Project project){        model.remProject(project);    }    public void addPub(Project project, String name){        Publication pub = new Book(name);        model.addPub(project, pub);    }    public void remPub(Project project, Publication publication){        model.remPub(project,publication);    }}