package nl.zorgkluis.demo.afstudeeropdracht.Fragments.StructuredFragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.Result;
import nl.zorgkluis.demo.afstudeeropdracht.Fragments.RootFragment;
import nl.zorgkluis.demo.afstudeeropdracht.R;
import nl.zorgkluis.hl7.StrucDoc.StrucDocChoice;
import nl.zorgkluis.hl7.StrucDoc.StrucDocCol;
import nl.zorgkluis.hl7.StrucDoc.StrucDocColGroup;
import nl.zorgkluis.hl7.StrucDoc.StrucDocContent;
import nl.zorgkluis.hl7.StrucDoc.StrucDocParagraph;
import nl.zorgkluis.hl7.StrucDoc.StrucDocString;
import nl.zorgkluis.hl7.StrucDoc.StrucDocTable;
import nl.zorgkluis.hl7.StrucDoc.StrucDocTd;
import nl.zorgkluis.hl7.StrucDoc.StrucDocTh;
import nl.zorgkluis.hl7.StrucDoc.StrucDocTr;

/**
 * Created by Zorgkluis (geert).
 */
public class ResultFragment extends RootFragment {

    private Result result;

    public ResultFragment() {
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_result, container, false);

        TextView txtName = (TextView) rootView.findViewById(R.id.txtName);
        txtName.setText(result.getCaption());

        TableLayout table1 = (TableLayout) rootView.findViewById(R.id.table1);
        TableLayout table2 = (TableLayout) rootView.findViewById(R.id.table2);

        StrucDocTable firstTable = null;
        StrucDocTable secondTable = null;

        try {
            firstTable = result.getTableList().get(0);
            secondTable = result.getTableList().get(1);
        } catch (IndexOutOfBoundsException ignored) {
        }

        handleTableView(table1, firstTable);
        handleTableView(table2, secondTable);

        return rootView;
    }

    private void handleTableView(TableLayout tableView, StrucDocTable table) {
        if (table != null) {
            float[] colGroup = createColGroup(table);
            createTableHeader(table, tableView, colGroup);
            createTableBody(table, tableView, colGroup);
        }
    }

    private float[] createColGroup(StrucDocTable table) {
        for (StrucDocChoice choice : table.getCol()) {
            if (choice.getChoice() instanceof StrucDocColGroup) {
                StrucDocColGroup group = (StrucDocColGroup) choice.getChoice();
                float[] colGroup = new float[group.getColList().size()];
                int i = 0;
                for (StrucDocCol col : group.getColList()) {
                    String width = col.getWidth().replace("%", "");
                    colGroup[i] = Float.valueOf(width);
                    i++;
                }
                return colGroup;
            }
        }
        return null;
    }

    private void createTableHeader(StrucDocTable table, TableLayout tableView, float[] colGroup) {
        // Create rows for table header
        for (StrucDocTr header : table.getThead().getTrList()) {
            TableRow headerRow = new TableRow(getContext());
            int i = 0;
            for (StrucDocChoice th : header.getTrContent()) {
                if (th.getChoice() instanceof StrucDocTh) {
                    TextView txtView = new TextView(getContext());
                    String value = th.getTh().getThContent().get(0).getStringValue();

                    if (colGroup != null) {
                        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, colGroup[i]);
                        txtView.setLayoutParams(params);
                        txtView.setTypeface(txtView.getTypeface(), Typeface.BOLD);
                        i++;
                    }

                    txtView.setText(value);
                    headerRow.addView(txtView);
                }
            }
            tableView.addView(headerRow);
        }
    }

    private void createTableBody(StrucDocTable table, TableLayout tableView, float[] colGroup) {
        // Create rows for table body
        for (StrucDocTr body : table.getTbody().get(0).getTrList()) {
            TableRow contentRow = new TableRow(getContext());
            int i = 0;

            for (StrucDocChoice choice : body.getTrContent()) {
                if (choice.getChoice() instanceof StrucDocTd) {
                    TextView txtView = new TextView(getContext());
                    String value = "";

                    // Create content for row
                    for (StrucDocChoice content : choice.getTd().getTdContent()) {
                        if (content.getChoice() instanceof StrucDocContent) {
                            value = readStrucDocContent(value, content.getContent());
                        }

                        if (content.getChoice() instanceof StrucDocString) {
                            value = ((StrucDocString) content.getChoice()).getValue();
                        }

                        if (content.getChoice() instanceof StrucDocParagraph) {
                            List<StrucDocChoice> paragraph = ((StrucDocParagraph) content.getChoice()).getParagraphContent();

                            if (paragraph.size() > 0) {
                                value = paragraph.get(0).getStringValue();
                            }
                        }
                    }

                    if (colGroup != null) {
                        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, colGroup[i]);
                        txtView.setLayoutParams(params);
                        i++;
                    }

                    txtView.setText(value);
                    contentRow.addView(txtView);
                }
            }

            tableView.addView(contentRow);
        }
    }

    private String readStrucDocContent(String value, StrucDocContent content){
        for(StrucDocChoice c : content.getContentList()) {
            if (c.getChoice() instanceof StrucDocString) {
                value += content.getContentItem(0).getStringValue() + " ";
            } else if (c.getChoice() instanceof StrucDocContent) {
                value = readStrucDocContent(value, c.getContent());
            }
        }
        return value;
    }
}
