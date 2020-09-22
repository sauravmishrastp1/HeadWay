package com.headwayagent.salesadviser_headwaygms.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.adapter.ServiceAdapter;
import com.headwayagent.salesadviser_headwaygms.models.ServiceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServiceManActivity extends AppCompatActivity {

    RecyclerView service_recyclerView;

    List<ServiceModel> serviceModelList;

    Toolbar toolbar;

    RequestQueue requestQueue;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_man);
        progressBar=findViewById(R.id.serviceprogressbar);
        service_recyclerView = findViewById(R.id.service_recyclerview);
        toolbar = findViewById(R.id.toolbarservice);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Services");

        showServices();

//        service_recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        serviceModelList = new ArrayList<>();
//
//        serviceModelList.add(new ServiceModel("Kent Ro Service 17 August 2019", "12999", "https://ibb.co/fqQ204p", "1", "kent Ro only"));
//        serviceModelList.add(new ServiceModel("Kent Ro Service 17 August 2019", "12999", "https://ibb.co/fqQ204p", "2", "kent Ro only"));
//        serviceModelList.add(new ServiceModel("Kent Ro Service 17 August 2019", "12999", "https://ibb.co/fqQ204p", "3", "kent Ro only"));
//        serviceModelList.add(new ServiceModel("Kent Ro Service 17 August 2019", "12999", "https://ibb.co/fqQ204p", "4", "kent Ro only"));
//        serviceModelList.add(new ServiceModel("Kent Ro Service 17 August 2019", "12999", "https://ibb.co/fqQ204p", "5", "kent Ro only"));
//        serviceModelList.add(new ServiceModel("Kent Ro Service 17 August 2019", "12999", "https://ibb.co/fqQ204p", "6", "kent Ro only"));
//        serviceModelList.add(new ServiceModel("Kent Ro Service 17 August 2019", "12999", "https://ibb.co/fqQ204p", "7", "kent Ro only"));
//        serviceModelList.add(new ServiceModel("Kent Ro Service 17 August 2019", "12999", "https://ibb.co/fqQ204p", "8", "kent Ro only"));
//        serviceModelList.add(new ServiceModel("Kent Ro Service 17 August 2019", "12999", "https://ibb.co/fqQ204p", "9", "kent Ro only"));
//
//        ServiceAdapter serviceAdapter = new ServiceAdapter(serviceModelList, this);
//        service_recyclerView.setAdapter(serviceAdapter);
//        serviceAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void showServices() {

        progressBar.setVisibility(View.VISIBLE);

        String url = "https://www.headwaygms.com/api/get-s-product?department=service";

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            String msg = response.getString("msg");



                            if (msg.equals("Success")) {


                                JSONArray productsArray = response.getJSONArray("services");


////////////////////////////////////////////////////////////////////////////Products //////////////////////////////////////////////

                                service_recyclerView.setLayoutManager(new LinearLayoutManager(ServiceManActivity.this));

                                serviceModelList = new ArrayList<>();

                                for (int y = 0; y < productsArray.length(); y++) {

                                    JSONObject jsonObject = productsArray.getJSONObject(y);

                                    String productimage = jsonObject.getString("image");

                                    serviceModelList.add(new ServiceModel(jsonObject.getString("name"), jsonObject.getString("price"), jsonObject.getString("image"), jsonObject.getString("id"), jsonObject.getString("description")));

                                }

                                ServiceAdapter serviceAdapter = new ServiceAdapter(serviceModelList, ServiceManActivity.this);
                                service_recyclerView.setAdapter(serviceAdapter);
                                serviceAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);

                            } else {
                                Toast.makeText(getApplicationContext(), "" + response.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
//                                    output.setText(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                    }
                }
        );
        requestQueue.add(jor);

    }
}
