package com.headwayagent.salesadviser_headwaygms.activity;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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


public class MyOrdersActivity extends AppCompatActivity {

    private RecyclerView myOrdersRecyclerview;
    private String email;
    private Toolbar toolbar;
    private String ain_nuber;
    private RequestQueue requestQueue;
    private ProgressBar progressBar;
    private String type;
    private View oopsview;
    private String adaptertype;
    private String OrderId;




    public MyOrdersActivity() {

    }



    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        myOrdersRecyclerview = findViewById(R.id.my_orers_recyclerview);
        progressBar=findViewById(R.id.myorderprogressbar);
        oopsview=findViewById(R.id.oopslinear);
        toolbar=findViewById(R.id.toolbarservice);

        setSupportActionBar(toolbar);


        UserDetails user = SharedPrefManager.getInstance(MyOrdersActivity.this).getUser();
        ain_nuber = user.getAin_number();
        Bundle bundle=getIntent().getExtras();

        if(bundle!=null)
        {
            type=bundle.getString("type");
            OrderId = bundle.getString("orderid");

            if (type.equals("1"))
            {
                adaptertype="sales";
                getSupportActionBar().setTitle("Orders");
            }
            else {

                if (!SharedPrefManager.getInstance(this).isCheckIn())
                {
                    startActivity(new Intent(MyOrdersActivity.this,LoginActivity.class));
                    finish();
                }

                adaptertype="service";
                getSupportActionBar().setTitle("Services");
            }
        }

        showOrders();



    }


    private void showOrders() {

       progressBar.setVisibility(View.VISIBLE);

        String url="https://www.headwaygms.com/api/orders?user_id="+ain_nuber+"&type="+type;

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject object=new JSONObject(response);
                            String msg=object.getString("msg");

                            if (msg.equals("Success")) {

                                JSONArray ja = object.getJSONArray("orders");
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                layoutManager.setOrientation(RecyclerView.VERTICAL);
                                myOrdersRecyclerview.setLayoutManager(layoutManager);

                                List<MyOrderItemModel> myOrderItemModels = new ArrayList<>();

                                for (int i = 0; i < ja.length(); i++) {

                                    JSONObject jsonObject = ja.getJSONObject(i);

                                    String image = jsonObject.getString("product_image");


                                    myOrderItemModels.add(new MyOrderItemModel(jsonObject.getString("order_status"), 2, "http://headwaygms.com/" + image, jsonObject.getString("title"), jsonObject.getString("price"), jsonObject.getString("order_id"),"",jsonObject.getString("id")));

                                }
                                     MyOrderAdapter myOrderAdapter = new MyOrderAdapter(myOrderItemModels, getApplicationContext(), adaptertype);
                                    myOrdersRecyclerview.setAdapter(myOrderAdapter);
                                    myOrderAdapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);

                            }

                            else {

                                oopsview.setVisibility(View.VISIBLE);
                                Toast.makeText(MyOrdersActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                            }

                        }

                        catch (Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(MyOrdersActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Server Not responding"+error.toString(),Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }){

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.profile:
                startActivity(new Intent(MyOrdersActivity.this,MyProfileActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return super.onCreateOptionsMenu(menu);
    }


}
