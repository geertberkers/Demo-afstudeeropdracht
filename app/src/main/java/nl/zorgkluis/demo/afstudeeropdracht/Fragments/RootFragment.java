package nl.zorgkluis.demo.afstudeeropdracht.Fragments;

import android.support.v4.app.Fragment;

import nl.zorgkluis.demo.afstudeeropdracht.ViewPager.BackPressImpl;
import nl.zorgkluis.demo.afstudeeropdracht.ViewPager.OnBackPressListener;

/**
 * Created by Zorgkluis (geert).
 */
public class RootFragment extends Fragment implements OnBackPressListener {

    @Override
    public boolean onBackPressed() {
        return new BackPressImpl(this).onBackPressed();
    }
}