package trainer.gui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import trainer.gui.system.Controller;
import trainer.models.TableEntry;
import vk.core.api.CompileError;
import vk.core.api.TestFailure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class TestTableController extends Controller {

    @FXML
    private BorderPane root;
    @FXML
    private Label tableLabel;
    @FXML
    private TableView<TableEntry> testTableView;
    @FXML
    private TableColumn tableColumn;

    private ObservableList<TableEntry> testOrCompileErrorList;


    public static TestTableController createWithName(String nameOfController) throws IOException {
        FXMLLoader loader = new FXMLLoader(TestTableController.class.getResource("/TestTableView.fxml"));
        loader.load();
        TestTableController statusSolutionController = loader.getController();
        statusSolutionController.setName(nameOfController);
        return statusSolutionController;
    }

    @Override
    public void initialize() {
        tableColumn.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("name"));
    }


    public void setCompilerError() {
        //TODO statusTableColumn auf Compilerfehler setzen, aber nur die Spalten, die Tests enthalten
    }

    /*public void willAppear() {
        tableColumn.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("testName"));
        failingTests = new List<>(/** fehlschlagende Tests (Collection von internal failures) *///);
        /*testTableView.setItems(failingTest);
    }*/


    public void setTableContent(Collection<CompileError> testCompileErrors, Collection<CompileError> solutionCompileErrors) {
        System.out.println(testCompileErrors);
        System.out.println(solutionCompileErrors);
/*        ObservableList<CompileError> a = FXCollections.emptyObservableList();
        for (CompileError error : testCompileErrors) a.add(error);

        testOrCompileErrorList = FXCollections.observableList((List)a);
        testTableView.setItems(testOrCompileErrorList);
        */
    }

    public void setTableContent(Collection<TestFailure> testFailures) {
        System.out.println(testFailures);

    }
}
