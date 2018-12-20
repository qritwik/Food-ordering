package com.library.apple.food;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
//        TextView textView = (TextView)findViewById(R.id.cate_name);
//        textView.setText(cat_name);


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

        StringRequest postRequest = new StringRequest(Request.Method.GET, cate_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            Toast.makeText(getApplicationContext(),jsonArray.toString(),Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        );
        queue.add(postRequest);
    }

}
