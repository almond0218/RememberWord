package com.example.almond.androidproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class LectureListFragment extends Fragment {
    int id;
    String l1, l2, l3, l4;
    int w1, w2, w3, w4;
    public LectureListFragment(int id, String l1, String l2, String l3, String l4, int w1, int w2, int w3, int w4) {
        // Required empty public constructor
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
        this.l4 = l4;
        this.w1 = w1;
        this.w2 = w2;
        this.w3 = w3;
        this.w4 = w4;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lecture_list, container, false);
        view.setTag("text" + id);
        TextView text1 = (TextView) view.findViewById(R.id.txt1);
        TextView text2 = (TextView) view.findViewById(R.id.txt2);
        TextView text3 = (TextView) view.findViewById(R.id.txt3);
        TextView text4 = (TextView) view.findViewById(R.id.txt4);
        TextView word1 = (TextView) view.findViewById(R.id.w1);
        TextView word2 = (TextView) view.findViewById(R.id.w2);
        TextView word3 = (TextView) view.findViewById(R.id.w3);
        TextView word4 = (TextView) view.findViewById(R.id.w4);

        text1.setText(l1);
        text2.setText(l2);
        text3.setText(l3);
        text4.setText(l4);
        word1.setText(w1 + "단어");
        word2.setText(w2 + "단어");
        word3.setText(w3 + "단어");
        word4.setText(w4 + "단어");

        return view;
    }
}
