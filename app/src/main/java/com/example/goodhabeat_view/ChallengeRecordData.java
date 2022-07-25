package com.example.goodhabeat_view;

public class ChallengeRecordData {
    String challenge_name;
    String challenge_period;
    int progress;

    public ChallengeRecordData(String challenge_name, String challenge_period, int progress) {
        this.challenge_name = challenge_name;
        this.challenge_period = challenge_period;
        this.progress = progress;
    }

    public String getChallenge_name() { return challenge_name; }

    public void setChallenge_name(String challenge_name) { this.challenge_name = challenge_name; }

    public String getChallenge_period() { return challenge_period; }

    public void setChallenge_period(String challenge_period) { this.challenge_period = challenge_period; }

    public int getProgress() { return progress; }

    public void setProgress(int progress) { this.progress = progress; }
}


