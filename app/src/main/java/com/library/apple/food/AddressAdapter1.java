package com.library.apple.food;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

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

public class AddressAdapter1 extends RecyclerView.Adapter<AddressAdapter1.MyViewHolder> {

    private Context context;
    private List<Place> list;
    private String auth_token;

//    private int lastSelectedPosition = -1;


    public AddressAdapter1(Context context, List<Place> list, String auth_token){
        this.context = context;
        this.list = list;
        this.auth_token = auth_token;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_address,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(view, list, context);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, final int i) {

        final Place place = list.get(i);

        viewHolder.add_line_1.setText(place.getAdd_line_1());

        String phone = context.getString(R.string.contact,place.add_phone_number);
        viewHolder.add_phone_number.setText(phone);

//        boolean selected = place.getAdd_selected();
//        if(selected==false){
//
//            viewHolder.add_selected.setChecked(false);
//        }
//        else {
//            viewHolder.add_selected.setChecked(true);
//        }





    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        List<Place> list;
        Context context;

        TextView add_line_1,add_phone_number;
//        RadioButton add_selected;


        public MyViewHolder(@NonNull View itemView, List<Place> list,Context context) {
            super(itemView);

            this.context = context;
            this.list = list;
            itemView.setOnClickListener(this);

            add_line_1 = (TextView)itemView.findViewById(R.id.add_line_1);
            add_phone_number = (TextView)itemView.findViewById(R.id.add_phone_number);

//            add_selected = (RadioButton)itemView.findViewById(R.id.add_selected);
        }

        @Override
        public void onClick(View view) {


            int pos = getAdapterPosition();
            Place place = this.list.get(pos);


            final String address_id = place.getAdd_id();

            String url_sel = "https://www.hungermela.com/api/v1/address/"+address_id+"/address_select/";




            RequestQueue queue = Volley.newRequestQueue(context);

            StringRequest postRequest = new StringRequest(Request.Method.POST, url_sel,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);

//                                lastSelectedPosition = i;
//                                notifyDataSetChanged();



                            Intent intent = new Intent(context,MainActivity.class);
                            context.startActivity(intent);










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
                    params.put("id", address_id);

                    return params;
                }
            };
            queue.add(postRequest);

        }
    }
}
