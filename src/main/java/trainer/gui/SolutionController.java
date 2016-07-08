package trainer.gui;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import trainer.App;
import trainer.gui.system.Controller;

import java.io.IOException;

public class SolutionController extends Controller {

    @FXML
    public BorderPane root;
    @FXML
    public TextArea testTextArea;
    @FXML
    public  TextArea codeTextArea;


    private String tempSavedCodeInput;



    public static SolutionController createWithName(String nameOfController) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SolutionController.class.getResource("/SolutionView.fxml"));
        fxmlLoader.load();
        SolutionController solutionController = fxmlLoader.getController();
        solutionController.setName(nameOfController);
        return solutionController;
    }

    public void disableTestTextArea() {
        testTextArea.setEditable(false);
        testTextArea.setDisable(true);
    }

    public void disableCodeTextArea() {
        codeTextArea.setEditable(false);
        codeTextArea.setDisable(true);
    }

    public void enableTestTextArea() {
        testTextArea.setEditable(true);
        testTextArea.setDisable(false);
        testTextArea.setFocusTraversable(false);
        testTextArea.requestFocus();
    }

    public void enableCodeTextArea() {
        codeTextArea.setEditable(true);
        codeTextArea.setDisable(false);
        testTextArea.setFocusTraversable(false);
        codeTextArea.requestFocus();
    }

    public void didAppear() {
        tempSavedCodeInput = readSolutionTemplate();
        testTextArea.setText(readTestTemplate());
        codeTextArea.setText(readSolutionTemplate());
        testTextArea.setEditable(true);
        codeTextArea.setDisable(true);
        testTextArea.setFocusTraversable(false);
        testTextArea.requestFocus();
    }


    public void deleteNewCodeAndSetOldCode() {
        codeTextArea.clear();
        codeTextArea.setText(tempSavedCodeInput);
    }

    public void tempSaveCode() {
        tempSavedCodeInput = codeTextArea.getText();
    }

    public String getCodeInput() {
        return codeTextArea.getText();
    }

    public String getTestInput() {
        return testTextArea.getText();
    }

    public String readTestTemplate() {
        return ((SelectionController) App.getInstance().controllers.get("selection")).selection.exercise.testTemplate.getTest_code();
    }

    public  String readSolutionTemplate() {
        return ((SelectionController) App.getInstance().controllers.get("selection")).selection.exercise.codeTemplate.getCode();
    }


}
