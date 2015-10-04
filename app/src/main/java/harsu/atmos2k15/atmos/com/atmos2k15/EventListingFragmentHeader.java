package harsu.atmos2k15.atmos.com.atmos2k15;


import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import helper.EventTableManager;

public class EventListingFragmentHeader extends Fragment {


    ViewPager mViewPager;
    TabLayout mTab;
    EventTableManager mTableManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_event_listing, container, false);
    }

    String tab;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.e("EventListingHeader","created");
        tab=getArguments().getString("tab");
        mTableManager=new EventTableManager(getActivity());
        ArrayList<String> tags= mTableManager.getDistinctTags(tab);



        mViewPager=(ViewPager) view.findViewById(R.id.event_list_fragment_container);
        mTab=(TabLayout) view.findViewById(R.id.event_list_tab);
        SectionPagerAdapter mAdapter = new SectionPagerAdapter(this,tags);
        mViewPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mViewPager);
        if(tags.size()<=1){
            mTab.setVisibility(View.GONE);

            Log.e("EventListingHeader", "Invisible");
        }



    }

    class SectionPagerAdapter extends FragmentStatePagerAdapter {

        ArrayList<String> tags;
        public SectionPagerAdapter(Fragment fm,ArrayList<String> tags) {
            super(fm.getChildFragmentManager());
            this.tags=new ArrayList<>();
            this.tags=tags;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return  tags.get(position);
        }

        @Override
        public Fragment getItem(int position) {

            Log.d("get Item", position + " ");
            EventListingFragment fragment=new EventListingFragment();
            Bundle args = new Bundle();
            args.putString("tag", tags.get(position));
            args.putString("tab", tab);
            fragment.setArguments(args);
            return fragment;

        }

        @Override
        public int getCount() {
            return tags.size();
        }

    }



}
