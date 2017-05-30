package nl.zorgkluis.demo.afstudeeropdracht.CDAObjects;

/**
 * Created by Zorgkluis (geert).
 */
public class Medication {

    private String name;
    private String status;
    private String toediening;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        if (status == null){
            return "Geen status bekend.";
        }

        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToediening() {
        if(toediening == null){
            return "Geen toediening bekend.";
        }
        return toediening;
    }

    public void setToediening(String toediening) {
        this.toediening = toediening;
    }
}
