package edu.sjsu.akshaynanda.whatsfordinner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


import android.content.Intent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.content.Intent;
import android.widget.SimpleAdapter;

/**
 * Created by Akshay on 9/28/2016.
 */


public class MealsHandlerActivity extends AppCompatActivity {

    public static String eatingOut = "Eating Out!";
    Utility utility;
    SimpleAdapter adapter;

    private static Map<String, Integer> recipiesForMealPlan = new HashMap<>();
    private static String[][] mealsSelected = new String[7][3];

    static {
        initMealsSelected();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals_handler);
    }


    public String[] getAvailableMeals() {

        Set<String> recipieSet = Utility.getRecipes();
        for (String recipe : recipieSet) {

        }

        return null;
        /*
        adapter = new SimpleAdapter(this, data, R.layout.activity_meals_handler);

        Set keys = recipieSet.
        String[] possibleMeals =  (String[]) keys.toArray(new String[keys.size()]);

        ArrayList<String> mealsToShow = new ArrayList<String>();
        for(int i=0; i<possibleMeals.length; i++){
            String r = possibleMeals[i];
            if(recipiesForMealPlan.get(r) == 0) continue;
            mealsToShow.add(r);
        }
        mealsToShow.add(eatingOut);
        return mealsToShow.toArray(new String[mealsToShow.size()]);
        */
    }

    private static void initMealsSelected() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 3; j++) {
                mealsSelected[i][j] = eatingOut;
            }
        }
    }

    public String getMealSelected(int row, int col) {
        return mealsSelected[row][col];
    }

    public void setMealSelected(int row, int col, String recipieName) {
        mealsSelected[row][col] = recipieName;
    }


    public void addToMealPlan(String recipieName) {
        recipieName = recipieName.toLowerCase();
        incrementMealCount(recipieName);
    }

    public void incrementMealCount(String recipieName) {
        Integer val = recipiesForMealPlan.get(recipieName);
        if (val == null) {
            recipiesForMealPlan.put(recipieName, 1);
        } else {
            recipiesForMealPlan.put(recipieName, val + 1);
        }
    }

    public void decrementMealCount(String recipieName) {
        Integer val = recipiesForMealPlan.get(recipieName);
        if (val != null && val > 0) {
            recipiesForMealPlan.put(recipieName, val - 1);
        }
    }


}




