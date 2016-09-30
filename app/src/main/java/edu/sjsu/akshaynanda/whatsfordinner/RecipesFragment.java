package edu.sjsu.akshaynanda.whatsfordinner;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class RecipesFragment extends Fragment {
    ArrayAdapter<String> adapter;
    View fragmentView;
    public RecipesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        fragmentView = view;

        String[] recipeNames = Utility.getRecipes().toArray(new String[Utility.getRecipes().size()]);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, recipeNames);
        ListView recipesList = (ListView)fragmentView.findViewById(R.id.recipes_list);
        recipesList.setAdapter(adapter);
        //setListAdapter(adapter);

        recipesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OnRecipeSelectionChangeListener listener = (OnRecipeSelectionChangeListener) getActivity();
                listener.OnSelectionChanged(adapter.getItem(i));
            }
        });

        recipesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String recipeName = adapter.getItem(i);
                Intent intent = new Intent(getActivity(), NewDishActivity.class);
                intent.putExtra("edit_recipe_name", recipeName);
                startActivity(intent);
                return false;
            }
        });

        return view;
    }

}

