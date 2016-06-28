package trainer.gui.startmenu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import trainer.App;
import trainer.gui.ProgrammViewController;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class StartMenuViewController {

    @FXML
    private ListView taskfield;

    public void startButton() throws IOException {

        Stage stage = App.getInstance().getStage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProgrammView.fxml"));

        GridPane pane = loader.load();
        Scene scene = new Scene(pane);

        stage.setScene(scene);
        stage.setTitle("TDD - Trainer");
        stage.show();

        TextArea tempTextArea = new TextArea();
        tempTextArea.setEditable(false);
        ProgrammViewController.setLeftTextArea(tempTextArea);
    }

    public File[] scanForFiles() {

        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        };

        File folder = new File("/tasks");
        File[] listOfAllFiles = folder.listFiles(filenameFilter);

        return listOfAllFiles;
    }

    public void setFileNames() {

        File[] listOfFiles = scanForFiles();

        File.



    }


}
