package GUI;import javafx.scene.control.ContextMenu;import javafx.scene.control.MenuItem;import javafx.scene.control.TreeItem;import javafx.scene.control.TreeView;public class PublicationTreeContextMenu {    private TreeView publicationTree;    public PublicationTreeContextMenu(TreeView publicationTree){        this.publicationTree = publicationTree;    }    private boolean isProjectNode(){        Object selected = publicationTree.getSelectionModel().getSelectedItem();        TreeItem<String> ti;        try {            ti = (TreeItem<>) selected;            if (ti.isLeaf() && !ti.getParent().equals(publicationTree.getRoot())){                return false;            } else {                return true;            }        } catch (ClassCastException e){            e.printStackTrace();        }        return false;    }    public ContextMenu createContextMenu(){        ContextMenu contextMenu = new ContextMenu();        MenuItem add = new MenuItem("Add");        MenuItem remove = new MenuItem("Remove");        contextMenu.setOnShowing(e -> {                add.setDisable(!isProjectNode());                remove.setDisable(isProjectNode());        });        add.setOnAction(e -> {            Object selected = publicationTree.getSelectionModel().getSelectedItem();            TreeItem<String> ti =  selected instanceof TreeItem ? (TreeItem<String>) selected : null;            if (!ti.isLeaf() || ti.getParent().equals(publicationTree.getRoot())){                ti.getChildren().add(new TreeItem<String>("Abramowicz"));            }});        remove.setOnAction(e -> {            Object selected = publicationTree.getSelectionModel().getSelectedItem();            TreeItem<String> ti =  selected instanceof TreeItem ? (TreeItem<String>) selected : null;            if (ti.isLeaf() && !ti.getParent().equals(publicationTree.getRoot())){                ti.getParent().getChildren().remove(ti);            }});        contextMenu.getItems().addAll(add,remove);        publicationTree.setContextMenu(contextMenu);        return contextMenu;    }}