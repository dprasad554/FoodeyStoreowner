package com.geekhive.foodeyrestaurant.restaurant.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.restaurant.activities.NewOrderDetailsActivity;
import com.geekhive.foodeyrestaurant.restaurant.beans.notification.Notifications;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder> {
    public Typeface Montserrat_Regular;
    public Typeface Montserrat_SemiBold;
    Context context;
    Notifications notifications;


    public NotificationListAdapter(Context context, Notifications notifications) {
        this.context = context;
        this.notifications = notifications;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        if (notifications.getNotification().get(position).getCart().getId() != null){
            final String name = notifications.getNotification().get(position).getUser().getFirstname() + " " + notifications.getNotification().get(position).getUser().getLastname();
            holder.custName.setText(name);
            holder.orderId.setText(String.valueOf(notifications.getNotification().get(position).getCart().getOrderId()));
            String dateTime = notifications.getNotification().get(position).getCart().getDate() + ", "+ notifications.getNotification().get(position).getCart().getTime();
            holder.orderTime.setText(dateTime);
            holder.notificationDetailsCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cartId = String.valueOf(notifications.getNotification().get(position).getCart().getId());
                    Intent intent = new Intent(context, NewOrderDetailsActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("cart_id", cartId);
                    context.startActivity(intent);
                }
            });
        }


    }
    @Override
    public int getItemCount() {
        return notifications.getNotification().size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView custName, orderId, orderTime;
        CardView notificationDetailsCard;
        public MyViewHolder(View itemView) {
            super(itemView);
            custName = itemView.findViewById(R.id.custName);
            orderId = itemView.findViewById(R.id.orderId);
            notificationDetailsCard = itemView.findViewById(R.id.notificationDetailsCard);
            orderTime = itemView.findViewById(R.id.orderTime);
        }
    }
}