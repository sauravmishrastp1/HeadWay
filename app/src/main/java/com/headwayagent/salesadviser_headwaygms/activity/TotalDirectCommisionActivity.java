package com.headwayagent.salesadviser_headwaygms.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.BaseUrl;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.VolleySingleton;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.SharedPreference.SharedPrefManager;
import com.headwayagent.salesadviser_headwaygms.adapter.TotalDirectCommissionAdapter;
import com.headwayagent.salesadviser_headwaygms.models.TotalDirectCommisionModel;
import com.headwayagent.salesadviser_headwaygms.models.UserDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TotalDirectCommisionActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private String ain;
    private View oopslinear;
    private List<TotalDirectCommisionModel> totalDirectCommisionModelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_direct_commision);

        recyclerView = findViewById(R.id.recyclerview);
        progressBar = findViewById(R.id.progressBar);
        toolbar = findViewById(R.id.toolbar);
        oopslinear=findViewById(R.id.oopslinear);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Direct Commission");
        UserDetails userDetails = SharedPrefManager.getInstance(this).getUser();
        ain=userDetails.getAin_number();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        getTotalDirectCommission();
    }


    private void getTotalDirectCommission()
    {
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.DIRECT_COMMISSION+ain,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);
                            String message = obj.getString("msg");

                            if (message.equals("Success")) {

                                JSONArray productsArray = obj.getJSONArray("order");

                                for (int y = 0; y < productsArray.length(); y++) {

                                    JSONObject jsonObject = productsArray.getJSONObject(y);

                                    String orderid = jsonObject.getString("order_id");
                                    String date = jsonObject.getString("Date");
                                    String commission = jsonObject.getString("commision");
                                    String orderAount = jsonObject.getString("order_amount");

                                    totalDirectCommisionModelList.add(new TotalDirectCommisionModel(date,orderAount,orderid,commission));



                                }

                                TotalDirectCommissionAdapter adapter=new TotalDirectCommissionAdapter(totalDirectCommisionModelList,TotalDirectCommisionActivity.this,"direct");
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(getApplicationContext(), "You Have No Direct Commission "+obj.getString("msg"), Toast.LENGTH_LONG).show();
                                oopslinear.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(TotalDirectCommisionActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Server Not Responding Check Inernet Connection", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }) {

        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.profile:
                startActivity(new Intent(TotalDirectCommisionActivity.this,MyProfileActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
