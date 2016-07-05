package trainer.gui;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import trainer.App;
import trainer.gui.system.Controller;

import java.io.IOException;

public class DescriptionController extends Controller {

    @FXML
    private VBox root;
    @FXML
    private TextArea descriptionTextArea;

    public static DescriptionController createWithName(String nameOfController) throws IOException {
        FXMLLoader loader = new FXMLLoader(DescriptionController.class.getResource("/DescriptionView.fxml"));
        loader.load();
        DescriptionController descriptionController = loader.getController();
        descriptionController.setName(nameOfController);
        return descriptionController;
    }

    public String readExercise() {
        /** Aufgabenstellung anzeigen */
        return ((SelectionController) App.getInstance().controllers.get("selection")).selection.exercise.description;
    }

    /**@Override
    public void willAppear() {
        if (((SelectionController) App.getInstance().controllers.get("selection")).selection.exercise.name == null) {
        } else {
            initialize(readExercise());
        }
    }*/
}
