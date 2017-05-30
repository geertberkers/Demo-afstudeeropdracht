package nl.zorgkluis.demo.afstudeeropdracht.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
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
import java.util.ArrayList;
import java.util.List;

import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.Allergy;
import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.Medication;
import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.PersonalInfo;
import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.Problem;
import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.Procedure;
import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.Result;
import nl.zorgkluis.demo.afstudeeropdracht.CodeSystem.LOINC;
import nl.zorgkluis.demo.afstudeeropdracht.Fragments.StructuredFragments.ListFragment;
import nl.zorgkluis.demo.afstudeeropdracht.R;
import nl.zorgkluis.hl7.ClinicalDocumentArchitecture.Act.ClinicalStatement.ClinicalStatement;
import nl.zorgkluis.hl7.ClinicalDocumentArchitecture.Act.ClinicalStatement.HL7Act;
import nl.zorgkluis.hl7.ClinicalDocumentArchitecture.Act.ClinicalStatement.HL7Organizer;
import nl.zorgkluis.hl7.ClinicalDocumentArchitecture.Act.ClinicalStatement.HL7Procedure;
import nl.zorgkluis.hl7.ClinicalDocumentArchitecture.Act.ClinicalStatement.HL7SubstanceAdministration;
import nl.zorgkluis.hl7.ClinicalDocumentArchitecture.Act.HL7ClinicalDocument;
import nl.zorgkluis.hl7.ClinicalDocumentArchitecture.Act.HL7Section;
import nl.zorgkluis.hl7.ClinicalDocumentArchitecture.ActRelationship.HL7Component;
import nl.zorgkluis.hl7.ClinicalDocumentArchitecture.ActRelationship.HL7Entry;
import nl.zorgkluis.hl7.DataTypes.HL7CodedWithEquivalents;
import nl.zorgkluis.hl7.StrucDoc.StrucDoc;
import nl.zorgkluis.hl7.StrucDoc.StrucDocBr;
import nl.zorgkluis.hl7.StrucDoc.StrucDocChoice;
import nl.zorgkluis.hl7.StrucDoc.StrucDocContent;
import nl.zorgkluis.hl7.StrucDoc.StrucDocItem;
import nl.zorgkluis.hl7.StrucDoc.StrucDocList;
import nl.zorgkluis.hl7.StrucDoc.StrucDocParagraph;
import nl.zorgkluis.hl7.StrucDoc.StrucDocString;
import nl.zorgkluis.hl7.StrucDoc.StrucDocTable;
import nl.zorgkluis.hl7.StrucDoc.StrucDocText;
import nl.zorgkluis.xmlparser.XMLReader;

/**
 * Created by Zorgkluis (geert).
 */
@SuppressWarnings("unchecked")
public class StructuredCDAFragment extends RootFragment implements AdapterView.OnItemClickListener {

    private ListView cdaListView;
    private TextView txtTitle;
    private TextView txtNote;
    private TextView txtNoteTitle;
    private TextView txtName;
    private TextView txtBirthday;
    private TextView txtGender;
    private TextView txtAddress;
    private TextView txtPhoneNumber;
    
    private String title;
    private String[] cdaContent;
    private HL7ClinicalDocument cdaDocument;

    private int backStackNumber = 0;

    private List<Allergy> allergies;
    private List<Medication> medications;
    private List<Problem> problems;
    private List<Procedure> procedures;
    private List<Result> results;

    private final AdapterView.OnItemClickListener onItemClickListener;

    public StructuredCDAFragment() {
        this.onItemClickListener = this;
        this.allergies = new ArrayList<>();
        this.medications = new ArrayList<>();
        this.problems = new ArrayList<>();
        this.procedures = new ArrayList<>();
        this.results = new ArrayList<>();
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
        View rootView = inflater.inflate(R.layout.fragment_structured_cda, container, false);

        initControls(rootView);
       

        // Set title and note
        String title = cdaDocument.getTitle().getValue();
        txtTitle.setText(title);

        createPersonalInfo();
        for (HL7Component component : cdaDocument.getStructuredBody().getComponents()) {
            HL7CodedWithEquivalents code = component.getSection().getCode();

            if (code.getCodeSystem().equals(LOINC.OID)) {
                String LOINCCode = code.getCode();

                switch (LOINCCode) {
                    case LOINC.CODE.NOTE:       createNote(component);          break;
                    case LOINC.CODE.ALLERGIES:  createAllergies(component);     break;
                    case LOINC.CODE.MEDICATION: createMedications(component);   break;
                    case LOINC.CODE.PROBLEMS:   createProblems(component);      break;
                    case LOINC.CODE.PROCEDURES: createProcedures(component);    break;
                    case LOINC.CODE.RESULTS:    createResults(component);       break;
                }
            }
        }

        createCDAListView();

        return rootView;
    }

