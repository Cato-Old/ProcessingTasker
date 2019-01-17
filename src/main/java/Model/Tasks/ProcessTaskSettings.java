package Model.Tasks;import java.nio.file.Path;import java.nio.file.Paths;import java.util.ArrayList;import java.util.Arrays;import java.util.List;public class ProcessTaskSettings {    private static ProcessTaskSettings instance = new ProcessTaskSettings();    private List<ProcessTask> definedTasks = new ArrayList<>();    public static ProcessTaskSettings getInstance() {        return instance;    }    private ProcessTaskSettings() {        definedTasks.addAll(Arrays.asList(                new ProcessTask("Copy and Renumber", Paths.get("C:\\Java\\projects\\ProcessingTasker\\script.py")),                new ProcessTask("Process to Djvu", Paths.get("C:\\Java\\projects\\ProcessingTasker\\scriptq.py"))));    }    public List<ProcessTask> getDefinedTasks() {        return definedTasks;    }    public void addDefinedTask(ProcessTask task) throws IllegalArgumentException {        if (definedTasks.contains(task)){            throw new IllegalArgumentException("This task has already been in the list");        } else {            definedTasks.add(task);        }    }}