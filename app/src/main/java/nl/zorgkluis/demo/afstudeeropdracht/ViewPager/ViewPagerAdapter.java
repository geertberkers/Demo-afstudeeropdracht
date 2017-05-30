package nl.zorgkluis.demo.afstudeeropdracht.ViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import nl.zorgkluis.demo.afstudeeropdracht.Fragments.CDAFragment;
import nl.zorgkluis.demo.afstudeeropdracht.Fragments.StructuredCDAFragment;

/**
 * Created by Zorgkluis (geert).
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    SparseArray<Fragment> registeredFragments = new SparseArray<>();

    /**
     * Create viewPager adapter
     *
     * @param fm
     */
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        final Fragment result;
        switch (position) {
            case 0:
                result = new CDAFragment();
                break;
            case 1:
                result = new StructuredCDAFragment();
                break;
            default:
                result = null;
                break;
        }

        return result;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        switch (position) {
            case 0:
                return "CDA Document";
            case 1:
                return "Structured CDA Document";
            default:
                return null;
        }
    }

    /**
     * On each Fragment instantiation we are saving the reference of that Fragment in a Map
     * It will help us to retrieve the Fragment by position
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    /**
     * Remove the saved reference from our Map on the Fragment destroy
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }


    /**
     * Get the Fragment by position
     *
     * @param position tab position of the fragment
     * @return
     */
    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
//
//    private final CDAFragment CDAFragment;
//    private final StructuredCDAFragment structuredCDAFragment;
//
//    public ViewPagerAdapter(FragmentManager fragmentManager,
//                            CDAFragment CDAFragment,
//                            StructuredCDAFragment structuredCDAFragment){
//        super(fragmentManager);
//        this.CDAFragment = CDAFragment;
//        this.structuredCDAFragment = structuredCDAFragment;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        return getFragment(position);
//    }
//
//    @Override
//    public int getCount() {
//        return 2;
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        Fragment fragment = getFragment(position);
//
//        if (fragment instanceof CDAFragment) {
//            return ((CDAFragment) fragment).getTitle();
//        } else if (fragment instanceof StructuredCDAFragment) {
//            return ((StructuredCDAFragment) fragment).getTitle();
//        } else {
//            return "TAB";
//        }
//    }
//
//    private Fragment getFragment(int position) {
//        switch (position){
//            case 0: return CDAFragment;
//            case 1: return structuredCDAFragment;
//            default: return CDAFragment;
//        }
//    }
}
