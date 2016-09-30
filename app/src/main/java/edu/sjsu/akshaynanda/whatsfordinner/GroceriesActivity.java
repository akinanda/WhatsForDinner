package edu.sjsu.akshaynanda.whatsfordinner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GroceriesActivity extends AppCompatActivity {
    ArrayAdapter adapter;
   // private TextView test = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groceries);
       // int arraySize=20;



        getSupportActionBar().setTitle("Groceries - Select a Recipe");
//String showTest = Utility.getRecipes().toArray(new String[Utility.getRecipes().size()]).toString();
  //      Toast.makeText(getApplicationContext(), showTest, Toast.LENGTH_LONG).show();

        adapter = new ArrayAdapter<String>(this, R.layout.list_view, R.id.list_text, Utility.getRecipes().toArray(new String[Utility.getRecipes().size()]));
        ListView listView = (ListView) findViewById(R.id.recipes_list_for_groceries);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(GroceriesActivity.this, GroceryListActivity.class);
                intent.putExtra("recipe", adapter.getItem(position).toString());
                startActivity(intent);
            }
        });

    }
}
