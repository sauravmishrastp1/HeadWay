package com.headwayagent.salesadviser_headwaygms.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.activity.OrderDetailsActivity;
import com.headwayagent.salesadviser_headwaygms.activity.ServiceDetailsActivity;
import com.headwayagent.salesadviser_headwaygms.models.MyOrderItemModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    private List<MyOrderItemModel> myOrderItemModelList;
    Context context;
    private String type;

    public MyOrderAdapter(List<MyOrderItemModel> myOrderItemModelList, Context context,String type) {
        this.myOrderItemModelList = myOrderItemModelList;
        this.context=context;
        this.type=type;
    }

    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder holder, final int position) {

        String resourse = myOrderItemModelList.get(position).getProductImage();
        int rating = myOrderItemModelList.get(position).getRating();
        final String productTitle = myOrderItemModelList.get(position).getProductTitle();
        String orderstatus = myOrderItemModelList.get(position).getOrderStatus();
        String price=myOrderItemModelList.get(position).getProductprice();


       // Toast.makeText(context, "title="+productTitle, Toast.LENGTH_SHORT).show();

        holder.setData(resourse, productTitle, orderstatus, rating,price);

       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equals("service"))
                {
                    Intent intent = new Intent(context, ServiceDetailsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Orderid", myOrderItemModelList.get(position).getOrderId());
                    intent.putExtra("backtype", "2");
                    context.startActivity(intent);
                }

                else {

                    Intent intent = new Intent(context, OrderDetailsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Orderid", myOrderItemModelList.get(position).getOrderId());
                    intent.putExtra("intent_type", type);
                    intent.putExtra("name",myOrderItemModelList.get(position).getOrderStatus());
                    intent.putExtra("backtype", "2");
                    context.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;

        ImageView orderIndicator;
        ImageView deliverIndicator,installedIndicator;
        TextView producttitle;
        TextView deliverStatus;
        TextView priceTextvieww;
        LinearLayout rate_now_container;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_image);
            orderIndicator = itemView.findViewById(R.id.order_indicator);
            deliverIndicator=itemView.findViewById(R.id.deliverindicator);
            producttitle = itemView.findViewById(R.id.product_title);
            deliverStatus = itemView.findViewById(R.id.order_deliverddate);
            priceTextvieww=itemView.findViewById(R.id.priceTextview);


        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void setData(String resourse, String title, String orderstatus, int rating,String price) {


            Picasso.get().load(resourse).placeholder(R.drawable.headwaygmslogo).into(productImage);
           // Glide.with(context).load(resourse).placeholder(R.drawable.headwaygmslogo).into(productImage);

            producttitle.setText(title);

            priceTextvieww.setText("\u20B9  "+price+" /-");

            if (orderstatus.equals("1")) {

                orderIndicator.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                deliverStatus.setText("Order Placed");

            } else if (orderstatus.equals("2")){
                orderIndicator.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                deliverStatus.setText("Confirmed");
            }

            else if (orderstatus.equals("3")){
                orderIndicator.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                deliverStatus.setText("Delivered");
            }

            else if (orderstatus.equals("4")){
                orderIndicator.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                deliverStatus.setText("Installed");
            }

            else {
                orderIndicator.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
                deliverStatus.setText("Cancelled");

            }




//            setRedeem(rating);
//
//            for (int i=0;i<rate_now_container.getChildCount();i++)
//            {
//                final int starposition=i;
//                rate_now_container.getChildAt(i).setOnClickListener(new View.OnClickListener() {
//                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//                    @Override
//                    public void onClick(View v) {
//                        setRedeem(starposition);
//                    }
//                });
//            }
//
//        }
//
//
//
//        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//        void setRedeem(int starposition)
//        {
//
//            for (int i=0;i<rate_now_container.getChildCount();i++)
//            {
//                ImageView starbutton=(ImageView)rate_now_container.getChildAt(i);
//                starbutton.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
//                if(i<=starposition)
//                {
//                    starbutton.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
//                }
//            }
//        }
        }


    }
}
