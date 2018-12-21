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

import com.bumptech.glide.Glide;

import java.util.List;

public class ResAdapter1 extends RecyclerView.Adapter<ResAdapter1.MyViewHolder> {

    private Context context;
    private List<Res1> list;
    private String auth_token;

    public ResAdapter1(Context context, List<Res1> list, String auth_token){
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

        final Res1 res = list.get(i);

        viewHolder.res_name.setText(res.getRes_name_c());
        viewHolder.res_cat.setText(res.getRes_cat_c());


        String open_at = context.getString(R.string.open_at,res.getRes_open_c());
//        viewHolder.res_loc.setText(res.getRes_loc());
        viewHolder.res_open.setText(open_at);
//        viewHolder.res_close.setText(res.getRes_close());

        Glide.with(context).load(res.getRes_image_c()).into(viewHolder.res_image);


    }

    @Override
    public int getItemCount() {

        return list.size();
    }





    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView res_name,res_cat,res_open;
        List<Res1> list;
        Context context;
        ImageView res_image;




        public MyViewHolder(@NonNull View itemView, List<Res1> list,Context context) {
            super(itemView);

            this.context = context;
            this.list = list;
            itemView.setOnClickListener(this);

            res_name = (TextView)itemView.findViewById(R.id.res_name);
            res_cat = (TextView)itemView.findViewById(R.id.res_cat);
//            res_loc = (TextView)itemView.findViewById(R.id.res_loc);
            res_open = (TextView)itemView.findViewById(R.id.res_open);
//            res_close = (TextView)itemView.findViewById(R.id.res_close);
            res_image = (ImageView)itemView.findViewById(R.id.res_image);



        }

        @Override
        public void onClick(View view) {

            int pos = getAdapterPosition();
            Res1 res = this.list.get(pos);

            Intent intent = new Intent(this.context,ScrollingActivity.class);
            intent.putExtra("res_id",res.getRes_id_c());

            intent.putExtra("res_name",res.getRes_name_c());
            intent.putExtra("auth_token",auth_token);
            intent.putExtra("res_image",res.getRes_image_c());
            this.context.startActivity(intent);

        }
    }
}
