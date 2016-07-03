package trainer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import trainer.gui.*;
import trainer.gui.system.Controller;

import java.io.IOException;
import java.util.LinkedHashMap;

public class App extends Application {
    private static App instance;

    private Stage stage;
    public LinkedHashMap<String, Controller> controllers = new LinkedHashMap<>();
    private Controller activeController;

    @Override
    public void init() throws Exception {
        super.init();
        instance = this;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /** Initialisierung */
        stage = primaryStage;
        stage.setTitle("Hauptmenü");
        stage.setScene(new Scene(new Pane()));

        controllers.put("selection", SelectionController.createWithName("selection"));
        controllers.put("trainer", TrainerController.createWithName("trainer"));
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

    public void startTrainer() throws IOException {
        /** Nach ausgewaehler Aufgabe wird der Trainer geoeffnet */
            showController("trainer");
            stage.setTitle("TDD - Trainer");
        }

    public void quit() {
        System.exit(0);
    }

    public static void main(String[] args){
        launch(args);
    }

    public static App getInstance(){
        return instance;
    }

    public Stage getStage(){
        return this.stage;
    }
}
