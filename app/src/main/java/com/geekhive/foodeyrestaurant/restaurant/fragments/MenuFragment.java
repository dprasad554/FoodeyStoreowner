package com.geekhive.foodeyrestaurant.restaurant.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.restaurant.activities.MenuListActivity;
import com.geekhive.foodeyrestaurant.restaurant.beans.foodstatus.FoodStatus;
import com.geekhive.foodeyrestaurant.restaurant.beans.restaurantmenulist.Food;
import com.geekhive.foodeyrestaurant.restaurant.beans.restaurantmenulist.RestaurantMenuList;
import com.geekhive.foodeyrestaurant.restaurant.utils.ConnectionDetector;
import com.geekhive.foodeyrestaurant.restaurant.utils.OnResponseListner;
import com.geekhive.foodeyrestaurant.restaurant.utils.SnackBar;
import com.geekhive.foodeyrestaurant.restaurant.utils.WebServices;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment implements OnResponseListner {

    View v;
    RecyclerView menuRecycler;
    ConnectionDetector mDetector;
    private String id;
    RestaurantMenuList restaurantMenuList;
    List<Food> foodList = new ArrayList<>();
    int quantity = 0;

    String price_;
    String mrp;
    String foodId;
    MenuAdapter menuAdapter;
    String res_id;


    public static MenuFragment getInstance(String url, String res_id) {
        MenuFragment topRated = new MenuFragment();
        // Supply index input as an argument
        Bundle args = new Bundle();
        args.putString("id", url);
        args.putString("res_id", res_id);
        //args.putString("name", name);
        topRated.setArguments(args);
        return topRated;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            id = args.getString("id", "0");
            res_id = args.getString("res_id", "0");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_menu, container, false);
        initialiseView(v, this.getActivity());



        setHasOptionsMenu(false);


        return v;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if (foodList != null){
                foodList.clear();
            }
            if (res_id != null){
                CallService(res_id, id);
            }

            if (menuAdapter != null) {
                menuAdapter.notifyDataSetChanged();
            }
        }
    }

    void initialiseView(View v, final Context ctx) {
        mDetector = new ConnectionDetector(getActivity());
        menuRecycler = v.findViewById(R.id.menulist);
        Bundle args = getArguments();
        id = args.getString("id", "0");
        res_id = args.getString("res_id", "0");
        if (foodList.size() == 0){
            CallService(res_id, id);
        }

    }

    private void CallService(String res_id, String category) {

        new WebServices(this).RestaurantMenuList(WebServices.Foodey_Services,
                WebServices.ApiType.restaurantmenulist, res_id, category );

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {

        if (apiType == WebServices.ApiType.restaurantmenulist) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {

                restaurantMenuList = (RestaurantMenuList) response;
                if (!isSucces || restaurantMenuList == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (restaurantMenuList != null){
                        if (restaurantMenuList.getFood() != null){
                            foodList = new ArrayList<>();
                            foodList.addAll(restaurantMenuList.getFood());
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            menuRecycler.setLayoutManager(linearLayoutManager);
                            MenuAdapter menuAdapter = new MenuAdapter();
                            menuRecycler.setAdapter(menuAdapter);
                        } else {
                            SnackBar.makeText(getActivity(), "No record found", SnackBar.LENGTH_SHORT).show();
                        }
                    } else {
                        SnackBar.makeText(getActivity(), "No record found", SnackBar.LENGTH_SHORT).show();
                    }

                }
            }
        } else if (apiType == WebServices.ApiType.foodStatus) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {

                FoodStatus foodStatus = (FoodStatus) response;

                if (!isSucces || foodStatus == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(getActivity(), MenuListActivity.class));
                }
            }
        }
    }

    public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

        public MenuAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MenuAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_menu_items, parent, false));
        }

        public void onBindViewHolder(final MenuAdapter.MyViewHolder holder, final int position) {

            String items = foodList.get(position).getName();
            holder.vT_admd_text.setText(items);
            price_ = foodList.get(position).getPrice();
            String price = "₹ " + foodList.get(position).getPrice();
            holder.vT_price.setText(price);
            if (foodList.get(position).getStatus().equals("1")){
                holder.vT_status.setText("Open");
            } else {
                holder.vT_status.setText("Closed");
            }
            holder.vL_admd_hide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    foodId = foodList.get(position).getId();
                    showCustomDialog();
                }
            });

        }

        public int getItemCount() {
            return foodList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            LinearLayout vL_admd_main, vL_admd_hide;
            ImageView vI_admd_image;
            TextView vT_admd_text, vT_price, vT_status;
            ImageView qtyM, qtyA;
            TextView qty;
            Button buttonAdd;

            public MyViewHolder(View view) {
                super(view);
                this.vL_admd_main = (LinearLayout) view.findViewById(R.id.vL_admd_main);
                this.vL_admd_hide = (LinearLayout) view.findViewById(R.id.vL_admd_hide);
                this.vT_admd_text = (TextView) view.findViewById(R.id.vT_admd_text);
                this.vT_status = view.findViewById(R.id.vT_status);
                vT_price = view.findViewById(R.id.vT_price);
            }
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

        restOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                CallStatusService("1");
            }
        });

        restOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                CallStatusService("0");
            }
        });

    }

    private void CallStatusService(String value) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).FoodStatus(WebServices.Foodey_Services,
                    WebServices.ApiType.foodStatus, foodId, value);
        }

    }
}
