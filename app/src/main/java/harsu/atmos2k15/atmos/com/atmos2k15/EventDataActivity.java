package harsu.atmos2k15.atmos.com.atmos2k15;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

import com.bowyer.app.fabtoolbar.FabToolbar;

import helper.EventTableManager;

public class EventDataActivity extends AppCompatActivity {


    NestedScrollView nestedScrollView;
    EventTableManager mTableManager;
    FabToolbar mFabToolbar;
    FloatingActionButton mFab;
    CollapsingToolbarLayout collapsingToolbarLayout;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_data);

        id=getIntent().getIntExtra("Event_id",-1);
        if(id==-1){
            finish();
        }
        //EventDataSet data= mTableManager.getEventData(id);

        nestedScrollView= (NestedScrollView) findViewById(R.id.scroll);
        mFabToolbar= (FabToolbar) findViewById(R.id.fabtoolbar);

        mFab= (FloatingActionButton) findViewById(R.id.call_organizer);
        collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("BITS Pilani");
        setSupportActionBar(toolbar);

        mFabToolbar.setFab(mFab);

        Bitmap icon2 = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.ashishastro);

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
                mFabToolbar.contractFab();
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
}
