package trainer.models;

import java.io.File;

public class Selection {

    public File folder;
    public Object exerciseName;

    public Selection(String nameOfFolder) {
        folder = new File(nameOfFolder);
    }

}
