package com.example.android.bakingapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeCardAdapter.RecipeCardAdapterOnClickHandler{

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_recipe);



        List<KRecipe> recipeList = new ArrayList<>();
        RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(this, recipeList, this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(recipeCardAdapter);

        FakeRecipeData fakeRecipeData = new FakeRecipeData(recipeList);
        fakeRecipeData.addFakeRecipes();
        recipeCardAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRecipeSelected(KRecipe selectedRecipe) {
        Context context = this;
        Class recipeDetailActivityDestination = RecipeDetailActivity.class;
        Intent openRecipeDetailActivity = new Intent(context, recipeDetailActivityDestination);
        Bundle bundle = new Bundle();
        bundle.putParcelable("selected_recipe", selectedRecipe);
        openRecipeDetailActivity.putExtras(bundle);
        startActivity(openRecipeDetailActivity);
    }
}






















