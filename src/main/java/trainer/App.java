package trainer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {

    private static App instance;
    private Stage stage;

    public App() {
        instance = this;
    }


    @Override
    public void start(Stage stage) throws Exception {

        this.stage = stage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StartMenuView.fxml"));

        BorderPane pane = loader.load();
        Scene scene = new Scene(pane);

        stage.setScene(scene);
        stage.setTitle("Menu");
        stage.show();


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
