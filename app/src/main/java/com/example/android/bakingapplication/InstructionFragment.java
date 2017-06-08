package com.example.android.bakingapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionFragment extends Fragment {

    private static final String TAG = InstructionFragment.class.getSimpleName();

    @BindView(R.id.short_step_description)
    TextView shortDescription;

    @BindView(R.id.long_step_description)
    TextView longDescription;

    public List<String> stepDescriptionList;

    public InstructionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instruction, container, false);
        ButterKnife.bind(this, view);

        shortDescription.setText(stepDescriptionList.get(0));

        Log.d(TAG, "InstructionFragment shortDescription: " + stepDescriptionList.get(0));

        longDescription.setText(stepDescriptionList.get(1));

        return view;
    }

    public void setStepDescriptionList(List<String> stepDescriptionList) {
        this.stepDescriptionList = stepDescriptionList;
    }
}
