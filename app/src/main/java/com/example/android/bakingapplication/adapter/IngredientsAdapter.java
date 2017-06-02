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

    private static final String TAG = IngredientsAdapter.class.getSimpleName();
    private List<String> ingredientList;

        public class IngredientsViewHolder extends RecyclerView.ViewHolder {
            public TextView ingredients;

            public IngredientsViewHolder(View view) {
                super(view);
                ingredients = (TextView) view.findViewById(R.id.textview_ingredients);
            }
        }

        public IngredientsAdapter(List<String> ingredientList) {
            this.ingredientList = ingredientList;
        }

        @Override
        public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_ingredients_list, parent, false);
            return new IngredientsViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final IngredientsViewHolder holder, int position) {
            String ingredient = ingredientList.get(position);
            Log.d(TAG, "Ingredient loaded from list: " + ingredient);
            Log.d(TAG, "ingredients include: " + ingredientList.get(1) + ingredientList.get(2));
            holder.ingredients.setText(ingredient);
        }

        @Override
        public int getItemCount() {
            return ingredientList.size();
        }
}
