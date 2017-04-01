package x_wolves.ais;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import x_wolves.ais.Fragments.FragFamily;
import x_wolves.ais.Fragments.FragFeed;
import x_wolves.ais.Fragments.FragMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager viewPager;
    TabLayout tabLayout;
    public Toolbar toolbar;
    private File file;
    private static final String FILENAME = "userData.json";
    JSONArray jsonArray;
    String mName = null, mContact = null, mMail = null;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Logs");
        getWindow().setStatusBarColor(getResources().getColor(R.color.mainColor));
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setupWithViewPager(viewPager);

        setTabIcons();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0: toolbar.setTitle("Logs");break;
                    case 1: toolbar.setTitle("Current Location");break;
                    case 2: toolbar.setTitle("Family");break;
                    default: break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        File extFileDir = getExternalFilesDir(null);

        file = new File(extFileDir,FILENAME);
        jsonArray = null;

//        try { jsonArray = readFile(); } catch (IOException | JSONException e) { Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();;}
//
//        try {
//            mName = jsonArray.getJSONObject(0).getString("name");
//            mMail = jsonArray.getJSONObject(0).getString("email");
//            mContact = jsonArray.getJSONObject(0).getString("contact");
//
//        } catch (JSONException e) {
//            Toast.makeText(this, "Fxile does not exists...", Toast.LENGTH_SHORT).show();
//        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });
        this.initialize();
    }

    private void initialize(){

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_acc_sett) {
            Intent intent = new Intent(this, AccountSettings.class);
            startActivity(intent);
        } else if (id == R.id.nav_fam_sett) {
            Intent intent = new Intent(this, FamilySettings.class);
            startActivity(intent);
        } else if (id == R.id.nav_app_sett) {
            Intent intent = new Intent(this, AppSettings.class);
            startActivity(intent);
        } else if (id == R.id.nav_feed) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(this, AboutAIS.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragFeed(), "Logs");
        adapter.addFragment(new FragMap(), "Map");
        adapter.addFragment(new FragFamily(), "Family");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

    }

    public JSONArray readFile() throws IOException, JSONException , NullPointerException {

        //FileInputStream fis = openFileInput("userdata.json");
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        StringBuffer b = new StringBuffer();
        while (bis.available() != 0) {
            char c = (char) bis.read();
            b.append(c);
        }

        bis.close();
        fis.close();

        JSONArray data = new JSONArray(b.toString());
        return data;
    }

    public void setTabIcons(){
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_log);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_map);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_fam);
    }



}
