package View.GUI.StringConverters;import Model.Tasks.ProcessTask;import javafx.util.StringConverter;public class TaskListViewStringConverter extends StringConverter<ProcessTask> {    @Override    public String toString(ProcessTask obj) {        return obj.getLabel() + " (" + obj.getPublication().getName() + ")";    }    @Override    public ProcessTask fromString(String string) {        return null;    }}