package nl.zorgkluis.demo.afstudeeropdracht.CDAObjects;

/**
 * Created by Zorgkluis (geert).
 */
public class Problem {

    private String name;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        if (status == null) {
            return "Geen status bekend";
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
