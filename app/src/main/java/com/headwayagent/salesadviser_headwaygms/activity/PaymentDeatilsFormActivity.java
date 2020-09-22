package com.headwayagent.salesadviser_headwaygms.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.BaseUrl;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.VolleySingleton;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.Registeration_Activity;
import com.headwayagent.salesadviser_headwaygms.SharedPreference.SharedPrefManager;
import com.headwayagent.salesadviser_headwaygms.adapter.Distrubeter_Adapter;
import com.headwayagent.salesadviser_headwaygms.adapter.SpinnerItemAdapter;
import com.headwayagent.salesadviser_headwaygms.models.Distributer_model;
import com.headwayagent.salesadviser_headwaygms.models.SpinnerItemModel;
import com.headwayagent.salesadviser_headwaygms.models.UserDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;

public class PaymentDeatilsFormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Toolbar toolbar;
    Spinner spinner,distrubutorspinerr;
    ArrayList<SpinnerItemModel> list;
   static TextView datebtn;
   static TextView timebtn;
   static String time="",date="";
   String paymentType;
   private ArrayList<Distributer_model>distributer_modelList = new ArrayList<>();
   Button placeOrderButton;
   String p_img,p_price,p_title,p_discription;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String fullName ,Pin,Ain;
    public static String distributorphone;
    String fullname;
    ProgressDialog progressDialog;
    public static String cdaphonen="0000";
    private DatePickerDialog.OnDateSetListener mDateSetListener;




    String pid,quantity,name,email,phone,address1,address2,city,state,pin,alternatephone,user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_deatils_form);
        toolbar=findViewById(R.id.toolbarservice);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Payment Details");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinner=findViewById(R.id.myspinner);
        distrubutorspinerr = findViewById(R.id.distributerspinner);
        datebtn=findViewById(R.id.date);
        timebtn=findViewById(R.id.time);
        placeOrderButton=findViewById(R.id.place_order_btn);


        Bundle extras = getIntent().getExtras();

        pid=extras.getString("pid");
        quantity=extras.getString("qty");
        name=extras.getString("name");
        email=extras.getString("email");
        phone=extras.getString("phone");
        address1=extras.getString("address1");
        address2=extras.getString("address2");
        city=extras.getString("city");
        state=extras.getString("state");
        pin=extras.getString("pin");
        alternatephone=extras.getString("alternate_phone");
        final String type=extras.getString("type");

        p_img=extras.getString("image");
        p_price=extras.getString("p_price");
        Toast.makeText(this, "price="+p_price, Toast.LENGTH_SHORT).show();
        p_title=extras.getString("p_title");
        p_discription=extras.getString("p_discription");

       // Toast.makeText(this, ""+p_title+" "+pid, LENGTH_SHORT).show();

        UserDetails userDetails = SharedPrefManager.getInstance(PaymentDeatilsFormActivity.this).getUser();
        //user_id=userDetails.getUserid();
        user_id=userDetails.getAin_number();


        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equals("sales")) {

                   // Toast.makeText(PaymentDeatilsFormActivity.this, "qty=" + quantity + " " + pid + " " + name + " " + email + " " + phone + " " + address1 + " " + address2 + " " + city + " " + "" + state + " " + pin + " " + " " + " " + alternatephone + "" + " " + time + " " + date + " " + paymentType + "userid=" + user_id, Toast.LENGTH_LONG).show();

                    if (time.equals(""))
                    {
                        Toast.makeText(PaymentDeatilsFormActivity.this, "Please Select Time", Toast.LENGTH_SHORT).show();
                    }

                    else if (date.equals("")){
                        Toast.makeText(PaymentDeatilsFormActivity.this, "Please Select Date", Toast.LENGTH_SHORT).show();
                    }else if(fullName.equals("Select Distributor") || fullName.equals("")){
                        Toast.makeText(PaymentDeatilsFormActivity.this, "please select Distributor", LENGTH_SHORT).show();

                    }

                    else {
                       verifyotp();
                    }
                }

                else {
                    if (time.equals(""))
                    {
                        Toast.makeText(PaymentDeatilsFormActivity.this, "Please Select Time", Toast.LENGTH_SHORT).show();
                    }

                    else if (date.equals("")){
                        Toast.makeText(PaymentDeatilsFormActivity.this, "Please Select Date", Toast.LENGTH_SHORT).show();
                    }

                    else {

                        placeServiceOrder();
                    }
                }
            }
        });
        getDistributerName();

        distributer_modelList.add(new Distributer_model("Select Distributor","","",""));

        Distrubeter_Adapter distrubeter_adapter = new Distrubeter_Adapter(this, (ArrayList<Distributer_model>) distributer_modelList);

        if (distrubutorspinerr != null) {

            distrubutorspinerr.setAdapter(distrubeter_adapter);
            distrubutorspinerr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    Distributer_model model = (Distributer_model) parent.getSelectedItem();
                    fullName = model.getName();
                    Pin = model.getPin();
                    Ain = model.getAin();

                   // Toast.makeText(PaymentDeatilsFormActivity.this, "ain="+Ain, LENGTH_SHORT).show();



                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }


        datebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                DialogFragment newFragment = new DatePickerFragment();
