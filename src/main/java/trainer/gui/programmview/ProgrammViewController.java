package trainer.gui.programmview;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class ProgrammViewController {


    @FXML
    private TextArea leftTextArea = new TextArea("");

    @FXML
    private TextArea rightTextArea = new TextArea("");

    @FXML
    private TextArea problem_definition = new TextArea("");

    @FXML
    private Circle statusbutton;

    public static String task;
    private int status = 0;

    public void initialize() throws IOException {

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

}
