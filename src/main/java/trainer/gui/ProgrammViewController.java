package trainer.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ProgrammViewController {


    @FXML
    private static TextArea leftTextArea;

    @FXML
    private static TextArea rightTextArea;

    public static void disableLeftTextArea (){
        leftTextArea.setEditable(false);
    }

    public static void disableRightTextArea (){
        rightTextArea.setEditable(false);
    }

    public static void enableLeftTextArea (){
        leftTextArea.setEditable(true);
    }

    public static void enableRightTextArea (){
        rightTextArea.setEditable(false);
    }

    public static void setLeftTextArea (TextArea textarea){
        leftTextArea = textarea;
    }
}