    private void initControls(View rootView) {
        cdaListView = (ListView) rootView.findViewById(R.id.listView);

        txtTitle = (TextView) rootView.findViewById(R.id.txtTitle);
        txtNote = (TextView) rootView.findViewById(R.id.txtNote);
        txtNoteTitle = (TextView) rootView.findViewById(R.id.txtNoteTitle);
        txtName = (TextView) rootView.findViewById(R.id.txtName);
        txtBirthday = (TextView) rootView.findViewById(R.id.txtBirthday);
        txtGender = (TextView) rootView.findViewById(R.id.txtGender);
        txtAddress = (TextView) rootView.findViewById(R.id.txtAddress);
        txtPhoneNumber = (TextView) rootView.findViewById(R.id.txtPhoneNumber);
    }
    
    private void createPersonalInfo() {
        PersonalInfo personalInfo = new PersonalInfo(cdaDocument.getRecordTargets().get(0));
        txtName.setText(personalInfo.getName());
        txtBirthday.setText(personalInfo.getBirthday());
        txtGender.setText(personalInfo.getGender());
        txtAddress.setText(personalInfo.getAddress());
        txtPhoneNumber.setText(personalInfo.getPhoneNumber());
    }

    private void createNote(HL7Component component) {
        String note = "";
        String noteTitle = component.getSection().getTitle().getValue();
        txtNoteTitle.setText(noteTitle);

        StrucDocText text = component.getSection().getText();
        note = createHtml(note, text.getContent());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            txtNote.setText(Html.fromHtml(note, Html.FROM_HTML_MODE_LEGACY));
        } else {
            //noinspection deprecation
            txtNote.setText(Html.fromHtml(note));
        }
    }

    /**
     * Create html to show
     * @param html string to save
     * @param contentList content to transform
     * @return transformedHtml
     */
    private String createHtml(String html, List<StrucDocChoice> contentList) {
        for (StrucDocChoice choice : contentList) {
            if (choice.getChoice() instanceof StrucDocContent) {

                String firstPart = html;
                String secondPart = (createHtml(html, choice.getContent().getContentList()));

                String styleCode = choice.getContent().getStyleCode().toUpperCase();
                switch (styleCode) {
                    case "BOLD":
                        html = firstPart + "<b>" + secondPart + "</b>";
                        break;
                    case "xIndent":     //TODO: Figure out
                        break;
                    case "flagdata":    //TODO: Figure out
                        break;
                    default:    break;
                }
            }

            if (choice.getChoice() instanceof StrucDocString) {
                html += choice.getStringValue();
            }

            if(choice.getChoice() instanceof StrucDocBr){
                html += "<br/>";
            }
        }

        return html;
    }

    private void createAllergies(HL7Component component) {
        StrucDocText text = component.getSection().getText();
        for(StrucDocChoice s : text.getContent()){
            if(s.getChoice() instanceof StrucDocList){
                StrucDocList list = s.getList();

                for(StrucDocItem item : list.getItems()){
                    String listItemID = item.getId();

                    if(!listItemID.contains("allergy")){
                        break;
                    }

                    Allergy allergy = new Allergy();

                    for(StrucDocChoice choice : item.getItems()){
                        if(choice.getChoice() instanceof StrucDocContent){
                            StrucDocContent content = choice.getContent();

                            String contentId = content.getId().replace(listItemID,"");

                            if(contentId.contains("allergen")){
                                String allergen = content.getContentItem(0).getStringValue();
                                allergy.setAllergen(allergen);                            }

                            if(contentId.contains("reaction")){
                                String reaction = content.getContentItem(0).getStringValue();
                                allergy.addReaction(reaction);
                            }

                            if(contentId.contains("severity")){
                                String severity = content.getContentItem(0).getStringValue();
                                allergy.setSeverity(severity);
                            }
                        }
                    }
                    allergies.add(allergy);
                }
            }
        }


//        List<HL7Act> acts = getEntryValues(component.getSection());
//        System.out.println("Allergies: " + acts.size());

//        for(HL7Act act : acts){
//            Allergy allergy = new Allergy();
//
//            for(HL7EntryRelationship entryRelationship : act.getEntryRelationships()){
//                if(entryRelationship.getClinicalStatement().getChoice() instanceof HL7Observation){
//                    HL7Observation observation = (HL7Observation) entryRelationship.getClinicalStatement().getChoice();
//
//                    HL7ParticipantRole participant = observation.getParticipants().get(0).getParticipantRole();
//                    String reference = participant.getCode().getOriginalText().getReference().getValue();
//
//                    allergy.setAllergen(getReferenceValue(reference));
//
//                    for(HL7EntryRelationship relationship: observation.getEntryRelationships()){
//                        if(entryRelationship.getClinicalStatement().getChoice() instanceof HL7Observation){
//                            HL7Observation observation1 = (HL7Observation) entryRelationship.getClinicalStatement().getChoice();
//                    }
//                }
//
//            }

    }

    private void createMedications(HL7Component component) {
        List<HL7SubstanceAdministration> administrations = getEntryValues(component.getSection());
        System.out.println("Medications: " + administrations.size());

        StrucDocText text = component.getSection().getText();
        for(StrucDocChoice s : text.getContent()){
            if(s.getChoice() instanceof StrucDocList){
                StrucDocList list = s.getList();

                for(StrucDocItem item : list.getItems()){
                    Medication medication = new Medication();

                    for(StrucDocChoice choice : item.getItems()){
                        if(choice.getChoice() instanceof StrucDocContent){
                            StrucDocContent content = choice.getContent();

                            String contentId = content.getId();

                            if(contentId == null){
                                String actief = content.getContentItem(0).getStringValue();
                                medication.setStatus(actief);
                            } else
                            if(contentId.contains("med")){
                                String name = content.getContentItem(0).getStringValue();
                                medication.setName(name);
                            }
                        }

                        if(choice.getChoice() instanceof StrucDocParagraph){
                            StrucDocParagraph content = choice.getParagraph();

                            for(StrucDocChoice test : content.getParagraphContent()){
                                if(test.getChoice() instanceof StrucDocString){
                                    medication.setToediening(((StrucDocString) test.getChoice()).getValue());
                                }
                            }
                        }
                    }
                    medications.add(medication);
                }
            }
        }
    }

    private void createProblems(HL7Component component) {
        List<HL7Act> acts = getEntryValues(component.getSection());
        System.out.println("Problem: " + acts.size());
        //TODO: Fill list of problems


        StrucDocText text = component.getSection().getText();
        for(StrucDocChoice s : text.getContent()){
            if(s.getChoice() instanceof StrucDocList){
                StrucDocList list = s.getList();

                for(StrucDocItem item : list.getItems()){
                    Problem problem = new Problem();

                    for(StrucDocChoice choice : item.getItems()){
                        if(choice.getChoice() instanceof StrucDocContent){
                            StrucDocContent content = choice.getContent();

                            String contentId = content.getId();

                            if(contentId.contains("name")){
                                String probleem = content.getContentItem(0).getStringValue();
                                problem.setName(probleem);
                            }
                        }

                        if(choice.getChoice() instanceof StrucDocString){
                            String status = ((StrucDocString) choice.getChoice()).getValue();
                            problem.setStatus(status);
                        }
                    }
                    problems.add(problem);
                }
            }
        }
    }

    private void createProcedures(HL7Component component) {
        List<HL7Procedure> procedures = getEntryValues(component.getSection());
        System.out.println("Procedure: " + procedures.size());

        StrucDocText text = component.getSection().getText();
        for(StrucDocChoice s : text.getContent()){
            if(s.getChoice() instanceof StrucDocList){
                StrucDocList list = s.getList();

                for(StrucDocItem item : list.getItems()){
                    Procedure procedure = new Procedure();

                    for(StrucDocChoice choice : item.getItems()){
                        if(choice.getChoice() instanceof StrucDocContent){
                            StrucDocContent content = choice.getContent();

                            String contentId = content.getId();

                            if(contentId.contains("name")){
                                String probleem = content.getContentItem(0).getStringValue();
                                procedure.setName(probleem);
                            }
                        }

                        if(choice.getChoice() instanceof StrucDocString){
                            String status = ((StrucDocString) choice.getChoice()).getValue();
                            procedure.setStatus(status);
                        }
                    }
                    this.procedures.add(procedure);
                }
            }
        }
    }

    private void createResults(HL7Component component) {
        List<HL7Organizer> organizers = getEntryValues(component.getSection());
        System.out.println("Result: " + organizers.size());

        StrucDocText text = component.getSection().getText();
        for (StrucDocChoice s : text.getContent()) {
            if (s.getChoice() instanceof StrucDocList) {
                StrucDocList list = s.getList();

                for (StrucDocItem item : list.getItems()) {
                    Result result = new Result();

                    String caption = item.getCaption().getCaptions().get(0).getStringValue();
                    result.setCaption(caption);

                    for (StrucDocChoice choice : item.getItems()) {
                        if (choice.getChoice() instanceof StrucDocTable) {

                            result.addTable((StrucDocTable)choice.getChoice());
                        }
//                            StrucDocContent content = choice.getContent();
//
//                            String contentId = content.getId();
//
//                            if(contentId.contains("name")){
//                                String probleem = content.getContentItem(0).getStringValue();
//                                procedure.setName(probleem);
//                            }
//                        }
//
//                        if(choice.getChoice() instanceof StrucDocString){
//                            String status = ((StrucDocString) choice.getChoice()).getValue();
//                            procedure.setStatus(status);
//                        }
//                    }
                        results.add(result);
                    }
                }
            }
        }
    }

    private <T extends ClinicalStatement> List<T> getEntryValues(HL7Section section){
        List<T> returnList = new ArrayList<>();
        for(HL7Entry entry : section.getEntries()){
            returnList.add((T) entry.getClinicalStatement().getChoice());
        }

        return returnList;
    }

    private void createCDAListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                cdaContent);

        cdaListView.setAdapter(adapter);
        cdaListView.setOnItemClickListener(onItemClickListener);
    }
    
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String clicked = cdaContent[position];
        switch (clicked) {
            case "Allergieën":      openAllergieen();       break;
            case "Medicatie":       openMedicaties();       break;
            case "Problemen":       openProblemen();        break;
            case "Verrichtingen":   openVerrichtingen();    break;
            case "Labuitslagen":    openResultaten();       break;
            default: createErrorToast(clicked);             break;
        }
    }

    private void openAllergieen() {
        ListFragment fragment = new ListFragment();
        fragment.setList(allergies);
        fragment.setTitle("Allergieën");
        openFragment(fragment);
    }

    private void openMedicaties() {
        ListFragment fragment = new ListFragment();
        fragment.setList(medications);
        fragment.setTitle("Medicaties");
        openFragment(fragment);
    }

    private void openProblemen() {
        ListFragment fragment = new ListFragment();
        fragment.setList(problems);
        fragment.setTitle("Problemen");
        openFragment(fragment);
    }

    private void openVerrichtingen() {
        ListFragment fragment = new ListFragment();
        fragment.setList(procedures);
        fragment.setTitle("Verrichtingen");
        openFragment(fragment);
    }

    private void openResultaten() {
        ListFragment fragment = new ListFragment();
        fragment.setList(results);
        fragment.setTitle("Resultaten");
        openFragment(fragment);
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_mainLayout, fragment).commit();
    }

    private void createErrorToast(String clicked) {
        Toast.makeText(getContext(), "Error opening " + clicked, Toast.LENGTH_SHORT).show();
    }

    /**
     * Create XML String from resourceId
     * @param resourceId id to create XML for
     * @return XML as string
     */
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
