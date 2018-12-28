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

import java.util.List;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.MyViewHolder> {

    private Context context;
    private List<Category> list;

    public categoryAdapter(Context context, List<Category> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_category,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(view, list, context);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {

        final Category category = list.get(i);

        viewHolder.cate_name.setText(category.getCate_name());

        String url = "https://www.hungermela.com"+category.getCate_picture();
        Glide.with(context).load(url).into(viewHolder.cate_picture);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cate_name;
        ImageView cate_picture;
        List<Category> list;
        Context context;

        public MyViewHolder(@NonNull View itemView, List<Category> list,Context context) {
            super(itemView);

            this.context = context;
            this.list = list;
            itemView.setOnClickListener(this);

            cate_name = (TextView)itemView.findViewById(R.id.cate_name);
            cate_picture = (ImageView)itemView.findViewById(R.id.cate_picture);


        }

        @Override
        public void onClick(View view) {

            int pos = getAdapterPosition();
            Category category = this.list.get(pos);

            Intent intent = new Intent(this.context,CategoryRes.class);
            intent.putExtra("cat_id",category.getCate_id());
            intent.putExtra("cate_name",category.getCate_name());
            intent.putExtra("cate_img",category.getCate_picture());
            this.context.startActivity(intent);

        }
    }
}
