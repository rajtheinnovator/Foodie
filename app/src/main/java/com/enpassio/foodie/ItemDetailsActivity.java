package com.enpassio.foodie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.enpassio.foodie.model.Ingredient;
import com.enpassio.foodie.model.Step;

import java.util.List;

/**
 * Created by ABHISHEK RAJ on 8/14/2017.
 */

public class ItemDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Set the content of the activity to use the activity_main.xml layout file */
        setContentView(R.layout.activity_item_detail);
        TextView textView = (TextView) findViewById(R.id.text2);
        Bundle bundle = getIntent().getExtras();
        String myString = "";
        myString += String.valueOf(bundle.get("myServings"));
        List<Ingredient> ingredients = bundle.getParcelableArrayList("myIngredients");
        List<Step> steps = bundle.getParcelableArrayList("mySteps");
        for (Ingredient ingredient : ingredients) {
            myString += ingredient.getMeasure();
        }
        for (Step step : steps) {
            myString += step.getDescription();
        }


        textView.setText(myString);
    }
}