//                newFragment.show(getSupportFragmentManager(), "timePicker");

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        PaymentDeatilsFormActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                //  Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                date = month + "/" + day + "/" + year;
                datebtn.setText(date);
            }
        };


        timebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                DialogFragment newFragment = new TimePickerFragment();
//               // getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//                newFragment.show(getSupportFragmentManager(), "timePicker");


                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(PaymentDeatilsFormActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                timebtn.setText(hourOfDay + ":" + minute);
                                PaymentDeatilsFormActivity.time=hourOfDay+" : "+minute;


                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });



        list=getList();

        SpinnerItemAdapter spinnerItemAdapter=new SpinnerItemAdapter(this,list);
        if (spinner!=null) {
            spinner.setAdapter(spinnerItemAdapter);
            spinner.setOnItemSelectedListener(this);
        }




    }

    private ArrayList<SpinnerItemModel> getList() {

        list=new ArrayList<>();
        list.add(new SpinnerItemModel("COD",R.drawable.cod));
//        list.add(new SpinnerItemModel("Net Banking",R.drawable.netbanking));
//        list.add(new SpinnerItemModel("Card Payment",R.drawable.ic_credit_card_black_24dp));
//        list.add(new SpinnerItemModel("UPI",R.drawable.netbanking));
//        list.add(new SpinnerItemModel("Paytm",R.drawable.paytm));
        return list;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        SpinnerItemModel model= (SpinnerItemModel) parent.getSelectedItem();
        paymentType=model.getSpinnerItemName();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void verifyotp(){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setIcon(R.drawable.headwaygmslogo);
        progressDialog.setTitle("Placing Order");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        String url="https://www.headwaygms.com/api/otp?phone_number="+phone;
       // Toast.makeText(this, "phone="+cdaphonen, LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);
                            String message = obj.getString("msg");
                            String status = obj.getString("status");


                            if (status.equals("200")) {
                                Toast.makeText(PaymentDeatilsFormActivity.this, "otpsend"+message, LENGTH_SHORT).show();
                                plaeoreder();
                                 progressDialog.dismiss();

                            } else {
                                Toast.makeText(PaymentDeatilsFormActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                 progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PaymentDeatilsFormActivity.this, "exception", Toast.LENGTH_SHORT).show();
                              progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaymentDeatilsFormActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                          progressDialog.dismiss();
                    }
                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("phone_number", phone);
//                params.put("type","1");
//
//
//                return params;+
//            }
        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        VolleySingleton.getInstance(PaymentDeatilsFormActivity.this).addToRequestQueue(stringRequest);




}

    private void getDistributerName(){
        if (distributer_modelList.size()>1)
        {
            distributer_modelList.clear();
        }
       // Toast.makeText(this, "pin="+CustomerDetailsFormActivity.statepin, LENGTH_SHORT).show();
        //Creating a string request
        String url="https://www.headwaygms.com/api/choose-distributor?state="+CustomerDetailsFormActivity.statepin;

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(CustomerDetailsFormActivity.this, "hii"+response, LENGTH_SHORT).show();

                        try {
                            JSONObject object=new JSONObject(response);
                            String msg=object.getString("msg");

                            if (msg.equals("Success")) {


                                JSONArray jsonObject = object.getJSONArray("distributor");
                                // Toast.makeText(CustomerDetailsFormActivity.this, "value"+ja, LENGTH_SHORT).show();


                                for (int i = 0; i < jsonObject.length(); i++) {

                                    JSONObject Object = jsonObject.getJSONObject(i);
                                    String fullname = Object.getString("full_name");
                                    String pin = Object.getString("pin");
                                    String ain = Object.getString("ain");
                                    //cdaphonen= object.getString("phone");

                                    distributer_modelList.add(new Distributer_model(fullname, pin, ain,cdaphonen));


                                }
                            }

                            else {
                                Toast.makeText(PaymentDeatilsFormActivity.this, "No distributor Not Found in This State", LENGTH_SHORT).show();
                            }

                        } catch (Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(PaymentDeatilsFormActivity.this, "something went wrong"+e, LENGTH_SHORT).show();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Server Not responding"+error.toString(),Toast.LENGTH_LONG).show();

                    }
                }){

        };


        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

     private void plaeoreder(){
       // Toast.makeText(this, "ain"+Registeration_Activity.ain, LENGTH_SHORT).show();
         Intent intent=new Intent(PaymentDeatilsFormActivity.this,OtpVerificationActivity.class);
         intent.putExtra("type","1");
         intent.putExtra("phone",phone);
         intent.putExtra("product_id", pid);
         intent.putExtra("name", name);
         intent.putExtra("email", email);
         intent.putExtra("phone", phone);
         intent.putExtra("address1", address1);
         intent.putExtra("address2", address2);
         intent.putExtra("city", city);
         intent.putExtra("state", CustomerDetailsFormActivity.statepin);
         intent.putExtra("pin", pin);
         intent.putExtra("alternate_phone", alternatephone);
         intent.putExtra("quantity",quantity);
         intent.putExtra("payment_type",paymentType);
         intent.putExtra("delivery_time", time);
         intent.putExtra("delivery_date",date);
         intent.putExtra("price",p_price);
         intent.putExtra("user_id",user_id);
         intent.putExtra("distributor_ain",Ain);
         intent.putExtra("product_name",p_title);
         startActivity(intent);



     }



    private void placeServiceOrder()
    {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setIcon(R.drawable.headwaygmslogo);
        progressDialog.setTitle("Placing Order");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.URL_PLACE_SERVICE_ORDER_SALES,

                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject=new JSONObject(response);

                            String message=jsonObject.getString("msg");

                            Toast.makeText(PaymentDeatilsFormActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                            if (message.equals("Success"))
                            {

                                Intent intent=new Intent(PaymentDeatilsFormActivity.this,ServiceDetailsActivity.class);

                                intent.putExtra("p_img",p_img);
                                intent.putExtra("p_price",p_price);
                                intent.putExtra("p_title",p_title);
                                intent.putExtra("p_discription",p_discription);
                                startActivity(intent);
                                finish();
                                progressDialog.dismiss();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(PaymentDeatilsFormActivity.this, "hello="+e.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        // Toast.makeText(PaymentDeatilsFormActivity.this, " error null", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", pid);
                params.put("name", name);
                params.put("email", email);
                params.put("phone", phone);
                params.put("address1", address1);
                params.put("address2", address2);
                params.put("city", city);
                params.put("state", state);
                params.put("pin", pin);
                params.put("alternate_phone", alternatephone);
                params.put("user_id",user_id);
                params.put("payment_type",paymentType);
                params.put("service_time", time);
                params.put("service_date", date);


                return params;
            }
        };

        queue.getCache().clear();

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));

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


@SuppressLint("ValidFragment")
class DatePickerFragment extends DialogFragment
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
        PaymentDeatilsFormActivity.date=day+"/"+month+"/"+year;
        PaymentDeatilsFormActivity.datebtn.setText(day+"-"+month+"-"+year);
    }
}



 @SuppressLint("ValidFragment")
 class TimePickerFragment extends DialogFragment
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

        PaymentDeatilsFormActivity.time=hourOfDay+" : "+minute;

       PaymentDeatilsFormActivity.timebtn.setText(hourOfDay+":"+minute);
    }




}
