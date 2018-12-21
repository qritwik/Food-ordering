package com.library.apple.food;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class CategoryRes extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.Adapter adapter09;
    RecyclerView.LayoutManager layoutManager09;
    private List<Res1> resList1;

    String auth_token;

    private ShimmerFrameLayout mShimmerViewContainer;

    String cate_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_res);

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.startShimmerAnimation();


        String cat_id = getIntent().getStringExtra("cat_id");

//
        TextView textView = (TextView)findViewById(R.id.cate_name1);
        textView.setText(getIntent().getStringExtra("cate_name"));

        ImageView imageView = (ImageView)findViewById(R.id.cate_img1);
        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("cate_img")).into(imageView);


        SharedPreferences sharedPreferences = getSharedPreferences("loginToken", Context.MODE_PRIVATE);
        auth_token = sharedPreferences.getString("token","error");



        resList1 = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.rv_cate_wise);
        adapter09 = new ResAdapter1(getApplicationContext(),resList1,auth_token);
        recyclerView.setAdapter(adapter09);

        layoutManager09 = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager09);

        recyclerView.setHasFixedSize(true);



        cate_url = "https://www.hungermela.com/api/v1/category/"+cat_id;

        getData();



    }



    private void getData() {


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                cate_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray restaurant = response.getJSONArray("restaurant");
                    for(int i = 0; i < restaurant.length(); i++){

                        JSONObject jsonObject = restaurant.getJSONObject(i);
                        final Res1 res = new Res1();

                        res.setRes_id_c(jsonObject.getString("id"));
                        res.setRes_name_c(jsonObject.getString("name"));
                        res.setRes_open_c(jsonObject.getString("open_time"));
                        res.setRes_close_c(jsonObject.getString("close_time"));
                        res.setRes_phone_number_c(jsonObject.getString("phone_number"));
                        res.setRes_line_1_c(jsonObject.getString("line_1"));
                        res.setRes_loc_c(jsonObject.getString("city"));
                        res.setRes_zip_code_c(jsonObject.getString("zip_code"));
                        res.setRes_chain_c(jsonObject.getString("chain"));
                        res.setRes_image_c(jsonObject.getString("image"));
                        res.setRes_city_c(jsonObject.getString("mainlocation"));
                        res.setRes_part_of_c(jsonObject.getString("part_of"));

                        String cat1 = jsonObject.getString("category");


                        //Json object request for category

                        String cat_url = "https://www.hungermela.com/api/v1/categories/"+cat1;


                        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                                cat_url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response1) {
                                Log.d(TAG, response1.toString());

                                try {

                                    res.setRes_cat_c(response1.getString("name"));

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.d(TAG, "Error: " + error.getMessage());
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                                30000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


                        MySingleton1.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);


                        //Ends here

                        resList1.add(res);




                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                mShimmerViewContainer.setVisibility(View.GONE);
                adapter09.notifyDataSetChanged();
                mShimmerViewContainer.stopShimmerAnimation();




            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(jsonObjReq);
    }

}
