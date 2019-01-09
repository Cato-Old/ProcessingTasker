package Model.Projects.Publications;import Model.Projects.Project;import Model.Projects.ProjectItem;import java.nio.file.Path;import java.util.Objects;public abstract class Publication implements ProjectItem {    Path sourcePath;    Path targetPath;    State currentState;    String name;    Project project;    public Publication(String name, Path sourcePath, Project project){        this.name = name;        this.project = project;        if (!project.getSourcePath().equals(sourcePath.getParent())){            throw new IllegalArgumentException("There is no such directory in the project directory.");        } else {            this.sourcePath = sourcePath;        }    }    public Path getSourcePath() {        return sourcePath;    }    public void setSourcePath(Path sourcePath) {        this.sourcePath = sourcePath;    }    public String getName() {        return name;    }    public Path getTargetPath() {        return targetPath;    }    public void setTargetPath(Path targetPath) {        this.targetPath = targetPath;    }    @Override    public String toString() {        return name;    }    @Override    public boolean equals(Object o) {        if (this == o) return true;        if (o == null || getClass() != o.getClass()) return false;        Publication that = (Publication) o;        return Objects.equals(sourcePath, that.sourcePath);    }    @Override    public int hashCode() {        return Objects.hash(sourcePath);    }}