package nl.zorgkluis.demo.afstudeeropdracht.Fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.zorgkluis.demo.afstudeeropdracht.R;
import nl.zorgkluis.demo.afstudeeropdracht.ViewPager.OnBackPressListener;
import nl.zorgkluis.demo.afstudeeropdracht.ViewPager.ViewPagerAdapter;
import nl.zorgkluis.demo.afstudeeropdracht.ViewPager.ZoomOutPageTransformer;

/**
 * Created by Zorgkluis (geert).
 */
public class MainFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;


    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer() );

        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Retrieve the currently visible Tab Fragment and propagate the onBackPressed callback
     *
     * @return true = if this fragment and/or one of its associates Fragment can handle the backPress
     */
    public boolean onBackPressed() {
        OnBackPressListener currentFragment = (OnBackPressListener) viewPagerAdapter.getRegisteredFragment(viewPager.getCurrentItem());
        return currentFragment != null && currentFragment.onBackPressed();
    }

}