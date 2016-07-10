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
import trainer.models.Exercise;
import trainer.time.Time;
import vk.core.api.CompilationUnit;
import vk.core.api.CompileError;
import vk.core.api.TestFailure;
import vk.core.internal.InternalCompiler;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
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
    public StackPane errorOrFailureStackPane;
    @FXML
    private Rectangle statusBar;
    @FXML
    private Label instructionLabel;


    private boolean isRefactor = false;
    private Exercise exercise;

    private ArrayList<Time> testTime = new ArrayList<>();
    private ArrayList<Time> codeTime = new ArrayList<>();
    private ArrayList<Time> refactorTime = new ArrayList<>();
    private ArrayList<String> testErrors = new ArrayList<>();
    private ArrayList<String> codeErrors = new ArrayList<>();
    private ArrayList<String> refactorErrors = new ArrayList<>();

    private long start;
    private long stop;


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
        trainerController.showChild("solution", trainerController.getRootForSolution());

        ErrorAndFailureController errorAndFailureController = ErrorAndFailureController.createWithName("errorAndFailure");
        errorAndFailureController.setParent(trainerController);
        trainerController.getChildren().put("errorAndFailure", errorAndFailureController);
        trainerController.showChild("errorAndFailure", trainerController.getRootForErrorAndFailureController());

        return trainerController;
    }

    @FXML
    public void quit() throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Wenn du die Übung beenden möchtest, wird eine nicht gespeicherte Lösung verworfen. Möchtest du die Übung trotzdem beenden?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Achtung");
        alert.initStyle(StageStyle.UNDECORATED);
        alert.initOwner(App.getInstance().stage);
        alert.initModality(Modality.WINDOW_MODAL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            displayResult();
        } else if (result.isPresent() && result.get() == ButtonType.NO) {
            return;
        }
    }

    @FXML
    public void editCode() {
        ((SolutionController) children.get("solution")).disableTestTextArea();
        ((SolutionController) children.get("solution")).enableCodeTextArea();
        backToEditTestMenuItem.setDisable(false);
        editCodeMenuItem.setDisable(true);
        instructionLabel.setText("Mache den Test funktionsfähig!");
    }

    @FXML
    public void endRefactor() {

        stop = System.currentTimeMillis();
        refactorTime.add(new Time(start, stop));
        start = System.currentTimeMillis();

        isRefactor = false;
        instructionLabel.setText("Schreibe einen Test und wähle Compile & Run!");
        endRefactorMenuItem.setDisable(true);
        editTest();
    }

    @FXML
    public void backToEditTest() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Wenn du zurück gehst, werden die letzten Änderungen im Code gelöscht!", ButtonType.CANCEL, ButtonType.OK);
        alert.setHeaderText("Wirklich zurück zum Test?");
        alert.initStyle(StageStyle.UNDECORATED);
        alert.initOwner(App.getInstance().stage);
        alert.initModality(Modality.WINDOW_MODAL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.CANCEL) {
            return;
        } else {
            ((SolutionController) children.get("solution")).deleteNewCodeAndSetOldCode();
            editTest();
        }
    }

    @FXML
    public void save() throws IOException {

        SelectionController controller = (SelectionController) App.getInstance().getController("selection");

        String pathString = controller.selection.catalog.folder.getAbsolutePath();
        pathString += "/" + controller.selection.exercise.name;

        Path folderPath = Paths.get(pathString);
        Path codePath = Paths.get(pathString + "/" + controller.selection.exercise.codeTemplate.getName() + ".java");
        Path testPath = Paths.get(pathString + "/" + controller.selection.exercise.testTemplate.getTest_name() + ".java");

        if (!Files.exists(folderPath) && Files.notExists(folderPath)) {
            Files.createDirectory(folderPath);
            Files.createFile(codePath);
            Files.createFile(testPath);

            TrainerController activeController = (TrainerController) App.getInstance().getController("trainer");
            SolutionController solutionController = (SolutionController) activeController.getChildren().get("solution");

            Files.write(codePath, solutionController.getCodeInput().getBytes(), StandardOpenOption.WRITE);
            Files.write(testPath, solutionController.getTestInput().getBytes(), StandardOpenOption.WRITE);
        } else {

            TrainerController activeController = (TrainerController) App.getInstance().getController("trainer");
            SolutionController solutionController = (SolutionController) activeController.getChildren().get("solution");

            Files.write(codePath, solutionController.getCodeInput().getBytes());
            Files.write(testPath, solutionController.getTestInput().getBytes());
        }
    }

    @FXML
    public void compileAndRun() {

        /** Hole die Eingaben */
        String testAreaInput = ((SolutionController) children.get("solution")).getTestInput();
        String solutionAreaInput = ((SolutionController) children.get("solution")).getCodeInput();

        /** Kompiliere */
        Compilation compilation = new Compilation(testAreaInput, solutionAreaInput);
        CompilationUnit[] testAndSolution = compilation.getTestAndSolution();
        InternalCompiler compiler = compilation.initializeCompiler(testAndSolution);
        compiler.compileAndRunTests();

        /** Hole die benoetigten Ergebnisse der Kompilierung */
        boolean hasCompileErrors = compiler.getCompilerResult().hasCompileErrors();
        Collection<CompileError> testCompileErrors = compiler.getCompilerResult().getCompilerErrorsForCompilationUnit(testAndSolution[0]);
        Collection<CompileError> codeCompileErrors = compiler.getCompilerResult().getCompilerErrorsForCompilationUnit(testAndSolution[1]);

        int numberOfFailedTests = 0;
        Collection<TestFailure> testFailures = null;
        if (!hasCompileErrors) {
            numberOfFailedTests = compiler.getTestResult().getNumberOfFailedTests();
            testFailures = compiler.getTestResult().getTestFailures();
        }

        /** setze Compilefehlerliste bzw. Testfailuretabelle */
        if (hasCompileErrors)
            ((ErrorAndFailureController) children.get("errorAndFailure")).setContent(testCompileErrors, codeCompileErrors);
        else if (numberOfFailedTests > 0)
            ((ErrorAndFailureController) children.get("errorAndFailure")).setContent(testFailures);
        else if (!hasCompileErrors && numberOfFailedTests == 0)
            ((ErrorAndFailureController) children.get("errorAndFailure")).setContent();


        /** Abfrage der moeglichen Situationen und entsprechende Aktion */
        if (isRefactor) {
            if (hasCompileErrors) {

                if (!testCompileErrors.isEmpty())
                    refactorErrors.add(testCompileErrors.toString());

                if (!codeCompileErrors.isEmpty())
                    refactorErrors.add(codeCompileErrors.toString());

                statusBar.setFill(Color.RED);
                endRefactorMenuItem.setDisable(true);
            } else if (numberOfFailedTests != 0) {
                statusBar.setFill(Color.RED);
                endRefactorMenuItem.setDisable(true);
            } else if (!hasCompileErrors && numberOfFailedTests == 0) {
                statusBar.setFill(Color.GREEN);
                endRefactorMenuItem.setDisable(false);
            }

        } else {
            /** Man befindet sich im Test - Editor */
            if (!((SolutionController) children.get("solution")).testTextArea.isDisabled()) {

                if (hasCompileErrors || numberOfFailedTests == 1) {
                    stop = System.currentTimeMillis();
                    testTime.add(new Time(start, stop));
                    start = System.currentTimeMillis();


                    if (!testCompileErrors.isEmpty())
                        testErrors.add(testCompileErrors.toString());

                    if (!codeCompileErrors.isEmpty())
                        testErrors.add(codeCompileErrors.toString());

                    statusBar.setFill(Color.RED);
                    editCodeMenuItem.setDisable(false);
                    instructionLabel.setText("Um in die nächste Phase zu kommen, drücke Edit Code!");
                } else if (numberOfFailedTests == 0) {

                    statusBar.setFill(Color.GREEN);
                    editCodeMenuItem.setDisable(true);
                    instructionLabel.setText("Alle Tests laufen. Füge weiteren Test hinzu oder speichere deine Lösung!");
                } else if (numberOfFailedTests > 1) {
                    statusBar.setFill(Color.RED);
                    editCodeMenuItem.setDisable(true);
                    instructionLabel.setText("Es muss genau ein Test fehlschlagen!");
                }


                /** Man befindet sich im Code - Editor */
            } else if (!((SolutionController) children.get("solution")).codeTextArea.isDisabled()) {

                if (!hasCompileErrors && numberOfFailedTests == 0) {
                    stop = System.currentTimeMillis();
                    codeTime.add(new Time(start, stop));
                    start = System.currentTimeMillis();

                    statusBar.setFill(Color.GREEN);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Alle Tests bestanden. Möchtest du deinen Code verbessern?", ButtonType.YES, ButtonType.NO);
                    alert.setHeaderText("Refactor");
                    alert.initStyle(StageStyle.UNDECORATED);
                    alert.initOwner(App.getInstance().stage);
                    alert.initModality(Modality.WINDOW_MODAL);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.YES) {
                        isRefactor = true;
                        instructionLabel.setText("REFACTOR");
                        endRefactorMenuItem.setDisable(true);
                        backToEditTestMenuItem.setDisable(true);
                        editCodeMenuItem.setDisable(true);

                        stop = System.currentTimeMillis();
                        codeTime.add(new Time(start, stop));
                        start = System.currentTimeMillis();
                    } else if (result.isPresent() && result.get() == ButtonType.NO) {
                        editTest();
                    }
                } else {

                    if (!testCompileErrors.isEmpty())
                        codeErrors.add(testCompileErrors.toString());

                    if (!codeCompileErrors.isEmpty())
                        codeErrors.add(codeCompileErrors.toString());

                    statusBar.setFill(Color.RED);
                }
            }
        }
    }

    private StackPane getRootForDescription() {
        return descriptionStackPane;
    }

    private StackPane getRootForSolution() {
        return solutionStackPane;
    }

    private StackPane getRootForErrorAndFailureController() {
        return errorOrFailureStackPane;
    }

    public void didAppear() {
        backToEditTestMenuItem.setDisable(true);
        editCodeMenuItem.setDisable(true);
        statusBar.setFill(Color.GRAY);
        endRefactorMenuItem.setDisable(true);
        instructionLabel.setText("Schreibe einen Test und wähle Compile & Run!");
    }

    public void editTest() {
        tempSaveCode();
        ((SolutionController) children.get("solution")).disableCodeTextArea();
        ((SolutionController) children.get("solution")).enableTestTextArea();
        backToEditTestMenuItem.setDisable(true);
        instructionLabel.setText("Schreibe einen Test und wähle Compile & Run!");
    }

    public void tempSaveCode() {
        ((SolutionController) children.get("solution")).tempSaveCode();
    }

    public void initialize() {
        start = System.currentTimeMillis();
    }

    public void displayResult() throws IOException {

        App.getInstance().controllers.put("result", ResultController.createWithName("result"));
        App.getInstance().showController("result");
        App.getInstance().stage.setTitle("Zusammenfasung");

    }

    public ArrayList<Time> getTestTime() {
        return testTime;
    }

    public ArrayList<Time> getCodeTime() {
        return codeTime;
    }

    public ArrayList<Time> getRefactorTime() {
        return refactorTime;
    }

    public ArrayList<String> getTestErrors(){
        return testErrors;
    }

    public ArrayList<String> getCodeErrors(){
        return codeErrors;
    }

    public ArrayList<String> getRefactorErrors(){
        return refactorErrors;
    }

}
