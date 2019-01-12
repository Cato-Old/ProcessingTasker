package Model.Projects;

import Model.Projects.Publications.Publication;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Project implements ProjectItem {
    List<Publication> publicationList = new ArrayList<>();
    String name;
    Path sourcePath;
    Path targetPath;

    public Project(String name, Path sourcePath){
        this.name = name;
        this.sourcePath = sourcePath;
    }

    public String getName() {
        return name;
    }

    public Path getSourcePath() {
        return sourcePath;
    }

    public Path getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(Path path) {
        this.targetPath = path;
        publicationList.forEach(e -> e.setTargetPath(path.resolve(e.getSourcePath().getFileName())));
    }

    public List<Publication> getPublications(){
        return publicationList;
    }

    public boolean addPublication(Publication pub){
        if (publicationList.contains(pub)){
            throw new IllegalArgumentException("This directory has been already added to the project.");
        }
        Path pubName = pub.getSourcePath().getFileName();
        Optional.ofNullable(targetPath)
                .ifPresent(path -> pub.setTargetPath(path.resolve(pubName)));
        return publicationList.add(pub);
    }

    public boolean removePublication(Publication pub){
        return publicationList.remove(pub);
    }

    @Override
    public String toString() {
        return name;
    }
}
