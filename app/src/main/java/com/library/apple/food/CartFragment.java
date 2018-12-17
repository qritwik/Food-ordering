package com.library.apple.food;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private List<CartItem> cartList;
    TextView btn_coupon;

    Button btn_empty;

    boolean status = true;

    TextView cart_tot_discount;

    EditText et_coupon;
    Button btn_proceed;

    String auth_token;
    private String cart_url = "http://www.hungermela.com/api/v1/cart/";

    TextView cart_res_name,cart_item_price,cart_tot_price,cart_del_price;

    View myView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_cart,container,false);



        cart_res_name = (TextView)myView.findViewById(R.id.cart_res_name);
        cart_item_price = (TextView)myView.findViewById(R.id.cart_item_price);
        cart_tot_price = (TextView)myView.findViewById(R.id.cart_tot_price);
        cart_del_price = (TextView)myView.findViewById(R.id.cart_del_price);

        cart_tot_discount = (TextView)myView.findViewById(R.id.cart_tot_discount);

        btn_coupon = (TextView) myView.findViewById(R.id.btn_coupon);

        et_coupon = (EditText)myView.findViewById(R.id.et_coupon);

        btn_empty = (Button)myView.findViewById(R.id.btn_empty);



        btn_proceed = (Button)myView.findViewById(R.id.btn_proceed);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginToken",Context.MODE_PRIVATE);
        auth_token = sharedPreferences.getString("token","error");


        cartList = new ArrayList<>();

        recyclerView = (RecyclerView)myView.findViewById(R.id.rv_cart);
        adapter = new CartAdapter(getActivity(),cartList,auth_token);
        recyclerView.setAdapter(adapter);

        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Order Placed",Toast.LENGTH_LONG).show();
            }
        });



        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        getData();
        getData1();


        btn_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                empty_cart(view);
            }
        });



            btn_coupon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(status == true){



                        String coup = et_coupon.getText().toString().trim();

                        if(!(coup.isEmpty())){

                            applyCoupon(coup);
                            et_coupon.setEnabled(false);
                            btn_coupon.setText("REMOVE");
                            btn_coupon.setTextColor(Color.RED);
                            status = false;
                            Toast.makeText(getActivity(),"Coupon Applied",Toast.LENGTH_LONG).show();


                        }
                        else{
                            Toast.makeText(getActivity(),"Apply coupon first",Toast.LENGTH_LONG).show();
                        }




                    }
                    else if(status == false){
                        et_coupon.setEnabled(true);
                        btn_coupon.setText("APPLY");
                        status = true;

                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        Fragment myFragment = new CartFragment();

                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();




                    }













                }
            });






        return myView;
    }


    private void applyCoupon(final String coupoun){

        String coupon_url = "http://www.hungermela.com/api/v1/final-price/";

        RequestQueue queue2 = Volley.newRequestQueue(getActivity());

        StringRequest postRequest = new StringRequest(Request.Method.POST, coupon_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            JSONObject js = new JSONObject(response);

                            String cart_total_without_dis = js.getString("total_cart_without_discount");
                            String price_item_tot1 = getActivity().getString(R.string.price,cart_total_without_dis);
                            cart_tot_price.setText(price_item_tot1);

                            String del_charge  = js.getString("delivery_charge");
                            String del_charge1 = getActivity().getString(R.string.price,del_charge);
                            cart_del_price.setText(del_charge1);

                            String discount_price = js.getString("discount_price");
                            String discount_price1 = getActivity().getString(R.string.dis_price,discount_price);
                            cart_tot_discount.setText(discount_price1);

                            String final_price = js.getString("final_price");
                            String final_price1 = getActivity().getString(R.string.proceed_to_pay,final_price);
                            btn_proceed.setText(final_price1);

                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("count",Context.MODE_PRIVATE);
                            String name = sharedPreferences.getString("count1","0");

                            String sub_title = getActivity().getString(R.string.price1,name,final_price);
                            cart_item_price.setText(sub_title);









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
                    }
                }
        ) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                        params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Token "+auth_token);
                return params;
            }


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("code", coupoun);
                return params;
            }
        };
        queue2.add(postRequest);



    }


    private void getData1(){

        RequestQueue queue1 = Volley.newRequestQueue(getActivity());

        String url_final = "http://www.hungermela.com/api/v1/final-price/";
        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url_final, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String price_item_tot = response.getString("total_cart");
                    String del_charge  = response.getString("delivery_charge");
                    String final_price = response.getString("final_price");

                    String price_item_tot1 = getActivity().getString(R.string.price,price_item_tot);
                    cart_tot_price.setText(price_item_tot1);

                    String del_charge1 = getActivity().getString(R.string.price,del_charge);
                    cart_del_price.setText(del_charge1);

                    String final_price1 = getActivity().getString(R.string.proceed_to_pay,final_price);
                    btn_proceed.setText(final_price1);


                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("count",Context.MODE_PRIVATE);
                    String name = sharedPreferences.getString("count1","0");

                    String sub_title = getActivity().getString(R.string.price1,name,final_price);
                    cart_item_price.setText(sub_title);




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                        params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Token "+auth_token);
                return params;
            }

        };

        queue1.add(jsonObjectRequest1);



    }


    private void empty_cart(final View view){

        String url_empty = "http://www.hungermela.com/api/v1/empty-cart/";
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest postRequest = new StringRequest(Request.Method.POST, url_empty,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        getData();
                        getData1();

                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        Fragment myFragment = new CartFragment();

                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                        params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Token "+auth_token);
                return params;
            }


        };
        queue.add(postRequest);
    }






    private void getData() {

        RequestQueue queue = Volley.newRequestQueue(getActivity());



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, cart_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    String count = response.getString("count");
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("count", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("count1",count);
                    editor.commit();

                    String total = response.getString("total");



                    JSONArray items = response.getJSONArray("items");

                    for(int i=0; i < response.length(); i++){
                        JSONObject jsonObject = items.getJSONObject(i);



                        CartItem cartItem = new CartItem();

                        cartItem.setId_id(jsonObject.getString("id"));

                       JSONObject item = jsonObject.getJSONObject("item");

                        cartItem.setCart_item_id(item.getString("id"));

                       cartItem.setVegnon_cart(item.getString("veg"));
                       cartItem.setTitle_cart(item.getString("name"));
                       cartItem.setNo_cart(jsonObject.getString("quantity"));
                       cartItem.setPrice_cart(jsonObject.getString("price"));


                       cartList.add(cartItem);






                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();


            }
        }){


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                        params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Token "+auth_token);
                return params;
            }

        };


        queue.add(jsonObjectRequest);





    }


}
