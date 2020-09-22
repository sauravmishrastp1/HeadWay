package com.headwayagent.salesadviser_headwaygms.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.BaseUrl;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.VolleySingleton;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.SharedPreference.SharedPrefManager;
import com.headwayagent.salesadviser_headwaygms.adapter.TotalDownlineAdapter;
import com.headwayagent.salesadviser_headwaygms.models.TotalDownlineModel;
import com.headwayagent.salesadviser_headwaygms.models.UserDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TotalDownlineActivity extends AppCompatActivity {

    private RecyclerView recyclerView,pendingrecyclerview,underprocessrecyclerview;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private String ain ;
    private View oopslinear,line,line2,line3;
    private List<TotalDownlineModel> approveModelList = new ArrayList<>();
    private List<TotalDownlineModel> pendingModellist = new ArrayList<>();
    private List<TotalDownlineModel> underprocessModelList = new ArrayList<>();
    private TextView aproveText,pendindText,underprocessText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_downline);


        recyclerView = findViewById(R.id.totaldownline_recycler);
        pendingrecyclerview = findViewById(R.id.Pending_recycler);
        line =findViewById(R.id.view);
        line2 = findViewById(R.id.view2);
        line3 = findViewById(R.id.view3);
        underprocessrecyclerview = findViewById(R.id.under_proccess_recycler);

        progressBar = findViewById(R.id.progressBar);
        toolbar = findViewById(R.id.toolbar);
        oopslinear=findViewById(R.id.oopslinear);
        aproveText = findViewById(R.id.approve);
        pendindText = findViewById(R.id.Pending);
        underprocessText = findViewById(R.id.under_proccess);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Total Downline");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        LinearLayoutManager pendinglayout = new LinearLayoutManager(this);
        pendingrecyclerview.setLayoutManager(pendinglayout);
        LinearLayoutManager underprocess = new LinearLayoutManager(this);
        underprocessrecyclerview.setLayoutManager(underprocess);
        Bundle bundle = getIntent().getExtras();
        ain = bundle.getString("ain");
        getTotalDownline();



    }






    private void getTotalDownline() {

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.VIEW_DOWNLINE+ain,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(TotalDownlineActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                        try {

                            JSONObject obj = new JSONObject(response);
                            String message = obj.getString("msg");
                           // Toast.makeText(TotalDownlineActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                            if (message.equals("Success")) {


                                JSONArray approveArray = obj.getJSONArray("active");



                                for (int y = 0; y < approveArray.length(); y++) {

                                    JSONObject jsonObject = approveArray.getJSONObject(y);

                                    String ain = jsonObject.getString("full_name");
                                    String order_total = jsonObject.getString("ain");

                                    approveModelList.add(new TotalDownlineModel(ain, order_total));


                                }
                                if(approveModelList.size()==0){
                                    aproveText.setVisibility(View.GONE);
                                    line.setVisibility(View.GONE);
                                }

                                TotalDownlineAdapter totalDownlineAdapter = new TotalDownlineAdapter(approveModelList, TotalDownlineActivity.this);
                                recyclerView.setAdapter(totalDownlineAdapter);
                                totalDownlineAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                oopslinear.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(getApplicationContext(), "You Have No Downline "+obj.getString("msg"), Toast.LENGTH_LONG).show();
                                oopslinear.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                aproveText.setVisibility(View.GONE);
                                pendindText.setVisibility(View.GONE);
                                underprocessText.setVisibility(View.GONE);
                                line.setVisibility(View.GONE);
                            }
                                if (message.equals("Success")) {
                                    JSONArray PendingArray = obj.getJSONArray("inactive");
                                    for (int y = 0; y < PendingArray.length(); y++) {

                                        JSONObject jsonObject = PendingArray.getJSONObject(y);

                                        String ain = jsonObject.getString("full_name");
                                        String order_total = jsonObject.getString("ain");

                                        pendingModellist.add(new TotalDownlineModel(ain, order_total));


                                    }
                                    if(pendingModellist.size()==0){
                                        underprocessText.setVisibility(View.GONE);
                                        line2.setVisibility(View.GONE);
                                    }
                                    TotalDownlineAdapter pendingadapter = new TotalDownlineAdapter(pendingModellist, TotalDownlineActivity.this);
                                    pendingrecyclerview.setAdapter(pendingadapter);
                                    pendingadapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);
                                    oopslinear.setVisibility(View.GONE);

//                                } else {
//                                    Toast.makeText(getApplicationContext(), "You Have No Downline " + obj.getString("msg"), Toast.LENGTH_LONG).show();
//                                    oopslinear.setVisibility(View.VISIBLE);
//                                    progressBar.setVisibility(View.GONE);
//                                    aproveText.setVisibility(View.GONE);
//                                    pendindText.setVisibility(View.GONE);
//                                    underprocessText.setVisibility(View.GONE);
//                                    line2.setVisibility(View.GONE);
//
//
//                                }
//
//                                if (message.equals("Success")) {
//                                    JSONArray underproccessArray = obj.getJSONArray("inactive");
//                                    for (int y = 0; y < underproccessArray.length(); y++) {
//
//                                        JSONObject jsonObject = underproccessArray.getJSONObject(y);
//
//                                        String ain = jsonObject.getString("full_name");
//                                        String order_total = jsonObject.getString("ain");
//
//                                        underprocessModelList.add(new TotalDownlineModel(ain, order_total));
//
//
//                                    }
//                                    if(underprocessModelList.size()==0){
//                                        pendindText.setVisibility(View.GONE);
//                                        line3.setVisibility(View.GONE);
//                                    }
//
//                                    //    Toast.makeText(TotalDownlineActivity.this, ""+underprocessModelList, Toast.LENGTH_SHORT).show();
//                                    TotalDownlineAdapter underprocessadapter = new TotalDownlineAdapter(underprocessModelList, TotalDownlineActivity.this);
//                                    underprocessrecyclerview.setAdapter(underprocessadapter);
//                                    underprocessadapter.notifyDataSetChanged();
//                                    progressBar.setVisibility(View.GONE);
//                                }
//
//
//                            else {
//                                Toast.makeText(getApplicationContext(), "You Have No Downline "+obj.getString("msg"), Toast.LENGTH_LONG).show();
//                                oopslinear.setVisibility(View.VISIBLE);
//                                progressBar.setVisibility(View.GONE);
//                                aproveText.setVisibility(View.GONE);
//                                pendindText.setVisibility(View.GONE);
//                                underprocessText.setVisibility(View.GONE);
//                                line3.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(TotalDownlineActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
