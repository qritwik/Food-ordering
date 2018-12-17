package com.library.apple.food;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private Context context;
    private List<Item> list;
    private String auth_token;

    public ItemAdapter(Context context, List<Item> list, String auth_token) {
        this.context = context;
        this.list = list;
        this.auth_token = auth_token;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {

        final Item item = list.get(i);

        viewHolder.item_name.setText(item.getItem_name());
        viewHolder.item_cuisine_name.setText(item.getItem_cuisine_name());

        String price1 = context.getString(R.string.price,item.getItem_price());

        viewHolder.item_price.setText(price1);

        String veg = item.getItem_veg();
        if (veg.equalsIgnoreCase("false")) {
            viewHolder.item_veg.setImageResource(R.drawable.non_veg);
        }

        Glide.with(context).load(item.getItem_picture()).into(viewHolder.item_picture);

        viewHolder.item_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String pk = item.getItem_id();


                RequestQueue queue = Volley.newRequestQueue(context);


                String url = "http://www.hungermela.com/api/v1/add-item/";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("Response", response);
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
                        params.put("pk", pk);
                        params.put("quantity","1");

                        return params;
                    }
                };
                queue.add(postRequest);


                final Snackbar snackBar = Snackbar.make(view,item.getItem_name()+" added to cart" , Snackbar.LENGTH_INDEFINITE);

                snackBar.setAction("GO TO CART", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        Fragment myFragment = new CartFragment();

                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();





                    }
                });
                snackBar.show();




//                Snackbar.make(view, item.getItem_name()+" added to cart" + pk, Snackbar.LENGTH_INDEFINITE)
//                        .setAction("CART", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//
//
//                           }
//                        }).setActionTextColor(Color.MAGENTA).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView item_picture, item_veg;
        TextView item_name, item_cuisine_name, item_price;
        FloatingActionButton item_fab;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item_picture = (ImageView) itemView.findViewById(R.id.item_picture);
            item_veg = (ImageView) itemView.findViewById(R.id.item_veg);
            item_name = (TextView) itemView.findViewById(R.id.item_name);
            item_cuisine_name = (TextView) itemView.findViewById(R.id.item_cuisine_name);
            item_price = (TextView) itemView.findViewById(R.id.item_price);

            item_fab = (FloatingActionButton) itemView.findViewById(R.id.item_fab);


        }

        @Override
        public void onClick(View view) {


        }
    }
}


