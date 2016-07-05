package trainer.gui;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;
import trainer.gui.system.Controller;

import java.io.IOException;

public class TestsStatusSolutionController extends Controller {

    @FXML
    private BorderPane root;

    // TableColumn<S,T> !!!!!!!
    @FXML
    private TableColumn testTableColumn;
    @FXML
    private TableColumn statusTableColumn;

    public static TestsStatusSolutionController createWithName(String nameOfController) throws IOException {
        FXMLLoader loader = new FXMLLoader(TestsStatusSolutionController.class.getResource("/TestsStatusSolutionView.fxml"));
        loader.load();
        TestsStatusSolutionController statusSolutionController = loader.getController();
        statusSolutionController.setName(nameOfController);
        return statusSolutionController;
    }


}
