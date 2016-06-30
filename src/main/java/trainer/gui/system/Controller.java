package trainer.gui.system;


import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.LinkedHashMap;

public class Controller {
    protected String name;
    protected Controller parent;
    protected LinkedHashMap<String, Controller> children = new LinkedHashMap<>();

    @FXML
    protected Pane root = new Pane();

    public void willAppear() {

    }
    public void didAppear() {

    }

    public void willDisappear() {

    }
    public void didDisappear() {

    }

    /** getter und setter für die Variablen */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Controller getParent() {
        return parent;
    }

    public void setParent(Controller parent) {
        this.parent = parent;
    }

    public LinkedHashMap<String, Controller> getChildren() {
        return children;
    }

    public Pane getRoot() {
        return root;
    }
}
