package com.example.android.bakingapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.bakingapplication.R;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    public static final String TAG = IngredientsAdapter.class.getSimpleName();

    public List<String> ingredientsList;

        public IngredientsAdapter(List<String> ingredientList) {
            this.ingredientsList = ingredientList;
        }

        @Override
        public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient, parent, false);
            return new IngredientsViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final IngredientsViewHolder holder, int position) {
            String ingredient = ingredientsList.get(position);
            Log.d(TAG, "Ingredient loaded from list: " + ingredientsList.get(position));
            holder.ingredients.setText(ingredient);
        }

        @Override
        public int getItemCount() {
            return ingredientsList.size();
        }

        public class IngredientsViewHolder extends RecyclerView.ViewHolder {
            public TextView ingredients;

            public IngredientsViewHolder(View view) {
                super(view);
                ingredients = (TextView) view.findViewById(R.id.textview_single_ingredient);
            }
        }
}
