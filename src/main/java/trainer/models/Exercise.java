package trainer.models;


import trainer.exercise.Setting;
import trainer.exercise.Template;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

public class Exercise {

    public Object name;
    public Path path;
    public String description;
    public LinkedHashMap<String, Template> templateList;
    public LinkedHashMap<String, Setting> settingList;
    public Catalog catalog;  /** Eine Aufgabe muss wissen, in welchem Katalog sie sich befindet, um sie aus dem entsprechenden Katalog laden zu koennen. */


    public Exercise(Catalog catalog, String name) {
        this.name = name;
        this.catalog = catalog;
        /** Anhand des Namens wird ein Objekt der ausgewählten Aufgabe erstellt */
        this.path = Paths.get(this.catalog.folder.getAbsolutePath() + "/" + this.name.toString());
        this.description = readDescription();
    }

    public String readDescription() {
        /** Aufgabe wird mit dem Reader als String eingelesen*/

        // TODO: Wenn eine Uebung aus Description, Templates und Settings besteht, so muss zunaechst die Description.txt geoeffnet werden
        // Falls eine Uebung aus einer .txt besteht, die Description, Templates und Settings beinhaltet, muss nach der Description gefiltert werden
        BufferedReader bR = null;
        try {
            bR = Files.newBufferedReader(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String tempLine;
        String description = "";
        try {
            while ((tempLine = bR.readLine()) != null)
                description += tempLine + "\n";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return description;
    }

}
