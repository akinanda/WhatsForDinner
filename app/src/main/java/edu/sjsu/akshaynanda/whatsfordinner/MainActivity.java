package edu.sjsu.akshaynanda.whatsfordinner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    Button meals, recipes, groceries, new_dish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Whats for Dinner");

        meals = (Button)findViewById(R.id.button_meals);
        meals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this, MealsActivity.class);
                startActivity(i);
            }
        });


        recipes = (Button)findViewById(R.id.button_recipe);
        recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this, RecipeActivity.class);
                startActivity(i);
            }
        });


        groceries = (Button)findViewById(R.id.button_groceries);
        groceries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this, GroceriesActivity.class);
                startActivity(i);
            }
        });


        new_dish = (Button)findViewById(R.id.button_new_dish);
        new_dish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this, NewDishActivity.class);
                startActivity(i);
            }
        });

        LinearLayout banner = (LinearLayout)findViewById(R.id.banner_container);
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showApplicationInfo();
            }
        });
        
    }

    private void showApplicationInfo() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("What's for Dinner");
        String message = "Author Name: Akshay Nanda\nSoftware Version: 1.0\nHelp URL: http://www.sjsu.edu\nCopyright: http://www.sjsu.edu";
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
