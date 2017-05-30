package nl.zorgkluis.demo.afstudeeropdracht.CodeSystem;

/**
 * Created by Zorgkluis (geert).
 */
public class LOINC {

    public final static String OID = "2.16.840.1.113883.6.1";

    public abstract class CODE {
//        public final static String ALERTS = "48765-2";
        public final static String MEDICATION = "10160-0";
        public final static String ALLERGIES = "48765-2";
        public final static String PROBLEMS = "11450-4";
        public final static String PROCEDURES = "47519-4";
        public final static String RESULTS = "30954-2";
//        public final static String MEDICATION_INSTRUCTION = "76662-6";
//        public final static String STATUS = "33999-4";
        public final static String NOTE = "X-CE-PFD";
    }

    public String getCodeFromName(String codeName){
        switch (codeName.toLowerCase()){
//            case "waarschuwingen":          return CODE.ALERTS;
            case "medicatie":               return CODE.MEDICATION;
            case "behandelingen":           return CODE.PROCEDURES;
            case "uitslagen":               return CODE.RESULTS;
            case "klachten en diagnosen":   return CODE.PROBLEMS;
            default:                        return null;
        }
    }
}
