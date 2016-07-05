package trainer.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import trainer.App;
import trainer.gui.system.Controller;

import java.io.IOException;
import java.util.Optional;

public class TrainerController extends Controller {

    @FXML
    private BorderPane root;
    @FXML
    private MenuItem backToEditTestMenuItem;
    @FXML
    private MenuItem editCodeMenuItem;
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
    @FXML
    public void quit() {
        App.getInstance().showController("selection");
    }

    @FXML
    public void editCode() {
        saveTest();
        ((EditableSolutionController) children.get("editableSolution")).disableTestTextArea();
        ((EditableSolutionController) children.get("editableSolution")).enableCodeTextArea();
        backToEditTestMenuItem.setDisable(false);
        editCodeMenuItem.setDisable(true);


    }

    @FXML
    public void editTest() {
        saveCode();
        ((EditableSolutionController) children.get("editableSolution")).disableCodeTextArea();
        ((EditableSolutionController) children.get("editableSolution")).enableTestTextArea();
        backToEditTestMenuItem.setDisable(true);
        editCodeMenuItem.setDisable(false);


    }

    public void saveTest() {
        ((EditableSolutionController) children.get("editableSolution")).saveTest();
    }

    public void saveCode() {
        ((EditableSolutionController) children.get("editableSolution")).saveCode();
    }

    @FXML
    public void backToEditTest() {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Wenn du zurück gehst, wird die letzten Änderungen im Code gelöscht!", ButtonType.CANCEL, ButtonType.OK);
            alert.setHeaderText("Wirklich zurück zum Test gehen?");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.initOwner(App.getInstance().stage);
            alert.initModality(Modality.WINDOW_MODAL);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                return;
            } else {
                ((EditableSolutionController) children.get("editableSolution")).deleteNewCode();
                editTest();
            }
    }

    @FXML
    public void save() {
        // TODO
    }

    @FXML
    public void compileAndRun() {
        // TODO: Compilieren lassen.
        // TODO: if (keine Fehler) {
        // TODO: if (alle Tests funktionieren){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Alle Tests bestanden. Möchtest du deinen Code verbessern?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Refactor");
        alert.initStyle(StageStyle.UNDECORATED);
        alert.initOwner(App.getInstance().stage);
        alert.initModality(Modality.WINDOW_MODAL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            editCode();
        } else if (result.isPresent() && result.get() == ButtonType.NO ){
            editTest();
        }
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

    public void  didAppear() {
        backToEditTestMenuItem.setDisable(true);
    }
}
