package trainer.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import trainer.App;
import trainer.compilation.Compilation;
import trainer.gui.system.Controller;
import vk.core.api.CompilationUnit;
import vk.core.api.CompileError;
import vk.core.internal.InternalCompiler;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
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

        SolutionController solutionController = SolutionController.createWithName("editableSolution");
        solutionController.setParent(trainerController);
        trainerController.getChildren().put("editableSolution", solutionController);
        trainerController.showChild("editableSolution", trainerController.getRootForEditableSolution());

        TestTableController testTableController = TestTableController.createWithName("testStatusSolution");
        testTableController.setParent(trainerController);
        trainerController.getChildren().put("testStatusSolution", testTableController);
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
        //saveTest();
        ((SolutionController) children.get("editableSolution")).disableTestTextArea();
        ((SolutionController) children.get("editableSolution")).enableCodeTextArea();
        backToEditTestMenuItem.setDisable(false);
    }

    @FXML
    public void editTest() {
        saveCode();
        ((SolutionController) children.get("editableSolution")).disableCodeTextArea();
        ((SolutionController) children.get("editableSolution")).enableTestTextArea();
        backToEditTestMenuItem.setDisable(true);
    }

    public void saveTest() {
        ((SolutionController) children.get("editableSolution")).saveTest();
    }

    public void saveCode() {
        ((SolutionController) children.get("editableSolution")).saveCode();
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
                ((SolutionController) children.get("editableSolution")).deleteNewCode();
                editTest();
            }
    }

    @FXML
    public void save() {
        // TODO mit Silja
    }

    @FXML
    public void compileAndRun() {
        /** Kompiliere die Eingaben und führe die Tests aus. Speichere das Ergebnis */


        String testAreaInput = ((SolutionController) children.get("editableSolution")).getTestInput();
        String solutionAreaInput = ((SolutionController) children.get("editableSolution")).getSolutionInput();
        Compilation compilation = new Compilation(testAreaInput,solutionAreaInput);

        CompilationUnit[] testAndSolution = compilation.getTestAndSolution();

        InternalCompiler compiler = compilation.initializeCompiler(testAndSolution);

        compiler.compileAndRunTests();
        boolean hasCompileErrors = compiler.getCompilerResult().hasCompileErrors();
        // muss evtl erst später initialisiert werden, da es bei einem CompileError gar nicht belegt werden kann
        int numberOfFailedTests = compiler.getTestResult().getNumberOfFailedTests();

        Collection<CompileError> testCompileErrors = compiler.getCompilerResult().getCompilerErrorsForCompilationUnit(testAndSolution[0]);
        Collection<CompileError> solutionCompileErrors = compiler.getCompilerResult().getCompilerErrorsForCompilationUnit(testAndSolution[1]);

        if (!((SolutionController) children.get("editableSolution")).testTextArea.isDisabled()) { //// Test wird editiert

            System.out.println(1);
            if (hasCompileErrors || numberOfFailedTests > 0) {
                statusBar.setFill(Color.RED);
            } else {
                statusBar.setFill(Color.GREEN);
            }

            ((SolutionController) children.get("editableSolution")).disableTestTextArea();
            ((SolutionController) children.get("editableSolution")).enableCodeTextArea();

        } else if (!((SolutionController) children.get("editableSolution")).codeTextArea.isDisabled()) { // Code wird editiert

            System.out.println(2);
            if (hasCompileErrors == false && numberOfFailedTests == 0) {
                statusBar.setFill(Color.GREEN);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Alle Tests bestanden. Möchtest du deinen Code verbessern?", ButtonType.YES, ButtonType.NO);
                alert.setHeaderText("Refactor");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.initOwner(App.getInstance().stage);
                alert.initModality(Modality.WINDOW_MODAL);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.YES) {
                    editCode();
                } else if (result.isPresent() && result.get() == ButtonType.NO) {
                    editTest();
                }
            } else {

                statusBar.setFill(Color.RED);

                System.out.println(hasCompileErrors);
                if (hasCompileErrors) {
                    for (CompileError ce : testCompileErrors) {
                        System.out.println(ce.toString());
                    }
                    // TODO Tabelle = CompileError
                    System.out.println();
                }
            }
        }
            // if (Alles kompiliert und alle Testmethoden laufen  == true) {
            //         Balken = green;
            //         Alert = Neue Testmethode oder Refactor?
            //         if (Neue Testmethode) {
            //                  speichere String aus CodeTextArea in codeInput; <- für backToTestEdit Funktion
            //                  schalte CodeTextArea aus;
            //                  schalte TestTextArea ein;
            //         }
            // } else {
            //          Balken = red;
            //          if (CompileError) {
            //              Tabelle = CompileError;
            //          } else if (mindestens eine Testmethode failed){
            //              Filtere failende Testmethoden
            //              Tabelle = Testmethoden die failen;
            //          }
            // }











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
        ((TestTableController) children.get("testStatusSolution")).setCompilerError();

        // TODO: else if (Test funktioniert nicht)
        ((TestTableController) children.get("testStatusSolution")).setTestError();*/

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
