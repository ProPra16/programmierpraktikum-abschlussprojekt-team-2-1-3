package trainer.gui;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import trainer.gui.system.Controller;
import trainer.models.TableEntry;

import java.io.IOException;
import java.util.List;

public class TestsStatusSolutionController extends Controller {

    @FXML
    private BorderPane root;
    @FXML
    private Label tableLabel;
    @FXML
    private TableView<TableEntry> testTableView;
    @FXML
    private TableColumn testTableColumn;

    private List<TableEntry> failingTests;

    public static TestsStatusSolutionController createWithName(String nameOfController) throws IOException {
        FXMLLoader loader = new FXMLLoader(TestsStatusSolutionController.class.getResource("/TestTableView.fxml"));
        loader.load();
        TestsStatusSolutionController statusSolutionController = loader.getController();
        statusSolutionController.setName(nameOfController);
        return statusSolutionController;
    }

    public void setCompilerError() {
        //TODO statusTableColumn auf Compilerfehler setzen, aber nur die Spalten, die Tests enthalten
    }

    /*public void willAppear() {
        testTableColumn.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("testName"));
        failingTests = new List<>(/** fehlschlagende Tests (Collection von internal failures) *///);
        /*testTableView.setItems(failingTest);
    }*/

}
