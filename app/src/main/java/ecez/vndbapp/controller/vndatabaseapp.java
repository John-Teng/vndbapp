package ecez.vndbapp.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.Adapters.TabPagerAdapter;
import ecez.vndbapp.controller.NetworkRequests.RequestDumpObjects;
import ecez.vndbapp.model.ServerRequest;
import ecez.vndbapp.model.SystemStatus;
import ecez.vndbapp.model.Tag;
import ecez.vndbapp.model.Trait;

public class vndatabaseapp extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String date;
    private TabLayout tabLayout;
    private TabPagerAdapter mTabPagerAdapter;
    private void getDemFiles (String saveDir) {
        try {
            File file = new File(getApplicationContext().getDir("data", Context.MODE_PRIVATE), saveDir);
            if (file == null) {
                Log.d("file","The file is nill");
            }
            if (file.length() != 0) {
                ObjectInputStream o = new ObjectInputStream(new FileInputStream(file));
                if (saveDir.equals("traitsMap"))
                    SystemStatus.getInstance().traitsMap = (HashMap<Integer, Trait>) o.readObject();
                else
                    SystemStatus.getInstance().tagsMap = (HashMap<Integer, Tag>) o.readObject();
                o.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ee){
            ee.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getDemFiles("traitsMap");
        getDemFiles("tagsMap");
        checkDate();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vndatabaseapp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Top"));
        tabLayout.addTab(tabLayout.newTab().setText("Popular"));
        tabLayout.addTab(tabLayout.newTab().setText("New"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        mTabPagerAdapter = new TabPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(mTabPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        AsyncTask b = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                SystemStatus.getInstance().loggedIn = ServerRequest.getInstance().login();
                List<NovelListFragment> fragments = mTabPagerAdapter.getFragmentList();
                Log.d("Fragment2", "THere are " + fragments.size() + " fragments");
                for (NovelListFragment f : fragments) {
                    Log.d("Fragment", "Updating a fragment");
                    f.loadList(1);
                }
                return true;
            }
        };
        b.execute();
        SystemStatus.getInstance().loadPreferences(getApplicationContext());

    }

    private void checkDate () {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Log.d("Current Date",currentDate);
        Boolean updateMap = false;

        SharedPreferences prefs = getSharedPreferences("Date", MODE_PRIVATE);
        String restoredText = prefs.getString("Last Open Date", null);
        if (restoredText == null) {
            Log.d("Last Open Date","null value");
            date = currentDate;
            updateMap = true;
        } else {
            Log.d("Last Open Date",restoredText);
            date = restoredText;
            Log.d("Date1",currentDate.substring(0,3));
            Log.d("Date2",date.substring(0,3));

            if (!currentDate.substring(0,3).equals(date.substring(0,3)))//year is different
                updateMap = true;

            int cm = Integer.parseInt(currentDate.substring(5,6));
            int m = Integer.parseInt(date.substring(5,6));
            int cd = Integer.parseInt(currentDate.substring(8,9));
            int d = Integer.parseInt(date.substring(8,9));

            if (Math.abs(cd-d) > 7 && Math.abs(cm-m) <= 1)
                updateMap = true;
            if (Math.abs(cm-m) > 1)
                updateMap = true;
        }

        if (SystemStatus.getInstance().tagsMap == null || SystemStatus.getInstance().traitsMap == null)
            updateMap = true;

        if (updateMap) {
            ProgressDialog dialog = new ProgressDialog(vndatabaseapp.this);
            RequestDumpObjects t = new RequestDumpObjects(getApplicationContext(),dialog, "https://vndb.org/api/traits.json.gz", "traitsMap");
            t.execute();
            RequestDumpObjects d = new RequestDumpObjects(getApplicationContext(),dialog, " https://vndb.org/api/tags.json.gz", "tagsMap");
            d.execute();

            //store the current date
            SharedPreferences.Editor editor = getSharedPreferences("Date", MODE_PRIVATE).edit();
            editor.putString("Last Open Date", date);
            editor.commit();
        }

    }

    @Override
    public void onDestroy () {
        //ServerRequest.disconnect();
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vndatabaseapp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(getApplicationContext(), AppPreferences.class);
            getApplicationContext().startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}