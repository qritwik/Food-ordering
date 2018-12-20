package com.library.apple.food;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class previousOrderAdapter extends RecyclerView.Adapter<previousOrderAdapter.MyViewHolder> {

    private Context context;
    private List<Order> list;

    public previousOrderAdapter(Context context,List<Order> list){

        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_orders,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(view,list,context);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {

        final Order order = list.get(i);

        viewHolder.res_name1.setText(order.getOrder_res_name());

        String pri = context.getString(R.string.price,order.getOrder_item_price());

        viewHolder.item_price1.setText(pri);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView res_name1,item_price1;
        List<Order> list;
        Context context;


        public MyViewHolder(@NonNull View itemView, List<Order> list,Context context) {
            super(itemView);

            this.context = context;
            this.list = list;

            itemView.setOnClickListener(this);


            res_name1 = (TextView)itemView.findViewById(R.id.res_name);
            item_price1 = (TextView)itemView.findViewById(R.id.item_price);

        }

        @Override
        public void onClick(View view) {

            int pos = getAdapterPosition();
            Order order = this.list.get(pos);

            Intent intent = new Intent(context,orderSummary.class);
            intent.putExtra("order_id",order.getOrder_order_id());
            intent.putExtra("address",order.getOrder_home_name());
            intent.putExtra("resturant",order.getOrder_res_name());
            intent.putExtra("price",order.getOrder_item_price());
            this.context.startActivity(intent);

        }
    }
}
