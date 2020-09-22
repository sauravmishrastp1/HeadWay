package com.headwayagent.salesadviser_headwaygms.activity;

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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.VolleySingleton;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.SharedPreference.SharedPrefManager;
import com.headwayagent.salesadviser_headwaygms.adapter.DsrAdapter;
import com.headwayagent.salesadviser_headwaygms.models.DsrModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewDSRActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private String ain;
    private RequestQueue requestQueue;
    private List<DsrModel>dsrModelList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dsr);
        toolbar=findViewById(R.id.viewdsrtoolbar );
        recyclerView=findViewById(R.id.viewdsrrecyclerview);
        progressBar=findViewById(R.id.progressBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All DSR");

        ain=SharedPrefManager.getInstance(this).getUser().getAin_number();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        getDsr();

    }


    private void getDsr()
    {
        progressBar.setVisibility(View.VISIBLE);

        String url="https://www.headwaygms.com/api/view-dsr?ain="+ain;

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET,url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try{

                            String msg=response.getString("msg");

                            if (msg.equals("Success")) {


                                JSONArray record = response.getJSONArray("record");

                                for (int y = 0; y < record.length(); y++) {

                                    JSONObject jsonObject = record.getJSONObject(y);

                                    String productname = jsonObject.getString("product_name");
                                    String name=jsonObject.getString("name");
                                    String email=jsonObject.getString("email");
                                    String phone=jsonObject.getString("phone");
                                    String address1=jsonObject.getString("address1");
                                    String address2=jsonObject.getString("address2");
                                    String city=jsonObject.getString("city");
                                    String state=jsonObject.getString("state");
                                    String pin=jsonObject.getString("pin");
                                    String alternatephone=jsonObject.getString("alternate_phone");
                                    String date=jsonObject.getString("created_at");
                                    String address=address1+","+address2+","+city+","+state+","+pin;

                                    dsrModelList.add(new DsrModel(name,email,phone,address,date,productname,alternatephone));

                                }


                                DsrAdapter dsrAdapter=new DsrAdapter(ViewDSRActivity.this,dsrModelList);
                                recyclerView.setAdapter(dsrAdapter);
                                dsrAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);

                            } else {
                                Toast.makeText(getApplicationContext(), "Data Not Found", Toast.LENGTH_SHORT).show();
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

        VolleySingleton.getInstance(this).addToRequestQueue(jor);



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
