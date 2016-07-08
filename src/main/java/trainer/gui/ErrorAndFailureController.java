package trainer.gui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import trainer.App;
import trainer.gui.system.Controller;
import trainer.models.TableEntry;
import vk.core.api.CompileError;
import vk.core.api.TestFailure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class ErrorAndFailureController extends Controller {

    @FXML
    private BorderPane root;
    @FXML
    private TextArea compileErrorTextArea;
    @FXML
    private ListView<String> testFailureListView;


    private ObservableList<String> testOrCompileErrorList = FXCollections.observableArrayList();


    public static ErrorAndFailureController createWithName(String nameOfController) throws IOException {
        FXMLLoader loader = new FXMLLoader(ErrorAndFailureController.class.getResource("/ErrorAndFailureView.fxml"));
        loader.load();
        ErrorAndFailureController errorAndFailureController = loader.getController();
        errorAndFailureController.setName(nameOfController);
        return errorAndFailureController;
    }

    @Override
    public void initialize() {
        testFailureListView.setItems(testOrCompileErrorList);
    }

    public void setContent(Collection<CompileError> testCompileErrors, Collection<CompileError> codeCompileErrors) {
        compileErrorTextArea.setText("Compilerfehler im Test:" + "\n"+ testCompileErrors.toString() + "\n" + "\n" + "Compilerfehler im Code:" + "\n" + codeCompileErrors.toString());
        testOrCompileErrorList.clear();
    }

    public void setContent(Collection<TestFailure> testFailures) {
        Object[] testFailuresArray = testFailures.toArray();
        testOrCompileErrorList.clear();


        for (Object o : testFailuresArray) {
            testOrCompileErrorList.add(((TestFailure) o).getMethodName());
        }

        compileErrorTextArea.clear();
    }

    public void setContent() {
        compileErrorTextArea.clear();
        testOrCompileErrorList.clear();
    }
}