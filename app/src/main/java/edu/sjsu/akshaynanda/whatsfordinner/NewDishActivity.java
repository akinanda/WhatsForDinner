package edu.sjsu.akshaynanda.whatsfordinner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NewDishActivity extends AppCompatActivity {
    TextView errorMessage;
    List<String> ingredientsList = new ArrayList<String>();
    List<AutoCompleteTextView> autoCompleteTextViews = new ArrayList<AutoCompleteTextView>();
    private static int IMAGE_REQUEST = 1;

    boolean editMode = false;
    String editRecipeName;
    Recipe editRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dish);

        if (savedInstanceState != null){
            editRecipeName = savedInstanceState.getString("editRecipe");
            savedInstanceState.putString("editRecipe", "");
        }

        Intent intent = getIntent();
        Bundle intentData = intent.getExtras();
        if(intentData!=null || !Utility.isEmpty(editRecipeName)) {
            editRecipeName = intentData.getString("edit_recipe_name");
            if(Utility.isEmpty(editRecipeName)) {
                editRecipeName = intentData.getString("edit_recipe_name");
            }
            if(editRecipeName != null && !Utility.isEmpty(editRecipeName)) {
                editMode = true;
                intent.putExtra("edit_recipe_name", "");//reset the intent data
                getSupportActionBar().setTitle("Edit Recipe - "+editRecipeName);
                editRecipe = Utility.getRecipe(editRecipeName);
            } else {
                getSupportActionBar().setTitle("New Dish");
            }
        }

        final EditText recipeName = (EditText) findViewById(R.id.recipe_name);
        final TextView dup_recipe = (TextView) findViewById(R.id.dup_message);
        final Button addRecipeImage = (Button) findViewById(R.id.add_recipe_image);
        final Button addRecipe = (Button) findViewById(R.id.add_recipe);
        final EditText recipeDirections = (EditText) findViewById(R.id.recipe_directions);

        errorMessage = (TextView) findViewById(R.id.error_message);

        recipeName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String recipe = "";
                if (recipeName.getText() != null) {
                    recipe = recipeName.getText().toString();
                }
                if (!Utility.isEmpty(recipe)) {
                    if (Utility.checkIfRecipeExists(recipe) && !editMode) {
                        recipeName.setText("");
                        dup_recipe.setVisibility(View.VISIBLE);
                    } else {
                        dup_recipe.setVisibility(View.GONE);
                    }
                }
            }
        });


        addRecipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectRecipeImage();
            }
        });

        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorMessage.setVisibility(View.INVISIBLE);
                if (recipeName.getText() == null || Utility.isEmpty(recipeName.getText().toString())) {
                    showErrorMessage("Please enter a recipe name");
                    return;
                } else if (Utility.checkIfRecipeExists(recipeName.getText().toString()) && !editMode) {
                    showErrorMessage("The recipe name already exists");
                    return;
                }

                ImageView imageView = (ImageView) findViewById(R.id.recipe_image);
                Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                Recipe recipe = new Recipe();
                recipe.setName(recipeName.getText().toString());
                recipe.setImage(image);
                ArrayList<String> ingredientsList = new ArrayList<String>();
                if (!autoCompleteTextViews.isEmpty()) {
                    for (int i = 0; i < autoCompleteTextViews.size(); i++) {
                        if(autoCompleteTextViews.get(i) != null
                                && autoCompleteTextViews.get(i).getText() != null
                                && !Utility.isEmpty(autoCompleteTextViews.get(i).getText().toString())) {
                            ingredientsList.add(autoCompleteTextViews.get(i).getText().toString().trim());
                        }
                    }
                }
                recipe.setIngredents(ingredientsList);
                if(recipeDirections != null && recipeDirections.getText() != null) {
                    recipe.setDirections(recipeDirections.getText().toString());
                } else {
                    recipe.setDirections("");
                }

                Utility.addRecipe(recipe.getName(), recipe);
                onBackPressed();
            }
        });

        createIngredientElements();

        if(editMode) {
            populateEditRecipeDetails(editRecipe);
        }
    }

    private void createIngredientElements() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ingredients);
        for (int i = 1; i <= 10; i++) {
            final AutoCompleteTextView autoText = new AutoCompleteTextView(this);
            autoText.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            autoText.setHint("Ingredient");
            autoCompleteTextViews.add(autoText);
            linearLayout.addView(autoText);
            autoText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus) {
                        String[] data = getUniqueIngredients(ingredientsList);
                        ArrayAdapter<?> adapter = new ArrayAdapter<Object>(NewDishActivity.this, android.R.layout.simple_list_item_1, data);
                        autoText.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        if (autoText.getText() != null && !Utility.isEmpty(autoText.getText().toString())) {
                            ingredientsList.add(autoText.getText().toString());
                        }
                    }
                }
            });
        }

    }

    private String[] getUniqueIngredients(List<String> data) {
        ArrayList<String> uniqueIngredients = new ArrayList<String>();
        if(data!=null && data.size()>0) {
            for(String item:data) {
                if(!uniqueIngredients.contains(item)) {
                    uniqueIngredients.add(item);
                }
            }
        }
        return uniqueIngredients.toArray(new String[uniqueIngredients.size()]);
    }

    private void showErrorMessage(String error) {
        errorMessage.setText(error);
        errorMessage.setVisibility(View.VISIBLE);
    }

    private void selectRecipeImage() {
        Intent selectImageIntent = new Intent(Intent.ACTION_PICK);
        File imageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String imageDirectoryUrl = imageDirectory.getPath();
        Uri data = Uri.parse(imageDirectoryUrl);
        selectImageIntent.setDataAndType(data, "image/*");
        startActivityForResult(selectImageIntent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // If picture is selected
            if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                    // getting bitmap from the stream and setting it as the recipe image
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    ImageView img = (ImageView) findViewById(R.id.recipe_image);
                    img.setImageBitmap(image);
                }
            } else {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void populateEditRecipeDetails(Recipe recipe) {
        if(!editMode) {
            return;
        }

        EditText recipeName = (EditText) findViewById(R.id.recipe_name);
        recipeName.setText(recipe.getName());
        recipeName.setEnabled(false);

        ImageView img = (ImageView) findViewById(R.id.recipe_image);
        img.setImageBitmap(recipe.getImage());
        ArrayList<String> ingredients = recipe.getIngredents();
        for(int i = 0; i< ingredients.size(); i++) {
            autoCompleteTextViews.get(i).setText(ingredients.get(i));
            ingredientsList.add(ingredients.get(i));
        }

        EditText recipeDirections = (EditText) findViewById(R.id.recipe_directions);
        recipeDirections.setText(recipe.getDirections());
        Button addRecipe = (Button) findViewById(R.id.add_recipe);
        addRecipe.setText("Save Changes");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if(editMode) {
            savedInstanceState.putString("editRecipe", editRecipeName);
        }
    }

}
