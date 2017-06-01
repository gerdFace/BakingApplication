package com.example.android.bakingapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    /* REVIEW: FEATHER IN YOUR CAP COMMENT--
     *   Unless you're actually doing something with the View/Resource/Asset/whatever that you specify by ID in the annotation,
     *   you don't even need lines #17-30. Not hurting anything and the next story will probably utilize them.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
