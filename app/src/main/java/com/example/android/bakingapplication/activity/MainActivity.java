package com.example.android.bakingapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;

import com.example.android.bakingapplication.Dagger.AppComponent;
import com.example.android.bakingapplication.Dagger.AppModule;
import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.adapter.RecipeCardAdapter;
import com.example.android.bakingapplication.model.FakeRecipeData;
import com.example.android.bakingapplication.model.KRecipe;
import java.util.List;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements RecipeCardAdapter.RecipeCardAdapterOnClickHandler {

    public static final String TAG = MainActivity.class.getSimpleName();
    
    @Inject
    FakeRecipeData fakeRecipeData;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((BakingApplication)getApplication()).getAppComponent().inject(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_recipe_list);

        // FakeRecipeData fakeRecipeData = FakeRecipeData.get();
        List<KRecipe> recipeList = fakeRecipeData.getRecipeList();
        RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(this, recipeList, this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recipeCardAdapter);

        Log.d(TAG, "onCreate: detail list contains: " + recipeList.get(0).getDetailList().get(0) + recipeList.get(1).getDetailList().get(0));
    }
    
    @Override
    public void onRecipeSelected(String selectedRecipe) {
        Context context = this;
        Class recipeDetailActivityDestination = DetailListActivity.class;
        Intent openRecipeDetailActivity = new Intent(context, recipeDetailActivityDestination);
        openRecipeDetailActivity.putExtra("name_of_food_item_selected", selectedRecipe);
        Log.d(TAG, "onRecipeSelected: " + selectedRecipe);
        startActivity(openRecipeDetailActivity);
    }
}






















