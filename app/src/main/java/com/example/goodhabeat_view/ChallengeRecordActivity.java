package com.example.goodhabeat_view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ChallengeRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_record);

        getSupportActionBar().hide();

        //과거 챌린지 기록 리사이클러뷰
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.challenge_record_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<ChallengeRecordData> challenge_records = new ArrayList<>();

        //과거 챌린지 기록 임시 데이터
        String[] names = {"체중 감량 3kg 목표", "식단 100% 지키기", "이번에도 지킨다!", "챌린지 힘들어", "채소를 많이 먹자", "탄수화물 줄이기", "트로피 갖고 싶다"};
        String[] periods = {"2022-01-01 ~ 2022-01-14", "2022-02-26 ~ 2022-03-04", "2022-03-05 ~ 2022-03-11", "2022-04-01 ~ 2022-04-14", "2022-04-19 ~ 2022-04-25", "2022-05-09 ~ 2022-05-15", "2022-06-02 ~ 2022-06-15"};
        int[] progress = {75, 100, 90, 85, 100, 70, 55, 80, 100, 95};

        for(int i=0; i<7; i++) {
            ChallengeRecordData records = new ChallengeRecordData(names[i], periods[i], progress[i]);
            challenge_records.add(records);

        }

        // 과거 챌린지 기록 리사이클러뷰 어댑터 연결
        recyclerView.setAdapter(new ChallengeRecordAdapter(challenge_records));
    }
}