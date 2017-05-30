package nl.zorgkluis.demo.afstudeeropdracht.Fragments.StructuredFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.Result;
import nl.zorgkluis.demo.afstudeeropdracht.Fragments.RootFragment;
import nl.zorgkluis.demo.afstudeeropdracht.R;

/**
 * Created by Zorgkluis (geert).
 */
public class ResultFragment extends RootFragment {

    private Result result;

    public ResultFragment(){
    }

    public void setResult(Result result){
        this.result = result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_result, container, false);

        TextView txtName = (TextView) rootView.findViewById(R.id.txtName);
        TableLayout table1 = (TableLayout) rootView.findViewById(R.id.table1);
        TableLayout table2 = (TableLayout) rootView.findViewById(R.id.table2);

        txtName.setText(result.getCaption());

        //TODO: Set tables

        return rootView;
    }

}
