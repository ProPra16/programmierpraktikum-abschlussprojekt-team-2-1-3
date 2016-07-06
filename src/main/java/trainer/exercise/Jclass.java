package trainer.exercise;


public class Jclass {

    private String class_name;
    private String class_code;

    public void initalizeJclass(String name, String code){
        class_name = name;
        class_code = code;
    }

    public String getName (){
        return class_name;
    }
    public String getCode (){ return class_code;}
}
