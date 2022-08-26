package com.accomp.app;

public class Timer {
    private long birth;
    private long death;

    public Timer(long lifeTime) {
        this.birth = System.currentTimeMillis();
        this.death = birth + lifeTime;
    }

    public boolean isTimeout() {
        return System.currentTimeMillis() >= this.death;
    }

    public long getRunTime() {
        return System.currentTimeMillis() - birth;
    }
}
