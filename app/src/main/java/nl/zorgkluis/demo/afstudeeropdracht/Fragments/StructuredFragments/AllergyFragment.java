package nl.zorgkluis.demo.afstudeeropdracht.Fragments.StructuredFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.Allergy;
import nl.zorgkluis.demo.afstudeeropdracht.Fragments.RootFragment;
import nl.zorgkluis.demo.afstudeeropdracht.R;

/**
 * Created by Zorgkluis (geert).
 */
public class AllergyFragment extends RootFragment {

    private Allergy allergy;

    public AllergyFragment(){
    }

    public void setAllergy(Allergy allergy){
        this.allergy = allergy;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_allergy, container, false);

        TextView txtAllergen = (TextView) rootView.findViewById(R.id.txtAllergen);
        TextView txtReaction = (TextView) rootView.findViewById(R.id.txtReaction);
        TextView txtSeverity = (TextView) rootView.findViewById(R.id.txtSeverity);

        txtAllergen.setText(allergy.getAllergen());
        txtReaction.setText(allergy.getReaction());
        txtSeverity.setText(allergy.getSeverity());

        return rootView;
    }
}
