package nl.zorgkluis.demo.afstudeeropdracht.Fragments.StructuredFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.Medication;
import nl.zorgkluis.demo.afstudeeropdracht.Fragments.RootFragment;
import nl.zorgkluis.demo.afstudeeropdracht.R;

/**
 * Created by Zorgkluis (geert).
 */
public class MedicationFragment extends RootFragment {

    private Medication medication;

    public MedicationFragment(){
    }

    public void setMedication(Medication medication){
        this.medication = medication;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_medication, container, false);

        TextView txtName = (TextView) rootView.findViewById(R.id.txtName);
        TextView txtStatus = (TextView) rootView.findViewById(R.id.txtStatus);
        TextView txtAdministration = (TextView) rootView.findViewById(R.id.txtAdministration);

        txtName.setText(medication.getName());
        txtStatus.setText(medication.getStatus());
        txtAdministration.setText(medication.getToediening());

        return rootView;
    }


}
