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
        // TODO mit Silja
    }

    @FXML
    public void compileAndRun() {
        if (!((EditableSolutionController) children.get("editableSolution")).testTextArea.isDisabled() == true) { // Test wird editiert
            // schalte TestTextArea aus;
            // schalte CodeTextArea ein;
        } else if (!((EditableSolutionController) children.get("editableSolution")).codeTextArea.isDisabled() == true) { // Code wird editiert
            // kompiliere Test und Code
            // if (Alles kompiliert == true) {
            //      run Testmethoden;
            //      if (Alle Testmethoden laufen) {
            //              Balken = green;
            //              Alert = Neue Testmethode oder Refactor?
            //              if (Neue Testmethode) {
            //                  speichere String aus CodeTextArea in codeInput; <- für backToTestEdit Funktion
            //                  schalte CodeTextArea aus;
            //                  schalte TestTextArea ein;
            //                 }
            //          } else {
            //              Balken = red;
            //              Filtere failende Testmethoden
            //              Tabelle = Testmethoden die failen;
            //
            //          }
            // } else { Balken = red; }
        }










        // Zusammenhangslose Codefragmente, die man für CompileAndRun verwenden kann:

       /* // TODO: Compilieren lassen.
        // TODO: if (keine CompilerFehler) {
        // TODO: if (alle Tests funktionieren){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Alle Tests bestanden. Möchtest du deinen Code verbessern?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Refactor");
        alert.initStyle(StageStyle.UNDECORATED);
        alert.initOwner(App.getInstance().stage);
        alert.initModality(Modality.WINDOW_MODAL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            editCode();
        } else if (result.isPresent() && result.get() == ButtonType.NO){
            editTest();
        }

        // TODO: else if (compiliert nicht)
        ((TestsStatusSolutionController) children.get("testStatusSolution")).setCompilerError();

        // TODO: else if (Test funktioniert nicht)
        ((TestsStatusSolutionController) children.get("testStatusSolution")).setTestError();*/

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
        statusBar.setFill(Color.GRAY);
    }
}
