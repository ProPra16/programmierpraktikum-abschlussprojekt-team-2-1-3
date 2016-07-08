package trainer.gui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
    private TableView<String> testTableView;
    @FXML
    private TableColumn testTableColumn;

    private ObservableList<TableEntry> testOrCompileErrorList;


    public static ErrorAndFailureController createWithName(String nameOfController) throws IOException {
        FXMLLoader loader = new FXMLLoader(ErrorAndFailureController.class.getResource("/ErrorAndFailureView.fxml"));
        loader.load();
        ErrorAndFailureController errorAndFailureController = loader.getController();
        errorAndFailureController.setName(nameOfController);
        return errorAndFailureController;
    }

    @Override
    public void initialize() {
        testTableColumn.setCellValueFactory(new PropertyValueFactory<TestFailure, String>("testFailure"));
    }

    public void setContent(Collection<CompileError> testCompileErrors, Collection<CompileError> codeCompileErrors) {
        compileErrorTextArea.setText("Compilerfehler im Test" + "\n"+ testCompileErrors.toString() + "\n" + "Compilerfehler im Code" + "\n" + codeCompileErrors.toString());
        testTableView.getItems().clear();
    }

    public void setContent(Collection<TestFailure> testFailures) {
        Object[] testFailuresArray = testFailures.toArray();
        ArrayList<String> testFailureNameArrayList = new ArrayList<>();

        for (Object o : testFailuresArray) {
            System.out.println(((TestFailure) o).getMethodName());
            testFailureNameArrayList.add(((TestFailure) o).getMethodName());
        }
        /*ObservableList<String> failureList = FXCollections.emptyObservableList();
        for (int i = 0; i < testFailureNameArrayList.size(); i++) {
            System.out.println(testFailureNameArrayList.get(i));
            failureList.add(testFailureNameArrayList.get(i));
        }
        testTableView.setItems(failureList);*/
        compileErrorTextArea.clear();
    }

    public void setContent() {
        compileErrorTextArea.clear();
        testTableView.getItems().clear();
    }
}
