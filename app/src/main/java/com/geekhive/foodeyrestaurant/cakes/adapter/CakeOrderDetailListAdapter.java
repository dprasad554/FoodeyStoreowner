package com.geekhive.foodeyrestaurant.cakes.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.cakes.activities.CakeViewSpecialCakeActivity;
import com.geekhive.foodeyrestaurant.cakes.beans.cakeorderdetailhistory.OrderDetailHistory;
import com.geekhive.foodeyrestaurant.cakes.utils.WebServices;
import com.squareup.picasso.Picasso;

public class CakeOrderDetailListAdapter extends RecyclerView.Adapter<CakeOrderDetailListAdapter.MyViewHolder> {

    Context context;
    OrderDetailHistory orderDetailHistory;
    String url = "";

    public CakeOrderDetailListAdapter(Context context, OrderDetailHistory orderDetailHistory) {
        this.context = context;
        this.orderDetailHistory = orderDetailHistory;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cake_order_detail_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.p_name.setText(orderDetailHistory.getOrder().getCakeCartDetail().get(position).getCake().getProductName());
        holder.tv_quantity.setText(orderDetailHistory.getOrder().getCakeCartDetail().get(position).getQuantity() + "  "
                + orderDetailHistory.getOrder().getCakeCartDetail().get(position).getCake().getQuantityDetail()+" * "
        +"2");
        if(orderDetailHistory.getOrder().getCakeCartDetail().get(position).getMrp().equals(orderDetailHistory.getOrder().getCakeCartDetail().get(position).getPrice())){
            holder.tv_mrp.setVisibility(View.GONE);
        }
        holder.tv_mrp.setText("Mrp Rs."+orderDetailHistory.getOrder().getCakeCartDetail().get(position).getMrp());
        holder.tv_mrp.setPaintFlags(holder.tv_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_price.setText("Rs."+orderDetailHistory.getOrder().getCakeCartDetail().get(position).getPrice());
        if(orderDetailHistory.getOrder().getCakeCartDetail().get(position).getCake().getImage() != null) {
            url = WebServices.Foodey_Store_Image_URL + orderDetailHistory.getOrder().getCakeCartDetail().get(position).getCake().getImage();
        }
        if (!url.equals("")){
            Picasso.get()
                    .load(url)//download URL
                    .error(R.drawable.foodey_logo_)//if failed
                    .into(holder.product_image);
        }
        if(orderDetailHistory.getOrder().getCakeCartDetail().get(position).getCake().getProductCategory().equals("3")){
            holder.tv_details.setVisibility(View.VISIBLE);
        }else {
            holder.tv_details.setVisibility(View.GONE);
        }
        holder.tv_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CakeViewSpecialCakeActivity.class);
                intent.putExtra("cart_id",orderDetailHistory.getOrder().getCakeCart().getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return orderDetailHistory.getOrder().getCakeCartDetail().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView p_name, tv_quantity,tv_mrp,tv_price,tv_details;
        ImageView product_image;

        public MyViewHolder(View itemView) {
            super(itemView);
            p_name = itemView.findViewById(R.id.p_name);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            tv_mrp = itemView.findViewById(R.id.tv_mrp);
            tv_price = itemView.findViewById(R.id.tv_price);
            product_image = itemView.findViewById(R.id.product_image);
            tv_details = itemView.findViewById(R.id.tv_details);

        }
    }
}