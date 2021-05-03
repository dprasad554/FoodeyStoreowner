package com.geekhive.foodeyrestaurant.cakes.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.cakes.adapter.CakeOrdersAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CakeOrdersActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.vI_aac_revenue_back)
    ImageView vI_aac_revenue_back;
    @BindView(R.id.revenueItems)
    RecyclerView revenueItems;

    LinearLayoutManager linearLayoutManager;
    CakeOrdersAdapter ordersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cake_activity_orders);
        ButterKnife.bind(this);

//        ordersAdapter = new OrdersAdapter(this, ordersAdapter);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        revenueItems.setLayoutManager(linearLayoutManager);
        revenueItems.setAdapter(ordersAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vI_aac_revenue_back:
                finish();
                break;
        }
    }
}
