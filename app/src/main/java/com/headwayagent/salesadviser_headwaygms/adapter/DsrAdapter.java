package com.headwayagent.salesadviser_headwaygms.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.models.DsrModel;

import java.util.List;

public class DsrAdapter extends RecyclerView.Adapter<DsrAdapter.ViewHolder> {

    Context  context;
    List<DsrModel> dsrModelList;

    public DsrAdapter(Context context, List<DsrModel> dsrModelList) {
        this.context = context;
        this.dsrModelList = dsrModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(context).inflate(R.layout.dsr_data_item_layout,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        holder.pname.setText(dsrModelList.get(position).getPname());
        holder.nametv.setText(dsrModelList.get(position).getName());
        holder.emailtv.setText(dsrModelList.get(position).getEmail());
        holder.phonetv.setText(dsrModelList.get(position).getPhone());
        holder.alternametv.setText(dsrModelList.get(position).getAlternatephone());
        holder.addresstv.setText(dsrModelList.get(position).getAddress());
        holder.datetv.setText(dsrModelList.get(position).getDate());

        holder.phonetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", dsrModelList.get(position).getPhone(), null));
                context.startActivity(intent);
            }
        });

        holder.alternametv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", dsrModelList.get(position).getAlternatephone(), null));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dsrModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView pname,nametv,emailtv,phonetv,alternametv,addresstv,datetv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pname=itemView.findViewById(R.id.prductname);
            nametv=itemView.findViewById(R.id.name);
            emailtv=itemView.findViewById(R.id.email);
            phonetv=itemView.findViewById(R.id.phone);
            alternametv=itemView.findViewById(R.id.alternate_phone);
            addresstv=itemView.findViewById(R.id.address);
            datetv=itemView.findViewById(R.id.date);
        }
    }
}
