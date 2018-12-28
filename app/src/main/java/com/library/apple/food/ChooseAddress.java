package com.library.apple.food;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseAddress extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter1;
    RecyclerView.LayoutManager layoutManager1;
    private List<Place> placesList;

    String auth_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);

        getAddress();

        SharedPreferences sharedPreferences = getSharedPreferences("loginToken", Context.MODE_PRIVATE);
        auth_token = sharedPreferences.getString("token","error");


        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.add_address_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Add new address",Toast.LENGTH_LONG).show();
            }
        });


        placesList = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.rv_choose_address);
        adapter1 = new AddressAdapter1(getApplicationContext(),placesList,auth_token);
        recyclerView.setAdapter(adapter1);

        layoutManager1 = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager1);

        recyclerView.setHasFixedSize(true);


        getData();

    }

    private void getData(){



        String url_address = "https://www.hungermela.com/api/v1/address/";


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest postRequest = new StringRequest(Request.Method.GET, url_address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");

                            for(int i = 0; i < jsonArray.length(); i++){

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                final Place place = new Place();

                                place.setAdd_id(jsonObject1.getString("id"));
                                place.setAdd_line_1(jsonObject1.getString("line_1"));
                                place.setAdd_phone_number(jsonObject1.getString("phone_number"));
                                place.setAdd_phone_verified(jsonObject1.getString("phone_verified"));
                                place.setAdd_selected(jsonObject1.getBoolean("selected"));
                                place.setAdd_location(jsonObject1.getString("location"));




                                placesList.add(place);
                                adapter1.notifyDataSetChanged();





                            }


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
        ) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+auth_token);
                return params;
            }



        };
        queue.add(postRequest);








    }


    public void getAddress(){

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("loginToken",Context.MODE_PRIVATE);
        auth_token = sharedPreferences.getString("token","error");


        String url_address = "https://www.hungermela.com/api/v1/address/";


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest postRequest = new StringRequest(Request.Method.GET, url_address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                if(jsonObject1.getBoolean("selected")==true){

                                    String line_1 = jsonObject1.getString("line_1");
                                    String phone_number = jsonObject1.getString("phone_number");



                                    String final_address = line_1+", "+phone_number;

                                    TextView complete_address = (TextView)findViewById(R.id.tv2123);






                                    complete_address.setText(final_address);






                                }

                            }


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
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        ) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + auth_token);
                return params;
            }


        };


        queue.add(postRequest);


    }



}
