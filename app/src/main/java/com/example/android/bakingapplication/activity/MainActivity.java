package com.example.android.bakingapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;

import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.adapter.RecipeCardAdapter;
import com.example.android.bakingapplication.model.FakeRecipeData;
import com.example.android.bakingapplication.model.KRecipe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeCardAdapter.RecipeCardAdapterOnClickHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_recipe);

        List<KRecipe> recipeList = new ArrayList<>();
        RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(this, recipeList, this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recipeCardAdapter);

        FakeRecipeData fakeRecipeData = new FakeRecipeData(recipeList);
        fakeRecipeData.addFakeRecipes();
        recipeCardAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRecipeSelected(KRecipe selectedRecipe) {
        Context context = this;
        Class recipeDetailActivityDestination = DetailListActivity.class;
        Intent openRecipeDetailActivity = new Intent(context, recipeDetailActivityDestination);
        openRecipeDetailActivity.putExtra("selected_recipe", selectedRecipe);
        startActivity(openRecipeDetailActivity);
    }
}






















