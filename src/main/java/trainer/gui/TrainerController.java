package trainer.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import trainer.App;
import trainer.gui.system.Controller;

import java.io.IOException;

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
        FXMLLoader loader = new FXMLLoader(TrainerController.class.getResource("/TrainerView.fxml"));
        loader.load();
        TrainerController trainerController = loader.getController();
        trainerController.setName(nameOfController);
        return trainerController;
    }

    public void initialize(String exercise) {

        rightTextArea.setEditable(false);
        problem_definition.setEditable(false);
        rightTextArea.setDisable(true);

        problem_definition.setText(exercise);
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

    public String readExercise() {
        /** Aufgabenstellung anzeigen */
        return ((SelectionController) App.getInstance().controllers.get("selection")).selection.exercise.description;
    }

    @Override
    public GridPane getRoot() {
        return root;
    }

    @Override
    public void willAppear() {
        if (((SelectionController) App.getInstance().controllers.get("selection")).selection.exercise.name == null) {
        } else {
            initialize(readExercise());
        }
    }
}
