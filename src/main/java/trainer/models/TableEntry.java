package trainer.models;


import javafx.beans.property.SimpleStringProperty;
import vk.core.api.CompileError;

import java.util.ArrayList;

public class TableEntry {
        private SimpleStringProperty testName = new SimpleStringProperty();

        public TableEntry(String name) {
            testName.set(name);
        }

        public TableEntry(CompileError compileError) {
            testName.set(compileError.toString());
        }

        public String getTestName() {
            return testName.get();
        }

        public SimpleStringProperty testProperty() {
            return testName;
        }

}
