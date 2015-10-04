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


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment implements RecyclerClickListener {


    RecyclerView recyclerView;
    EventListingAdapter mAdapter;
    EventTableManager tableManager;
    ArrayList<EventSet> data;
    public FavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.favourite_container);
        mAdapter = new EventListingAdapter(getActivity());
        mAdapter.setClickListener(this);
        tableManager = new EventTableManager(getActivity());

        data = new ArrayList<>();
        data = tableManager.getFavourites();
        mAdapter.setEvents(data);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onClick(View v, int pos) {
        if (v.getId() == R.id.favourite_icon) {
            tableManager.toggleFavourite(data.get(pos).getId());
            data.remove(pos);
            mAdapter.notifyItemRemoved(pos);
        } else if (v.getId() == R.id.custom_event_row) {
            Intent intent = new Intent(getActivity(), EventDataActivity.class);
            intent.putExtra("Event_id", data.get(pos).getId());
            startActivity(intent);
        }
    }
}
