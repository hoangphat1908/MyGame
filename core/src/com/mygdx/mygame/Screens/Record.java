package com.mygdx.mygame.Screens;

import java.io.Serializable;

/**
 * Created by hoangphat1908 on 5/12/2017.
 */

public class Record implements Serializable{
    private int time;
    public Record(int time){
        this.time = time;
    }
    public int getTime(){
        return time;
    }

    @Override
    public boolean equals(Object obj) {
        Record other = (Record) obj;
        return this.time == other.time;
    }
}
