package com.library.apple.food;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class orderSummary extends AppCompatActivity {

    TextView order_id,success_subtitle,res_name,home_name,price_item_tot;

    String success_order_id;
    String success_address;
    String success_res;
    String success_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        order_id = (TextView)findViewById(R.id.sum_order_id);
        success_subtitle = (TextView)findViewById(R.id.sum_success_subtitle);
        res_name = (TextView)findViewById(R.id.sum_res_name);
        home_name = (TextView)findViewById(R.id.sum_home_name);
        price_item_tot = (TextView)findViewById(R.id.sum_price_item_tot);

        success_order_id = getIntent().getStringExtra("order_id");
        success_address = getIntent().getStringExtra("address");
        success_res = getIntent().getStringExtra("resturant");
        success_price = getIntent().getStringExtra("price");

        String order_id_new = getString(R.string.order_id,success_order_id);
        order_id.setText(order_id_new);

        String subtitle_new = getString(R.string.success_subtitle_new,success_price);
        success_subtitle.setText(subtitle_new);

        res_name.setText(success_res);

        home_name.setText(success_address);

        String price_new = getString(R.string.price,success_price);
        price_item_tot.setText(price_new);



    }


}
