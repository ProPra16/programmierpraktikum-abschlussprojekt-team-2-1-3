package trainer.exercise;


public class Setting {

    private Boolean[] settings = new Boolean[3];

    public void setSettings(Boolean[] settings){

        if (settings.length != 2)
            System.out.println("Nicht exakt 3 Settings angebeben!");

        else{
            this.settings[0] = settings[0];
            this.settings[1] = settings[1];
        }
    }

    public Boolean[] getSettings(){
        return settings;
    }

    // TODO: Spezifikation der Einstellungen (Babysteps,...) einer Aufgabe
}
