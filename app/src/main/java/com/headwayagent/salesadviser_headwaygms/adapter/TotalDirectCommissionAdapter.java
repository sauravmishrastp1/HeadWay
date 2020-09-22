package com.headwayagent.salesadviser_headwaygms.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.activity.MyOrdersActivity;
import com.headwayagent.salesadviser_headwaygms.activity.MyProfileActivity;
import com.headwayagent.salesadviser_headwaygms.activity.OrderDetailsActivity;
import com.headwayagent.salesadviser_headwaygms.models.TotalDirectCommisionModel;

import java.util.List;

public class TotalDirectCommissionAdapter extends RecyclerView.Adapter<TotalDirectCommissionAdapter.ViewHolder> {

    List<TotalDirectCommisionModel>totalDirectCommisionModelList;
    Context context;
    String type;


    public TotalDirectCommissionAdapter(List<TotalDirectCommisionModel> totalDirectCommisionModelList, Context context,String type) {
        this.totalDirectCommisionModelList = totalDirectCommisionModelList;
        this.context = context;
        this.type=type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.total_direct_commison_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        String date=totalDirectCommisionModelList.get(position).getDate();
        String orderAmt=totalDirectCommisionModelList.get(position).getOrderamt();
        String orderid=totalDirectCommisionModelList.get(position).getOrderid();
        String commission=totalDirectCommisionModelList.get(position).getCommission();

        holder.datetv.setText(date);
        holder.orderamtTv.setText(orderAmt);
        holder.orderidtv.setText(orderid);
        holder.commissiontv.setText(commission);

        if (type.equals("indirect"))
        {
            holder.datehead.setText("AIN NUMBER");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(context, MyProfileActivity.class);
                    intent.putExtra("type",type);
                    intent.putExtra("ain",totalDirectCommisionModelList.get(position).getDate());
                    intent.putExtra("orderid",totalDirectCommisionModelList.get(position).getOrderid());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);


                }
            });
        }
        if (type.equals("direct")){
            holder.datehead.setText("AIN NUMBER");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(context, OrderDetailsActivity.class);
                    intent.putExtra("intent_type",type);
                    //intent.putExtra("ain",totalDirectCommisionModelList.get(position).getDate());
                    intent.putExtra("Orderid",totalDirectCommisionModelList.get(position).getOrderid());
                    intent.putExtra("backtype", "2");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);


                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return totalDirectCommisionModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView datetv,orderamtTv,orderidtv,commissiontv,datehead;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            datetv=itemView.findViewById(R.id.date);
            orderamtTv=itemView.findViewById(R.id.orderamount);
            orderidtv=itemView.findViewById(R.id.orderid);
            commissiontv=itemView.findViewById(R.id.commission);
            datehead=itemView.findViewById(R.id.datehaed);
        }
    }
}
