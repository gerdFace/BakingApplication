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
import com.example.android.bakingapplication.model.FakeRecipeData;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailListFragment extends Fragment {

    public static final String TAG = DetailListActivity.class.getClass().getSimpleName();
	private static final String NAME_OF_FOOD_ITEM_KEY = "name_of_food_item_key";
	
	private String nameOfFoodItem;
    private DetailItemCallbacks callbacks;
	private DetailListAdapter detailListAdapter;

    @BindView(R.id.rv_detail_list)
    RecyclerView rvDetailList;

    public DetailListFragment() {
    }

//    Interface that enables fragment to communicate with host activity
    public interface DetailItemCallbacks {
        void onRecipeDetailButtonClicked(int position);
    }
	
	
	public static DetailListFragment newInstance(String nameOfFoodItem) {
		Bundle args = new Bundle();
		args.putString(NAME_OF_FOOD_ITEM_KEY, nameOfFoodItem);
		
		DetailListFragment detailListFragment = new DetailListFragment();
		detailListFragment.setArguments(args);
		return detailListFragment;
	}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	
	    nameOfFoodItem = getArguments().getString(NAME_OF_FOOD_ITEM_KEY);
	
	    if (nameOfFoodItem != null) {
		    Log.d(TAG, "DetailListFragment onCreate: " + nameOfFoodItem);
	    }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_list, container, false);
        ButterKnife.bind(this, view);
	    
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);

        rvDetailList.setLayoutManager(layoutManager);
	    
	    updateUI();

        return view;
    }
	
	private void updateUI() {
		ArrayList<String> detailList = FakeRecipeData.get().getKRecipe(nameOfFoodItem).getDetailList();
		
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
	
	//	@Override
//	public void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//		outState.putParcelable(DetailListActivity.SAVED_RECIPE, recipe);
//	}
}


