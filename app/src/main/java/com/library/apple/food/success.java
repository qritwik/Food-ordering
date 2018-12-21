package com.library.apple.food;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class success extends AppCompatActivity {

    String auth_token;
    String success_order_id;
    String success_address;
    String success_res;
    String success_price;
    String user_name;

    TextView order_id,success_subtitle,res_name,home_name,price_item_tot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        order_id = (TextView)findViewById(R.id.order_id);
        success_subtitle = (TextView)findViewById(R.id.success_subtitle);
        res_name = (TextView)findViewById(R.id.res_name);
        home_name = (TextView)findViewById(R.id.home_name);
        price_item_tot = (TextView)findViewById(R.id.price_item_tot);


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("loginToken", Context.MODE_PRIVATE);
        auth_token = sharedPreferences.getString("token","error");

        getData();





    }



    private void getData(){

        String order_url = "https://www.hungermela.com/api/v1/order/";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                order_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
//                    String total_orders_count = response.getString("count");
                    JSONArray jsonArray = response.getJSONArray("results");

                    for(int i = (jsonArray.length()-1); i > (jsonArray.length()-2); i--){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        success_order_id = jsonObject.getString("id");
                        success_address = jsonObject.getString("complete_address");
                        success_res = jsonObject.getString("restaurant");
                        success_price = jsonObject.getString("total");
                        user_name = jsonObject.getString("user_name");



                        String order_id_new = getString(R.string.order_id,success_order_id);
                        order_id.setText(order_id_new);

                        String subtitle_new = getString(R.string.success_subtitle,success_price);
                        success_subtitle.setText(subtitle_new);

                        res_name.setText(success_res);

                        home_name.setText(success_address);

                        String price_new = getString(R.string.price,success_price);
                        price_item_tot.setText(price_new);

                    }


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
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+auth_token);
                return params;
            }



        };

        MySingleton1.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);





    }






//    private void getData(){
//
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//
//        String order_url = "https://www.hungermela.com/api/v1/order/";
//
//        StringRequest postRequest = new StringRequest(Request.Method.GET, order_url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // response
//                        Log.d("Response", response);
//
//                        try {
//                            JSONArray jsonArray = new JSONArray(response);
//                            for(int i = (jsonArray.length()-1); i > (jsonArray.length()-2); i--){
//
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                                success_order_id = jsonObject.getString("id");
//                                success_address = jsonObject.getString("complete_address");
//                                success_res = jsonObject.getString("restaurant");
//                                success_price = jsonObject.getString("total");
//                                user_name = jsonObject.getString("user_name");
//
//
//
//                                String order_id_new = getString(R.string.order_id,success_order_id);
//                                order_id.setText(order_id_new);
//
//                                String subtitle_new = getString(R.string.success_subtitle,success_price);
//                                success_subtitle.setText(subtitle_new);
//
//                                res_name.setText(success_res);
//
//                                home_name.setText(success_address);
//
//                                String price_new = getString(R.string.price,success_price);
//                                price_item_tot.setText(price_new);
//
//
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // error
//                        Log.d("Error.Response", error.toString());
//                    }
//                }
//        ) {
//
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
////                        params.put("Content-Type", "application/json; charset=UTF-8");
//                params.put("Authorization", "Token "+auth_token);
//                return params;
//            }
//
//        };
//        queue.add(postRequest);
//
//    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
