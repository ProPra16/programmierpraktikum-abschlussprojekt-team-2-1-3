package trainer.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import trainer.App;
import trainer.gui.system.Controller;
import trainer.models.Selection;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TrainerController extends Controller {

    @FXML
    private GridPane root;

    @FXML
    private TextArea leftTextArea = new TextArea("");

    @FXML
    private TextArea rightTextArea = new TextArea("");

    @FXML
    private TextArea problem_definition = new TextArea("");

    @FXML
    private Circle statusbutton;

    public String task;
    private int status = 0;

    public static TrainerController createWithName(String nameOfController) throws IOException {
        FXMLLoader loader = new FXMLLoader(SelectionController.class.getResource("/TrainerView.fxml"));
        loader.load();
        TrainerController trainerController = loader.getController();
        trainerController.setName(nameOfController);
        return trainerController;
    }

    public void initialize(String task) {

        rightTextArea.setEditable(false);
        problem_definition.setEditable(false);
        rightTextArea.setDisable(true);

        problem_definition.setText(task);
    }

    public void disableLeftTextArea() {
        leftTextArea.setEditable(false);
    }

    public void disableRightTextArea() {
        rightTextArea.setEditable(false);
    }

    public void enableLeftTextArea() {
        leftTextArea.setEditable(true);
    }

    public void enableRightTextArea() {
        rightTextArea.setEditable(false);
    }

    public void colorcheck (){
        if (status == 0){
            statusbutton.setFill(Color.LIGHTGREEN);
            status = 1;
        }

        else if (status == 1){
            statusbutton.setFill(Color.ORANGERED);
            status = 0;
        }
    }

    @Override
    public GridPane getRoot() {
        return root;
    }

    @Override
    public void willAppear() {
        /** Pfad für ausgewählte Aufgabe wird erstellt und die Aufgabe wird mit dem Reader als String eingelesen.*/

        if (((SelectionController)App.getInstance().controllers.get("selection")).selection.exerciseName == null) {
        } else {
            Path taskPath = Paths.get(((SelectionController) App.getInstance().controllers.get("selection")).selection.folder.getAbsolutePath() + "/" + ((SelectionController) App.getInstance().controllers.get("selection")).selection.exerciseName.toString());
            BufferedReader bR = null;
            try {
                bR = Files.newBufferedReader(taskPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String tempLine;
            String task = "";

            try {
                while ((tempLine = bR.readLine()) != null)
                    task += tempLine + "\n";
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.task = task;
            initialize(this.task);
        }

    }
}
