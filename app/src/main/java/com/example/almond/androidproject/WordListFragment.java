package com.example.almond.androidproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by almond on 2017. 6. 23..
 */

public class WordListFragment extends Fragment {
    int id;
    String eng, kor;

    public WordListFragment(int id, String eng, String kor) {
        this.id = id;
        this.eng = eng;
        this.kor = kor;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word, container, false);
        view.setTag("text" + id);

        TextView korText = (TextView) view.findViewById(R.id.kor);
        TextView engText = (TextView) view.findViewById(R.id.eng);

        korText.setText(kor);
        engText.setText(eng);

        return view;
    }
}
