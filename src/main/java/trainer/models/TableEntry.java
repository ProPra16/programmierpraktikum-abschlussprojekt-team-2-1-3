package trainer.models;


import javafx.beans.property.SimpleStringProperty;

public class TableEntry {
        private SimpleStringProperty testName = new SimpleStringProperty();


        public TableEntry(String name) {
            testName.set(name);
        }

        public String getTestName() {
            return testName.get();
        }

        public SimpleStringProperty testProperty() {
            return testName;
        }

}
