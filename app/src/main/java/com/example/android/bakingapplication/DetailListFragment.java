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
import android.widget.Button;

import com.example.android.bakingapplication.activity.BakingApplication;
import com.example.android.bakingapplication.activity.DetailListActivity;
import com.example.android.bakingapplication.adapter.DetailListAdapter;
import com.example.android.bakingapplication.model.RecipeData;
import com.example.android.bakingapplication.model.Step;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class DetailListFragment extends Fragment {

    private static final String TAG = DetailListActivity.class.getClass().getSimpleName();
    private static final String NAME_OF_FOOD_ITEM_KEY = "name_of_food_item_key";
    private static final String ID_OF_FOOD_ITEM_KEY = "id_of_food_item_key";

    private String nameOfFoodItem;
    private int foodID;
    private DetailItemCallbacks callbacks;
	private DetailListAdapter detailListAdapter;
    private List<Step> detailList;

    @Inject
    Realm realm;

    @BindView(R.id.rv_detail_list)
    RecyclerView rvDetailList;

    @BindView(R.id.ingredient_button)
    Button ingredientButton;

    public DetailListFragment() {
    }

//    Interface that enables fragment to communicate with host activity
    public interface DetailItemCallbacks {
        void onRecipeDetailButtonClicked(String nameOfStep);
    }
	
	
	public static DetailListFragment newInstance(String nameOfFoodItem, int foodID) {
		Bundle args = new Bundle();
		args.putString(NAME_OF_FOOD_ITEM_KEY, nameOfFoodItem);
		args.putInt(ID_OF_FOOD_ITEM_KEY, foodID);

		DetailListFragment detailListFragment = new DetailListFragment();
		detailListFragment.setArguments(args);
		return detailListFragment;
	}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	
	    nameOfFoodItem = getArguments().getString(NAME_OF_FOOD_ITEM_KEY, "");

        foodID = getArguments().getInt(ID_OF_FOOD_ITEM_KEY, 0);

        if (nameOfFoodItem != null) {
		    Log.d(TAG, "DetailListFragment onCreate: " + nameOfFoodItem);
	    }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_list, container, false);

        ((BakingApplication)getActivity().getApplication()).getAppComponent().inject(this);

        ButterKnife.bind(this, view);

        // TODO fix: recyclerview scrolls under static ingredientButton

        ingredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbacks.onRecipeDetailButtonClicked("Ingredients");
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);

        rvDetailList.setLayoutManager(layoutManager);

        // TODO save Instance, add else statement -- does statePager auto save instance?
        if (savedInstanceState == null) {
            detailList = realm.where(RecipeData.class)
                    .equalTo("id", foodID)
                    .findFirst().getSteps();
        }

        updateUI();

        return view;
    }

	private void updateUI() {
		if (detailListAdapter == null) {
			detailListAdapter = new DetailListAdapter(detailList, callbacks);
			
			rvDetailList.setAdapter(detailListAdapter);
		} else {
			detailListAdapter.notifyDataSetChanged();
		}
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
	
	@Override
	public void onResume() {
		super.onResume();
		updateUI();
	}
	
		@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(DetailListActivity.SAVED_RECIPE_NAME, nameOfFoodItem);
	}
}


