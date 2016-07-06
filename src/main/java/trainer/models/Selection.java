package trainer.models;

import java.io.File;

public class Selection {

    public Catalog catalog = new Catalog();
    public Exercise exercise;


    public Selection(String nameOfFolder) {
        catalog.folder = new File(nameOfFolder);
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }


}
