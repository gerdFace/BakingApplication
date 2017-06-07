package com.example.android.bakingapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.android.bakingapplication.DetailListFragment;
import com.example.android.bakingapplication.R;
import java.util.List;

public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.DetailListViewHolder>{

    private static final String TAG = DetailListAdapter.class.getSimpleName();

    private final DetailListFragment.DetailItemCallbacks callback;

    private List<String> detailList;

    public DetailListAdapter(List<String> detailList, DetailListFragment.DetailItemCallbacks callback) {
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
        final String detailButtonText = detailList.get(position);
        Log.d(TAG, "DetailList onBindViewHolder: " + detailList.get(position));
        holder.detailButton.setText(detailButtonText);
        holder.detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check validity of the callback
                if (callback != null) {
                    callback.onRecipeDetailButtonClicked(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    public class DetailListViewHolder extends RecyclerView.ViewHolder{
        public Button detailButton;

        public DetailListViewHolder(View itemView) {
            super(itemView);
            detailButton = (Button) itemView.findViewById(R.id.detail_button);
        }
    }
}