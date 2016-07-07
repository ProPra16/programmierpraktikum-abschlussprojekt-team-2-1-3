package trainer.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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
import vk.core.api.TestFailure;
import vk.core.internal.InternalCompiler;

import java.io.IOException;
import java.util.Collection;
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
    private MenuItem endRefactorMenuItem;
    @FXML
    private MenuItem showSettingsMenuItem;
    @FXML
    private MenuItem aboutMenuItem;
    @FXML
    private MenuItem handbuchMenuItem;
    @FXML
    private StackPane descriptionStackPane;
    @FXML
    private StackPane solutionStackPane;
    @FXML
    private StackPane testSolutionStackPane;
    @FXML
    private Rectangle statusBar;
    @FXML
    private Label instructionLabel;


    public String task;
    private boolean allTestsRun;
    private boolean isRefactor = false;


    public static TrainerController createWithName(String nameOfController) throws IOException {
        FXMLLoader loader = new FXMLLoader(TrainerController.class.getResource("/TrainerView.fxml"));
        loader.load();
        TrainerController trainerController = loader.getController();
        trainerController.setName(nameOfController);


        DescriptionController descriptionController = DescriptionController.createWithName("description");
        descriptionController.setParent(trainerController);
        trainerController.getChildren().put("description", descriptionController);
        trainerController.showChild("description", trainerController.getRootForDescription());

        SolutionController solutionController = SolutionController.createWithName("solution");
        solutionController.setParent(trainerController);
        trainerController.getChildren().put("solution", solutionController);
        trainerController.showChild("solution", trainerController.getRootForEditableSolution());

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
        ((SolutionController) children.get("solution")).disableTestTextArea();
        ((SolutionController) children.get("solution")).enableCodeTextArea();
        backToEditTestMenuItem.setDisable(false);
        editCodeMenuItem.setDisable(true);
        instructionLabel.setText("Mache den Test funktionsfähig!");
    }

    @FXML
    public void endRefactor() {
        isRefactor = false;
        instructionLabel.setText("Schreibe einen Test und wähle Compile & Run!");
        endRefactorMenuItem.setDisable(true);
        editTest();
    }


    public void editTest() {
        saveCode();
        ((SolutionController) children.get("solution")).disableCodeTextArea();
        ((SolutionController) children.get("solution")).enableTestTextArea();
        backToEditTestMenuItem.setDisable(true);
        instructionLabel.setText("Schreibe einen Test und wähle Compile & Run!");
        statusBar.setFill(Color.GREEN);
    }

    public void saveTest() {
        ((SolutionController) children.get("solution")).saveTest();
    }

    public void saveCode() {
        ((SolutionController) children.get("solution")).saveCode();
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
                ((SolutionController) children.get("solution")).deleteNewCode();
                editTest();
            }
    }

    @FXML
    public void save() {
        // TODO
    }

    @FXML
    public void compileAndRun() {
        /** Kompiliere die Eingaben und führe die Tests aus. Speichere das Ergebnis */


        String testAreaInput = ((SolutionController) children.get("solution")).getTestInput();
        String solutionAreaInput = ((SolutionController) children.get("solution")).getSolutionInput();
        Compilation compilation = new Compilation(testAreaInput,solutionAreaInput);

        CompilationUnit[] testAndSolution = compilation.getTestAndSolution();

        InternalCompiler compiler = compilation.initializeCompiler(testAndSolution);

        compiler.compileAndRunTests();
        boolean hasCompileErrors = compiler.getCompilerResult().hasCompileErrors();

        int numberOfFailedTests = 0;
        if (!hasCompileErrors) numberOfFailedTests = compiler.getTestResult().getNumberOfFailedTests();

        Collection<CompileError> testCompileErrors = compiler.getCompilerResult().getCompilerErrorsForCompilationUnit(testAndSolution[0]);
        Collection<CompileError> solutionCompileErrors = compiler.getCompilerResult().getCompilerErrorsForCompilationUnit(testAndSolution[1]);

        Collection<TestFailure> testFailures = null;
        if (!hasCompileErrors) testFailures = compiler.getTestResult().getTestFailures();

        // TODO Funktioniert noch nicht
        ((TestTableController)children.get("testStatusSolution")).setTableContent(testCompileErrors,solutionCompileErrors);



        if (isRefactor) {
            if (hasCompileErrors) {
                statusBar.setFill(Color.RED);
                // TODO Ausgabe der CompileErrors
                endRefactorMenuItem.setDisable(true);
            } else if (numberOfFailedTests != 0) {
                statusBar.setFill(Color.RED);
                // TODO Tabelle = TestFailures;
                ((TestTableController)children.get("testStatusSolution")).setTableContent(testFailures);
                endRefactorMenuItem.setDisable(true);
            } else if (!hasCompileErrors || numberOfFailedTests == 0) {
                statusBar.setFill(Color.GREEN);
                endRefactorMenuItem.setDisable(false);
            }



        } else {
            if (!((SolutionController) children.get("solution")).testTextArea.isDisabled()) { // Test wird editiert

                if (hasCompileErrors || numberOfFailedTests == 1) {
                    editCodeMenuItem.setDisable(false);
                    statusBar.setFill(Color.RED);
                    instructionLabel.setText("Um in die nächste Phase zu kommen, drücke Edit Code!");
                    ((TestTableController)children.get("testStatusSolution")).setTableContent(testFailures);
                    ((TestTableController)children.get("testStatusSolution")).setTableContent(testCompileErrors,solutionCompileErrors);

                } else if (numberOfFailedTests == 0) {
                    editCodeMenuItem.setDisable(true);
                    statusBar.setFill(Color.GREEN);
                    instructionLabel.setText("Alle Tests laufen. Füge weiteren Test hinzu oder speichere deine Lösung!");

                } else if (numberOfFailedTests > 1) {
                    editCodeMenuItem.setDisable(true);
                    statusBar.setFill(Color.RED);
                    instructionLabel.setText("Es muss genau ein Test fehlschlagen!");
                    ((TestTableController) children.get("testStatusSolution")).setTableContent(testFailures);
                }


            } else if (!((SolutionController) children.get("solution")).codeTextArea.isDisabled()) { // Code wird editiert

                if (!hasCompileErrors && numberOfFailedTests == 0) {
                    statusBar.setFill(Color.GREEN);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Alle Tests bestanden. Möchtest du deinen Code verbessern?", ButtonType.YES, ButtonType.NO);
                    alert.setHeaderText("Refactor");
                    alert.initStyle(StageStyle.UNDECORATED);
                    alert.initOwner(App.getInstance().stage);
                    alert.initModality(Modality.WINDOW_MODAL);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.YES) {
                        isRefactor = true;
                        endRefactorMenuItem.setDisable(true);
                        instructionLabel.setText("REFACTOR");
                        backToEditTestMenuItem.setDisable(true);
                        editCodeMenuItem.setDisable(true);
                    } else if (result.isPresent() && result.get() == ButtonType.NO) {
                        editTest();
                    }
                } else {
                    statusBar.setFill(Color.RED);
                    if (hasCompileErrors) {

                        // TODO Tabelle = CompileError
                        ((TestTableController)children.get("testStatusSolution")).setTableContent(testCompileErrors, solutionCompileErrors);

                    } else if (numberOfFailedTests > 0) {

                        // TODO Tabelle = testFailures
                        ((TestTableController)children.get("testStatusSolution")).setTableContent(testFailures);
                    }
                }
            }

        }


    }

    private StackPane getRootForDescription() { return descriptionStackPane; }

    private StackPane getRootForEditableSolution() { return solutionStackPane; }

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
        editCodeMenuItem.setDisable(true);
        statusBar.setFill(Color.GRAY);
        endRefactorMenuItem.setDisable(true);
        instructionLabel.setText("Schreibe einen Test und wähle Compile & Run!");

    }
}
