package com.headwayagent.salesadviser_headwaygms.activity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.VolleySingleton;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.SharedPreference.SharedPrefManager;
import com.headwayagent.salesadviser_headwaygms.adapter.DashBoardAdapter;
import com.headwayagent.salesadviser_headwaygms.models.DashBoardModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SalesDashBoardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<DashBoardModel>dashBoardModelList;
    private Toolbar toolbar;
    private String downline="",order="",direct="",indirect="";
    private String ainNumber;
    int notification_id=0;
    int message_count =1;

    private ProgressBar progressBar;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_dash_board);

        recyclerView=findViewById(R.id.sales_recyclerview);
        toolbar=findViewById(R.id.toolbarservice);
        progressBar=findViewById(R.id.dashboardProgressbar);





        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DASHBOARD");
        ainNumber=SharedPrefManager.getInstance(this).getUser().getAin_number();
        getDashboardData(ainNumber);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        dashBoardModelList=new ArrayList<>();


    }
    @Override
    protected void onStart() {
        super.onStart();
        AppUpdateChecker appUpdateChecker=new AppUpdateChecker(SalesDashBoardActivity.this);  //pass the activity in constructure
        appUpdateChecker.checkForUpdate(false); //mannual check false here
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.profile:
                startActivity(new Intent(SalesDashBoardActivity.this,MyProfileActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return super.onCreateOptionsMenu(menu);
    }




    private void getDashboardData(String ain_number)
    {
        progressBar.setVisibility(View.VISIBLE);

        String url="https://www.headwaygms.com/api/home?ain="+ain_number;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);

                            String message = obj.getString("msg");

                            if (message.equals("Success")) {

                                downline=obj.getString("downline");
                                order=obj.getString("order");
                                direct=obj.getString("direct");
                                indirect=obj.getString("indirect");

                               // Toast.makeText(SalesDashBoardActivity.this, "downline="+downline, Toast.LENGTH_SHORT).show();

                                //dashBoardModelList.add(new DashBoardModel("DIRECT SALES ORDERS",R.drawable.directsale,order,R.drawable.gradient2));
                               // dashBoardModelList.add(new DashBoardModel("TOTAL DIRECT COMMISSION",R.drawable.directsale, direct,R.drawable.gradient1));
                                //dashBoardModelList.add(new DashBoardModel("TOTAL INDIRECT COMMISSION",R.drawable.directsale,indirect,R.drawable.gradient4));
                                dashBoardModelList.add(new DashBoardModel("CDA REGISTRATION",R.drawable.directsale,"",R.drawable.gradient2));
                               // dashBoardModelList.add(new DashBoardModel("NEW ORDER",R.drawable.directsale,"",R.drawable.gradient1));
                                dashBoardModelList.add(new DashBoardModel("MY ORDERS",R.drawable.directsale,"",R.drawable.gradient4));
                                dashBoardModelList.add(new DashBoardModel("TOTAL DOWNLINE",R.drawable.directsale, downline,R.drawable.gradient3));

//                                dashBoardModelList.add(new DashBoardModel("DSR",R.drawable.directsale,"",R.drawable.gradient5));
//                                dashBoardModelList.add(new DashBoardModel("VIEW DSR",R.drawable.directsale,"",R.drawable.gradient7));
//



                                DashBoardAdapter gridProductAdapter = new DashBoardAdapter(dashBoardModelList, getApplicationContext(),"sales");
                                recyclerView.setAdapter(gridProductAdapter);
                                gridProductAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);



                            } else {
                                Toast.makeText(getApplicationContext(), "Dashoard Data Not Found "+obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Server Not Responding  check your network connection", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }) {

        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


}
