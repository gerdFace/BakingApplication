package com.example.android.bakingapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.adapter.RecipeCardAdapter;
import com.example.android.bakingapplication.model.RecipeData;
import com.example.android.bakingapplication.retrofit.RecipeService;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements RecipeCardAdapter.RecipeCardAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<RecipeData> recipeList;

    @Inject
    Retrofit retrofit;

    @Inject
    Realm realm;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((BakingApplication) getApplication()).getAppComponent().inject(this);

        recipeList = realm.where(RecipeData.class).findAll();

        configureUI();

        if (recipeList.size() == 0) {
            Call<List<RecipeData>> recipeCall = retrofit.create(RecipeService.class).getRecipes();

            recipeCall.enqueue(new Callback<List<RecipeData>>() {

                @Override
                public void onResponse(Call<List<RecipeData>> call, Response<List<RecipeData>> response) {
                    Log.d(TAG, "onResponse: " + response.code());
                    if (response.isSuccessful()) {
                        recipeList = response.body();
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(recipeList);
                        realm.commitTransaction();
                        Log.d(TAG, "Recipe data was loaded from website");
                        configureUI();
                    }
                }

                @Override
                public void onFailure(Call<List<RecipeData>> call, Throwable t) {
                    Log.d(TAG, "onFailure: Could not load recipe data from network path" + t.toString());
                }
            });
        }
        Log.d(TAG, "onCreate: recipe list contains: " + recipeList.toString());
    }

    private void configureUI() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_recipe_list);

        RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(this, recipeList, this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recipeCardAdapter);
    }
    
    @Override
    public void onRecipeSelected(String nameOfFoodItemSelected, int foodID) {
        Class recipeDetailActivityDestination = DetailListActivity.class;
        Intent openRecipeDetailActivity = new Intent(this, recipeDetailActivityDestination);
        openRecipeDetailActivity.putExtra("id_of_food_selected", foodID);
        openRecipeDetailActivity.putExtra("name_of_food_selected", nameOfFoodItemSelected);
        Log.d(TAG, "onRecipeSelected: " + nameOfFoodItemSelected + "ID: " + foodID);
        startActivity(openRecipeDetailActivity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}






















