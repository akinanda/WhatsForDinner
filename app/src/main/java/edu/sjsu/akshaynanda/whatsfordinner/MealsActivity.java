package edu.sjsu.akshaynanda.whatsfordinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MealsActivity extends AppCompatActivity {

    private static final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private static final String[] meals = {"Breakfast", "Lunch", "Dinner"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

        getSupportActionBar().setTitle("Meals");

        for (int i = 0; i < days.length; i++) {
            for (int j = 0; j < meals.length; j++) {
                String buttonID = meals[j] + "_" + days[i] + "_" + "button";
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                final Button btn = (Button) findViewById(resID);
                final int row = i;
                final int col = j;

            }
        }
    }
}