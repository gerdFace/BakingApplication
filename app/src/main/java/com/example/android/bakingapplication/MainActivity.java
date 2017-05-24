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
    private List<Recipe> recipeList;

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
        String[] thumbnails = new String[] {
                "http://honestcooking.com/wp-content/uploads/2013/09/p8010158.jpg",
                "data:image/jpeg;base64,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",
                "https://addapinch.com/wp-content/blogs.dir/3/files/2014/08/yellow-cake-recipe-DSC_4666.jpg",
                "data:image/jpeg;base64,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"};

        Recipe testRecipe = new Recipe("Nutella Pie", "6", "8", "8", thumbnails[0]);
        Log.d(TAG, "Image URL: " + thumbnails[0]);
        recipeList.add(testRecipe);

        testRecipe = new Recipe("Brownies", "6", "10", "10", thumbnails[1]);
        recipeList.add(testRecipe);

        testRecipe = new Recipe("Yellow Cake", "3", "10", "15", thumbnails[2]);
        recipeList.add(testRecipe);

        testRecipe = new Recipe("Cheesecake", "10", "4", "6", thumbnails[3]);
        recipeList.add(testRecipe);

        recipeCardAdapter.notifyDataSetChanged();
    }
}






















