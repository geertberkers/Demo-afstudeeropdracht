package nl.zorgkluis.demo.afstudeeropdracht.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import nl.zorgkluis.demo.afstudeeropdracht.Fragments.CDAFragment;
import nl.zorgkluis.demo.afstudeeropdracht.Fragments.StructuredCDAFragment;
import nl.zorgkluis.demo.afstudeeropdracht.Layout.ViewPagerAdapter;
import nl.zorgkluis.demo.afstudeeropdracht.Layout.ZoomOutPageTransformer;
import nl.zorgkluis.demo.afstudeeropdracht.R;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    private CDAFragment CDAFragment;
    private StructuredCDAFragment structuredCDAFragment;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragments();
        initViewPager();
        initTabLayout();
    }

    private void initFragments() {
        CDAFragment = new CDAFragment();
        structuredCDAFragment = new StructuredCDAFragment();
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(
                getSupportFragmentManager(),
                CDAFragment,
                structuredCDAFragment

        );

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer() );
    }

    private void initTabLayout() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
