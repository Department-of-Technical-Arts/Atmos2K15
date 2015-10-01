package harsu.atmos2k15.atmos.com.atmos2k15;



import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import harsu.atmos2k15.atmos.com.atmos2k15.adapter.EventListingAdapter;
import harsu.atmos2k15.atmos.com.atmos2k15.set.EventSet;
import helper.EventTableManager;
import helper.RecyclerClickListener;


public class EventListingFragment extends Fragment implements RecyclerClickListener {

    public EventListingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    RecyclerView recyclerView;
    EventListingAdapter mAdapter;
    ArrayList<EventSet> events;
    EventTableManager mTableManager;
    String tag,tab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_listing2, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tag=getArguments().getString("tag");
        tab=getArguments().getString("tab");
        recyclerView=(RecyclerView) view.findViewById(R.id.event_list_container);
        mAdapter=new EventListingAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);
        mTableManager=new EventTableManager(getActivity());
        events=new ArrayList<>();
        events=mTableManager.getEvents(tag,tab);
        mAdapter.setEvents(events);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.setClickListener(this);

    }

    @Override
    public void onClick(View v, int pos) {
        if(v.getId()==R.id.favourite_icon){
            events.get(pos).setFavourite(mTableManager.toggleFavourite(events.get(pos).getId()));
            mAdapter.notifyItemChanged(pos);
        }
        else if(v.getId()==R.id.custom_event_row){
            Intent intent=new Intent(getActivity(),EventDataActivity.class);
            intent.putExtra("Event_id",events.get(pos).getId());
            startActivity(intent);
        }
    }
}
