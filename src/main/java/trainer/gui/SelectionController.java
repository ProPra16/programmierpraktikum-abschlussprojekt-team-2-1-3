package trainer.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import trainer.App;
import trainer.gui.system.Controller;
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

public class SelectionController extends Controller {

    @FXML
    public VBox root;
    @FXML
    private TextField catalogTextField;
    @FXML
    public ListView listView;
    @FXML
    public TextArea exerciseTextArea;
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
    public void showExercise(MouseEvent arg0) {
        /** Aufgabenstellung der ausgewaehlten Aufgabe anzeigen */
        selection.setExerciseName(listView.getSelectionModel().getSelectedItem().toString());
        exerciseTextArea.setText(readExercise());
    }

    @FXML
    public void quit() {
        System.exit(0);
    }

    @FXML
    public void startTrainer() throws IOException {
        /** Trainer starten */
        selection.exerciseName = listView.getSelectionModel().getSelectedItem();
        App.getInstance().startTrainer();
    }

    @FXML
    public void searchForCatalogs() {
        /** Katalog aendern  (beim klicken des ... Button)*/
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Switch Folder");
        File selectedDirectory = directoryChooser.showDialog(App.getInstance().getStage());

        if (selectedDirectory != null)
            selection.catalog.folder = selectedDirectory;
        try {
            loadFiles();
            catalogTextField.setText(selection.catalog.folder.getAbsolutePath());
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

        Path taskPath = Paths.get(selection.catalog.folder.getAbsolutePath() + "/" + selection.exerciseName.toString());
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

    public void setSelection(Selection selection) {
        this.selection = selection;
    }

    public Selection getSelection() {
        return selection;
    }
}
