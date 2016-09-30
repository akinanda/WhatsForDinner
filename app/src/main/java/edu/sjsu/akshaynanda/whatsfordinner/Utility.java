package edu.sjsu.akshaynanda.whatsfordinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Akshay on 9/25/2016.
 */

public class Utility {
    public static HashMap<String, Recipe> recipes = new HashMap<String, Recipe>();
    public static HashMap<String, String> ingredients = new HashMap<String, String>();

    public static Boolean checkIfRecipeExists(String name) {
        if(recipes != null && !recipes.isEmpty() && recipes.containsKey(name)) {
            return true;
        }
        return false;
    }

    public static void addRecipe(String name, Recipe recipe) {
        recipes.put(name, recipe);
    }

    public static Set<String> getRecipes() {
        return recipes.keySet();
    }

    public static Recipe getRecipe(String recipeName) {
        return recipes.get(recipeName);
    }

    public static Boolean isEmpty(String str) {
        if(str!=null && !str.trim().equals("")) {
            return false;
        }
        return true;
    }

    public static String showIngredients(ArrayList<String> ingredientsList) {
        String ingredients = "";
        if(ingredientsList != null && !ingredientsList.isEmpty()) {
            Map<String, Integer> ingredientsMaps = new HashMap<String, Integer>();

            for(int i = 0; i<ingredientsList.size(); i++) {
                if(ingredientsMaps.containsKey(ingredientsList.get(i))) {
                    ingredientsMaps.put(ingredientsList.get(i), ingredientsMaps.get(ingredientsList.get(i)) +1);
                } else {
                    ingredientsMaps.put(ingredientsList.get(i), 1);
                }
            }

            for (Map.Entry<String, Integer> ingredient : ingredientsMaps.entrySet()) {
                ingredients +="* ";
                ingredients +=ingredient.getKey();
                ingredients +="(";
                ingredients +=ingredient.getValue();
                ingredients +=")";
                ingredients +="\n";
            }
        }
        return ingredients;
    }
}
