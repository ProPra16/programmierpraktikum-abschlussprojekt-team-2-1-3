package trainer.gui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
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

    private ObservableList<String> testOrCompileErrorList = FXCollections.observableArrayList();

    private SortedList<String> sortedList;


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
        ArrayList<String> testFailureMethodNameArrayList = new ArrayList<>();

        for (Object o : testFailuresArray) {
            System.out.println(((TestFailure) o).getMethodName());
            testFailureMethodNameArrayList.add(((TestFailure) o).getMethodName());
        }

        /*ArrayList<TableEntry> tableEntries = new ArrayList<>();
        for (String methodName : testFailureMethodNameArrayList) {
            TableEntry tableEntry = new TableEntry(methodName);
            tableEntries.add(tableEntry);
        }
       */

        //testOrCompileErrorList.add(0, "hallo");

        testTableView.setItems(sortedList);


                //emptyObservableList();
        for (int i = 0; i < testFailureMethodNameArrayList.size(); i++) {
            //System.out.println(testFailureMethodNameArrayList.get(i));
            //failureList.add(testFailureMethodNameArrayList.get(i));
        }


        //testTableView.setItems(failureList);
        compileErrorTextArea.clear();
    }

    public void setContent() {
        compileErrorTextArea.clear();
        testTableView.getItems().clear();
    }

    @Override
    public void willAppear() {
        sortedList = new SortedList<>(testOrCompileErrorList);
        testTableView.setItems(sortedList);
    }

}
