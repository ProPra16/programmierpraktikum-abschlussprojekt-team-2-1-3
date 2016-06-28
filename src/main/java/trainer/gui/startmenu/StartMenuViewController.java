package trainer.gui.startmenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import trainer.App;
import trainer.gui.programmview.ProgrammViewController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class StartMenuViewController {

    @FXML
    private ListView listView;
    private ObservableList<String> observableList;

    private File folder = new File("tasks");

    public ArrayList scanForFiles() throws IOException {

        ArrayList temporaerArrayList = new ArrayList();
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        };

        File[] listOfAllFiles = folder.listFiles(filenameFilter);

        for (File file : listOfAllFiles) {
            if (file.isFile()) {
                temporaerArrayList.add(file.getName());
            }
        }
        return temporaerArrayList;
    }

    public void setFileNames() throws IOException {

        ArrayList arrayList = scanForFiles();
        Collections.sort(arrayList);

        observableList = FXCollections.observableList(arrayList);
        listView.setItems(observableList);

    }

    public void loadButton() throws IOException {
        scanForFiles();
        setFileNames();
    }

    @FXML
    public void startProgramm() throws IOException {

        Object filename = listView.getSelectionModel().getSelectedItem();

        if (filename == null) {
        } else {
            Path taskpath = Paths.get(folder.getAbsolutePath() + "/" + filename.toString());
            BufferedReader bR = Files.newBufferedReader(taskpath);

            String temp;
            String result = "";

            while ((temp = bR.readLine()) != null)
                result += temp + "\n";

            ProgrammViewController controller = new ProgrammViewController();
            controller.task = result;

            Stage stage = App.getInstance().getStage();
            FXMLLoader loader = new FXMLLoader(controller.getClass().getResource("/ProgrammView.fxml"));

            GridPane pane = loader.load();
            Scene scene = new Scene(pane);

            stage.setScene(scene);
            stage.setTitle("TDD - Trainer");
            stage.show();
        }
    }

    @FXML
    public void switchFolderButton() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Switch Folder");
        File selectedDirectory =
                directoryChooser.showDialog(App.getInstance().getStage());

        if (selectedDirectory != null)
            folder = selectedDirectory;
    }
}
