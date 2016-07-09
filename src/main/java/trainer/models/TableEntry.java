package trainer.models;


import javafx.beans.property.SimpleStringProperty;
import vk.core.api.CompileError;

public class TableEntry {
        private SimpleStringProperty testMethodName = new SimpleStringProperty();

        public TableEntry(String name) {
            testMethodName.set(name);
        }

        public TableEntry(CompileError compileError) {
            testMethodName.set(compileError.toString());
        }

        public String getTestMethodName() {
            return testMethodName.get();
        }

        public SimpleStringProperty testProperty() {
            return testMethodName;
        }

}
