package com.example.fitnessenthusiasts.activities.HelperClasses.Models;

public class WinnersModel {
    private String winner,runnerUp1,runnerUp2;
    private long endedAt;

    public WinnersModel() {
    }

    public WinnersModel(String winner, String runnerUp1, String runnerUp2, long endedAt) {
        this.winner = winner;
        this.runnerUp1 = runnerUp1;
        this.runnerUp2 = runnerUp2;
        this.endedAt = endedAt;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getRunnerUp1() {
        return runnerUp1;
    }

    public void setRunnerUp1(String runnerUp1) {
        this.runnerUp1 = runnerUp1;
    }

    public String getRunnerUp2() {
        return runnerUp2;
    }

    public void setRunnerUp2(String runnerUp2) {
        this.runnerUp2 = runnerUp2;
    }

    public long getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(long endedAt) {
        this.endedAt = endedAt;
    }
}
