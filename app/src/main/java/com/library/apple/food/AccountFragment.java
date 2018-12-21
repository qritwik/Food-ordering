package com.library.apple.food;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class AccountFragment extends Fragment {

    TextView name1,emailPhon1,address1;
    View myView;

//    EditText et_name,et_email,et_phone,et_address;

    TextView btn_edit;
    TextView btn_address;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter12;
    RecyclerView.LayoutManager layoutManager12;


    private List<Order> orderList;

    String auth_token;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_account,container,false);

        name1 = (TextView)myView.findViewById(R.id.toolbar_account);
        emailPhon1 = (TextView)myView.findViewById(R.id.editde10);
        address1 = (TextView)myView.findViewById(R.id.editde8);


        orderList = new ArrayList<>();

        recyclerView = (RecyclerView)myView.findViewById(R.id.rv1234);
        adapter12 = new previousOrderAdapter(getActivity(),orderList);
        recyclerView.setAdapter(adapter12);

        layoutManager12 = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager12);

        recyclerView.setHasFixedSize(true);




        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginToken",Context.MODE_PRIVATE);
        auth_token = sharedPreferences.getString("token","error");

        String first_name = sharedPreferences.getString("first_name","First Name");
        String last_name = sharedPreferences.getString("last_name","Last Name");
        String email = sharedPreferences.getString("email","Email");

        String name2 = first_name+" "+last_name;
        name1.setText(name2);

        emailPhon1.setText(email);

        String address = "BMSIT BOYS HOSTEL, YELAHANKA";
        address1.setText(address);



//
//        et_name = (EditText)myView.findViewById(R.id.name);
//        et_email = (EditText)myView.findViewById(R.id.email);
//        et_phone = (EditText)myView.findViewById(R.id.phone);
//        et_address = (EditText)myView.findViewById(R.id.address);
//
//
//
//        et_name.setText(name2);
//        et_email.setText(email);








        Button logout = (Button)myView.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginToken", Context.MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();

                Intent intent = new Intent(getActivity(),login.class);
                startActivity(intent);

            }
        });


        getData();





        return myView;
    }


    private void getData(){

        String order_url = "https://www.hungermela.com/api/v1/order/";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                order_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    String total_orders_count = response.getString("count");
                    JSONArray jsonArray = response.getJSONArray("results");

                    for(int i = (jsonArray.length()-1); i >= 0; i--){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Order order = new Order();

                        order.setOrder_res_name(jsonObject.getString("restaurant"));
                        order.setOrder_item_price(jsonObject.getString("total"));
                        order.setOrder_home_name(jsonObject.getString("complete_address"));
                        order.setOrder_order_id(jsonObject.getString("id"));

                        orderList.add(order);


                    }

                    adapter12.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+auth_token);
                return params;
            }



        };

        MySingleton1.getInstance(getActivity()).addToRequestQueue(jsonObjReq);





    }


//    private void getData(){
//
//        String order_url = "https://www.hungermela.com/api/v1/order/";
//
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(order_url, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(final JSONArray response) {
//                for (int i = (response.length()-1); i >= 0; i--) {
//                    try {
//                        final JSONObject jsonObject = response.getJSONObject(i);
//
//                        Order order = new Order();
//
//                        order.setOrder_res_name(jsonObject.getString("restaurant"));
//                        order.setOrder_item_price(jsonObject.getString("total"));
//                        order.setOrder_home_name(jsonObject.getString("complete_address"));
//                        order.setOrder_order_id(jsonObject.getString("id"));
//
//                        orderList.add(order);
//
//
//                        } catch (JSONException e) {
//                        e.printStackTrace();
//
//                    }
//                }
//
//
//                adapter12.notifyDataSetChanged();
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("Volley", error.toString());
//                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
//
//            }
//        })
//
//
//
//        {
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "Token "+auth_token);
//                return params;
//            }
//
//
//
//        };
//
//
//
//
//
//        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);
//
//
//    }
}
