package nl.zorgkluis.demo.afstudeeropdracht.Fragments.StructuredFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import nl.zorgkluis.demo.afstudeeropdracht.Adapter.GenericAdapter;
import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.Allergy;
import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.Medication;
import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.Problem;
import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.Procedure;
import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.Result;
import nl.zorgkluis.demo.afstudeeropdracht.Fragments.RootFragment;
import nl.zorgkluis.demo.afstudeeropdracht.R;

/**
 * Created by Zorgkluis (geert).
 */
public class  ListFragment <T> extends RootFragment implements AdapterView.OnItemClickListener {

    private String title;
    private List<T> objectList;
    private GenericAdapter adapter;

    private final AdapterView.OnItemClickListener onItemClickListener;

    public ListFragment() {
        this.onItemClickListener = this;
    }

    public void setList(List<T> objectList){
        this.objectList = objectList;
    }

    public void setTitle(String title){
        this.title = title;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        TextView txtView = (TextView) rootView.findViewById(R.id.txtCDAContent);

        txtView.setText(title);
        adapter = new GenericAdapter(objectList);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Object object = adapter.getItem(position);

        if(object instanceof Allergy){
            AllergyFragment fragment = new AllergyFragment();
            fragment.setAllergy((Allergy) object);
            openFragment(fragment);
        } else if (object instanceof Medication){
             MedicationFragment fragment = new MedicationFragment();
             fragment.setMedication((Medication) object);
             openFragment(fragment);
        } else if (object instanceof Procedure){
            ProcedureFragment fragment = new ProcedureFragment();
            fragment.setProcedure((Procedure) object);
            openFragment(fragment);
        } else if (object instanceof Problem){
            ProblemFragment fragment = new ProblemFragment();
            fragment.setProblem((Problem) object);
            openFragment(fragment);
        } else if (object instanceof Result){
            ResultFragment fragment = new ResultFragment();
            fragment.setResult((Result) object);
            openFragment(fragment);
        }
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_mainLayout, fragment).commit();
    }

}