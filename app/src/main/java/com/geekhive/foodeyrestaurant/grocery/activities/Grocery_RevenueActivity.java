package com.geekhive.foodeyrestaurant.grocery.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.grocery.adapter.Grocery_RevenueListAdapter;

import butterknife.BindView;

public class Grocery_RevenueActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.vI_aac_revenue_back)
    ImageView vI_aac_revenue_back;
    @BindView(R.id.revenueItems)
    RecyclerView revenueItems;

    LinearLayoutManager linearLayoutManager;
    Grocery_RevenueListAdapter revenueListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocery_activity_revenue);
//        ButterKnife.bind(this);

        vI_aac_revenue_back = findViewById(R.id.vI_aac_revenue_back);
        vI_aac_revenue_back.setOnClickListener(this);

        revenueItems = findViewById(R.id.revenueItems);
        revenueListAdapter = new Grocery_RevenueListAdapter(this);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        revenueItems.setLayoutManager(linearLayoutManager);
        revenueItems.setAdapter(revenueListAdapter);

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
