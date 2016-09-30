package edu.sjsu.akshaynanda.whatsfordinner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RecipeActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        getSupportActionBar().setTitle("Recipes");

        String[] recipeNames = Utility.getRecipes().toArray(new String[Utility.getRecipes().size()]);
        adapter = new ArrayAdapter<String>(RecipeActivity.this, android.R.layout.simple_list_item_1, recipeNames);
        ListView recipesList = (ListView) findViewById(R.id.recipes_list);
        recipesList.setAdapter(adapter);
        //setListAdapter(adapter);

        recipesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String recipeName = adapter.getItem(i);
                Intent intent = new Intent(RecipeActivity.this, RecipeDetailsActivity.class);
                intent.putExtra("recipe_name", recipeName);
                startActivity(intent);
            }
        });

        recipesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String recipeName = adapter.getItem(i);
                Intent intent = new Intent(RecipeActivity.this, NewDishActivity.class);
                intent.putExtra("edit_recipe_name", recipeName);
                startActivity(intent);
                return true;
            }
        });

    }
}


