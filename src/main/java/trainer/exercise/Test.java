package trainer.exercise;


public class Test {

    private String test_name;
    private String test_code;


    public void initalizeTest(String name, String code){
        test_name = name;
        test_code = code;
    }

    public String getTest_code() {
        return test_code;
    }

    public String getTest_name() {
        return test_name;
    }
}
