package trainer.models;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import trainer.App;
import trainer.exercise.Jclass;
import trainer.exercise.Test;
import trainer.gui.SelectionController;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

public class Exercise {

    public Object xmlObject;

    /**
     * Eine Aufgabe muss wissen, in welchem Katalog sie sich befindet, um sie aus dem entsprechenden Katalog laden zu koennen.
     */

    public Catalog catalog;
    public final Path path;

    public String name;
    public String description;
    public Jclass codeTemplate = new Jclass();
    public Test testTemplate = new Test();
    public LinkedHashMap<String, Boolean> settingList = new LinkedHashMap<>();



    public Exercise(Catalog catalog, Object object) throws IOException, SAXException, ParserConfigurationException {

        this.xmlObject = object;
        this.catalog = catalog;

        /** Anhand des Namens wird ein Objekt der ausgewählten Aufgabe erstellt */

        this.path = Paths.get(this.catalog.folder.getAbsolutePath() + "/" + this.xmlObject.toString());
        readExercise();
    }

    public void readExercise() throws ParserConfigurationException, IOException, SAXException {
        /** Aufgabe wird mit dem Reader als String eingelesen*/


        SelectionController controller = (SelectionController) App.getInstance().getController("selection");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();


        Document doc = builder.parse(path.toFile());
        Element root = doc.getDocumentElement();

        NodeList nodelist = root.getElementsByTagName("exercise");


        org.w3c.dom.Node node = nodelist.item(0);

        if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {

            Element element = (Element) node;

            name =  element.getAttribute("name");

            String pathString = controller.selection.catalog.folder.getAbsolutePath();
            pathString += "/" + name;

            Path codePath = Paths.get(pathString + "/" + element.getElementsByTagName("class_name").item(0).getTextContent() + ".java");
            Path testPath = Paths.get(pathString + "/" + element.getElementsByTagName("test_name").item(0).getTextContent() + ".java");

            if(Files.exists(codePath) && !Files.notExists(codePath))
                codeTemplate.initalizeJclass(element.getElementsByTagName("class_name").item(0).getTextContent(), new String (Files.readAllBytes(codePath)));

            else
            codeTemplate.initalizeJclass(element.getElementsByTagName("class_name").item(0).getTextContent(), element.getElementsByTagName("class_code").item(0).getTextContent());

            if(Files.exists(testPath) && !Files.notExists(testPath))
                testTemplate.initalizeTest(element.getElementsByTagName("test_name").item(0).getTextContent(), new String (Files.readAllBytes(testPath)));

            else
                testTemplate.initalizeTest(element.getElementsByTagName("test_name").item(0).getTextContent(), element.getElementsByTagName("test_code").item(0).getTextContent());

            description = element.getElementsByTagName("description").item(0).getTextContent();

            settingList.put("babysteps_value", Boolean.valueOf(element.getElementsByTagName("babysteps_value").item(0).getTextContent()));
            settingList.put("timetracking_value", Boolean.valueOf(element.getElementsByTagName("timetracking_value").item(0).getTextContent()));
        }
    }
}


