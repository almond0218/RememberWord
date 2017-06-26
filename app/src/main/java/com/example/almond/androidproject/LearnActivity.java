package com.example.almond.androidproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LearnActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager pager;
    String lesson;
    int wordCnt;
    ArrayList<String> arr;
    MyPgaerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        TextView page = (TextView) findViewById(R.id.page);
        this.lesson = intent.getStringExtra("lesson");
        this.wordCnt = intent.getIntExtra("cnt", 1);
        this.arr = intent.getStringArrayListExtra("arr");
        page.setText("1 / " + wordCnt);

        TextView meanToggle = (TextView) findViewById(R.id.textView);
        meanToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup group = (ViewGroup) pager.findViewWithTag("text" + pager.getCurrentItem());
                TextView kor = (TextView) group.findViewById(R.id.kor);
                TextView eng = (TextView) group.findViewById(R.id.eng);

                if(kor.getCurrentTextColor() == 16777215) {
                    kor.setTextColor(Color.parseColor("#FFFFFF"));
                    eng.setTextColor(Color.parseColor("#00FFFFFF"));
                }

                else {
                    kor.setTextColor(Color.parseColor("#00FFFFFF"));
                    eng.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }
        });

        pager = (ViewPager) findViewById(R.id.word_pager);
        adapter = new MyPgaerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                TextView page = (TextView) findViewById(R.id.page);
                page.setText((position + 1) + " / " + wordCnt);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public JSONArray getJSONArray() {
        try {
            InputStream is = getAssets().open("data.json");
            String json;
            JSONObject data;
            JSONArray jArray;
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            data = new JSONObject(json);
            jArray = new JSONArray(data.getString("data"));

            return jArray;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
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
        getMenuInflater().inflate(R.menu.learn, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class MyPgaerAdapter extends FragmentPagerAdapter {

        public MyPgaerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new WordListFragment(position, arr.get(position + position), arr.get(position + position + 1));
        }

        @Override
        public int getCount() {
            return wordCnt;
        }
    }
}
