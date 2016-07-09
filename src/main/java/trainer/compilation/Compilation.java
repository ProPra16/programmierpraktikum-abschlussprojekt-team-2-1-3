package trainer.compilation;

import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.internal.InternalCompiler;


public class Compilation {

    private CompilationUnit[] testAndCode = new CompilationUnit[2];
    private InternalCompiler compiler;


    public Compilation (String testInput, String codeInput) {
        testAndCode[0] = new CompilationUnit(getClassName(testInput), testInput, isATest(testInput));
        testAndCode[1] = new CompilationUnit(getClassName(codeInput), codeInput, isATest(codeInput));
    }


    /** sucht den Klassennamen in dem übergebenen String, der die ganze Klasse enthält und gibt ihn zurück */
    public String getClassName(String classContent) {
        String className = "";
        int index = classContent.indexOf("class");
        index += 6;

        while ((classContent.charAt(index) != ' ') && (classContent.charAt(index) != '{') ) {
            className += classContent.charAt(index);
            index += 1;
        }
        return className;
    }

    public boolean isATest(String classContent) {
        String className = getClassName(classContent);
        if (className.indexOf("Test") > -1)
            return true;
        else
            return false;
    }

    public InternalCompiler initializeCompiler(CompilationUnit[] testAndCdoe) {
        compiler = (InternalCompiler) CompilerFactory.getCompiler(testAndCdoe);
        return compiler;
    }

    public CompilationUnit[] getTestAndCode() {
        return testAndCode;
    }
}
