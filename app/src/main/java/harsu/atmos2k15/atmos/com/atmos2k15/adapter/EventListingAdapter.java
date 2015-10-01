package harsu.atmos2k15.atmos.com.atmos2k15.adapter;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import harsu.atmos2k15.atmos.com.atmos2k15.R;
import harsu.atmos2k15.atmos.com.atmos2k15.set.EventSet;
import helper.EventTableManager;
import helper.RecyclerClickListener;

/**
 * Created by admin on 28-09-2015.
 */
public class EventListingAdapter extends RecyclerView.Adapter<EventListingAdapter.MyViewHolder> {

    Context context;
    LayoutInflater inflater;

    EventTableManager tableManager;

    ArrayList<EventSet> events;
    RecyclerClickListener clickListener;

    public void setClickListener(RecyclerClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public EventListingAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        tableManager = new EventTableManager(context);

    }

    public void setEvents(ArrayList<EventSet> events) {
        this.events = events;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.event_listing_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final EventSet event=events.get(position);
        holder.name.setText(event.getName());
        holder.start_time.setText(getTime(event.getStart_time()));
        holder.venue.setText(event.getVenue());
        if(event.isFavourite())
            holder.favourite.setImageResource(R.drawable.ic_heart);
        else
            holder.favourite.setImageResource(R.drawable.ic_heart_outline_black_48dp);

        if(event.getImage_downloaded()==0){
            Picasso.with(context).load(event.getImg_link())
                    .resize(300,200)
                    .centerCrop()
                    .into(holder.image, new Callback() {
                @Override
                public void onSuccess() {
                    String path = saveToInternalSorage(((BitmapDrawable) holder.image.getDrawable()).getBitmap(), event.getName());
                    Log.e("save Path",path);
                    tableManager.imageDownloaded(event.getId(), path);
                }

                @Override
                public void onError() {

                }
            });

        }
        else {
            Log.e("Load from ",event.getImg_link());
            holder.image.setImageBitmap(loadImageFromStorage(event.getImg_link(),event.getName()));
        }

    }
    private String getTime(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        String temp = calendar.get(Calendar.HOUR_OF_DAY)%12
                + " " +
                calendar.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault());

        return temp;

    }
    private String saveToInternalSorage(Bitmap bitmapImage,String name) {
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory,name+".jpg");

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            Log.e("Save unsuccessful",e.toString());
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }
    private Bitmap loadImageFromStorage(String path,String name) {

        try {
            File f = new File(path, name+".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image,favourite;
        TextView name, start_time, venue;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.event_image);
            name = (TextView) itemView.findViewById(R.id.event_name);
            start_time = (TextView) itemView.findViewById(R.id.start_time);
            venue = (TextView) itemView.findViewById(R.id.venue);
            favourite=(ImageView) itemView.findViewById(R.id.favourite_icon);
            if(clickListener!=null){
                favourite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onClick(v,getAdapterPosition());
                    }
                });
            }
        }
    }
}
