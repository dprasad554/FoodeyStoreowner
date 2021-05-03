package com.geekhive.foodeyrestaurant.restaurant.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.restaurant.fragments.FixturesTabs;
import com.geekhive.foodeyrestaurant.restaurant.utils.Prefs;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuListActivity extends AppCompatActivity implements View.OnClickListener{

    public Typeface NIRMALAB;
    //String rest_id;
    @BindView(R.id.containerView)
    FrameLayout vRArdMenuList;
    @BindView(R.id.vI_aac_menu_back)
    ImageView vIAacMenuBack;
    @BindView(R.id.vT_aac_menu_header)
    TextView vTAacMenuHeader;

    private Fragment viewFragment;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        ButterKnife.bind(this);
        //rest_id = getIntent().getStringExtra("res_id");
        vIAacMenuBack.setOnClickListener(this);
        initializeFonts();
        setTypeFace();

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("res_id", Prefs.getUserId(this));
// set Fragmentclass Arguments
        FixturesTabs fragobj = new FixturesTabs();
        fragobj.setArguments(bundle);
        mFragmentTransaction.replace(R.id.containerView, fragobj).commit();
    }

    private void setTypeFace() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                vTAacMenuHeader.setTypeface(NIRMALAB);
            }
        };
        r.run();
    }


    private void initializeFonts() {
        this.NIRMALAB = Typeface.createFromAsset(getAssets(), "NIRMALAB.TTF");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vI_aac_menu_back:
                finish();
                break;
        }
    }
}
