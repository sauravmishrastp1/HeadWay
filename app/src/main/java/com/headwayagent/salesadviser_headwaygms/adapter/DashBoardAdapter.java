package com.headwayagent.salesadviser_headwaygms.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.headwayagent.salesadviser_headwaygms.MainActivity;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.Registeration_Activity;
import com.headwayagent.salesadviser_headwaygms.SharedPreference.SharedPrefManager;
import com.headwayagent.salesadviser_headwaygms.activity.Activ_Inactiv;
import com.headwayagent.salesadviser_headwaygms.activity.CustomerDetailsFormActivity;
import com.headwayagent.salesadviser_headwaygms.activity.MyOrdersActivity;
import com.headwayagent.salesadviser_headwaygms.activity.MyProfileActivity;
import com.headwayagent.salesadviser_headwaygms.activity.ServiceManActivity;
import com.headwayagent.salesadviser_headwaygms.activity.TotalDirectCommisionActivity;
import com.headwayagent.salesadviser_headwaygms.activity.TotalIndirectCommissionActivity;
import com.headwayagent.salesadviser_headwaygms.activity.ViewDSRActivity;
import com.headwayagent.salesadviser_headwaygms.models.DashBoardModel;

import java.util.List;

public class DashBoardAdapter extends RecyclerView.Adapter<DashBoardAdapter.ViewHolder> {

    List<DashBoardModel>dashBoardModelList;
    Context context;
    String  dashboard_type;


    public DashBoardAdapter(List<DashBoardModel> dashBoardModelList, Context context,String dashboard_type) {
        this.dashBoardModelList = dashBoardModelList;
        this.context = context;
        this.dashboard_type=dashboard_type;
    }

    @NonNull
    @Override
    public DashBoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dashboard_item_layout,viewGroup,false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashBoardAdapter.ViewHolder viewHolder, final int position) {


        String title=dashBoardModelList.get(position).getTitle();
        int    imgId=dashBoardModelList.get(position).getBackgroundImg();
        String count=dashBoardModelList.get(position).getCountwork();
           viewHolder.title_textview.setText(title);
           viewHolder.view.setBackgroundResource(dashBoardModelList.get(position).getBackgroundResourse());

           if (imgId==1)
           {
               viewHolder.divider.setVisibility(View.GONE);
               viewHolder.imgview.setVisibility(View.GONE);
           }
           else {

               ViewGroup.LayoutParams layoutParams= viewHolder.relView.getLayoutParams();
               layoutParams.height= ViewGroup.LayoutParams.WRAP_CONTENT;

               viewHolder.imgview.setImageResource(imgId);
           }


           if (count.equals(""))
           {
               viewHolder.totaltv.setVisibility(View.GONE);
           }

           if (position==2 || position==3)
           {
               viewHolder.totaltv.setText("\u20B9"+count);
           }

           else {

               viewHolder.totaltv.setText(count);
           }



        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String type = dashBoardModelList.get(position).getTitle();

                if (type.equals("NEW ORDER")) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("type", "dasboard");
                    context.startActivity(intent);
                } else if (type.equals("SERVICE PLACE")) {
                    Intent intent = new Intent(context, ServiceManActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }


                if (type.equals("MY PROFILE")) {
                    Intent intent = new Intent(context, MyProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }

                if (type.equals("NEW SERVICES")) {
                    Intent intent = new Intent(context, MyOrdersActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("type", "2");
                    context.startActivity(intent);

                }

                if (type.equals("DSR")) {
                    Intent intent = new Intent(context, CustomerDetailsFormActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("type", "dsr");
                    context.startActivity(intent);
                }

                if (type.equals("MY ORDERS")) {
                    Intent intent = new Intent(context, MyOrdersActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("type", "1");
                    context.startActivity(intent);
                }


                if (type.equals("TOTAL DOWNLINE")) {
                    Intent intent = new Intent(context, Activ_Inactiv.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("ain", SharedPrefManager.getInstance(context).getUser().getAin_number());
                    context.startActivity(intent);
                }


                if (type.equals("DIRECT SALES ORDERS")) {
                    Intent intent = new Intent(context, MyOrdersActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("type", "1");
                    context.startActivity(intent);
                }


                if (type.equals("TOTAL DIRECT COMMISSION")) {
                    Intent intent = new Intent(context, TotalDirectCommisionActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("type", "1");
                    context.startActivity(intent);
                }


                if (type.equals("TOTAL INDIRECT COMMISSION")) {
                    Intent intent = new Intent(context, TotalIndirectCommissionActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("type", "1");
                    context.startActivity(intent);
                }


                if (type.equals("VIEW DSR")) {
                    Intent intent = new Intent(context, ViewDSRActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                if (type.equals("CDA REGISTRATION"))
                {
                    Intent intent = new Intent(context, Registeration_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }






            }
        });

    }

    @Override
    public int getItemCount() {
        return dashBoardModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView  title_textview;
        View view;
        ImageView imgview;
        TextView totaltv;
        View relView;
        View divider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title_textview=itemView.findViewById(R.id.dashboard_title);
            view=itemView.findViewById(R.id.dashboard_layout);
            imgview=itemView.findViewById(R.id.dash_img);
            totaltv=itemView.findViewById(R.id.totalnumber);
            relView=itemView.findViewById(R.id.dashboardrel);
            divider=itemView.findViewById(R.id.divider);


        }
    }
}
