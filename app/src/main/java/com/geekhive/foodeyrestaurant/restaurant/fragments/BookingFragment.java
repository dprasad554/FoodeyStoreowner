package com.geekhive.foodeyrestaurant.restaurant.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.restaurant.beans.booking.BookingList;
import com.geekhive.foodeyrestaurant.restaurant.beans.bookingconfirm.BookingConfirm;
import com.geekhive.foodeyrestaurant.restaurant.utils.BaseActivityInterface;
import com.geekhive.foodeyrestaurant.restaurant.utils.ConnectionDetector;
import com.geekhive.foodeyrestaurant.restaurant.utils.OnResponseListner;
import com.geekhive.foodeyrestaurant.restaurant.utils.Prefs;
import com.geekhive.foodeyrestaurant.restaurant.utils.SnackBar;
import com.geekhive.foodeyrestaurant.restaurant.utils.WebServices;

public class BookingFragment extends Fragment implements OnResponseListner {

    View v;
    BaseActivityInterface setToolBarTitle;
    RecyclerView bookRecyclerView;
    ConnectionDetector mDetector;
    String message;
    String custNo = "";
    private final int CALL_REQUEST = 100;
    //ConnectionDetector mDetector;

    public BookingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.booking_fragment, container, false);
        initialiseView(v, this.getActivity());

        setHasOptionsMenu(false);

        setToolBarTitle = (BaseActivityInterface) getActivity();
        setToolBarTitle.setToolBarTitle("Home");


        return v;

    }

    void initialiseView(View v, final Context ctx) {
        mDetector = new ConnectionDetector(getActivity());
        bookRecyclerView = v.findViewById(R.id.bookRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        bookRecyclerView.setLayoutManager(linearLayoutManager);

        CallService();
        //pushNotification();


    }

    private void CallService() {
        String id = Prefs.getUserId(getActivity());
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).BookingListRest(WebServices.Foodey_Services,
                    WebServices.ApiType.bookinglist, Prefs.getUserId(getActivity()));
        }

    }


    private void CallBookingService(String res_id, String book_status, String booking_id) {
        String id = Prefs.getUserId(getActivity());
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).BookingStatus(WebServices.Foodey_Services,
                    WebServices.ApiType.bookstatus, res_id, book_status, booking_id);
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {

        if (apiType == WebServices.ApiType.bookinglist) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {

                BookingList bookingList = (BookingList) response;
                if (!isSucces || bookingList == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (bookingList != null){
                        if (bookingList.getBookDetail() != null){
                            if (bookingList.getBookDetail().size() != 0){
                                BookingListAdapter homeListAdapter = new BookingListAdapter(getActivity(), bookingList);
                                bookRecyclerView.setAdapter(homeListAdapter);
                            }

                        } else {
                            SnackBar.makeText(getActivity(), bookingList.getMessage(), SnackBar.LENGTH_SHORT).show();
                        }
                    } else {
                        SnackBar.makeText(getActivity(), "No record found", SnackBar.LENGTH_SHORT).show();
                    }


                }
            }
        } else if (apiType == WebServices.ApiType.bookstatus) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {

                BookingConfirm bookingConfirm = (BookingConfirm) response;
                if (!isSucces || bookingConfirm == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (bookingConfirm != null){
                        if (bookingConfirm.getMessage() != null){
                            SnackBar.makeText(getActivity(), bookingConfirm.getMessage(), SnackBar.LENGTH_SHORT).show();
                            CallService();
                        }
                    }


                }
            }
        }

    }


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
            final String name = "Name: " + bookingList.getBookDetail().get(position).getBooking().getName();
            holder.customerName.setText(name);
            String mob = "Mobile No: " + bookingList.getBookDetail().get(position).getBooking().getMobile();
            holder.customerMob.setText(mob);
            String alt = "Alt Mobile No: " + bookingList.getBookDetail().get(position).getBooking().getAlternateMobile();
            holder.customerAlternate.setText(alt);
            String bookingId = "Booking ID: " + bookingList.getBookDetail().get(position).getBooking().getId();
            holder.orderId.setText(bookingId);
            String book = "Date & time: " + bookingList.getBookDetail().get(position).getBooking().getBookDate() + ", " + bookingList.getBookDetail().get(position).getBooking().getTime();
            holder.orderTime.setText(book);
            String guests = "Total Guest(s): " + bookingList.getBookDetail().get(position).getBooking().getNoGuest();
            holder.orderGuest.setText(guests);
            if (bookingList.getBookDetail().get(position).getBooking().getStatus().equals("0")){
                holder.booking_status.setVisibility(View.GONE);
                holder.acceptOrder.setVisibility(View.VISIBLE);
                holder.cancelOrder.setVisibility(View.VISIBLE);
            } else {
                holder.booking_status.setVisibility(View.VISIBLE);
                holder.acceptOrder.setVisibility(View.GONE);
                holder.cancelOrder.setVisibility(View.GONE);
                if (bookingList.getBookDetail().get(position).getBooking().getStatus().equals("1")){
                    holder.booking_status.setText("Confirmed");
                } else {
                    holder.booking_status.setText("Canceled");
                }
            }
            holder.acceptOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CallBookingService(bookingList.getBookDetail().get(position).getBooking().getResId(), "1", bookingList.getBookDetail().get(position).getBooking().getId());
                }
            });

            holder.cancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CallBookingService(bookingList.getBookDetail().get(position).getBooking().getResId(), "2", bookingList.getBookDetail().get(position).getBooking().getId());
                }
            });

            holder.callCust.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    custNo = bookingList.getBookDetail().get(position).getBooking().getMobile();
                    callPhoneNumber();
                }
            });

            holder.callCustAlt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    custNo = bookingList.getBookDetail().get(position).getBooking().getAlternateMobile() + "";
                    callPhoneNumber();
                }
            });
        }
        @Override
        public int getItemCount() {
            return bookingList.getBookDetail().size();
        }
        public class MyViewHolder extends RecyclerView.ViewHolder {
            // init the item view's
            TextView customerName, customerMob, customerAlternate, orderId, orderTime, booking_status, orderGuest;
            Button acceptOrder, cancelOrder;
            CardView productDetailsCard;
            TextView callCust, callCustAlt;
            public MyViewHolder(View itemView) {
                super(itemView);
                acceptOrder = itemView.findViewById(R.id.acceptOrder);
                cancelOrder = itemView.findViewById(R.id.cancelOrder);
                customerName = itemView.findViewById(R.id.customerName);
                customerMob = itemView.findViewById(R.id.customerMob);
                orderId = itemView.findViewById(R.id.orderId);
                productDetailsCard = itemView.findViewById(R.id.productDetailsCard);
                orderTime = itemView.findViewById(R.id.orderTime);
                booking_status = itemView.findViewById(R.id.booking_status);
                customerAlternate = itemView.findViewById(R.id.customerAlternate);
                orderGuest  = itemView.findViewById(R.id.orderGuest);
                callCust = itemView.findViewById(R.id.callCust);
                callCustAlt = itemView.findViewById(R.id.callCustAlt);
            }
        }
    }


    public void callPhoneNumber() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST);

                    return;
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + custNo));
                    startActivity(callIntent);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == CALL_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + custNo));
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            } else {
                Toast.makeText(getActivity(), "Please allow calling permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
