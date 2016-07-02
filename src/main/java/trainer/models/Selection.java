package trainer.models;

import java.io.File;

public class Selection {

    public Catalog catalog = new Catalog();
    public Object exerciseName;

    public static Selection create(String nameOfFolder) {
        Selection selection = new Selection(nameOfFolder);
        return selection;
    }

    public Selection(String nameOfFolder) {
        catalog.folder = new File(nameOfFolder);
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }


}
