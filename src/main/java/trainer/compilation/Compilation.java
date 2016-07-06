package trainer.compilation;

import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.internal.InternalCompiler;


public class Compilation {

/*
    String testFileName;
    String solutionFileName;
*/

    private String testInput;
    private String solutionInput;
    private CompilationUnit[] testAndSolution = new CompilationUnit[2];
    private InternalCompiler compiler;


    public Compilation (String testInput, String solutionInput) {
        this.testInput = testInput;
        this.solutionInput = solutionInput;
        testAndSolution[0] = new CompilationUnit(getClassName(testInput), testInput, isATest(testInput));
        testAndSolution[1] = new CompilationUnit(getClassName(solutionInput), solutionInput, isATest(solutionInput));
    }

    /*
    public String readTextFile(String fileName) {
        String content = "";
        String line;
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));
            while ((line = bufferedReader.readLine()) != null)
                content += line;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    */

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

    public String getClassContent(String classContent) {
        return classContent;
    }

    public boolean isATest(String classContent) {
        String className = getClassName(classContent);
        if (className.indexOf("Test") > -1)
            return true;
        else
            return false;
    }





    /** wenn der user run klickt, soll compileAndRun() ausgeführt werden */
    public void compileAndRun() {

        compiler.compileAndRunTests();
        compiler.getCompilerResult();
        compiler.getTestResult();


    }

    public InternalCompiler initializeCompiler(CompilationUnit[] testAndSolution) {
        compiler = (InternalCompiler) CompilerFactory.getCompiler(testAndSolution);
        return compiler;
    }

    public CompilationUnit[] getTestAndSolution() {
        return testAndSolution;
    }
}
