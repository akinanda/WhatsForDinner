package edu.sjsu.akshaynanda.whatsfordinner;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroceryListActivity extends AppCompatActivity {
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        Intent i = getIntent();
        String recipeName = i.getExtras().getString("recipe");

        Recipe recipe = Utility.getRecipe(recipeName);
        getSupportActionBar().setTitle("Groceries List - " + recipeName);

        Map<String, Integer> recipeMap = new HashMap<String, Integer>();
        ArrayList<String> ingredients = recipe.getIngredents();
        for (String ing : ingredients) {
            if (recipeMap.containsKey(ing)) {
                recipeMap.put(ing, recipeMap.get(ing) + 1);
            } else {
                recipeMap.put(ing, 1);
            }
        }

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> tempMap;

        for (Map.Entry<String, Integer> ingredient : recipeMap.entrySet()) {
            tempMap = new HashMap<String, String>();
            tempMap.put("item", ingredient.getKey());
            tempMap.put("qty", ingredient.getValue().toString());
            data.add(tempMap);
        }

        adapter = new SimpleAdapter(this, data, R.layout.fragment_groceries_list, new String[]{"item", "qty"}, new int[]{R.id.item_name, R.id.item_qty});
        ListView listView = (ListView) findViewById(R.id.groceries_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showHideButtons(view);
            }
        });
        listView.setAdapter(adapter);
    }

    public boolean showHideButtons(View view) {
        //show menu
        Button addBtn = (Button) view.findViewById(R.id.add_qty);
        Button removeBtn = (Button) view.findViewById(R.id.remove_qty);
        if (addBtn.isShown() && removeBtn.isShown()) {
            addBtn.setVisibility(View.INVISIBLE);
            removeBtn.setVisibility(View.INVISIBLE);
        } else {
            addBtn.setVisibility(View.VISIBLE);
            removeBtn.setVisibility(View.VISIBLE);
        }
        return true;
    }

    public void addQty(View v) {
        //get the row the clicked button is in
        LinearLayout parent = (LinearLayout) v.getParent();
        TextView item = (TextView) parent.getChildAt(0);
        TextView child = (TextView) parent.getChildAt(2);
        int qty = Integer.parseInt(child.getText().toString());
        ++qty;
        child.setText("" + qty);
        if (qty == 1) {
            item.setPaintFlags(item.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    public void removeQty(View v) {
        //get the row the clicked button is in
        LinearLayout parent = (LinearLayout) v.getParent();
        TextView item = (TextView) parent.getChildAt(0);
        TextView child = (TextView) parent.getChildAt(2);
        int qty = Integer.parseInt(child.getText().toString());
        if (qty != 0) {
            --qty;
            child.setText("" + qty);
            if (qty == 0) {
                item.setPaintFlags(item.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }


}