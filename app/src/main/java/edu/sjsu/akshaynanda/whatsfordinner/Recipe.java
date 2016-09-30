package edu.sjsu.akshaynanda.whatsfordinner;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Akshay on 9/25/2016.
 */

public class Recipe {

    String name;
    Bitmap image;
    ArrayList<String> ingredents;
    String directions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public ArrayList<String> getIngredents() {
        return ingredents;
    }

    public void setIngredents(ArrayList<String> ingredents) {
        this.ingredents = ingredents;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }
}
