package com.example.almond.androidproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static com.example.almond.androidproject.R.id.box1;
import static com.example.almond.androidproject.R.id.box2;
import static com.example.almond.androidproject.R.id.box3;
import static com.example.almond.androidproject.R.id.box4;
import static com.example.almond.androidproject.R.id.box5;
import static com.example.almond.androidproject.R.id.box6;
import static com.example.almond.androidproject.R.id.box7;

public class QuizActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    boolean locked = false;
    int value=0;
    TextView mText;
    Intent intent;
    ArrayList<String> arr = new ArrayList<String>();
    int[] answerIds;
    int answerCnt = 0;
    TextView[] textViews;
    int beforeId = -1;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mText = (TextView) findViewById(R.id.timer);
        mHandler.sendEmptyMessage(0);

        intent = getIntent();
        arr = intent.getStringArrayListExtra("arr");
        Log.d("test: ", String.valueOf(arr));

        quizSetting();

    }

    Handler mHandler = new Handler() {
        int min = 0;
        public void handleMessage(Message msg) {
            value++;

            if(value % 60 == 0) {
                value = 0;
                min++;
            }

            mText.setText(min + " : " + value);
            mHandler.sendEmptyMessageDelayed(0,1000);
        }
    };

    public void goToMain(View view) {
        finish();
    }

    public void quizSetting() {
        Random random = new Random();
        int wordCount = 16;
        int randomInt;
        TextView[] boxes = new TextView[] {
                (TextView) findViewById(box1),
                (TextView) findViewById(box2),
                (TextView) findViewById(box3),
                (TextView) findViewById(box4),
                (TextView) findViewById(box5),
                (TextView) findViewById(box6),
                (TextView) findViewById(box7),
                (TextView) findViewById(R.id.box8),
                (TextView) findViewById(R.id.box9),
                (TextView) findViewById(R.id.box10),
                (TextView) findViewById(R.id.box11),
                (TextView) findViewById(R.id.box12),
                (TextView) findViewById(R.id.box13),
                (TextView) findViewById(R.id.box14),
                (TextView) findViewById(R.id.box15),
                (TextView) findViewById(R.id.box16)
        };
        textViews = boxes;
        ArrayList<Integer> answerTmp = new ArrayList<>();
        for (int i = 0; i < 16; ++i) {
            answerTmp.add(i / 2);
        }
        answerIds = new int[16];
        for (int i = 0; i < boxes.length; ++i) {
            randomInt = random.nextInt(wordCount--);
            answerIds[i] = answerTmp.get(randomInt);
            boxes[i].setText(arr.get(randomInt));
            boxes[i].setId(i);
            boxes[i].setOnClickListener(this);
            answerTmp.remove(randomInt);
            arr.remove(randomInt);
        }
        beforeId = -1;

        // GridLayout grid = (GridLayout) findViewById(R.id.grid);
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
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


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

    @Override
    public void onClick(View v) {
        if (locked) return;
        final int i = v.getId();
        final int currBefore = beforeId;
        final TextView textView = (TextView) v;
        textView.setTextColor(Color.parseColor("#ffffff"));
        if (currBefore != -1) {
            beforeId = -1;
            locked = true;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (answerIds[currBefore] != answerIds[i]) {
                        textView.setTextColor(Color.alpha(0));
                        textViews[currBefore].setTextColor(Color.alpha(0));
                    } else {
                        textView.setOnClickListener(null);
                        textViews[currBefore].setOnClickListener(null);
                        answerCnt ++;
                        if (answerCnt >= 8) {
                            mHandler.removeMessages(0);
                        }
                    }
                    beforeId = -1;
                    locked = false;
                }
            }, 1000);
        } else {
            beforeId = i;
        }
    }
}
