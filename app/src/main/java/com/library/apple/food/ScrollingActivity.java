package com.library.apple.food;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private List<Item> itemList;
    private String item_url = "https://www.hungermela.com/api/v1/restaurants/";
    String res_id;
    int flag=0;
    String auth_token;

    ImageView image_res;

    private ShimmerFrameLayout mShimmerViewContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.fab_cart1);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

//        image_res = (ImageView)findViewById(R.id.image_res1);


        mShimmerViewContainer = findViewById(R.id.shimmer_view_container123);

        mShimmerViewContainer.startShimmerAnimation();

        auth_token = getIntent().getStringExtra("auth_token");

//        View decorView = getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);

        TextView explore_rest_name = (TextView)findViewById(R.id.explore_rest_name);

        res_id = getIntent().getStringExtra("res_id");

        String res_name = getIntent().getStringExtra("res_name");

        explore_rest_name.setText(res_name);
//        String res_image = getIntent().getStringExtra("res_image");
//        Glide.with(getApplicationContext()).load(res_image).into(image_res);


        itemList = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.rv_res);
        adapter = new ItemAdapter(getApplicationContext(),itemList,auth_token);
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);




        getData();
    }



    private void getData() {


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(item_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);


                        JSONArray item_array = jsonObject.getJSONArray("items");
                        for(int j = 0; j < item_array.length(); j++){
                            JSONObject jsonObject1 = item_array.getJSONObject(i);
                            String items_id = jsonObject1.getString("restaurant");
                            if(items_id.equalsIgnoreCase(res_id)){
                                flag=1;
                                break;
                            }
                        }

                        if(flag==1){

                            JSONArray item_array1 = jsonObject.getJSONArray("items");
                            for(int j = 0; j < item_array1.length(); j++){
                                JSONObject jsonObject2 = item_array.getJSONObject(j);

                                Item item1 = new Item();

                                item1.setItem_name(jsonObject2.getString("name"));
                                item1.setItem_id(jsonObject2.getString("id"));
                                item1.setItem_price(jsonObject2.getString("price"));
                                item1.setItem_veg(jsonObject2.getString("veg"));
                                item1.setItem_picture(jsonObject2.getString("picture"));
                                item1.setItem_restaurant(jsonObject2.getString("restaurant"));

                                JSONObject cusines = jsonObject2.getJSONObject("cuisine");
                                item1.setItem_cuisine_name(cusines.getString("name"));

                                itemList.add(item1);


                            }


                        }




                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }

                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());

                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();


            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);

    }
}
