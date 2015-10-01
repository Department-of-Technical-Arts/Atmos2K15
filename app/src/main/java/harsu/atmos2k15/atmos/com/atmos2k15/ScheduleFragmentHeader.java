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

import java.util.ArrayList;

import helper.ScheduleTableManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragmentHeader extends Fragment {


    ScheduleTableManager mTableManager;
    ViewPager mViewPager;
    TabLayout mTab;

    public ScheduleFragmentHeader() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_fragment_header, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTableManager = new ScheduleTableManager(getActivity());

        mViewPager = (ViewPager) view.findViewById(R.id.event_schedule_viewpager);
        mTab = (TabLayout) view.findViewById(R.id.event_schedule_tab);
        SectionPagerAdapter mAdapter = new SectionPagerAdapter(this);


        mViewPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mViewPager);
        if(!mTableManager.dataPresent())
        {
            mTab.setVisibility(View.GONE);
            Log.e("ScheduleFragmentHeader", "Invisible");
        }


    }

    class SectionPagerAdapter extends FragmentStatePagerAdapter {



        public SectionPagerAdapter(Fragment fm) {
            super(fm.getChildFragmentManager());

        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "DAY 1";
                case 1:
                    return "DAY 2";
                case 2:
                    return "DAY 3";
            }
            return "";

        }

        @Override
        public Fragment getItem(int position) {

            Log.d("get Item", position + " ");
            EventScheduleFragment fragment = new EventScheduleFragment();
            Bundle args = new Bundle();
            args.putInt("day", position);
            fragment.setArguments(args);
            return fragment;

        }

        @Override
        public int getCount() {
            return 3;
        }

    }

}
