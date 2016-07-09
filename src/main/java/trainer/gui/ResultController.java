package trainer.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;
import trainer.App;
import trainer.gui.system.Controller;
import trainer.time.Time;

import java.io.IOException;
import java.util.ArrayList;

public class ResultController extends Controller {

    @FXML
    PieChart timeChart;

    @FXML
    ListView errorView;

    public void initialize() {
        setTimeChart();
    }

    public static ResultController createWithName(String nameOfController) throws IOException {
        FXMLLoader loader = new FXMLLoader(ResultController.class.getResource("/ResultView.fxml"));
        loader.load();
        ResultController resultController = loader.getController();
        resultController.setName(nameOfController);

        return resultController;
    }

    public void setTimeChart() {

        TrainerController trainerController = (TrainerController) App.getInstance().getController("trainer");
        ArrayList<Time> code = trainerController.getCodeTime();
        ArrayList<Time> test = trainerController.getTestTime();
        ArrayList<Time> refactor = trainerController.getRefactorTime();

        long codeduration = 0;
        long testduration = 0;
        long refactorduration = 0;

        for (Time duration : code) {
            codeduration += duration.getDuration();
        }

        for (Time duration : test) {
            testduration += duration.getDuration();
        }

        for (Time duration : refactor) {
            refactorduration += duration.getDuration();
        }

        System.out.println(codeduration);
        System.out.println(testduration);
        System.out.println(refactorduration);

        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                new PieChart.Data("Code", codeduration),
                new PieChart.Data("Test", testduration),
                new PieChart.Data("Refactor", refactorduration)
        );

        timeChart = new PieChart(data);

    }

}
