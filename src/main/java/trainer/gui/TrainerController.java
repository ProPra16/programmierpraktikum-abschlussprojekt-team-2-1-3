package trainer.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import trainer.gui.system.Controller;

import java.io.IOException;

public class TrainerController extends Controller {

    @FXML
    private BorderPane root;
    @FXML
    private MenuItem backToEditTestMenuItem;
    @FXML
    private MenuItem editCodeMenuItem;
    @FXML
    private MenuItem editTestMenuItem;
    @FXML
    private MenuItem saveMenuItem;
    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private MenuItem compileAndRunMenuItem;
    @FXML
    private MenuItem showSettingsMenuItem;
    @FXML
    private MenuItem aboutMenuItem;
    @FXML
    private MenuItem handbuchMenuItem;
    @FXML
    private StackPane descriptionStackPane;
    @FXML
    private StackPane editableSolutionStackPane;
    @FXML
    private StackPane testSolutionStackPane;
    @FXML
    private Rectangle statusBar;

    /*@FXML
    private TextArea leftTextArea = new TextArea("");
    @FXML
    private TextArea rightTextArea = new TextArea("");
    @FXML
    private TextArea problem_definition = new TextArea("");
    @FXML
    private Circle statusbutton;*/

    public String task;
    private boolean allTestsRun;


    public static TrainerController createWithName(String nameOfController) throws IOException {
        FXMLLoader loader = new FXMLLoader(TrainerController.class.getResource("/TrainerView.fxml"));
        loader.load();
        TrainerController trainerController = loader.getController();
        trainerController.setName(nameOfController);
        trainerController.initialize();


        DescriptionController descriptionController = DescriptionController.createWithName("description");
        descriptionController.setParent(trainerController);
        trainerController.getChildren().put("description", descriptionController);
        trainerController.showChild("description", trainerController.getRootForDescription());

        EditableSolutionController editableSolutionController = EditableSolutionController.createWithName("editableSolution");
        editableSolutionController.setParent(trainerController);
        trainerController.getChildren().put("editableSolution", editableSolutionController);
        trainerController.showChild("editableSolution", trainerController.getRootForEditableSolution());

        TestsStatusSolutionController testsStatusSolutionController = TestsStatusSolutionController.createWithName("testStatusSolution");
        testsStatusSolutionController.setParent(trainerController);
        trainerController.getChildren().put("testStatusSolution", testsStatusSolutionController);
        trainerController.showChild("testStatusSolution", trainerController.getRootForTestSolution());

        //trainerController.getGame().setMineField(descriptionController.getMinefield());*/

        // TODO: StatusSoltionController
        return trainerController;
    }
    private void initialize() {
        statusBar.setFill(Color.GRAY);

    }

    private StackPane getRootForDescription() { return descriptionStackPane; }

    private StackPane getRootForEditableSolution() { return editableSolutionStackPane; }

    private StackPane getRootForTestSolution() { return testSolutionStackPane; }

    @Override
    public Pane getRoot() {
        return root;
    }

    public void colorcheck (){
        if (allTestsRun == true){
            statusBar.setFill(Color.LIGHTGREEN);
            //allTestsRun = false;
        }

        else if (allTestsRun == false){
            statusBar.setFill(Color.ORANGERED);
            //allTestsRun = true;
        }
    }
}
