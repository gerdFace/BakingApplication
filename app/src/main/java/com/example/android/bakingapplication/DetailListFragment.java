package com.example.android.bakingapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.bakingapplication.activity.DetailListActivity;
import com.example.android.bakingapplication.adapter.DetailListAdapter;
import com.example.android.bakingapplication.model.KRecipe;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailListFragment extends Fragment {

    public static final String TAG = DetailListActivity.class.getClass().getSimpleName();
	
	private static final String RECIPE_KEY = "recipe_key";
	
	private KRecipe recipe;

    private DetailItemCallbacks callbacks;

    @BindView(R.id.rv_detail_list)
    RecyclerView rvDetailList;

    public DetailListFragment() {
    }

//    Interface that enables fragment to communicate with host activity
    public interface DetailItemCallbacks {
        void onRecipeDetailButtonClicked(int position);
    }
	
	
	public static DetailListFragment newInstance(KRecipe recipe) {
		Bundle args = new Bundle();
		args.putParcelable(RECIPE_KEY, recipe);
		
		DetailListFragment detailListFragment = new DetailListFragment();
		detailListFragment.setArguments(args);
		return detailListFragment;
	}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	
	    recipe = getArguments().getParcelable(RECIPE_KEY);
	
	    if (recipe != null) {
		    Log.d(TAG, "DetailListFragment onCreate: " + recipe.getDessertName());
	    }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_list, container, false);
        ButterKnife.bind(this, view);

        DetailListAdapter detailListAdapter = new DetailListAdapter(recipe.getDetailList(), callbacks);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);

        rvDetailList.setLayoutManager(layoutManager);

        rvDetailList.setAdapter(detailListAdapter);

        return view;
    }

//    Ensure that host activity implements the callback interface
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callbacks = (DetailItemCallbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                                                 + " must implement OnImageClickListener");
        }
    }

//    Reset callback when fragment detaches from host activity
    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }
}


