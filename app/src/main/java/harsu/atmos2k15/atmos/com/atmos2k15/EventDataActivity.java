package harsu.atmos2k15.atmos.com.atmos2k15;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bowyer.app.fabtoolbar.FabToolbar;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import harsu.atmos2k15.atmos.com.atmos2k15.set.EventDataSet;
import helper.EventTableManager;

public class EventDataActivity extends AppCompatActivity {

    EventDataSet data;
    NestedScrollView nestedScrollView;
    EventTableManager mTableManager;
    FabToolbar mFabToolbar;
    FloatingActionButton mFab;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView image;
    TextView eventDescription;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_data);

        id=getIntent().getIntExtra("Event_id", -1);
        if(id==-1){
            finish();
        }
        mTableManager=new EventTableManager(this);
        data=null;
        data = mTableManager.getEventData(id);
        if(data==null){
            finish();
        }

        image=(ImageView) findViewById(R.id.event_image);
        eventDescription=(TextView) findViewById(R.id.event_descrption);

        setImage();
        eventDescription.setText(Html.fromHtml(data.getDetails()));

        nestedScrollView= (NestedScrollView) findViewById(R.id.scroll);
        mFabToolbar= (FabToolbar) findViewById(R.id.fabtoolbar);

        mFab= (FloatingActionButton) findViewById(R.id.call_organizer);
        collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(data.getName());
        setSupportActionBar(toolbar);

        mFabToolbar.setFab(mFab);

        Bitmap icon2 = ((BitmapDrawable) image.getDrawable()).getBitmap();

        Palette.generateAsync(icon2, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int primaryDark = getResources().getColor(R.color.primary_dark);
                int primary = getResources().getColor(R.color.primary);
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkVibrantColor(primaryDark));
                mFabToolbar.setColor(palette.getMutedColor(primary));
            }
        });

        nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    mFabToolbar.contractFab();


                }
                return false;
            }
        });


        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabToolbar.expandFab();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setImage() {
        if(data.getImage_downloaded()==0){
            Picasso.with(this).load(data.getImage_link())
                    .resize(300,200)
                    .centerCrop()
                    .into(image, new Callback() {
                        @Override
                        public void onSuccess() {
                            String path = saveToInternalSorage(((BitmapDrawable) image.getDrawable()).getBitmap(), data.getName());

                            mTableManager.imageDownloaded(data.getId(), path);
                        }

                        @Override
                        public void onError() {

                        }
                    });

        }
        else {

            image.setImageBitmap(loadImageFromStorage(data.getImage_link(),data.getName()));
        }
    }
    private String saveToInternalSorage(Bitmap bitmapImage,String name) {
        ContextWrapper cw = new ContextWrapper(this);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
