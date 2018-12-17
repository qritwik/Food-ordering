package com.library.apple.food;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ResAdapter extends RecyclerView.Adapter<ResAdapter.MyViewHolder> {

    private Context context;
    private List<Res> list;
    private String auth_token;

    public ResAdapter(Context context, List<Res> list, String auth_token){
        this.context = context;
        this.list = list;
        this.auth_token = auth_token;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_res,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(view, list, context);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {

        final Res res = list.get(i);

        viewHolder.res_name.setText(res.getRes_name());
        viewHolder.res_cat.setText(res.getRes_cat());
        viewHolder.res_loc.setText(res.getRes_loc());
        viewHolder.res_open.setText(res.getRes_open());
        viewHolder.res_close.setText(res.getRes_close());

        Glide.with(context).load(res.getRes_image()).into(viewHolder.res_image);


    }

    @Override
    public int getItemCount() {

        return list.size();
    }





    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView res_name,res_cat,res_loc,res_open,res_close;
        List<Res> list;
        Context context;
        ImageView res_image;




        public MyViewHolder(@NonNull View itemView, List<Res> list,Context context) {
            super(itemView);

            this.context = context;
            this.list = list;

            itemView.setOnClickListener(this);

            res_name = (TextView)itemView.findViewById(R.id.res_name);
            res_cat = (TextView)itemView.findViewById(R.id.res_cat);
            res_loc = (TextView)itemView.findViewById(R.id.res_loc);
            res_open = (TextView)itemView.findViewById(R.id.res_open);
            res_close = (TextView)itemView.findViewById(R.id.res_close);
            res_image = (ImageView)itemView.findViewById(R.id.res_image);



        }

        @Override
        public void onClick(View view) {

            int pos = getAdapterPosition();
            Res res = this.list.get(pos);

            Intent intent = new Intent(this.context,ScrollingActivity.class);
            intent.putExtra("res_id",res.getRes_id());
            intent.putExtra("res_name",res.getRes_name());
            intent.putExtra("auth_token",auth_token);
            intent.putExtra("res_image",res.getRes_image());
            this.context.startActivity(intent);

        }
    }
}
