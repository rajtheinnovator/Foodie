package com.enpassio.foodie.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.enpassio.foodie.R;
import com.enpassio.foodie.fragments.ItemDetailsFragment;

import static com.enpassio.foodie.util.Constants.ITEMS_DETAILS_BUNDLE_KEY;


public class ItemDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Set the content of the activity to use the activity_main.xml layout file */
        setContentView(R.layout.activity_item_detail);
        Bundle bundle = getIntent().getBundleExtra(ITEMS_DETAILS_BUNDLE_KEY);

        if (savedInstanceState == null) {
            ItemDetailsFragment itemDetailsFragment = new ItemDetailsFragment();
            itemDetailsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.containerItemDetails, itemDetailsFragment)
                    .commit();
        }
    }
}
