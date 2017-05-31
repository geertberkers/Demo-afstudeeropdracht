package nl.zorgkluis.demo.afstudeeropdracht.CDAObjects;

import java.util.ArrayList;
import java.util.List;

import nl.zorgkluis.hl7.StrucDoc.StrucDocTable;

/**
 * Created by Zorgkluis (geert).
 */
public class Result {

    private String caption;
    private List<StrucDocTable> tableList;

    public Result(){
        this.tableList = new ArrayList<>();
    }
    public String getCaption() {
        return caption.replace(" - ", "\n").replace(") (", ")\n(");
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void addTable(StrucDocTable table) {
        tableList.add(table);
    }

    public List<StrucDocTable> getTableList() {
        return tableList;
    }


}
