package harsu.atmos2k15.atmos.com.atmos2k15;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import helper.EventTableManager;
import helper.RecyclerClickListener;

public class EventChooserFragment extends Fragment implements RecyclerClickListener {


    RecyclerView recyclerView;


    public EventChooserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_chooser, container, false);
    }
    ArrayList<EventChooserSet> tabs;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.event_chooser_container);
        EventChooserAdapter mAdapter = new EventChooserAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        tabs = new ArrayList<>();
        tabs.add(new EventChooserSet("Technical Events", R.drawable.space_wallpaper));
        tabs.add(new EventChooserSet("Sciences", R.drawable.space_wallpaper));
        tabs.add(new EventChooserSet("Workshops", R.drawable.space_wallpaper));
        tabs.add(new EventChooserSet("Others", R.drawable.space_wallpaper));
        tabs.add(new EventChooserSet("Initiatives", R.drawable.space_wallpaper));
        mAdapter.setEvents(tabs);
        mAdapter.setClickListener(this);


    }

    @Override
    public void onClick(View v, int pos) {
        if (pos < 4) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            Fragment fragment=new EventListingFragmentHeader();
            transaction.addToBackStack("events");

            Bundle args = new Bundle();
            args.putString("tab", tabs.get(pos).getName());
            fragment.setArguments(args);
            transaction.replace(R.id.container, fragment, "EventHeader");
            transaction.commit();
        }
        else{
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            Fragment fragment=new InitiativeFragment();
            transaction.addToBackStack("events");
            transaction.replace(R.id.container, fragment, "initiative");
            transaction.commit();
        }
    }

    private class EventChooserAdapter extends RecyclerView.Adapter<EventChooserAdapter.MyViewHolder> {

        Context context;
        LayoutInflater inflater;
        ArrayList<EventChooserSet> events;
        RecyclerClickListener clickListener;

        public EventChooserAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            events = new ArrayList<>();
        }

        public void setClickListener(RecyclerClickListener clickListener) {
            this.clickListener = clickListener;
        }

        public void setEvents(ArrayList<EventChooserSet> events) {
            this.events = events;
            notifyDataSetChanged();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(inflater.inflate(R.layout.event_chooser_row, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.name.setText(events.get(position).getName());
            Picasso.with(context).load(events.get(position).getImg_resource()).into(holder.imageView);

        }

        @Override
        public int getItemCount() {
            return events.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView name;

            public MyViewHolder(View itemView) {
                super(itemView);

                imageView = (ImageView) itemView.findViewById(R.id.event_chooser_image);
                name = (TextView) itemView.findViewById(R.id.event_chooser_name);
                if (clickListener != null) {
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clickListener.onClick(v, getAdapterPosition());
                        }
                    });
                }
            }
        }
    }

    class EventChooserSet {
        private String name;
        private int Img_resource;

        public EventChooserSet(String name, int img_resource) {
            this.name = name;
            Img_resource = img_resource;
        }

        public String getName() {
            return name;
        }

        public int getImg_resource() {
            return Img_resource;
        }
    }
}
