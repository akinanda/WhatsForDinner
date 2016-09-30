package edu.sjsu.akshaynanda.whatsfordinner;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import static edu.sjsu.akshaynanda.whatsfordinner.Utility.showIngredients;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailsFragment extends Fragment {
    final static String NAME = "name";
    String currentRecipe = "";

    String[] recipes;
    TextView recipeName;
    ImageView recipeImage;
    TextView recipeIngredients;
    TextView recipeDirections;


    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recipes = Utility.getRecipes().toArray(new String[Utility.getRecipes().size()]);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // If the Activity is recreated, the savedInstanceStare Bundle isn't empty
        // we restore the previous version name selection set by the Bundle.
        // This is necessary when in two pane layout
        if (savedInstanceState != null){
            currentRecipe = savedInstanceState.getString(NAME);
        }

        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        recipeName = (TextView) view.findViewById(R.id.recipe_name);
        recipeImage = (ImageView) view.findViewById(R.id.recipe_image);
        recipeIngredients = (TextView) view.findViewById(R.id.recipe_ingredients);
        recipeDirections = (TextView) view.findViewById(R.id.recipe_directions);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null){
            showRecipeDetails(args.getString(NAME));
        } else if(currentRecipe != ""){
            showRecipeDetails(currentRecipe);
        }
    }

    public void showRecipeDetails(String recipeName){
        currentRecipe = recipeName;
        Recipe recipe = Utility.getRecipe(recipeName);
        this.recipeName.setText(recipeName);
        this.recipeImage.setImageBitmap(recipe.getImage());
        this.recipeIngredients.setText(showIngredients(recipe.getIngredents()));
        this.recipeDirections.setText(recipe.getDirections());


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(NAME,currentRecipe);
    }

}
