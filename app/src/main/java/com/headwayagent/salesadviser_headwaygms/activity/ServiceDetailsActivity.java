package com.headwayagent.salesadviser_headwaygms.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.BaseUrl;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.VolleySingleton;
import com.headwayagent.salesadviser_headwaygms.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ServiceDetailsActivity extends AppCompatActivity {

    Button completeService_btn;
    Toolbar toolbar;
    ImageView productImageview;
    TextView  productPriceTextview;
    TextView  productTitleTextview;
    TextView  productDiscritionTextview;
    public static TextView  rescheduletimeTv,rescheduledayTv;
    TextView  nametv,phonetv,addresstv,pintv,alternatephonetv,sTimetv,sDatetv;
    ProgressBar progressBar;
    String orderId;
    private ProgressDialog progressDialog;
    private String myrescheduledate,myrescheduletime;
    private Button reschedulebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        completeService_btn=findViewById(R.id.complete_button);
        productImageview=findViewById(R.id.product_image);
        productPriceTextview=findViewById(R.id.product_price);
        productTitleTextview=findViewById(R.id.product_title);
        productDiscritionTextview=findViewById(R.id.product_discription);
        progressBar=findViewById(R.id.progressBr);
        nametv=findViewById(R.id.name);
        phonetv=findViewById(R.id.phone);
        addresstv=findViewById(R.id.address);
        pintv=findViewById(R.id.pin);
        alternatephonetv=findViewById(R.id.alternate_phone);
        sTimetv=findViewById(R.id.s_time);
        sDatetv=findViewById(R.id.s_date);
        rescheduletimeTv=findViewById(R.id.service_reschedule_time);
        rescheduledayTv=findViewById(R.id.service_reschedule_day);
        reschedulebtn=findViewById(R.id.reschedule_button);

        toolbar=findViewById(R.id.toolbarservice);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Service Details");

        Bundle extras = getIntent().getExtras();

        orderId=extras.getString("Orderid");




        completeService_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ServiceDetailsActivity.this,OtpVerificationActivity.class);
                intent.putExtra("order-id",orderId);
                startActivity(intent);
            }
        });

        setServiceDetailsData(orderId);

        rescheduletimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new TimePickerFragment3();
                newFragment.show(getSupportFragmentManager(), "TimePicker");
            }
        });


        rescheduledayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment3();
                newFragment.show(getSupportFragmentManager(), "DatePicker");
            }
        });


        reschedulebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rescheduleService(orderId);
            }
        });

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


    private void setServiceDetailsData(String orderID) {

        progressBar.setVisibility(View.VISIBLE);
        String url="http://www.headwaygms.com/api/orders-details?order_id="+orderID;
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.getCache().clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject=new JSONObject(response);
                            JSONObject object= jsonObject.getJSONObject("orders");
                            String img=object.getString("product_image");
                            String  title=object.getString("title");
                            String price=object.getString("price");
                            String name=object.getString("name");
                            String phone=object.getString("phone");
                            String address1=object.getString("address1");
                            String address2=object.getString("address2");
                            String pin=object.getString("pin");
                            String alternatephone=object.getString("alternate_phone");
                            String installdate=object.getString("insta_date");
                            String installtime=object.getString("insta_time");


                            nametv.setText(name);
                            phonetv.setText(phone);
                            addresstv.setText(address1+" "+address2);
                            pintv.setText(pin);
                            alternatephonetv.setText(alternatephone);
                            sTimetv.setText(installtime);
                            sDatetv.setText(installdate);
                            Glide.with(ServiceDetailsActivity.this).load("http://headwaygms.com/"+img).placeholder(R.drawable.headwaygmslogo).into(productImageview);
                            productTitleTextview.setText(title);
                            productPriceTextview.setText("Rs "+price+"/-");
                            progressBar.setVisibility(View.GONE);

                        } catch (Exception e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(ServiceDetailsActivity.this, "Response error", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);


                    }
                }) {

        };

        queue.getCache().clear();
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }




    private void rescheduleService(final String orderID) {

        myrescheduledate = rescheduledayTv.getText().toString();
        myrescheduletime = rescheduletimeTv.getText().toString();


        if (myrescheduledate.equals("DD/MM/YY")) {
            Toast.makeText(this, "Please Enter date", Toast.LENGTH_SHORT).show();

        }

        else  if (myrescheduletime.equals("HH:MM")) {
            Toast.makeText(this, "Please Enter time", Toast.LENGTH_SHORT).show();

        } else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Rescheduling");
            progressDialog.setTitle("Please wait...");
            progressDialog.setIcon(R.drawable.headwaygmslogo);
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.URL_SERVICERESCHEDULE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {

                                JSONObject obj = new JSONObject(response);


                                String message = obj.getString("msg");

                                if (message.equals("Success")) {

                                    JSONObject jsonObject = obj.getJSONObject("services");

                                    String serviceDatee = jsonObject.getString("insta_date");
                                    String serviceTimee = jsonObject.getString("insta_time");

                                    sDatetv.setText(serviceDatee);
                                    sTimetv.setText(serviceTimee);
                                    progressDialog.dismiss();

                                } else {
                                    Toast.makeText(ServiceDetailsActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(ServiceDetailsActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ServiceDetailsActivity.this, "Server Not Responding"+error.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("order_id", orderID);
                    params.put("reschedule_time", myrescheduletime);
                    params.put("reschedule_date", myrescheduledate);

                    return params;
                }
            };

            VolleySingleton.getInstance(ServiceDetailsActivity.this).addToRequestQueue(stringRequest);


        }
    }


}




@SuppressLint("ValidFragment")
class DatePickerFragment3 extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        ServiceDetailsActivity.rescheduledayTv.setText(day+"/"+month+"/"+year);
    }
}


@SuppressLint("ValidFragment")
class TimePickerFragment3 extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String am_pm;


        if (hourOfDay<12)
        {
            am_pm="AM";
        }

        else
        {
            am_pm="PM";
        }

        ServiceDetailsActivity.rescheduletimeTv.setText(hourOfDay+":"+minute+" "+am_pm);

    }


}
