package com.enpassio.foodie;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.enpassio.foodie.model.Ingredient;
import com.enpassio.foodie.model.RecepieList;
import com.enpassio.foodie.model.Step;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* referenced from the
 * @link: http://www.journaldev.com/13629/okhttp-android-example-tutorial
 */
public class MainActivity extends AppCompatActivity {

    public String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    TextView txtString;
    String myresponse;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*referenced from the @link:
        * https://stackoverflow.com/a/33427922/5770629
        */
        mHandler = new Handler(Looper.getMainLooper());
        txtString = (TextView) findViewById(R.id.text);

        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();

                myresponse = "";

                List<Ingredient> ingredient;
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                List<RecepieList> posts = new ArrayList<RecepieList>();
                //Instruct GSON to parse as a Post array (which we convert into a list)
                posts = Arrays.asList(gson.fromJson(myResponse, RecepieList[].class));
                for (RecepieList recepieList : posts) {
                    myresponse += recepieList.getName() + "...";
                    ingredient = recepieList.getIngredients();
                    for (Ingredient ingredient1 : ingredient) {
                        myresponse += ingredient1.getIngredient() + ".....";
                    }
                    for (Step step:recepieList.getSteps()){
                        myresponse+=step.getDescription();
                    }
                    myresponse+="......."+"\n";
                }


                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        txtString.setText(myresponse);
                    }
                });


            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("my_tag", "onFailureCalled inside MAinActivity");

                call.request();
            }
        });

    }

}