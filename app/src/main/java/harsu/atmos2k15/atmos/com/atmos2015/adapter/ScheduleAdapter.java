package harsu.atmos2k15.atmos.com.atmos2015.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import harsu.atmos2k15.atmos.com.atmos2015.R;
import harsu.atmos2k15.atmos.com.atmos2015.set.ScheduleSet;
import helper.RecyclerClickListener;

/**
 * Created by harsu on 6/23/2015.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyHolder> implements StickyRecyclerHeadersAdapter<ScheduleAdapter.MyHeaderHolder> {
    private final LayoutInflater layoutInflater;
    ArrayList<ScheduleSet> scheduleSets;
    RecyclerClickListener clickListener;

    Context context;
    int day;
    Calendar morn_start = Calendar.getInstance(), morn_end = Calendar.getInstance(), aft_end = Calendar.getInstance(), eve_end = Calendar.getInstance();

    public ScheduleAdapter(Context context, int day) {
        layoutInflater = LayoutInflater.from(context);
        scheduleSets = new ArrayList<>();
        this.context = context;
        this.day = day;


        morn_start.set(2015, Calendar.OCTOBER, 9 + day, 3, 0);
        morn_end.set(2015, Calendar.OCTOBER, 9 + day, 11, 59);
        aft_end.set(2015, Calendar.OCTOBER, 9 + day, 15, 59);
        eve_end.set(2015, Calendar.OCTOBER, 9 + day, 18, 59);



    }

    public void setScheduleSets(ArrayList<ScheduleSet> scheduleSets) {
        this.scheduleSets = scheduleSets;
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.custom_event_schedule_row, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(MyHolder myHolder, final int i) {

        myHolder.name.setText(scheduleSets.get(i).getName());
        myHolder.startTime.setText(getTime(scheduleSets.get(i).getStart_time()));
        myHolder.venue.setText(scheduleSets.get(i).getVenue());

    }

    private String getTime(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat format1 = new SimpleDateFormat("h a");
        String temp = format1.format(calendar.getTime());
        return temp;

    }

    public void setClickListener(RecyclerClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public long getHeaderId(int i) {
        // return scheduleSets.get(i).getName().charAt(0);
        long startTime = scheduleSets.get(i).getStart_time();
        int result;

        if (startTime >= morn_start.getTimeInMillis() && startTime < morn_end.getTimeInMillis()) {
            result=0;
        }
        else if (startTime >= morn_end.getTimeInMillis() && startTime < aft_end.getTimeInMillis()) {
            result=1;
        }
        else if (startTime >= aft_end.getTimeInMillis() && startTime < eve_end.getTimeInMillis()) {
            result=2;
        }
        else {
            result=3;
        }


        return result;

    }

    @Override
    public MyHeaderHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        return new MyHeaderHolder(layoutInflater.inflate(R.layout.custom_schedule_header, viewGroup, false));
    }

    @Override
    public void onBindHeaderViewHolder(MyHeaderHolder myHeaderHolder, int i) {
        long startTime = scheduleSets.get(i).getStart_time();
        int result;

        if (startTime >= morn_start.getTimeInMillis() && startTime < morn_end.getTimeInMillis()) {
            result=0;
        }
        else if (startTime >= morn_end.getTimeInMillis() && startTime < aft_end.getTimeInMillis()) {
            result=1;
        }
        else if (startTime >= aft_end.getTimeInMillis() && startTime < eve_end.getTimeInMillis()) {
            result=2;
        }
        else {
            result=3;
        }

        switch (result) {
            case 0:
                myHeaderHolder.header.setText("Morning");
                break;
            case 1:
                myHeaderHolder.header.setText("Afternoon");
                break;
            case 2:
                myHeaderHolder.header.setText("Evening");
                break;
            case 3:
                myHeaderHolder.header.setText("Night");
                break;

        }


    }

    @Override
    public int getItemCount() {
        return scheduleSets.size();
    }




    public class MyHolder extends RecyclerView.ViewHolder {
        TextView name, startTime, venue;


        public MyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.event_schedule_name);
            venue = (TextView) itemView.findViewById(R.id.event_schedule_venue);
            startTime = (TextView) itemView.findViewById(R.id.event_schedule_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onClick(v, getLayoutPosition());
                    }
                }
            });

        }

    }

    public class MyHeaderHolder extends RecyclerView.ViewHolder {
        TextView header;

        public MyHeaderHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.header_text);
        }
    }
}
