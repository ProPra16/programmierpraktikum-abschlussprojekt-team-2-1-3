package trainer.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import trainer.App;
import trainer.gui.system.Controller;
import trainer.models.Selection;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class SelectionController extends Controller {

    @FXML
    private ListView listView;
    private ObservableList<String> taskObservableList;
    public BorderPane root;

    public Selection selection = new Selection("tasks");

    public static SelectionController createWithName(String nameOfController) throws IOException {
        FXMLLoader loader = new FXMLLoader(SelectionController.class.getResource("/SelectionView.fxml"));
        loader.load();
        SelectionController selectionController = loader.getController();
        selectionController.setName(nameOfController);
        return selectionController;
    }

    @FXML
    public void loadFiles() throws IOException {
        ArrayList listOfTextFiles = scanForFiles();
        Collections.sort(listOfTextFiles);
        taskObservableList = FXCollections.observableList(listOfTextFiles);
        listView.setItems(taskObservableList);
    }

    @FXML
    public void switchFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Switch Folder");
        File selectedDirectory =
                directoryChooser.showDialog(App.getInstance().getStage());

        if (selectedDirectory != null)
            selection.folder = selectedDirectory;
    }

    @FXML
    public void startTrainer() throws IOException {
        selection.exerciseName = listView.getSelectionModel().getSelectedItem();
        App.getInstance().startTrainer();
    }

    public ArrayList scanForFiles() throws IOException {
        ArrayList listOfTextFiles = new ArrayList();
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        };
        File[] listOfAllFiles = selection.folder.listFiles(filenameFilter);
        for (File file : listOfAllFiles) {
            if (file.isFile()) {
                listOfTextFiles.add(file.getName());
            }
        }
        return listOfTextFiles;
    }

}
