package com.headwayagent.salesadviser_headwaygms.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.activity.CustomerDetailsFormActivity;
import com.headwayagent.salesadviser_headwaygms.models.ServiceModel;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    List<ServiceModel> serviceModeList;
    Context context;

    public ServiceAdapter(List<ServiceModel> serviceModelList, Context context) {
        this.serviceModeList = serviceModelList;
        this.context=context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.services_item_layout,viewGroup,false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        String servicePhoto=serviceModeList.get(position).getService_image();
        String serviceTitle=serviceModeList.get(position).getService_name();
        String  servicePrice=serviceModeList.get(position).getService_price();

       Glide.with(context).load(servicePhoto).placeholder(R.drawable.headwaygmslogo).into(viewHolder.serviceImage);
        viewHolder.serviceTitle.setText(serviceTitle);
        viewHolder.servicePrice.setText("Rs "+servicePrice+" /-");
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, CustomerDetailsFormActivity.class);
                intent.putExtra("image",serviceModeList.get(position).getService_image());
                intent.putExtra("title",serviceModeList.get(position).getService_name());
                intent.putExtra("service_discription",serviceModeList.get(position).getService_details());
                intent.putExtra("service_price",serviceModeList.get(position).getService_price());
                intent.putExtra("pid",serviceModeList.get(position).getService_id());
                intent.putExtra("type","service_place");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceModeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView serviceImage;
        TextView   serviceTitle;

        TextView servicePrice;

        Button    buyService;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            serviceImage=itemView.findViewById(R.id.service_image);
            serviceTitle=itemView.findViewById(R.id.service_title);
            servicePrice=itemView.findViewById(R.id.service_price);

        }
    }
}
