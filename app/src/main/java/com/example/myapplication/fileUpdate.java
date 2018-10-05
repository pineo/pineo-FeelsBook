package com.example.myapplication;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class fileUpdate {


    public static void saveState(Context context, String fname,ArrayList<Emotion> array) {
        /*https://www.quora.com/How-do-I-save-an-object-to-a-file-in-Android*/
        FileOutputStream filewrite;
        File directory = context.getFilesDir();
        File file = new File(directory, fname);
        try {
            filewrite = new FileOutputStream(file);
            ObjectOutputStream objectwrite = new ObjectOutputStream(filewrite);
            objectwrite.writeObject(array);
            objectwrite.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static ArrayList<Emotion> loadState(Context context, String fname, ArrayList<Emotion> array) {
        /*https://www.quora.com/How-do-I-save-an-object-to-a-file-in-Android*/
        FileInputStream filein;
        File directory = context.getFilesDir();
        File file = new File(directory, fname);
        try {
            filein = new FileInputStream(file);
            ObjectInputStream objectin = new ObjectInputStream(filein);
            array = (ArrayList<Emotion>) objectin.readObject();
            filein.close();
            objectin.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (OptionalDataException e1) {
            e1.printStackTrace();
        } catch (StreamCorruptedException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } if(array == null){
            fileUpdate.saveState(context,fname,array);
        }
        return array;
    }
    public static ArrayList<Emotion> sortArray(ArrayList<Emotion> array){
        int largerDate;
        ArrayList<Emotion> new_array = new ArrayList<>();
        while(array.size()>0){
            largerDate = 0;
            for(int i = 0;i<array.size();i++){
                try {
                    if (dateCompare(array.get(largerDate).gettimeStamp(), array.get(i).gettimeStamp())) {
                        largerDate = i;
                    }
                } catch (java.text.ParseException pe) {
                    pe.printStackTrace();
                }
            }
            new_array.add(array.get(largerDate));
            array.remove(largerDate);
        }
        return new_array;

    }
    public static boolean dateCompare(String date1, String date2) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date firstDate = format.parse(date1);
        Date secondDate = format.parse(date2);

        return (firstDate.compareTo(secondDate) < 0);
    }
}

