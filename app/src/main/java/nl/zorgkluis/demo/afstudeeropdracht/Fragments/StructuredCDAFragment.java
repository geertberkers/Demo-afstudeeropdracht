package nl.zorgkluis.demo.afstudeeropdracht.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.PersonalInfo;
import nl.zorgkluis.demo.afstudeeropdracht.CodeSystem.LOINC;
import nl.zorgkluis.demo.afstudeeropdracht.R;
import nl.zorgkluis.hl7.ClinicalDocumentArchitecture.Act.HL7ClinicalDocument;
import nl.zorgkluis.hl7.ClinicalDocumentArchitecture.ActRelationship.HL7Component;
import nl.zorgkluis.hl7.DataTypes.HL7CodedWithEquivalents;
import nl.zorgkluis.hl7.StrucDoc.StrucDocBr;
import nl.zorgkluis.hl7.StrucDoc.StrucDocChoice;
import nl.zorgkluis.hl7.StrucDoc.StrucDocContent;
import nl.zorgkluis.hl7.StrucDoc.StrucDocString;
import nl.zorgkluis.hl7.StrucDoc.StrucDocText;
import nl.zorgkluis.xmlparser.XMLReader;

/**
 * Created by Zorgkluis (geert).
 */
public class StructuredCDAFragment extends Fragment implements AdapterView.OnItemClickListener {

    private String title;
    private String[] cdaContent;
    private HL7ClinicalDocument cdaDocument;
    private final AdapterView.OnItemClickListener onItemClickListener;

    public StructuredCDAFragment() {
        this.onItemClickListener = this;
        this.title = "Structured CDA Document";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.cdaDocument = createCDADocument();
        this.cdaContent = getResources().getStringArray(R.array.cda_content);
    }

    private HL7ClinicalDocument createCDADocument() {
        String xml = createXMLFromResource(R.raw.cda_document);
        XMLReader reader = new XMLReader(xml);
        return reader.readElement(HL7ClinicalDocument.class, "ClinicalDocument");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.structured_cda_fragment, container, false);

        ListView cdaListView = (ListView) rootView.findViewById(R.id.listView);

        TextView txtTitle = (TextView) rootView.findViewById(R.id.txtTitle);
        TextView txtNote = (TextView) rootView.findViewById(R.id.txtNote);
        TextView txtNoteTitle = (TextView) rootView.findViewById(R.id.txtNoteTitle);
        TextView txtName = (TextView) rootView.findViewById(R.id.txtName);
        TextView txtBirthday = (TextView) rootView.findViewById(R.id.txtBirthday);
        TextView txtGender = (TextView) rootView.findViewById(R.id.txtGender);
        TextView txtAddress = (TextView) rootView.findViewById(R.id.txtAddress);
        TextView txtPhoneNumber = (TextView) rootView.findViewById(R.id.txtPhoneNumber);

        // Set title and note
        String title = cdaDocument.getTitle().getValue();
        txtTitle.setText(title);

        for (HL7Component component : cdaDocument.getStructuredBody().getComponents()) {
            HL7CodedWithEquivalents code = component.getSection().getCode();

            if (code.getCodeSystem().equals(LOINC.OID)) {
                String LOINCCode = code.getCode();

                switch (LOINCCode) {
                    case LOINC.CODE.NOTE:
                        String note = "";
                        String noteTitle = component.getSection().getTitle().getValue();
                        txtNoteTitle.setText(noteTitle);

                        StrucDocText text = component.getSection().getText();
//                        txtNote.setText(createStringFromStrucDocContent(new SpannableStringBuilder(), text.getContent()));

                        note = createHtml(note, text.getContent());
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            txtNote.setText(Html.fromHtml(note, Html.FROM_HTML_MODE_LEGACY));
                        } else {
                            txtNote.setText(Html.fromHtml(note));
                        }
                        break;
                    case LOINC.CODE.ALLERGIES:
                        break;
                    case LOINC.CODE.MEDICATION:
                        break;
                    case LOINC.CODE.PROBLEMS:
                        break;
                    case LOINC.CODE.PROCEDURES:
                        break;
                    case LOINC.CODE.RESULTS:
                        break;


                }
            }
        }

        // Set personal info
        PersonalInfo personalInfo = new PersonalInfo(cdaDocument.getRecordTargets().get(0));
        txtName.setText(personalInfo.getName());
        txtBirthday.setText(personalInfo.getBirthday());
        txtGender.setText(personalInfo.getGender());
        txtAddress.setText(personalInfo.getAddress());
        txtPhoneNumber.setText(personalInfo.getPhoneNumber());

        // Create list for CDA content
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                cdaContent);

        cdaListView.setAdapter(adapter);
        cdaListView.setOnItemClickListener(onItemClickListener);

        return rootView;
    }

    private SpannableStringBuilder createStringFromStrucDocContent(SpannableStringBuilder note, List<StrucDocChoice> contentList) {
        for (StrucDocChoice choice : contentList) {

            if (choice.getChoice() instanceof StrucDocContent) {
                if (note.length() != 0) {
                    note.append("\n");
                }

                int index = note.length();

                String styleCode = choice.getContent().getStyleCode().toUpperCase();

                note.append(createStringFromStrucDocContent(note, choice.getContent().getContentList()));
                switch (styleCode) {

                    case "BOLD":
                        note.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), index, note.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        break;
                    default:
                        break;
                }
            }

            if (choice.getChoice() instanceof StrucDocString) {
                if (note.length() != 0) {
                    note.append("\n");
                }
                note.append(choice.getStringValue());
            }
        }

        return note;
    }

    private String createHtml(String note, List<StrucDocChoice> contentList) {
        for (StrucDocChoice choice : contentList) {
            if (choice.getChoice() instanceof StrucDocContent) {

                String firstPart = note;
                String secondPart = (createHtml(note, choice.getContent().getContentList()));

                String styleCode = choice.getContent().getStyleCode().toUpperCase();
                switch (styleCode) {
                    case "BOLD":
                        note = firstPart + "<b>" + secondPart + "</b>";
                        break;
                    default:
                        break;
                }
            }

            if (choice.getChoice() instanceof StrucDocString) {
                note += choice.getStringValue();
            }

            if(choice.getChoice() instanceof StrucDocBr){
                note += "<br/>";
            }
        }

        return note;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String clicked = cdaContent[position];

        switch (clicked) {
            case "Allergieën":
                openAllergieen();
                break;
            case "Medicatie":
                openMedicatie();
                break;
            case "Problemen":
                openProblemen();
                break;
            case "Verrichtingen":
                openVerrichtingen();
                break;
            case "Resultaten":
                openResultaten();
                break;
            default:
                createErrorToast(clicked);
                break;
        }
    }

    private void openAllergieen() {
        System.out.println("Clicked Allergieen");
    }

    private void openMedicatie() {
        System.out.println("Clicked Medicatie");

    }

    private void openProblemen() {
        System.out.println("Clicked Problemen");

    }

    private void openVerrichtingen() {
        System.out.println("Clicked Verrichtingen");

    }

    private void openResultaten() {
        System.out.println("Clicked Resultaten");

    }

    private void createErrorToast(String clicked) {
        Toast.makeText(getContext(), "Error opening " + clicked, Toast.LENGTH_SHORT).show();
    }

    private String createXMLFromResource(int resourceId) {
        InputStream inputStream = getResources().openRawResource(resourceId);

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        StringBuilder text = new StringBuilder();

        try {
            while ((line = bufferedReader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            return null;
        }

        return text.toString();
    }

    public String getTitle() {
        return title;
    }

}
