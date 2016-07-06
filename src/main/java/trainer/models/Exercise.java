package trainer.models;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import trainer.exercise.Jclass;
import trainer.exercise.Setting;
import trainer.exercise.Template;
import trainer.exercise.Test;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class Exercise {

    public Object name;
    public final Path path;
    public String description;
    public LinkedHashMap<String, Template> templateList;
    public LinkedHashMap<String, Setting> settingList;
    public Catalog catalog;
    /**
     * Eine Aufgabe muss wissen, in welchem Katalog sie sich befindet, um sie aus dem entsprechenden Katalog laden zu koennen.
     */

    public HashMap<String, Object> exercice = new HashMap<>();

    public Exercise(Catalog catalog, Object object) throws IOException, SAXException, ParserConfigurationException {

        exercice.put("class", new Jclass());
        exercice.put("test", new Test());
        exercice.put("config", new Setting());

        this.name = object;
        this.catalog = catalog;
        /** Anhand des Namens wird ein Objekt der ausgewählten Aufgabe erstellt */
        this.path = Paths.get(this.catalog.folder.getAbsolutePath() + "/" + this.name.toString());
        readExercise();

    }

    public void readExercise() throws ParserConfigurationException, IOException, SAXException {
        /** Aufgabe wird mit dem Reader als String eingelesen*/

        // TODO: Wenn eine Uebung aus Description, Templates und Settings besteht, so muss zunaechst die Description.txt geoeffnet werden
        // Falls eine Uebung aus einer .txt besteht, die Description, Templates und Settings beinhaltet, muss nach der Description gefiltert werden


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();


        Document doc = builder.parse(path.toFile());
        Element root = doc.getDocumentElement();

        NodeList nodelist = root.getElementsByTagName("exercise");


        org.w3c.dom.Node node = nodelist.item(0);

        if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {

            Element element = (Element) node;

            exercice.put("exercise_name", element.getAttribute("name"));

            Jclass jclass = (Jclass) exercice.get("class");
            jclass.initalizeJclass(element.getElementsByTagName("class_name").item(0).getTextContent(), element.getElementsByTagName("class_code").item(0).getTextContent());

            Test test = (Test) exercice.get("test");
            test.initalizeTest(element.getElementsByTagName("test_name").item(0).getTextContent(), element.getElementsByTagName("test_code").item(0).getTextContent());

            exercice.put("description", element.getElementsByTagName("description").item(0).getTextContent());
            description = element.getElementsByTagName("description").item(0).getTextContent();

            Setting settings = (Setting) exercice.get("config");
            Boolean[] sets = {Boolean.getBoolean(element.getElementsByTagName("babysteps_value").item(0).getTextContent()), Boolean.getBoolean(element.getElementsByTagName("timetracking_value").item(0).getTextContent())};
            settings.setSettings(sets);
        }
    }
}


