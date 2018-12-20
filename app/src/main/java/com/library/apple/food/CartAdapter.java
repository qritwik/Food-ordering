package com.library.apple.food;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private Context context;
    private List<CartItem> list;
    private String auth_token;

    public CartAdapter(Context context, List<CartItem> list, String auth_token){
        this.context = context;
        this.list = list;
        this.auth_token = auth_token;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_cart,viewGroup,false);
        CartAdapter.MyViewHolder myViewHolder = new CartAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {

        final CartItem item = list.get(i);

        viewHolder.title_cart.setText(item.getTitle_cart());
        viewHolder.no_cart.setText(item.getNo_cart());

        String price1 = context.getString(R.string.price,item.getPrice_cart());

        viewHolder.price_cart.setText(price1);


        String veg = item.getVegnon_cart();
        if (veg.equalsIgnoreCase("false")) {
            viewHolder.vegnon_cart.setImageResource(R.drawable.non_veg);

        }


        //-----------------------------------------------------------------------------


        //cart plus button
        viewHolder.btn_plus_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                String url_plus = "https://www.hungermela.com/api/v1/change-quantity/";
                final String item_id = item.getCart_item_id();


                RequestQueue queue = Volley.newRequestQueue(context);

                StringRequest postRequest = new StringRequest(Request.Method.POST, url_plus,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("Response", response);



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


                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("pk", item_id);

                        int quantity = Integer.parseInt(item.getNo_cart());
                        String quan = String.valueOf(quantity+1);
                        params.put("quantity",quan);

                        return params;
                    }
                };
                queue.add(postRequest);




            }
        });
        //cart plus ends here


        //-----------------------------------------------------------------------------


        //cart minus button
        viewHolder.btn_minus_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                int quantity = Integer.parseInt(item.getNo_cart());



                if(quantity==1){



                    String url_minus = "https://www.hungermela.com/api/v1/delete-item/";
                    final String item_id = item.getCart_item_id();



                    RequestQueue queue = Volley.newRequestQueue(context);

                    StringRequest postRequest = new StringRequest(Request.Method.POST, url_minus,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // response
                                    Log.d("Response", response);



                                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                Fragment myFragment = new CartFragment();

                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();




                                    notifyDataSetChanged();

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
                            params.put("pk", item.getId_id());



                            return params;
                        }
                    };
                    queue.add(postRequest);





                }



                else if(quantity > 1){


                    String url_minus = "https://www.hungermela.com/api/v1/change-quantity/";
                    final String item_id = item.getCart_item_id();



                    RequestQueue queue = Volley.newRequestQueue(context);

                    StringRequest postRequest = new StringRequest(Request.Method.POST, url_minus,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // response
                                    Log.d("Response", response);

                                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                Fragment myFragment = new CartFragment();

                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();




                                    notifyDataSetChanged();

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
                            params.put("pk", item_id);

                            int quantity = Integer.parseInt(item.getNo_cart());
                            String quan = String.valueOf(quantity-1);
                            params.put("quantity",quan);


                            return params;
                        }
                    };
                    queue.add(postRequest);



                }





            }
        });
        //cart minus ends here


        //-----------------------------------------------------------------------------




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title_cart, no_cart, price_cart;
        ImageView vegnon_cart;
        TextView btn_minus_cart, btn_plus_cart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            vegnon_cart = (ImageView)itemView.findViewById(R.id.vegnon_cart);
            title_cart = (TextView)itemView.findViewById(R.id.title_cart);
            no_cart = (TextView)itemView.findViewById(R.id.no_cart);
            price_cart = (TextView)itemView.findViewById(R.id.price_cart);

            //Button
            btn_minus_cart = (TextView)itemView.findViewById(R.id.btn_minus_cart);
            btn_plus_cart = (TextView)itemView.findViewById(R.id.btn_plus_cart);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
