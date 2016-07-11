import org.junit.Test;
import trainer.compilation.Compilation;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CompilationTest {

    String validTestInput = "public class FooTest {" +
            "@Test" +
            "public void testFooMethod() {" +
            "}" +
            "}";

    String validCodeInput = "public class Foo {" +
            "public void fooMethod() {}" +
            "}";

    String invalidCodeInput = "public Foo {" +
            "public void fooMethod() {}" +
            "}";

    Compilation compilation = new Compilation(validTestInput, invalidCodeInput);

    @Test
    public void testingGetClassName_shouldNotResultInFailure() {
        String className = compilation.getClassName(validTestInput);
        assertTrue(className.equals("FooTest"));
    }

    @Test
    public void testingGetClassName_shouldResultInFailure() {
        String className = compilation.getClassName(invalidCodeInput);
        assertFalse("getClassName() sollte eigentlich fehlschlagen!", className.equals("Foo"));
    }

    @Test
    public void testingIsATest_shouldNotResultInFailure() {
        boolean isATest = compilation.isATest(validTestInput);
        assertTrue(isATest);
    }

    @Test
    public void testingIsATest_shouldResultInFailure() {
        boolean isATest = compilation.isATest(validCodeInput);
        assertFalse("Code wird fälschlicherweise als Test aufgefasst!", isATest);
    }
}
