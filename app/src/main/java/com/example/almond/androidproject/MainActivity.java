package com.example.almond.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final LinearLayout indi_layout = (LinearLayout) findViewById(R.id.indi);
        pager = (ViewPager) findViewById(R.id.main_pager);
        MyPgaerAdapter adapter = new MyPgaerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i = 0; i < indi_layout.getChildCount(); i++){
                    ImageView indi = (ImageView) indi_layout.getChildAt(i);
                    indi.setImageResource(R.drawable.page_indi_non);
                }
                ImageView t_indi = (ImageView) indi_layout.getChildAt(position);
                t_indi.setImageResource(R.drawable.page_indi);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // int id = item.getItemId();

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

    class MyPgaerAdapter extends FragmentPagerAdapter{

        public MyPgaerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new LectureListFragment(position, "1강" ,"2강", "3강", "4강", 40, 94, 76, 98);
                case 1: return new LectureListFragment(position, "5강" ,"6강", "7강", "8강", 30, 87, 72, 37);
                case 2: return new LectureListFragment(position, "9강" ,"10강", "11강", "12강", 88, 29, 113, 51);
                case 3: return new LectureListFragment(position, "13강" ,"14강", "15강", "16강", 102, 72, 100, 51);
                case 4: return new LectureListFragment(position, "17강", "18강", "19강", "20강", 65, 70, 24, 39);
                case 5: return new LectureListFragment(position, "21강", "22강", "23강", "24강", 46, 34, 38, 35);
                case 6: return new LectureListFragment(position, "25강", "26강", "27강", "28강", 37, 37, 46, 39);
                case 7: return new LectureListFragment(position, "29강", "30강", "30강", "30강", 60, 48, 48, 48);

            }
            return null;
        }

        @Override
        public int getCount() {
            return 8;
        }
    }

    public void goToQuiz(View view) throws JSONException {
        Intent intent = new Intent(this, QuizActivity.class);
        ArrayList<String> arr = null;
        arr = getRandomeWord();
        intent.putExtra("arr", arr);
        startActivity(intent);
    }

    public void goToLearn(View view) {
        Intent intent = new Intent(this, LearnActivity.class);
        intent.putExtra("lesson", "star");
        startActivity(intent);
    }

    public void lesson1(View view) {
        LinearLayout l = (LinearLayout) findViewById(view.getId());
        TextView text = (TextView)l.getChildAt(0);
        JSONArray jArray = getJSONArray();
        JSONObject jObject;
        int cnt = 0;
        ArrayList<String> sarr = new ArrayList<String>();

        for(int i = 0; i < jArray.length(); i++) {
            try {
                jObject = jArray.getJSONObject(i);

                if(jObject.getString("lesson").equals(text.getText().toString().split("강")[0] + ".0")) {
                    sarr.add(jObject.getString("word"));
                    sarr.add(jObject.getString("mean"));
                    cnt++;
                }
            }

            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(this, LearnActivity.class);
        intent.putExtra("lesson", text.getText().toString().split("강")[0] + ".0");
        intent.putExtra("cnt", cnt);
        intent.putExtra("arr", sarr);
        startActivity(intent);
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

    public ArrayList<String> getRandomeWord() throws JSONException {
        JSONArray jsonArray = getJSONArray();
        Random random = new Random();
        int randomInt[] = new int[8];
        ArrayList<String> arr = new ArrayList<String>();
        JSONObject jsonObject;

        for(int i = 0; i < 8; i++) {
            randomInt[i] = random.nextInt(1758);

            for(int j = 1; j < i; j++) {
                if(randomInt[j] == randomInt[j - 1]) i--;
            }
        }

        for(int i = 0; i < 8; i++) {
            arr.add(((JSONObject)jsonArray.getJSONObject(randomInt[i])).getString("word"));
            arr.add(((JSONObject)jsonArray.getJSONObject(randomInt[i])).getString("mean"));
        }

        return arr;
    }
}
