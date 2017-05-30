package nl.zorgkluis.demo.afstudeeropdracht.Layout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import nl.zorgkluis.demo.afstudeeropdracht.Fragments.CDAFragment;
import nl.zorgkluis.demo.afstudeeropdracht.Fragments.StructuredCDAFragment;

/**
 * Created by Zorgkluis (geert).
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final CDAFragment CDAFragment;
    private final StructuredCDAFragment structuredCDAFragment;

    public ViewPagerAdapter(FragmentManager fragmentManager,
                            CDAFragment CDAFragment,
                            StructuredCDAFragment structuredCDAFragment){
        super(fragmentManager);
        this.CDAFragment = CDAFragment;
        this.structuredCDAFragment = structuredCDAFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return getFragment(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Fragment fragment = getFragment(position);

        if (fragment instanceof CDAFragment) {
            return ((CDAFragment) fragment).getTitle();
        } else if (fragment instanceof StructuredCDAFragment) {
            return ((StructuredCDAFragment) fragment).getTitle();
        } else {
            return "TAB";
        }
    }

    private Fragment getFragment(int position) {
        switch (position){
            case 0: return CDAFragment;
            case 1: return structuredCDAFragment;
            default: return CDAFragment;
        }
    }
}
