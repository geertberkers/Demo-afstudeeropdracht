package nl.zorgkluis.demo.afstudeeropdracht.Fragments.StructuredFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.Procedure;
import nl.zorgkluis.demo.afstudeeropdracht.Fragments.RootFragment;
import nl.zorgkluis.demo.afstudeeropdracht.R;

/**
 * Created by Zorgkluis (geert).
 */
public class ProcedureFragment extends RootFragment {

    private Procedure procedure;

    public ProcedureFragment(){
    }

    public void setProcedure(Procedure procedure){
        this.procedure = procedure;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_procedure, container, false);

        TextView txtName = (TextView) rootView.findViewById(R.id.txtName);
        TextView txtStatus = (TextView) rootView.findViewById(R.id.txtStatus);

        txtName.setText(procedure.getName());
        txtStatus.setText(procedure.getStatus());

        return rootView;
    }


}
