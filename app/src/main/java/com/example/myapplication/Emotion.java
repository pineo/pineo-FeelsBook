package com.example.myapplication;

import android.widget.*;

import java.text.DateFormat;
import java.util.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class Emotion implements Serializable {
    String name;
    private boolean clicked;
    ImageButton button;
    private String comment, currentState, timeStamp;

    public Emotion(String state, String comment){
        this.currentState = state;
        this.comment = comment;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        this.timeStamp = df.format(Calendar.getInstance().getTime());
    }

    public void setEmotionalState(String state){
        this.currentState = state;
    }
    public void setOpDescr(String descr){
        this.comment = descr;
    }
    public void setDate(String time){
        this.timeStamp = time;
    }

    public String gettimeStamp(){
        return this.timeStamp;
    }

    public String getEmotionalState(){
        return this.currentState;
    }

    public String getDiscription(){
        return this.comment;
    }



    //set emotion public

    //get emotion public



}
