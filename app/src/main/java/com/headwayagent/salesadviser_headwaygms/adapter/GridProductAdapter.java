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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.BaseUrl;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.VolleySingleton;
import com.headwayagent.salesadviser_headwaygms.MainActivity;
import com.headwayagent.salesadviser_headwaygms.activity.CustomerDetailsFormActivity;
import com.headwayagent.salesadviser_headwaygms.activity.SalesDashBoardActivity;
import com.headwayagent.salesadviser_headwaygms.models.GridProductDetailsModel;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.activity.AddressProductDetailsActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.headwayagent.salesadviser_headwaygms.MainActivity.alternatephone;
import static com.headwayagent.salesadviser_headwaygms.MainActivity.phone;

public class GridProductAdapter extends RecyclerView.Adapter<GridProductAdapter.ViewHolder> {

    List<GridProductDetailsModel> gridProductDetailsModelList;
    Context context;
    String type;

    public GridProductAdapter(List<GridProductDetailsModel> gridProductDetailsModelList,Context context,String type) {
        this.gridProductDetailsModelList = gridProductDetailsModelList;
        this.context=context;
        this.type=type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_item_list_layout, viewGroup, false);

        return new GridProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        String img= gridProductDetailsModelList.get(position).getImage();
        String title=gridProductDetailsModelList.get(position).getProductTitle();
        String price= gridProductDetailsModelList.get(position).getProductPrice();

        Picasso.get().load(img).placeholder(R.drawable.headwaygmslogo).into(viewHolder.ServiceproductImageView);
       // Glide.with(context).load(img).placeholder(R.drawable.headwaygmslogo).into(viewHolder.ServiceproductImageView);
        viewHolder.ServiceTitleTextView.setText(title);
        viewHolder.ServicePriceTextView.setText("RS "+price+" /-");


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equals("dsr"))
                {

                    Intent intent=new Intent(context,AddressProductDetailsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("name", MainActivity.name);
                    intent.putExtra("type","dsr");
                    intent.putExtra("email", MainActivity.email);
                    intent.putExtra("phone", phone);
                    intent.putExtra("address1", MainActivity.address1);
                    intent.putExtra("address2", MainActivity.address2);
                    intent.putExtra("city", MainActivity.city);
                    intent.putExtra("state", MainActivity.state);
                    intent.putExtra("pin", MainActivity.pin);
                    intent.putExtra("alternate_phone", alternatephone);
                    intent.putExtra("price", gridProductDetailsModelList.get(position).getProductPrice());
                    intent.putExtra("pid", gridProductDetailsModelList.get(position).getImgid());
                    intent.putExtra("image", gridProductDetailsModelList.get(position).getImage());
                    context.startActivity(intent);
                }

                else if (type.equals("notplace"))
                {
                    String pid=gridProductDetailsModelList.get(position).getImgid();
                  //  Toast.makeText(context, ""+pid+" "+name+" "+email+" "+phone+" "+address1+" "+address2+" "+city+" "+state+" "+pin+" "+alternatephone, Toast.LENGTH_LONG).show();
                    addCustomer(pid);
                }

                else {

                    Intent intent = new Intent(context, CustomerDetailsFormActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("type","sales");
                    intent.putExtra("price", gridProductDetailsModelList.get(position).getProductPrice());
                    intent.putExtra("pid", gridProductDetailsModelList.get(position).getImgid());
                    intent.putExtra("image", gridProductDetailsModelList.get(position).getImage());
                    intent.putExtra("p_title", gridProductDetailsModelList.get(position).getProductTitle());
                    context.startActivity(intent);
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return gridProductDetailsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ServiceproductImageView;
        TextView   ServiceTitleTextView;
        TextView   ServicePriceTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ServiceproductImageView=itemView.findViewById(R.id.product_img);
            ServiceTitleTextView=itemView.findViewById(R.id.product_title);
            ServicePriceTextView=itemView.findViewById(R.id.product_price);
        }
    }

    private void addCustomer(final String pid)
    {

        MainActivity.progressBar.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.URL_DAILY_REPORT,

                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject=new JSONObject(response);
                            String message=jsonObject.getString("msg");

                            if (message.equals("Success"))
                            {

                                Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
                                MainActivity.progressBar.setVisibility(View.GONE);
                                Intent intent=new Intent(context,SalesDashBoardActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);

                            }

                            else {
                                Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                                MainActivity.progressBar.setVisibility(View.GONE);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "hello="+e.toString(), Toast.LENGTH_SHORT).show();
                            MainActivity.progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        MainActivity.progressBar.setVisibility(View.GONE);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", MainActivity.name);
                params.put("email", MainActivity.email);
                params.put("phone", phone);
                params.put("address1", MainActivity.address1);
                params.put("address2", MainActivity.address2);
                params.put("city", MainActivity.city);
                params.put("state", MainActivity.state);
                params.put("pin", MainActivity.pin);
                params.put("alternate_phone", alternatephone);
                params.put("user_id",MainActivity.user_id);
                params.put("p_id",pid);

                return params;
            }
        };

        queue.getCache().clear();

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }


}

