package com.example.android.bakingapplication;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardAdapter.RecipeViewHolder> {

    private Context mContext;
    private List<Recipe> recipeList;

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        public TextView dessertName;
        public TextView numberOfSteps;
        public TextView numberOfIngredients;
        public TextView numberOfServings;
        public ImageView thumbnail;

        public RecipeViewHolder(View view) {
            super(view);
            dessertName = (TextView) view.findViewById(R.id.dessert_name);
            numberOfSteps = (TextView) view.findViewById(R.id.number_of_steps);
            numberOfIngredients = (TextView) view.findViewById(R.id.number_of_ingredients);
            numberOfServings = (TextView) view.findViewById(R.id.number_of_servings);
            thumbnail = (ImageView) view.findViewById(R.id.recipe_image);
        }
    }

    public RecipeCardAdapter(Context mContext, List<Recipe> recipeList) {
        this.mContext = mContext;
        this.recipeList = recipeList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.dessertName.setText(recipe.getDessertName());
        holder.numberOfSteps.setText(recipe.getNumberOfSteps());
        holder.numberOfIngredients.setText(recipe.getNumberOfIngredients());
        holder.numberOfServings.setText(recipe.getNumberOfServings());

        Glide.with(mContext).load(recipe.getThumbnail()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }


}

