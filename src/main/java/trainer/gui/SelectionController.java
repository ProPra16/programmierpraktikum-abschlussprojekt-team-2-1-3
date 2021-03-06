package trainer.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import org.xml.sax.SAXException;
import trainer.App;
import trainer.gui.system.Controller;
import trainer.models.Exercise;
import trainer.models.Selection;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class SelectionController extends Controller {

    @FXML
    private VBox root;
    @FXML
    private TextField catalogTextField;
    @FXML
    private ListView exercisesListView;
    @FXML
    public TextArea exerciseTextArea;

    private ObservableList<String> exerciseObservableList;
    public Selection selection = new Selection("tasks");

    public static SelectionController createWithName(String nameOfController) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SelectionController.class.getResource("/SelectionView.fxml"));
        fxmlLoader.load();
        SelectionController selectionController = fxmlLoader.getController();
        selectionController.setName(nameOfController);
        return selectionController;
    }

    @FXML
    public void showExercise() throws ParserConfigurationException, SAXException, IOException {
        /** Aufgabenstellung der ausgewaehlten Aufgabe anzeigen */

        if (exercisesListView.getSelectionModel().getSelectedItem() != null) {

            selection.exercise = new Exercise(selection.catalog, exercisesListView.getSelectionModel().getSelectedItem().toString());
            exerciseTextArea.setText(selection.exercise.description);
            App.getInstance().controllers.put("trainer", TrainerController.createWithName("trainer"));

        }
        App.getInstance().stage.setOnCloseRequest(event -> {
            ((TrainerController) App.getInstance().controllers.get("trainer")).running = false;
        });
    }

    @FXML
    public void quit() {
        App.getInstance().quit();
    }

    @FXML
    public void startTrainer() throws IOException, ParserConfigurationException, SAXException {
        /** Trainer starten */
        if (exercisesListView.getSelectionModel().getSelectedItem() != null) {
            selection.exercise.xmlObject = exercisesListView.getSelectionModel().getSelectedItem();
            App.getInstance().startTrainer();
        }
    }

    @FXML
    public void searchForCatalogs() {
        /** Katalog aendern  (beim Klicken des ... Button)*/
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
        ArrayList listOfXMLFiles = new ArrayList();
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".xml");
            }
        };
        File[] listOfAllFiles = selection.catalog.folder.listFiles(filenameFilter);
        for (File file : listOfAllFiles) {
            if (file.isFile()) {
                listOfXMLFiles.add(file.getName());
            }
        }
        return listOfXMLFiles;
    }

    public void loadFiles() throws IOException {
        ArrayList listOfTextFiles = scanForFiles();
        Collections.sort(listOfTextFiles);
        exerciseObservableList = FXCollections.observableList(listOfTextFiles);
        exercisesListView.setItems(exerciseObservableList);
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

    public void didAppear() {
        if (exercisesListView.getSelectionModel() != null) {
            try {
                showExercise();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
