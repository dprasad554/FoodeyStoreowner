package com.geekhive.foodeyrestaurant.restaurant.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.restaurant.activities.AddMenu_Activity;
import com.geekhive.foodeyrestaurant.restaurant.activities.LoginActivity;
import com.geekhive.foodeyrestaurant.restaurant.activities.MenuListActivity;
import com.geekhive.foodeyrestaurant.restaurant.activities.OrderDetailsActivity;
import com.geekhive.foodeyrestaurant.restaurant.activities.OrdersActivity;
import com.geekhive.foodeyrestaurant.restaurant.activities.QRCodeActivity;
import com.geekhive.foodeyrestaurant.restaurant.activities.RevenueActivity;
import com.geekhive.foodeyrestaurant.restaurant.beans.restaurantstat.RestaurantStatus;
import com.geekhive.foodeyrestaurant.restaurant.beans.sleepmode.SleepMode;
import com.geekhive.foodeyrestaurant.restaurant.beans.tableopen.TableBookingOpen;
import com.geekhive.foodeyrestaurant.restaurant.beans.takeaway.TakeAway;
import com.geekhive.foodeyrestaurant.restaurant.notifications.NotificationActivity;
import com.geekhive.foodeyrestaurant.restaurant.utils.BaseActivityInterface;
import com.geekhive.foodeyrestaurant.restaurant.utils.ConnectionDetector;
import com.geekhive.foodeyrestaurant.restaurant.utils.OnResponseListner;
import com.geekhive.foodeyrestaurant.restaurant.utils.Prefs;
import com.geekhive.foodeyrestaurant.restaurant.utils.SnackBar;
import com.geekhive.foodeyrestaurant.restaurant.utils.WebServices;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment implements OnResponseListner, CompoundButton.OnCheckedChangeListener {

    View v;
    BaseActivityInterface setToolBarTitle;
    RecyclerView orderRecyclerView;
    ConnectionDetector mDetector;
    Switch openClosed;
    LinearLayout menuList;
    ImageView iv_ownerImage;
    LinearLayout linearLogOut;
    LinearLayout linear_addMenu;
    LinearLayout notifications;
    LinearLayout linear_qrMenu;
    CardView dEarn, nOrders;
    TextView vT_st;
    TextView vT_tb;
    Switch openClosedT;
    TextView vT_sm;
    Switch openClosedsm;
    TextView vT_ta;
    Switch openClosedta;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.profile_fragment, container, false);
        initialiseView(v, this.getActivity());

        setHasOptionsMenu(false);

        setToolBarTitle = (BaseActivityInterface) getActivity();
        setToolBarTitle.setToolBarTitle("Profile");


        return v;

    }

    void initialiseView(View v, final Context ctx) {
        mDetector = new ConnectionDetector(getActivity());
        openClosed = v.findViewById(R.id.openClosed);
        vT_st = v.findViewById(R.id.vT_st);
        vT_tb = v.findViewById(R.id.vT_tb);
        menuList = v.findViewById(R.id.menuList);
        openClosedT = v.findViewById(R.id.openClosedT);
        vT_sm = v.findViewById(R.id.vT_sm);
        openClosedsm = v.findViewById(R.id.openClosedsm);

        vT_ta = v.findViewById(R.id.vT_ta);
        openClosedta = v.findViewById(R.id.openClosedta);
        iv_ownerImage = v.findViewById(R.id.iv_ownerImage);
        linearLogOut = v.findViewById(R.id.linearLogOut);
        linear_addMenu = v.findViewById(R.id.linear_addMenu);
        linear_qrMenu = v.findViewById(R.id.linear_qrMenu);
        notifications = v.findViewById(R.id.notifications);
        dEarn = v.findViewById(R.id.dEarn);
        nOrders = v.findViewById(R.id.nOrders);

        openClosed.setOnCheckedChangeListener(this);

        dEarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RevenueActivity.class));
            }
        });

        nOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OrdersActivity.class));
            }
        });

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NotificationActivity.class));
            }
        });

        if (Prefs.getOpenClose(getActivity()).equals("Open")) {
            openClosed.setChecked(true);
            vT_st.setText(Prefs.getOpenClose(getActivity()));
        } else {
            openClosed.setChecked(false);
            vT_st.setText(Prefs.getOpenClose(getActivity()));
        }

        if (Prefs.getOpenCloseTB(getActivity()).equals("Open")) {
            openClosedT.setChecked(true);
            vT_tb.setText(Prefs.getOpenClose(getActivity()));
        } else {
            openClosedT.setChecked(false);
            vT_tb.setText(Prefs.getOpenClose(getActivity()));
        }

        if (Prefs.getOpenCloseTA(getActivity()).equals("Available")) {
            openClosedta.setChecked(true);
            vT_ta.setText(Prefs.getOpenCloseTA(getActivity()));
        } else {
            openClosedta.setChecked(false);
            vT_ta.setText(Prefs.getOpenCloseTA(getActivity()));
        }

        if (Prefs.getOnOff(getActivity()).equals("On")) {
            openClosedsm.setChecked(true);
            vT_sm.setText(Prefs.getOnOff(getActivity()));
        } else {
            openClosedsm.setChecked(false);
            vT_sm.setText(Prefs.getOnOff(getActivity()));
        }

        linear_qrMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QRCodeActivity.class);
                startActivity(intent);
            }
        });

        final TextView owner = (TextView) v.findViewById(R.id.tv_ownerName);
        Typeface myCustomFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/JosefinSans-Bold.ttf");
        owner.setTypeface(myCustomFont);

        final TextView designation = (TextView) v.findViewById(R.id.tv_ownerDesignation);
        Typeface myCustomFont1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/JosefinSans-Bold.ttf");
        designation.setTypeface(myCustomFont1);

        final TextView daily = (TextView) v.findViewById(R.id.tv_daily);
        Typeface myCustomFont2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-SemiBold.ttf");
        daily.setTypeface(myCustomFont2);

        final TextView order = (TextView) v.findViewById(R.id.tv_order);
        Typeface myCustomFont3 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-SemiBold.ttf");
        order.setTypeface(myCustomFont3);

        final TextView addMenu = (TextView) v.findViewById(R.id.tv_addMenu);
        Typeface myCustomFont4 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-SemiBold.ttf");
        addMenu.setTypeface(myCustomFont4);

        final TextView listMenu = (TextView) v.findViewById(R.id.tv_menuList);
        Typeface myCustomFont5 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-SemiBold.ttf");
        listMenu.setTypeface(myCustomFont5);

        final TextView restaurant = (TextView) v.findViewById(R.id.tv_restaurant);
        Typeface myCustomFont6 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-SemiBold.ttf");
        restaurant.setTypeface(myCustomFont6);

        final TextView logout = (TextView) v.findViewById(R.id.tv_Logout);
        Typeface myCustomFont7 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-SemiBold.ttf");
        logout.setTypeface(myCustomFont7);

        final LinearLayout linear1 = (LinearLayout) v.findViewById(R.id.linearLayout1);
        linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout linearLayout5 = v.findViewById(R.id.linearLayout5);
        linearLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });

        menuList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MenuListActivity.class));
            }
        });

        if (!(Prefs.getUserName(getActivity()).equals(""))) {
            owner.setText(Prefs.getUserName(getActivity()));
        }
        if (!(Prefs.getMobileNumber(getActivity()).equals(""))) {
            designation.setText(Prefs.getMobileNumber(getActivity()));
        }

        if (!(Prefs.getUserProfileUrl(getActivity()).equals(""))) {
            Picasso.get()
                    .load(WebServices.Foodey_Image_Url + Prefs.getUserProfileUrl(getActivity()))
                    .into(iv_ownerImage);
        }

        linearLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                Prefs.setUserId(getActivity(), "");
                Prefs.setUserName(getActivity(), "");
                Prefs.setMobileNumber(getActivity(), "");
                Prefs.setUserEmail(getActivity(), "");
                Prefs.setUserProfileUrl(getActivity(), "");
                startActivity(intent);
            }
        });

        linear_addMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddMenu_Activity.class));
            }
        });

        openClosedT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    CallTableService("1");
                    Prefs.setOpenCloseTB(getActivity(), "Open");
                    vT_tb.setText(Prefs.getOpenCloseTB(getActivity()));
                } else {
                    CallTableService("0");
                    Prefs.setOpenCloseTB(getActivity(), "Closed");
                    vT_tb.setText(Prefs.getOpenCloseTB(getActivity()));
                }
            }
        });

        openClosedsm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    CallSleepService("1");
                    Prefs.setOnOff(getActivity(), "On");
                    vT_sm.setText(Prefs.getOnOff(getActivity()));
                } else {
                    CallSleepService("0");
                    Prefs.setOnOff(getActivity(), "Off");
                    vT_sm.setText(Prefs.getOnOff(getActivity()));
                }
            }
        });

        openClosedta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    CallTakeService("1");
                    Prefs.setOpenCloseTA(getActivity(), "Available");
                    vT_ta.setText(Prefs.getOpenCloseTA(getActivity()));
                } else {
                    CallTakeService("0");
                    Prefs.setOpenCloseTA(getActivity(), "Un-Available");
                    vT_ta.setText(Prefs.getOpenCloseTA(getActivity()));
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            CallService("1");
            Prefs.setOpenClose(getActivity(), "Open");
            vT_st.setText(Prefs.getOpenClose(getActivity()));

        } else {
            CallService("0");
            Prefs.setOpenClose(getActivity(), "Closed");
            vT_st.setText(Prefs.getOpenClose(getActivity()));
        }

    }

    private void showCustomDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.login_dialog);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);


        // set the custom dialog components - text, image and button
        TextView restOnline = (TextView) dialog.findViewById(R.id.restOnline);
        TextView restOffline = (TextView) dialog.findViewById(R.id.restOffline);

        /*restOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                CallService("1");
                Prefs.setOpenClose(getActivity(),"Open");
                openClosed.setText(Prefs.getOpenClose(getActivity()));
            }
        });

        restOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                CallService("0");
                Prefs.setOpenClose(getActivity(),"Close");
                openClosed.setText(Prefs.getOpenClose(getActivity()));
            }
        });*/

    }

    private void CallService(String value) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).RestaurantStatus(WebServices.Foodey_Services,
                    WebServices.ApiType.restaurantStatus, Prefs.getUserId(getActivity()), value);
        }

    }

    private void CallTakeService(String value) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).TakeAwayStatus(WebServices.Foodey_Services,
                    WebServices.ApiType.tastatus, Prefs.getUserId(getActivity()), value);
        }

    }

    private void CallTableService(String value) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).TableBookingStatus(WebServices.Foodey_Services,
                    WebServices.ApiType.tbstatus, Prefs.getUserId(getActivity()), value);
        }

    }

    private void CallSleepService(String value) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).SleepModeStatus(WebServices.Foodey_Services,
                    WebServices.ApiType.smstatus, Prefs.getUserId(getActivity()), value);
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {

        if (apiType == WebServices.ApiType.restaurantStatus) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {

                RestaurantStatus restaurantStatus = (RestaurantStatus) response;
                if (!isSucces || restaurantStatus == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    SnackBar.makeText(getActivity(), restaurantStatus.getMessage(), SnackBar.LENGTH_SHORT).show();
                }
            }
        } else if (apiType == WebServices.ApiType.tastatus) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {

                TakeAway takeAway = (TakeAway) response;
                if (!isSucces || takeAway == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    SnackBar.makeText(getActivity(), takeAway.getMessage(), SnackBar.LENGTH_SHORT).show();
                }
            }
        } else if (apiType == WebServices.ApiType.tbstatus) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {

                TableBookingOpen tableBookingOpen = (TableBookingOpen) response;
                if (!isSucces || tableBookingOpen == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    SnackBar.makeText(getActivity(), tableBookingOpen.getMessage(), SnackBar.LENGTH_SHORT).show();
                }
            }
        } else if (apiType == WebServices.ApiType.smstatus) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {

                SleepMode sleepMode = (SleepMode) response;
                if (!isSucces || sleepMode == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    SnackBar.makeText(getActivity(), sleepMode.getMessage(), SnackBar.LENGTH_SHORT).show();
                }
            }
        }

    }
}
