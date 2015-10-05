package harsu.atmos2k15.atmos.com.atmos2015;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;

import harsu.atmos2k15.atmos.com.atmos2015.adapter.ScheduleAdapter;
import harsu.atmos2k15.atmos.com.atmos2015.set.ScheduleSet;
import helper.RecyclerClickListener;
import helper.ScheduleTableManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventScheduleFragment extends Fragment implements RecyclerClickListener {


    public EventScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_schedule, container, false);

    }

    RecyclerView recyclerView;
    ScheduleTableManager mTableManager;
    ScheduleAdapter mAdapter;
    int day;
    ArrayList<ScheduleSet> data;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        day=getArguments().getInt("day");
        recyclerView=(RecyclerView) view.findViewById(R.id.event_schedule_container);
        mTableManager=new ScheduleTableManager(getActivity());

        data = new ArrayList<>();
        data=mTableManager.getSchedule(day);
        mAdapter=new ScheduleAdapter(getActivity(),day);
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mAdapter);
        recyclerView.addItemDecoration(headersDecor);

        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });
        recyclerView.setAdapter(mAdapter);
        mAdapter.setScheduleSets(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.setClickListener(this);

    }

    @Override
    public void onClick(View v, int pos) {
        if(v.getId()==R.id.event_schedule_row) {
            Intent intent = new Intent(getActivity(), EventDataActivity.class);
            intent.putExtra("Event_id", data.get(pos).getEvent_id());
            startActivity(intent);
            Log.e("started",pos+" ");

        }
    }
}
