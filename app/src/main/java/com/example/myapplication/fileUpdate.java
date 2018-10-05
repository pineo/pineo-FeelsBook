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

    /**
     *
     * This will save a given array object to a specified filepath in internal memory
     *
     * @param context The state in which the file is regarding
     * @param fname The filename path to the file saved in memory
     * @param array The array object to be written
     */
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

    /**
     *
     * This will receive a given arraylist and return it to the activity accessing it
     *
     * @param context The state in which the file is regarding
     * @param fname The filename path to the file saved in memory
     * @param array The array object to be written
     *
     * @return Returns an array list of type Emotion
     */
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

    /**
     * Sorts an array list of type Emotion to descending order regarding date.
     *
     * @param array An arraylist of type Emotion that is not sorted by date
     *
     * @return An array list of type Emotion that is sorted in descending order with repect to date
     */
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

    /**
     * Compares two dates to figure out which occured before/after the other.
     *
     * @param date1 The first date to be compared
     * @param date2 The second date to be compared
     * @return Returns True if the first date is before the second date, and false if not.
     * @throws ParseException To make sure the file could be read
     */
    private static boolean dateCompare(String date1, String date2) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date firstDate = format.parse(date1);
        Date secondDate = format.parse(date2);

        return (firstDate.compareTo(secondDate) < 0);
    }
}

