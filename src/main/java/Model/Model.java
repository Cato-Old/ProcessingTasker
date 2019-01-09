package Model;import Model.Projects.Project;import Model.Projects.Publications.Publication;import java.util.ArrayList;import java.util.List;import java.util.Observable;import static Model.ViewElement.*;public class Model extends Observable {    List<Project> projects = new ArrayList<>();    public void addProject(Project project){        projects.add(project);        setChanged();        notifyObservers(PUBLICATION_TREE);    }    public void remProject(Project project){        projects.remove(project);        setChanged();        notifyObservers(PUBLICATION_TREE);    }    public void addPub(Project project, Publication publication){        project.addPublication(publication);        setChanged();        notifyObservers(PUBLICATION_TREE);    }    public void remPub(Project project, Publication publication){        project.removePublication(publication);        setChanged();        notifyObservers(PUBLICATION_TREE);    }    public List<Project> getProjects() {        return projects;    }}