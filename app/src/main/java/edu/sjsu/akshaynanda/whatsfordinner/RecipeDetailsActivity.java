package edu.sjsu.akshaynanda.whatsfordinner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import static edu.sjsu.akshaynanda.whatsfordinner.Utility.showIngredients;

public class RecipeDetailsActivity extends AppCompatActivity {

    String[] recipes;
    TextView recipeName;
    ImageView recipeImage;
    TextView recipeIngredients;
    TextView recipeDirections;
    String recipeNameForDetails;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        if (savedInstanceState != null){
            recipeNameForDetails = savedInstanceState.getString("recipeNameForDetails");
            savedInstanceState.putString("recipeNameForDetails", "");
        }
        Intent intent = getIntent();
        Bundle intentData = intent.getExtras();
        if(intentData!=null || !Utility.isEmpty(recipeNameForDetails)) {
            if(Utility.isEmpty(recipeNameForDetails)) {
                recipeNameForDetails = intentData.getString("recipe_name");
            }
            if(recipeNameForDetails != null && !Utility.isEmpty(recipeNameForDetails)) {
                intent.putExtra("recipe_name", "");//reset the intent data
                getSupportActionBar().setTitle("Recipe - "+recipeNameForDetails);
                recipe = Utility.getRecipe(recipeNameForDetails);
            } else {
                Intent backIntent = new Intent(RecipeDetailsActivity.this, RecipeActivity.class);
                startActivity(backIntent);
            }
        }

        recipes = Utility.getRecipes().toArray(new String[Utility.getRecipes().size()]);
        recipeName = (TextView) findViewById(R.id.recipe_name);
        recipeImage = (ImageView) findViewById(R.id.recipe_image);
        recipeIngredients = (TextView) findViewById(R.id.recipe_ingredients);
        recipeDirections = (TextView) findViewById(R.id.recipe_directions);

        showRecipeDetails(recipe);

    }

    public void showRecipeDetails(Recipe recipe){
        recipeName.setText(recipe.getName());
        recipeImage.setImageBitmap(recipe.getImage());
        recipeIngredients.setText(showIngredients(recipe.getIngredents()));
        recipeDirections.setText(recipe.getDirections());
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("recipeNameForDetails", recipeNameForDetails);
    }
}
