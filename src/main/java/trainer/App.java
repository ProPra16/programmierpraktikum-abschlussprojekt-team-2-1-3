package trainer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import trainer.gui.startmenu.SelectionController;
import trainer.gui.system.Controller;

import java.util.LinkedHashMap;
import java.util.StringJoiner;

public class App extends Application {
    private static App instance;

    private Stage stage;
    private LinkedHashMap<String, Controller> controllers = new LinkedHashMap<>();
    private Controller activeController;

    /*public App() {
        instance = this;
    }*/


    @Override
    public void start(Stage primaryStage) throws Exception {
        /** Initialisierung */
        stage = primaryStage;
        stage.setTitle("Menu");
        stage.setScene(new Scene(new Pane()));


        controllers.put("selection", SelectionController.createWithName("selection"));
        showController("selection");

        /** Anzeigen */
        stage.show();


    }

    public void showController(String nameOfController) {
        if (!controllers.containsKey(nameOfController)) return;
        if (activeController != null) {
            activeController.willDisappear();
            for (Controller controller : activeController.getChildren().values()) {
                controller.willDisappear();
            }
            stage.getScene().setRoot(new Pane());
            for (Controller controller : activeController.getChildren().values()) {
                controller.didDisappear();
            }
            activeController.didDisappear();
        }
        activeController = controllers.get(nameOfController);
        activeController.willAppear();
        for (Controller controller : activeController.getChildren().values()) {
            controller.willAppear();
        }
        stage.getScene().setRoot(activeController.getRoot());
        for (Controller controller : activeController.getChildren().values()) {
            controller.didAppear();
        }
        activeController.didAppear();
    }

    public static void main(String[] args){
        launch(args);
    }

    public static App getInstance (){
        return instance;
    }

    public Stage getStage(){
        return this.stage;
    }
}
