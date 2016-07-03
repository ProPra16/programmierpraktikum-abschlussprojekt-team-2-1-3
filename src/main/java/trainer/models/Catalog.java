package trainer.models;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

public class Catalog {

    public File folder;
    public Path path;
    ArrayList<Exercise> exerciseArrayList;
    // wird die exerciseArrayList ueberhaupt gebraucht? Die Exercise weiß, welchem Catalog sie gehoert und wird damit auch erzeugt

    // TODO: Keinen default Ordner (dh der Nutzer muss zunächst ein Katalog selbst auswählen)
    // ansonsten eine Loesung finden, dem default Catalog "tasks" den richtigen Pfad zuzuordnen

}
