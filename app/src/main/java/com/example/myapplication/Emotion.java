package com.example.myapplication;

import android.widget.*;

import java.text.DateFormat;
import java.util.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class Emotion implements Serializable {
    private String comment, currentState, timeStamp;

    /**
     *Class constructor, which creates a datetime on the creation of the instance as per the specified format
     *
     *@param state The type of emotion (Happy, Sad, Fear, Love, Anger, Surprise)
     *@param comment The optional description entered for the instance of the emotion
     */
    public Emotion(String state, String comment){
        this.currentState = state;
        this.comment = comment;
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        this.timeStamp = format.format(Calendar.getInstance().getTime());
    }

    /**
     *Sets the emotional state to the state passed to the function
     *
     *@param state The type of emotion (Happy, Sad, Fear, Love, Anger, Surprise)
     */
    public void setEmotionalState(String state){
        this.currentState = state;
    }

    /**
     *Sets the optional description to the string passed to the function
     *
     *@param descr the optional description
     */
    public void setOpDescr(String descr){
        this.comment = descr;
    }

    /**
     *Sets the emotional state to the state passed to the function
     *
     *@param time Sets the time to a new time passed to the function as a string
     */
    public void setDate(String time){
        this.timeStamp = time;
    }

    /**
     *Returns the time of the instance
     *
     */
    public String gettimeStamp(){
        return this.timeStamp;
    }

    /**
     *Returns the emotional state of the instance
     *
     */
    public String getEmotionalState(){
        return this.currentState;
    }

    /**
     *Returns the discription of the instance
     *
     */
    public String getDiscription(){
        return this.comment;
    }


}
