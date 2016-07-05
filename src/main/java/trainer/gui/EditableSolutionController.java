package trainer.gui;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import trainer.gui.system.Controller;

import java.io.IOException;

public class EditableSolutionController extends Controller {

    @FXML
    public BorderPane root;
    @FXML
    private TextArea testTextArea;
    @FXML
    private  TextArea codeTextArea;


    public static EditableSolutionController createWithName(String nameOfController) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EditableSolutionController.class.getResource("/EditableSolutionView.fxml"));
        fxmlLoader.load();
        EditableSolutionController editableSolutionController = fxmlLoader.getController();
        editableSolutionController.setName(nameOfController);
        editableSolutionController.initialize();
        return editableSolutionController;
    }

    public void initialize() {

        testTextArea.setEditable(true);
        codeTextArea.setDisable(true);
    }

    public void disableTestTextArea() {
        testTextArea.setEditable(false);
    }

    public void disableCodeTextArea() {
        codeTextArea.setEditable(false);
    }

    public void enableTestTextArea() {
        testTextArea.setEditable(true);
    }

    public void enableCodeTextArea() {
        codeTextArea.setEditable(false);
    }

}
