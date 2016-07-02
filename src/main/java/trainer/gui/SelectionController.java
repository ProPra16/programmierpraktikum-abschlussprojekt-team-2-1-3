package trainer.gui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import trainer.App;
import trainer.gui.system.Controller;
import trainer.models.Catalog;
import trainer.models.Selection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class SelectionController extends Controller{


    @FXML
    public VBox root;

    @FXML
    private TextField catalogTextField;

    @FXML
    public ListView listView;

    @FXML
    private TextArea excerciseTextArea;

    private ObservableList<String> exerciseObservableList;


    public Selection selection = new Selection("tasks");

    public static SelectionController createWithName(String nameOfController) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SelectionController.class.getResource("/SelectionView.fxml"));
        fxmlLoader.load();
        SelectionController SelectionController = fxmlLoader.getController();
        SelectionController.setName(nameOfController);
        return SelectionController;
    }

    @FXML
    public void quit() {
        System.exit(0);
    }

    @FXML
    public void startTrainer() throws IOException {
        selection.exerciseName = listView.getSelectionModel().getSelectedItem();
        App.getInstance().startTrainer();
    }

    @FXML
    public void searchForCatalogs() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Switch Folder");
        File selectedDirectory = directoryChooser.showDialog(App.getInstance().getStage());

        if (selectedDirectory != null)
            selection.catalog.folder = selectedDirectory;
        try {
            loadFiles();
            catalogTextField.setText(selection.catalog.folder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList scanForFiles() throws IOException {
        ArrayList listOfTextFiles = new ArrayList();
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        };
        File[] listOfAllFiles = selection.catalog.folder.listFiles(filenameFilter);
        for (File file : listOfAllFiles) {
            if (file.isFile()) {
                listOfTextFiles.add(file.getName());
            }
        }
        return listOfTextFiles;
    }

    public void setSelection(Selection selection) {
        this.selection = selection;
    }

    public Selection getSelection() {
        return selection;
    }

    public void loadFiles() throws IOException {
        ArrayList listOfTextFiles = scanForFiles();
        Collections.sort(listOfTextFiles);
        exerciseObservableList = FXCollections.observableList(listOfTextFiles);
        listView.setItems(exerciseObservableList);
    }

    public void willAppear() {
        /** Katalogpfad in das Textfenster laden*/
        catalogTextField.setText(selection.catalog.folder.getAbsoluteFile().getAbsolutePath());

        /** Aufgaben in die Liste laden */
        try {
            loadFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readExercise() {
        /** Pfad für ausgewählte Aufgabe wird erstellt und die Aufgabe wird mit dem Reader als String eingelesen.*/

        Path taskPath = Paths.get(((SelectionController) App.getInstance().controllers.get("selection")).selection.catalog.folder.getAbsolutePath() + "/" + ((SelectionController) App.getInstance().controllers.get("selection")).selection.exerciseName.toString());
        BufferedReader bR = null;
        try {
            bR = Files.newBufferedReader(taskPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String tempLine;
        String exercise = "";
        try {
            while ((tempLine = bR.readLine()) != null)
                exercise += tempLine + "\n";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exercise;

    }

    /* TODO: clickedExercise(), sobald der Nutzer auf ein ListView Eintrag klickt:
            Fange die angeklickte Aufgabe ab und schreibe die Aufgabenstellung in die exerciseTextArea
             Achtung, die erste Zeile von readExercise muss auch geändert werden.
    */

    @FXML
    public void clickedExercise() {
        excerciseTextArea.setText(readExercise());
    }


}
