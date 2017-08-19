package com.enpassio.foodie;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.enpassio.foodie.activities.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

        // Create an Intent to launch MainActivity when clicked
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        int key_vaule = sharedPref.getInt(String.valueOf(appWidgetId), 1);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(String.valueOf(appWidgetId));
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        switch (key_vaule) {
            case 1:
                views.setTextViewText(R.id.recipeName, "Nutella Pie");
                views.setImageViewResource(R.id.recipeImage, R.drawable.nutellapie);
                break;
            case 2:
                views.setImageViewResource(R.id.recipeImage, R.drawable.brownie);
                views.setTextViewText(R.id.recipeName, "Brownie");
                break;
            case 3:
                views.setImageViewResource(R.id.recipeImage, R.drawable.yellowcake);
                views.setTextViewText(R.id.recipeName, "Yellow Cake");
                break;
            case 4:
                views.setImageViewResource(R.id.recipeImage, R.drawable.cheesecake);
                views.setTextViewText(R.id.recipeName, "Cheesecake");
                break;
            default:
                break;

        }

        // Widgets allow click handlers to only launch pending intents
        views.setOnClickPendingIntent(R.id.recipeName, pendingIntent);
        views.setOnClickPendingIntent(R.id.recipeImage, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

