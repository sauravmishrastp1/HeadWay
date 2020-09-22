package com.headwayagent.salesadviser_headwaygms.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.SharedPreference.SharedPrefManager;
import com.headwayagent.salesadviser_headwaygms.adapter.MyOrderAdapter;
import com.headwayagent.salesadviser_headwaygms.models.MyOrderItemModel;
import com.headwayagent.salesadviser_headwaygms.models.UserDetails;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DeliveryBoyActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private View no_orderView;
    private ProgressBar progressBar;
    private RequestQueue requestQueue;
    private String ain_number;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_boy);
        recyclerView=findViewById(R.id.orders_recyclerview);
        no_orderView=findViewById(R.id.no_orderlinear);
        progressBar=findViewById(R.id.progressBar);
        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Orders For You");

        UserDetails user = SharedPrefManager.getInstance(DeliveryBoyActivity.this).getUser();
        ain_number = user.getAin_number();

        showOrders();
    }


    private void showOrders() {

        progressBar.setVisibility(View.VISIBLE);

        String url="http://www.headwaygms.com/api/orders?user_id="+ain_number;

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject object=new JSONObject(response);

                            JSONArray ja = object.getJSONArray("orders");
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            layoutManager.setOrientation(RecyclerView.VERTICAL);
                            recyclerView.setLayoutManager(layoutManager);

                            List<MyOrderItemModel> myOrderItemModels = new ArrayList<>();

                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jsonObject = ja.getJSONObject(i);

                                String image = jsonObject.getString("product_image");

                                myOrderItemModels.add(new MyOrderItemModel(jsonObject.getString("order_status"), 2, "http://headwaygms.com/"+image, jsonObject.getString("title"), jsonObject.getString("price"), jsonObject.getString("order_id"),"", jsonObject.getString("id")));

                            }

                            MyOrderAdapter myOrderAdapter = new MyOrderAdapter(myOrderItemModels, getApplicationContext(),"d-boy");

                            recyclerView.setAdapter(myOrderAdapter);
                            myOrderAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);


                        }

                        catch (Exception e)
                        {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }){

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

}
