package harsu.atmos2k15.atmos.com.atmos2015;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import harsu.atmos2k15.atmos.com.atmos2015.services.ScheduleUpdateService;
import helper.ScheduleTableManager;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout mDrawer;
    CustomActionBarDrawerToggle mDrawerToggle;
    NavigationView mNavigation;
    FragmentManager manager;
    int backNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        backNumber = 0;

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new CustomActionBarDrawerToggle(this, mDrawer, toolbar);
        mNavigation = (NavigationView) findViewById(R.id.navigationView);
        mDrawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        manager = getSupportFragmentManager();
        FragmentTransaction transaction2 = manager.beginTransaction();
        Fragment fragment2 = new HomeFragment();
        transaction2.replace(R.id.container, fragment2, "home");
        transaction2.commit();


        //todo show nav drawer when user open apps for first time
        //mDrawer.openDrawer(Gravity.LEFT);

        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                manager.popBackStack("events", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment fragment;

                switch (menuItem.getItemId()) {
                    case R.id.home_menu:
                        fragment = new HomeFragment();
                        transaction.replace(R.id.container, fragment, "home");
                        transaction.commit();
                        menuItem.setChecked(true);
                        mDrawer.closeDrawer(Gravity.LEFT);

                        break;

                    case R.id.reachCampus:
                        fragment = new ReachCampus();
                        transaction.replace(R.id.container, fragment, "reachCampus");
                        transaction.commit();
                        menuItem.setChecked(true);
                        mDrawer.closeDrawer(Gravity.LEFT);

                        break;

                    case R.id.campusMap:
                        fragment = new Maps();
                        transaction.replace(R.id.container, fragment, "maps");
                        transaction.commit();
                        menuItem.setChecked(true);
                        mDrawer.closeDrawer(Gravity.LEFT);

                        break;
                    case R.id.schedule:
                        ScheduleTableManager mTableManager = new ScheduleTableManager(MainActivity.this);
                        if (!mTableManager.dataPresent()) {
                            Toast.makeText(MainActivity.this, "Schedule has not been updated. Please check internet connection", Toast.LENGTH_LONG).show();
                            break;
                        }
                        fragment = new ScheduleFragmentHeader();
                        transaction.replace(R.id.container, fragment, "schedule");
                        transaction.commit();
                        menuItem.setChecked(true);
                        mDrawer.closeDrawer(Gravity.LEFT);
                        break;

                    case R.id.register:
                        fragment = new Register();
                        transaction.replace(R.id.container, fragment, "register");
                        transaction.commit();
                        menuItem.setChecked(true);
                        mDrawer.closeDrawer(Gravity.LEFT);

                        break;
                    case R.id.events:

                        fragment = new EventChooserFragment();
                        transaction.replace(R.id.container, fragment, "events");
                        transaction.commit();
                        menuItem.setChecked(true);
                        mDrawer.closeDrawer(Gravity.LEFT);

                        break;
                    case R.id.contactUs:

                        fragment = new ContactsFragment();
                        transaction.replace(R.id.container, fragment, "contacts");
                        transaction.commit();
                        menuItem.setChecked(true);
                        mDrawer.closeDrawer(Gravity.LEFT);

                        break;
                    case R.id.favourites:
                        fragment = new FavouriteFragment();
                        transaction.replace(R.id.container, fragment, "favourite");
                        transaction.commit();
                        menuItem.setChecked(true);
                        mDrawer.closeDrawer(Gravity.LEFT);

                        break;
                    case R.id.feed:
                        fragment = new FeedFragment();
                        transaction.replace(R.id.container, fragment, "feed");
                        transaction.commit();
                        menuItem.setChecked(true);
                        mDrawer.closeDrawer(Gravity.LEFT);


                }

                return false;
            }
        });
        Intent intent = new Intent(this, ScheduleUpdateService.class);
        startService(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.home) {
            mDrawer.openDrawer(Gravity.LEFT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        backNumber = 0;
    }

    @Override
    public void onBackPressed() {

        if (mDrawer.isDrawerOpen(Gravity.LEFT)) {
            mDrawer.closeDrawer(Gravity.LEFT);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else if (backNumber < 1) {
            backNumber++;
            Toast.makeText(this, "Press Back again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    backNumber = 0;
                }
            }, 2000);
        } else {
            super.onBackPressed();
        }
    }

    private class CustomActionBarDrawerToggle extends ActionBarDrawerToggle {
        public CustomActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar) {
            super(activity, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        }

        public CustomActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout) {
            super(activity, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        }

    }
}
