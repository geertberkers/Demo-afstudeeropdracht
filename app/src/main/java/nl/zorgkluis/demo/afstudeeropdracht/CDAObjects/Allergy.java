package nl.zorgkluis.demo.afstudeeropdracht.CDAObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zorgkluis (geert).
 */
public class Allergy {

    private String allergen;
    private String severity;
    private List<String> reactions;

    public Allergy(){
        reactions = new ArrayList<>();
    }

    public void setAllergen(String allergen) {
        this.allergen = allergen;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public void addReaction(String reaction) {
        reactions.add(reaction);
    }

    public String getAllergen() {
        return allergen;
    }

    public String getSeverity() {
        if(severity == null){
            return "Geen ersntigheid bekend";
        }
        return severity;
    }

    public String getReaction(){
        if(reactions.size() == 0){
            return "Geen symptonen bekend";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for(String reaction : reactions){

            stringBuilder.append("- ");
            stringBuilder.append(reaction);
            stringBuilder.append("\n");
        }

        return stringBuilder.toString().trim();
    }

    public List<String> getReactions() {
        return reactions;
    }
}
