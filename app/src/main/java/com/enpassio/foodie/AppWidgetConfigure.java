package com.enpassio.foodie;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class AppWidgetConfigure extends AppCompatActivity {

    int mAppWidgetId;
    RadioGroup containerRadioGroup;
    String selectedRadioStringKey;
    SharedPreferences sharedPref;
    int anInt;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_widget_configure);
        setResult(RESULT_CANCELED);
        containerRadioGroup = (RadioGroup) findViewById(R.id.containerRadioGroup);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            editor = sharedPref.edit();
            editor.remove(String.valueOf(mAppWidgetId));

        }
        Button radioGroup = (Button) findViewById(R.id.okButton);
        radioGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                widgetSelected();
            }
        });
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

    }

    public void widgetSelected() {
        int checkedRadioButton = containerRadioGroup.getCheckedRadioButtonId();


        switch (checkedRadioButton) {
            case R.id.radioNutell:
                anInt = 1;
            case R.id.brownie:
                anInt = 2;
            case R.id.yellowCake:
                anInt = 3;
            case R.id.cheesecake:
                anInt = 4;
        }
        editor.putInt(String.valueOf(mAppWidgetId), anInt);
        editor.apply();
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        editor.remove(String.valueOf(mAppWidgetId));
        finish();
    }

}
