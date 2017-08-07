package com.example.android.bakingapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.android.bakingapplication.view.fragment.DetailListFragment;
import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.model.Step;

import java.util.List;

public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.DetailListViewHolder> {

    private static final String TAG = DetailListAdapter.class.getSimpleName();

    private DetailListFragment.DetailItemCallbacks callback;

    private List<Step> stepsList;

    public DetailListAdapter(DetailListFragment.DetailItemCallbacks callback) {
        this.callback = callback;
    }

    public void updateDetailListAdapter(List<Step> steps) {
        this.stepsList = steps;
        notifyDataSetChanged();
    }

    @Override
    public DetailListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail, parent, false);
        return new DetailListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DetailListViewHolder holder, int position) {
        final String detailButtonText = stepsList.get(position).getShortDescription();
        Log.d(TAG, "DetailList short description: " + detailButtonText);
        Log.d(TAG, "DetailList onBindViewHolder: " + stepsList.get(position));
        holder.detailButton.setText(detailButtonText);
        holder.detailButton.setOnClickListener(v -> {
        // Check validity of callback
            if (callback != null) {
                callback.onRecipeDetailButtonClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    public class DetailListViewHolder extends RecyclerView.ViewHolder{
        public Button detailButton;

        public DetailListViewHolder(View itemView) {
            super(itemView);
            detailButton = (Button) itemView.findViewById(R.id.detail_button);
        }
    }
}