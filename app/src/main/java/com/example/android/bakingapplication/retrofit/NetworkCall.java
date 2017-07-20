package com.example.android.bakingapplication.retrofit;

import android.util.Log;
import com.example.android.bakingapplication.model.RecipeDatum;
import java.util.List;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NetworkCall {

    @Inject
    Retrofit retrofit;

    private static final String TAG = NetworkCall.class.getSimpleName();
    private List<RecipeDatum> recipeList;

    public NetworkCall() {
        recipeList = makeNetworkCall();
    }

    private List<RecipeDatum> makeNetworkCall() {
        Call<List<RecipeDatum>> recipeCall = retrofit.create(RecipeService.class).getRecipes();

        recipeCall.enqueue(new Callback<List<RecipeDatum>>() {

            @Override
            public void onResponse(Call<List<RecipeDatum>> call, Response<List<RecipeDatum>> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    recipeList = response.body();
                    Log.d(TAG, "Recipe data was loaded from website");
                    Log.d(TAG, "onResponse: " + response.body().get(0));
                }
            }

            @Override
            public void onFailure(Call<List<RecipeDatum>> call, Throwable t) {
                Log.d(TAG, "onFailure: Could not load recipe data from network path" + t.toString());
            }
        });
        return recipeList;
    }

    public List<RecipeDatum> getRecipeList() {
        return recipeList;
    }
}
