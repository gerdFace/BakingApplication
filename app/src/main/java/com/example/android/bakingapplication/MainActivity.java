package com.example.android.bakingapplication;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecipeCardAdapter recipeCardAdapter;
    private List<KRecipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_recipe);

        recipeList = new ArrayList<>();
        recipeCardAdapter = new RecipeCardAdapter(this, recipeList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(recipeCardAdapter);
        addFakeRecipes();
    }

    private void addFakeRecipes() {
        int[] thumbnails = new int[] {
                R.mipmap.nutella_pie,
                R.mipmap.brownies,
                R.mipmap.yellow_cake,
                R.mipmap.cheesecake};

        KRecipe testRecipe = new KRecipe("Nutella Pie", "6", "8", "8", thumbnails[0]);
        Log.d(TAG, "Image URL: " + thumbnails[0]);
        recipeList.add(testRecipe);

        testRecipe = new KRecipe("Brownies", "6", "10", "10", thumbnails[1]);
        recipeList.add(testRecipe);

        testRecipe = new KRecipe("Yellow Cake", "3", "10", "15", thumbnails[2]);
        recipeList.add(testRecipe);

        testRecipe = new KRecipe("Cheesecake", "10", "4", "6", thumbnails[3]);
        recipeList.add(testRecipe);

        recipeCardAdapter.notifyDataSetChanged();
    }
}






















