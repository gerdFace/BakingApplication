package com.example.android.bakingapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.android.bakingapplication.DetailListFragment;
import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.model.Step;

import java.util.List;

public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.DetailListViewHolder> implements View.OnClickListener {

    private static final String TAG = DetailListAdapter.class.getSimpleName();

    private final DetailListFragment.DetailItemCallbacks callback;

    private List<Step> detailList;

//    private String ingredientButtonText = "Ingredients";
    private String detailButtonText;

    public DetailListAdapter(List<Step> detailList, DetailListFragment.DetailItemCallbacks callback) {
        this.detailList = detailList;
        this.callback = callback;
    }

    @Override
    public DetailListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail, parent, false);
        return new DetailListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DetailListViewHolder holder, int position) {
        detailButtonText = detailList.get(position).getShortDescription();
//        TODO add ingredients button to recipe list
        Log.d(TAG, "DetailList onBindViewHolder: " + detailList.get(position));
//        holder.ingredientButton.setText(ingredientButtonText);
//        holder.ingredientButton.setOnClickListener(this);
        holder.detailButton.setText(detailButtonText);
        holder.detailButton.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    @Override
    public void onClick(View v) {
        // Check validity of the callback
        if (callback != null) {
            switch (v.getId()) {
//                case R.id.ingredient_button:
//                    callback.onRecipeDetailButtonClicked(ingredientButtonText);
//                    break;
                case R.id.detail_button:
                    callback.onRecipeDetailButtonClicked(detailButtonText);
                    break;
            }
        }
    }

    public class DetailListViewHolder extends RecyclerView.ViewHolder{
        public Button detailButton;
//        public Button ingredientButton;

        public DetailListViewHolder(View itemView) {
            super(itemView);
//            ingredientButton = (Button) itemView.findViewById(R.id.ingredient_button);
            detailButton = (Button) itemView.findViewById(R.id.detail_button);
        }
    }
}