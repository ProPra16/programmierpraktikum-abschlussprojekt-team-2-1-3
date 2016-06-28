package trainer.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import trainer.App;

import java.io.IOException;

public class StartMenuViewController {

    public void startButton() throws IOException {

        Stage stage = App.getInstance().getStage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProgrammView.fxml"));

        GridPane pane = loader.load();
        Scene scene = new Scene(pane);

        stage.setScene(scene);
        stage.setTitle("TDD - Trainer");
        stage.show();

        TextArea tempTextArea = new TextArea();
        ProgrammViewController.setLeftTextArea(tempTextArea);
        ProgrammViewController.disableLeftTextArea();
    }
}
