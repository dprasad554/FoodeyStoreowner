package com.geekhive.foodeyrestaurant.grocery.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.grocery.activities.Grocery_AddMenu_Activity;
import com.geekhive.foodeyrestaurant.grocery.activities.Grocery_LoginActivity;
import com.geekhive.foodeyrestaurant.grocery.activities.Grocery_NotificationActivity;
import com.geekhive.foodeyrestaurant.grocery.activities.Grocery_OrdersActivity;
import com.geekhive.foodeyrestaurant.grocery.activities.Grocery_RevenueActivity;
import com.geekhive.foodeyrestaurant.grocery.utils.BaseActivityInterface;
import com.geekhive.foodeyrestaurant.grocery.utils.ConnectionDetector;
import com.geekhive.foodeyrestaurant.grocery.utils.PrefsGrocery;

public class GroceryProfileFragment extends Fragment {

    View v;
    BaseActivityInterface setToolBarTitle;
    RecyclerView orderRecyclerView;
    ConnectionDetector mDetector;
    TextView openClosed;
    LinearLayout menuList;
    ImageView iv_ownerImage;
    LinearLayout linearLogOut;
    LinearLayout linear_addMenu;
    LinearLayout notifications;
    CardView dEarn, nOrders;
    public GroceryProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.grocery_profile_fragment, container, false);
        initialiseView(v, this.getActivity());

        setHasOptionsMenu(false);

        setToolBarTitle = (BaseActivityInterface) getActivity();
        setToolBarTitle.setToolBarTitle("Profile");

        return v;

    }

    void initialiseView(View v, final Context ctx) {
        mDetector = new ConnectionDetector(getActivity());
        openClosed = v.findViewById(R.id.openClosed);
        iv_ownerImage = v.findViewById(R.id.iv_ownerImage);
        linearLogOut = v.findViewById(R.id.linearLogOut);
        linear_addMenu = v.findViewById(R.id.linear_addMenu);
        notifications = v.findViewById(R.id.notifications);
        dEarn = v.findViewById(R.id.dEarn);
        nOrders = v.findViewById(R.id.nOrders);

        dEarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Grocery_RevenueActivity.class));
            }
        });

        nOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Grocery_OrdersActivity.class));
            }
        });

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Grocery_NotificationActivity.class));
            }
        });


        final TextView owner = (TextView)v.findViewById(R.id.tv_ownerName);
        Typeface myCustomFont = Typeface.createFromAsset(getActivity().getAssets(),"fonts/JosefinSans-Bold.ttf");
        owner.setTypeface(myCustomFont);

        final TextView designation = (TextView)v.findViewById(R.id.tv_ownerDesignation);
        Typeface myCustomFont1 = Typeface.createFromAsset(getActivity().getAssets(),"fonts/JosefinSans-Bold.ttf");
        designation.setTypeface(myCustomFont1);

        final TextView daily = (TextView)v.findViewById(R.id.tv_daily);
        Typeface myCustomFont2 = Typeface.createFromAsset(getActivity().getAssets(),"fonts/OpenSans-SemiBold.ttf");
        daily.setTypeface(myCustomFont2);

        final TextView order = (TextView)v.findViewById(R.id.tv_order);
        Typeface myCustomFont3 = Typeface.createFromAsset(getActivity().getAssets(),"fonts/OpenSans-SemiBold.ttf");
        order.setTypeface(myCustomFont3);

        final TextView addMenu = (TextView)v.findViewById(R.id.tv_addMenu);
        Typeface myCustomFont4 = Typeface.createFromAsset(getActivity().getAssets(),"fonts/OpenSans-SemiBold.ttf");
        addMenu.setTypeface(myCustomFont4);

        final TextView restaurant = (TextView)v.findViewById(R.id.tv_restaurant);
        Typeface myCustomFont6 = Typeface.createFromAsset(getActivity().getAssets(),"fonts/OpenSans-SemiBold.ttf");
        restaurant.setTypeface(myCustomFont6);

        final TextView logout = (TextView)v.findViewById(R.id.tv_Logout);
        Typeface myCustomFont7 = Typeface.createFromAsset(getActivity().getAssets(),"fonts/OpenSans-SemiBold.ttf");
        logout.setTypeface(myCustomFont7);

        LinearLayout linearLayout5 = v.findViewById(R.id.linearLayout5);
        linearLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });

        linear_addMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Grocery_AddMenu_Activity.class));
            }
        });

        if (!(PrefsGrocery.getUserNameGrocery(getActivity()).equals(""))){
            owner.setText(PrefsGrocery.getUserNameGrocery(getActivity()));
        }
        if (!(PrefsGrocery.getMobileNumberGrocery(getActivity()).equals(""))){
            designation.setText(PrefsGrocery.getMobileNumberGrocery(getActivity()));
        }

//        if(!(Prefs.getUserProfileUrl(getActivity()).equals(""))){
//            Picasso.get().load(WebServices.Foodey_Image_URL +
//                    Prefs.getUserProfileUrl(getActivity())).into(iv_ownerImage);
//        }

        linearLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Grocery_LoginActivity.class);
                PrefsGrocery.setUserIdGrocery(getActivity(),"");
                PrefsGrocery.setUserNameGrocery(getActivity(),"");
                PrefsGrocery.setUserEmailGrocery(getActivity(),"");
                PrefsGrocery.setUserProfileUrlGrocery(getActivity(),"");
                startActivity(intent);
            }
        });
    }

    private void showCustomDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.grocery_login_dialog);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);


        // set the custom dialog components - text, image and button
        TextView restOnline = (TextView) dialog.findViewById(R.id.restOnline);
        TextView restOffline = (TextView) dialog.findViewById(R.id.restOffline);

        restOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                PrefsGrocery.setOpenCloseGrocery(getActivity(),"Open");
                openClosed.setText(PrefsGrocery.getOpenCloseGrocery(getActivity()));
            }
        });

        restOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                PrefsGrocery.setOpenCloseGrocery(getActivity(),"Close");
                openClosed.setText(PrefsGrocery.getOpenCloseGrocery(getActivity()));
            }
        });

    }
}
