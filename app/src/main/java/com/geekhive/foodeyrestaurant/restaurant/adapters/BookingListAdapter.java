package com.geekhive.foodeyrestaurant.restaurant.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.restaurant.activities.NewOrderDetailsActivity;
import com.geekhive.foodeyrestaurant.restaurant.beans.booking.BookingList;
import com.geekhive.foodeyrestaurant.restaurant.beans.neworder.Order;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.MyViewHolder> {
    public Typeface Montserrat_Regular;
    public Typeface Montserrat_SemiBold;
    Context context;
    BookingList bookingList;


    public BookingListAdapter(Context context, BookingList bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        final String name = bookingList.getBookDetail().get(position).getBooking().getName();
        holder.customerName.setText(name);
        holder.customerMob.setText(bookingList.getBookDetail().get(position).getBooking().getMobile());
        holder.orderId.setText(bookingList.getBookDetail().get(position).getBooking().getId());
        String book = "Date & time: " + bookingList.getBookDetail().get(position).getBooking().getDate() + bookingList.getBookDetail().get(position).getBooking().getTime();
        holder.orderTime.setText(book);



    }
    @Override
    public int getItemCount() {
        return bookingList.getBookDetail().size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView customerName, customerMob, orderId, orderTime;
        Button acceptOrder, cancelOrder;
        CardView productDetailsCard;
        public MyViewHolder(View itemView) {
            super(itemView);
            acceptOrder = itemView.findViewById(R.id.acceptOrder);
            cancelOrder = itemView.findViewById(R.id.cancelOrder);
            customerName = itemView.findViewById(R.id.customerName);
            customerMob = itemView.findViewById(R.id.customerMob);
            orderId = itemView.findViewById(R.id.orderId);
            productDetailsCard = itemView.findViewById(R.id.productDetailsCard);
            orderTime = itemView.findViewById(R.id.orderTime);
        }
    }
}