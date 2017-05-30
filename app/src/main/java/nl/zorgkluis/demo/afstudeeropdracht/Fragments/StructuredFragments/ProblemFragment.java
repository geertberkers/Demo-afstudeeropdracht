package nl.zorgkluis.demo.afstudeeropdracht.Fragments.StructuredFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.Problem;
import nl.zorgkluis.demo.afstudeeropdracht.Fragments.RootFragment;
import nl.zorgkluis.demo.afstudeeropdracht.R;

/**
 * Created by Zorgkluis (geert).
 */
public class ProblemFragment extends RootFragment {

    private Problem problem;

    public ProblemFragment(){
    }

    public void setProblem(Problem problem){
        this.problem = problem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_problem, container, false);

        TextView txtName = (TextView) rootView.findViewById(R.id.txtName);
        TextView txtStatus = (TextView) rootView.findViewById(R.id.txtStatus);

        txtName.setText(problem.getName());
        txtStatus.setText(problem.getStatus());

        return rootView;
    }
}
