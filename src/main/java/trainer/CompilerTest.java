package trainer;

import trainer.compilation.Compilation;
import trainer.gui.TrainerController;
import vk.core.api.CompilationUnit;
import vk.core.internal.InternalCompiler;


public class CompilerTest {
    public static void main(String[] args) {

        String solutionInput = "public class Foo { \n"
                + "     public static int fourtyTwo() { \n"
                + "         return 41 + 1; \n"
                + "     }\n"
                + "}";

        String testInput = "import static org.junit.Assert.*; \n"
                + "import org.junit.Test; \n"
                + "public class FooTest { \n"
                + "     @Test \n"
                + "     public voi leTest() { \n"
                + "         assertEquals(42, Foo.fourtyTwo()); \n"
                + "     }\n"
                + "}";


        /** so werden aus den Inputs die CompilationUnits gemacht */
        Compilation compilation = new Compilation(testInput, solutionInput);

        InternalCompiler compiler = compilation.initializeCompiler(compilation.getTestAndSolution());

        TrainerController test = new TrainerController();

        /* Test für Umwandlung von String zu CompilationUnit:

        System.out.println(compilation.testUnit.getClassName());
        System.out.println(compilation.testUnit.getClassContent());
        System.out.println(compilation.testUnit.isATest());

        System.out.println(compilation.solutionUnit.getClassName());
        System.out.println(compilation.solutionUnit.getClassContent());
        System.out.println(compilation.solutionUnit.isATest());
        */

        test.compileAndRun(testInput, solutionInput);

        //compilation.compileAndRun();

    }
}
