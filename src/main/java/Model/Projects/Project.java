package Model.Projects;import Model.Publications.Publication;import java.util.ArrayList;import java.util.List;public class Project {    List<Publication> publicationList = new ArrayList<>();    String name;    public Project(String name){        this.name = name;    }    public String getName() {        return name;    }    public List<Publication> getPublications(){        return publicationList;    }    public boolean addPublication(Publication pub){        return publicationList.add(pub);    }    public boolean removePublication(Publication pub){        return publicationList.remove(pub);    }}