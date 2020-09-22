package com.headwayagent.salesadviser_headwaygms.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.PluralsRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.VolleySingleton;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.activity.MyProfileActivity;
import com.headwayagent.salesadviser_headwaygms.activity.OrderDetailsActivity;
import com.headwayagent.salesadviser_headwaygms.models.MyOrderItemModel;
import com.headwayagent.salesadviser_headwaygms.models.TotalDownlineModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fragment.Inactive_Fragement;

public class Iactive_adapter extends RecyclerView.Adapter<Iactive_adapter.ViewHolder >{
     public static String ainnumber;
    private ProgressBar progressBar;

    List<TotalDownlineModel> totalDownlineModelList;
    Context context;

    public Iactive_adapter(List<TotalDownlineModel> totalDownlineModelList, Context context) {
        this.totalDownlineModelList = totalDownlineModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public Iactive_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.total_downline_item_layout,viewGroup,false);

        return new Iactive_adapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull Iactive_adapter.ViewHolder viewHolder, final int position) {
        viewHolder.aintv.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation));
        viewHolder.numberoforderstv.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation));
        viewHolder.ain.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation));
        viewHolder.number.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation));
        ainnumber=totalDownlineModelList.get(position).getAinnumber();
        String numberoforder=totalDownlineModelList.get(position).getNumberoforders();

        viewHolder.aintv.setText(numberoforder);
        viewHolder.numberoforderstv.setText(ainnumber);


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               checkForOrder(position,totalDownlineModelList.get(position).getAinnumber());

            }
        });

    }

    @Override
    public int getItemCount() {
        return  totalDownlineModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView aintv,numberoforderstv;
        TextView ain,number;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            aintv=itemView.findViewById(R.id.aintv);
            numberoforderstv=itemView.findViewById(R.id.numberoforderstv);
            ain = itemView.findViewById(R.id.name1);
            number =itemView.findViewById(R.id.name2);
        }
    }

    private void checkForOrder(final int position,String ainnumber)

    {
        Toast.makeText(context, "ain="+ainnumber, Toast.LENGTH_SHORT).show();
      //  progressBar.setVisibility(View.VISIBLE);
        final ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        String url="https://www.headwaygms.com/api/check-order?ain="+ainnumber;



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject object=new JSONObject(response);

                            String status=object.getString("status");
                           // Toast.makeText(context, "respons="+response, Toast.LENGTH_SHORT).show();

                            if (status.equals("200"))
                            {
                                String orderid=object.getString("order_id");

                                Intent intent = new Intent(context, OrderDetailsActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("Orderid",orderid);
                                intent.putExtra("intent_type", "sales");
                                intent.putExtra("name","1");
                                intent.putExtra("backtype", "2");
                                context.startActivity(intent);
                                progressDialog.dismiss();
                            }
                            else {
                                Intent intent=new Intent(context, MyProfileActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("type","InActive");
                                intent.putExtra("ain",totalDownlineModelList.get(position).getAinnumber());
                                context.startActivity(intent);
                                progressDialog.dismiss();
                            }
                        }

                        catch (Exception e)
                        {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }){

        };

        VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
        RequestQueue reQuestQueue = Volley.newRequestQueue(context);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        reQuestQueue.add(stringRequest);
    }
}
