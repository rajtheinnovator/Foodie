package com.enpassio.foodie.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enpassio.foodie.R;
import com.enpassio.foodie.adapters.MenuListAdapter;
import com.enpassio.foodie.model.RecepieList;
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

import static com.enpassio.foodie.util.Constants.RECIPE_ARRAY_LIST_KEY;
/* referenced from the
 * @link: http://www.journaldev.com/13629/okhttp-android-example-tutorial
 */

/**
 * Created by ABHISHEK RAJ on 8/13/2017.
 */

public class MenuFragment extends Fragment {

    static String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    RecyclerView mMenuRecyclerView;
    MenuListAdapter menuListAdapter;
    List<RecepieList> recepieLists;
    ArrayList<RecepieList> recepieListArrayList;
    Context mContext;
    private boolean mTwoPane;


    public MenuFragment() {
        //necessary empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getContext();
        /* set that it has a menu */
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Inflate the layout for this fragment */
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        if (savedInstanceState != null) {
            recepieListArrayList = savedInstanceState.getParcelableArrayList("recepieListArrayList");
        } else {
            recepieListArrayList = new ArrayList<>();
        }
        mMenuRecyclerView = rootView.findViewById(R.id.menuListRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mMenuRecyclerView.setLayoutManager(layoutManager);

        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }

        menuListAdapter = new MenuListAdapter(getContext(), recepieListArrayList);
        mMenuRecyclerView.setAdapter(menuListAdapter);

        if (rootView.findViewById(R.id.menuDetailFrame) != null) {
            mTwoPane = true;

        }
        return rootView;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPE_ARRAY_LIST_KEY, recepieListArrayList);
    }

    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                recepieLists = new ArrayList<RecepieList>();
                //Instruct GSON to parse as a Post array (which we convert into a list)
                recepieLists = Arrays.asList(gson.fromJson(response.body().toString(), RecepieList[].class));

                for (RecepieList recepieList : recepieLists) {
                    recepieListArrayList.add(recepieList);
                }
                //referenced from the @link: https://stackoverflow.com/a/14978267/5770629
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        menuListAdapter.setRecipeData(recepieLists);
                        menuListAdapter.setTwoPaneStatus(mTwoPane);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }
        });

    }


}
