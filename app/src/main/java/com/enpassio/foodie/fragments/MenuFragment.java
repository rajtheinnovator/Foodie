package com.enpassio.foodie.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enpassio.foodie.R;
import com.enpassio.foodie.adapters.MenuListAdapter;
import com.enpassio.foodie.model.RecepieList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.util.Log.v;

/**
 * Created by ABHISHEK RAJ on 8/13/2017.
 */

public class MenuFragment extends Fragment {

    static String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    RecyclerView mMenuRecyclerView;
    MenuListAdapter menuListAdapter;

    public MenuFragment() {
        //necessary empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* set that it has a menu */
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Inflate the layout for this fragment */
        View rootView = inflater.inflate(R.layout.fragment_menu_activity, container, false);
        mMenuRecyclerView = (RecyclerView) rootView.findViewById(R.id.menuListRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mMenuRecyclerView.setLayoutManager(layoutManager);

        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMenuRecyclerView.setAdapter(new MenuListAdapter(getContext(), new ArrayList<RecepieList>()));
        return rootView;

    }

    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myJSONResponseFromServer = response.body().string();

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                List<RecepieList> posts = new ArrayList<RecepieList>();
                //Instruct GSON to parse as a Post array (which we convert into a list)
                posts = Arrays.asList(gson.fromJson(loadJSONFromAsset(), RecepieList[].class));
                Log.v("my_tag", "ArrayList is: " + posts.toString());
                menuListAdapter.setMovieData((ArrayList<RecepieList>) posts);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                v("my_tag", "onFailure called with url " + call.request().url());
                v("my_tag", "exception is: " + e.toString());
                // final String myJSONResponseFromServer = response.body().string();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                List<RecepieList> posts;
                //Instruct GSON to parse as a Post array (which we convert into a list)
                posts = Arrays.asList(gson.fromJson(loadJSONFromAsset(), RecepieList[].class));
                Log.v("my_tag", "ArrayList is: " + posts.toString());
                Log.v("my_tag", "loadJSONFromAsset is :" + loadJSONFromAsset());


                menuListAdapter = new MenuListAdapter(getContext(), posts);
                menuListAdapter.setMovieData(posts);


            }
        });

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("jsondata.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
