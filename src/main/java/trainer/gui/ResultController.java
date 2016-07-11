package trainer.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import trainer.App;
import trainer.gui.system.Controller;
import trainer.time.Time;

import java.io.IOException;
import java.util.ArrayList;

public class ResultController extends Controller {

    @FXML
    BarChart timeChart;

    @FXML
    ListView testList;

    @FXML
    ListView codeList;

    @FXML
    ListView refactorList;

    @FXML
    Label numberOfTestErrors;

    @FXML
    Label numberOfCodeErrors;

    @FXML
    Label numberOfRefactorErrors;

    public void initialize() {
        setTimeChart();
        setErrorView();
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

        codeduration = codeduration/1000;
        testduration = testduration/1000;
        refactorduration = refactorduration/1000;

        XYChart.Series testseries = new XYChart.Series();
        XYChart.Series codeseries = new XYChart.Series();
        XYChart.Series refactorseries = new XYChart.Series();

        testseries.setName("Testphase");
        testseries.getData().add(new XYChart.Data<>("",testduration));

        codeseries.setName("Codephase");
        codeseries.getData().add(new XYChart.Data<>("",codeduration));

        refactorseries.setName("Refactorphase");
        refactorseries.getData().add(new XYChart.Data<>("",refactorduration));

        timeChart.getData().addAll(testseries, codeseries, refactorseries);
        timeChart.setTitle("Zeitverteilung");

    }

    public void setErrorView(){

        TrainerController trainerController = (TrainerController) App.getInstance().getController("trainer");

        ArrayList<String> testErrors = trainerController.getTestErrors();
        ArrayList<String> codeErrors = trainerController.getCodeErrors();
        ArrayList<String> refactorErrors = trainerController.getRefactorErrors();

        testList.setItems(FXCollections.observableArrayList(testErrors));
        codeList.setItems(FXCollections.observableArrayList(codeErrors));
        refactorList.setItems(FXCollections.observableArrayList(refactorErrors));

        numberOfTestErrors.setText("Testfehler: " + testErrors.size());
        numberOfCodeErrors.setText("Codefehler: " + codeErrors.size());
        numberOfRefactorErrors.setText("Refactorfehlger: " + refactorErrors.size());

    }

    @FXML
    public void back(){
        App.getInstance().showController("selection");
    }

}
