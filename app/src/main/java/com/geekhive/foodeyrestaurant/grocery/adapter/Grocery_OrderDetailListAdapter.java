package com.geekhive.foodeyrestaurant.grocery.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.grocery.beans.groceryorderdetailhistory.OrderDetailHistory;
import com.geekhive.foodeyrestaurant.grocery.utils.WebServices;
import com.squareup.picasso.Picasso;

public class Grocery_OrderDetailListAdapter extends RecyclerView.Adapter<Grocery_OrderDetailListAdapter.MyViewHolder> {

    Context context;
    OrderDetailHistory orderDetailHistory;
    String url = "";

    public Grocery_OrderDetailListAdapter(Context context, OrderDetailHistory orderDetailHistory) {
        this.context = context;
        this.orderDetailHistory = orderDetailHistory;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_order_detail_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.p_name.setText(orderDetailHistory.getOrder().getGCartDetail().get(position).getGrocery().getProductName());
        holder.tv_quantity.setText(orderDetailHistory.getOrder().getGCartDetail().get(position).getQuantity() + "  "
                + orderDetailHistory.getOrder().getGCartDetail().get(position).getGrocery().getQuantityDetail()+" * "
        +"2");
        if(orderDetailHistory.getOrder().getGCartDetail().get(position).getMrp().equals(orderDetailHistory.getOrder().getGCartDetail().get(position).getPrice())){
            holder.tv_mrp.setVisibility(View.GONE);
        }
        holder.tv_mrp.setText("Mrp Rs."+orderDetailHistory.getOrder().getGCartDetail().get(position).getMrp());
        holder.tv_mrp.setPaintFlags(holder.tv_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_price.setText("Rs."+orderDetailHistory.getOrder().getGCartDetail().get(position).getPrice());
        if(orderDetailHistory.getOrder().getGCartDetail().get(position).getGrocery().getImage() != null) {
            url = WebServices.Foodey_Store_Image_URL + orderDetailHistory.getOrder().getGCartDetail().get(position).getGrocery().getImage();
        }
        if (!url.equals("")){
            Picasso.get()
                    .load(url)//download URL
                    .error(R.drawable.foodey_logo_)//if failed
                    .into(holder.product_image);
        }

    }

    @Override
    public int getItemCount()
    {
        return orderDetailHistory.getOrder().getGCartDetail().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView p_name, tv_quantity, orderId, orderTime,tv_mrp,tv_price;
        Button viewOrder;
        CardView productDetailsCard;
        ImageView product_image;
        public MyViewHolder(View itemView) {
            super(itemView);
            p_name = itemView.findViewById(R.id.p_name);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            tv_mrp = itemView.findViewById(R.id.tv_mrp);
            tv_price = itemView.findViewById(R.id.tv_price);
            product_image = itemView.findViewById(R.id.product_image);

        }
    }
}