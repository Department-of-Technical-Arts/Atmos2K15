package harsu.atmos2k15.atmos.com.atmos2k15.adapter;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import harsu.atmos2k15.atmos.com.atmos2k15.R;
import harsu.atmos2k15.atmos.com.atmos2k15.set.FeedSet;
import helper.EventTableManager;
import helper.RecyclerClickListener;

/**
 * Created by admin on 28-09-2015.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {

    Context context;
    LayoutInflater inflater;

    EventTableManager tableManager;

    ArrayList<FeedSet> feeds;
    RecyclerClickListener clickListener;

    public FeedAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        tableManager = new EventTableManager(context);

    }

    public void setClickListener(RecyclerClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setFeeds(ArrayList<FeedSet> feeds) {
        this.feeds = feeds;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.custom_feed_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.name.setText(feeds.get(position).getName());
        holder.post_time.setText(getTime(feeds.get(position).getPosted_time()));
        holder.message.setText(feeds.get(position).getMessage());
    }

    private String getTime(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        String temp = calendar.get(Calendar.HOUR_OF_DAY) % 12+":"+ calendar.get(Calendar.MINUTE)
                + " " +
                calendar.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault());

        return temp;

    }


    @Override
    public int getItemCount() {
        return feeds.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView name, post_time, message;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.feed_name);
            post_time = (TextView) itemView.findViewById(R.id.feed_time);
            message = (TextView) itemView.findViewById(R.id.feed_message);

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
