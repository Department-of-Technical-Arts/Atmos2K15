package harsu.atmos2k15.atmos.com.atmos2k15;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import harsu.atmos2k15.atmos.com.atmos2k15.adapter.FeedAdapter;
import harsu.atmos2k15.atmos.com.atmos2k15.set.FeedSet;
import helper.FeedTableManager;
import helper.RecyclerClickListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment implements RecyclerClickListener {


    RecyclerView recyclerView;
    FeedAdapter mAdapter;
    ArrayList<FeedSet> feedSets;
    FeedTableManager tableManager;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.feed_container);
        mAdapter = new FeedAdapter(getActivity());
        feedSets = new ArrayList<>();
        tableManager = new FeedTableManager(getActivity());
        feedSets = tableManager.getFeeds();
        mAdapter.setFeeds(feedSets);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.setClickListener(this);
    }

    @Override
    public void onClick(View v, int pos) {
        //todo start event description Activity
    }
}
