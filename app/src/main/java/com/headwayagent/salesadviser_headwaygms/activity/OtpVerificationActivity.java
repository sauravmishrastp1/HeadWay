package com.headwayagent.salesadviser_headwaygms.activity;
 import android.app.ProgressDialog;
 import android.content.Intent;
 import androidx.appcompat.app.AppCompatActivity;
 import android.os.Bundle;
 import androidx.appcompat.widget.Toolbar;
 import android.view.MenuItem;
 import android.view.View;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.Toast;
 import com.android.volley.AuthFailureError;
 import com.android.volley.DefaultRetryPolicy;
 import com.android.volley.Request;
 import com.android.volley.RequestQueue;
 import com.android.volley.Response;
 import com.android.volley.VolleyError;
 import com.android.volley.toolbox.StringRequest;
 import com.android.volley.toolbox.Volley;
 import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.BaseUrl;
 import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.VolleySingleton;
 import com.headwayagent.salesadviser_headwaygms.R;
 import com.headwayagent.salesadviser_headwaygms.Registeration_Activity;
 import com.headwayagent.salesadviser_headwaygms.SalesDashbBoard;

 import org.json.JSONException;
 import org.json.JSONObject;
 import java.util.HashMap;
 import java.util.Map;
public class OtpVerificationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button submitbtn;
    private EditText happycodeET;
    private ProgressDialog progressDialog;
    private String otp;
    ProgressDialog progressDialogl;
    private String happy_code,orderid,type, phone,product_id,name,email,address1,address2,city,state,pin,alternate_phone,
            quantity,payment_type,delivery_time,delivery_date,price,user_id,distributor_ain,producvtname="null";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        toolbar=findViewById(R.id.toolbarservice);
        submitbtn=findViewById(R.id.submitotp);
        happycodeET=findViewById(R.id.happycode);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Happy Code Verfication");

        Bundle bundle=getIntent().getExtras();
        type = bundle.getString("type");
        if(type.equals("2")){
            orderid = bundle.getString("order_id");
            otp = bundle.getString("otp");
            Toast.makeText(OtpVerificationActivity.this, "otp=="+otp,Toast.LENGTH_SHORT).show();

        }
        if(type.equals("3")){
            orderid = bundle.getString("order-id");
            otp = bundle.getString("otp");

        }


        if(type.equals("1")){
            product_id = bundle.getString("product_id");
            name = bundle.getString("name");
            email = bundle.getString("email");
            address1 = bundle.getString("address1");
            address2 = bundle.getString("address2");
            city = bundle.getString("city");
            state = bundle.getString("state");
            pin = bundle.getString("pin");
            alternate_phone = bundle.getString("alternate_phone");
            quantity = bundle.getString("quantity");
            payment_type = bundle.getString("payment_type");
            delivery_time = bundle.getString("delivery_time");
            price = bundle.getString("price");
            user_id = bundle.getString("user_id");
            distributor_ain = bundle.getString("distributor_ain");
            phone = bundle.getString("phone");
            producvtname = bundle.getString("product_name");
            delivery_date = bundle.getString("delivery_date");
          //  Toast.makeText(this, ""+product_id+user_id+ CustomerDetailsFormActivity.statepin+name+email+address1+address2+pin+state+
                   // quantity+payment_type+delivery_time+delivery_date+price+distributor_ain+phone, Toast.LENGTH_SHORT).show();


            if (email.equals(""))
            {
                email="null";
            }
        }

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                happy_code=happycodeET.getText().toString();

                if (happy_code.equals(""))
                {
                    Toast.makeText(OtpVerificationActivity.this, "Please Enter Happy Code", Toast.LENGTH_SHORT).show();
                }

                else {

                    if (type.equals("1")) {
                        verifyOtp();
                    }
                    else {
                           if(type.equals("2")) {
                               if (otp.equals(happy_code)) {
                                  Toast.makeText(OtpVerificationActivity.this, "verify", Toast.LENGTH_SHORT).show();
                                   completeOrder();
                               }
                               else {
                                   Toast.makeText(OtpVerificationActivity.this, "Otp did not match", Toast.LENGTH_SHORT).show();
                               }
                           }
                        else {
                            Toast.makeText(OtpVerificationActivity.this, "otp does not match", Toast.LENGTH_SHORT).show();
                        }
                        if(type.equals("3")){
                            if(otp.equals(happy_code)){
                                intaled();
                            }
                            else {
                                Toast.makeText(OtpVerificationActivity.this, "Otp Did not match", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

            }
        });
    }

    private void intaled() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        String url ="https://www.headwaygms.com/api/mark-as-installation?order_id="+orderid;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);
                            String message = obj.getString("msg");
                            //    Toast.makeText(.this, "" + message, Toast.LENGTH_SHORT).show();


                            if (message.equals("Success Installation")) {
                                Toast.makeText(OtpVerificationActivity.this, "Product Instaled Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(OtpVerificationActivity.this, SalesDashbBoard.class);

                                startActivity(intent);
                                progressDialog.dismiss();

                            } else {
                                Toast.makeText(OtpVerificationActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(OtpVerificationActivity.this, "exception", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OtpVerificationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("order_id", orderid);


                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(OtpVerificationActivity.this).addToRequestQueue(stringRequest);


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


    private void verifyOtp()
    {
      progressDialog =new ProgressDialog(this);
        progressDialog.setIcon(R.drawable.headwaygmslogo);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        String url ="https://www.headwaygms.com/api/otp-verified?phone_number="+phone+"&otp="+happy_code;
        //Toast.makeText(this, "phone"+phone+happy_code, Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);
                            String message = obj.getString("msg");
                            String status = obj.getString("status");

                            if (status.equals("200")) {

                                 placeOrder();


                            } else {
                                Toast.makeText(OtpVerificationActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(OtpVerificationActivity.this, "exception", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OtpVerificationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        VolleySingleton.getInstance(OtpVerificationActivity.this).addToRequestQueue(stringRequest);

    }
    private void  placeOrder()
    {
       // Toast.makeText(this, "ain="+Registeration_Activity.ain, Toast.LENGTH_SHORT).show();

        RequestQueue queue = Volley.newRequestQueue(this);

        String url ="https://www.headwaygms.com/api/place-sales-order";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,

                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject=new JSONObject(response);

                            String message=jsonObject.getString("msg");

                            if (message.equals("Success"))
                            {

                                JSONObject object=jsonObject.getJSONObject("order");

                                String orderId=  object.getString("order_id");
                                Intent intent=new Intent(OtpVerificationActivity.this,OrderDetailsActivity.class);
                                intent.putExtra("Orderid",orderId);
                                intent.putExtra("intent_type","sales");
                                intent.putExtra("backtype","1");
                                startActivity(intent);
                                finish();
                                progressDialog.dismiss();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(OtpVerificationActivity.this, "hello="+e.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("product_id", product_id);
                params.put("name", name);
                params.put("email", email);
                params.put("phone", phone);
                params.put("address1", address1);
                params.put("address2", address2);
                params.put("city", city);
                params.put("state", CustomerDetailsFormActivity.statepin);
                params.put("pin", pin);
                params.put("alternate_phone", alternate_phone);
                params.put("quantity",quantity);
                params.put("payment_type",payment_type);
                params.put("delivery_time", delivery_time);
                params.put("delivery_date", delivery_date);
                params.put("price",price);
                params.put("user_id",user_id);
                params.put("distributor_ain",distributor_ain);
                params.put("product_name",producvtname);
                params.put("applicant_id", Registeration_Activity.ain);

                return params;
            }
        };

        queue.getCache().clear();
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    private void  completeOrder()
    {
       // Toast.makeText(this, "orderid"+orderid, Toast.LENGTH_SHORT).show();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        String url ="https://www.headwaygms.com/api/mark-as-delivered?order_id="+orderid;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);
                            String message = obj.getString("msg");
                            String status = obj.getString("status");
                            Toast.makeText(OtpVerificationActivity.this, "" +response, Toast.LENGTH_LONG).show();


                            if (status.equals("200")) {
                                    Toast.makeText(OtpVerificationActivity.this, "Product id Deleverd Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(OtpVerificationActivity.this, SalesDashbBoard.class);

                                startActivity(intent);
                                progressDialog.dismiss();

                            } else {
                                Toast.makeText(OtpVerificationActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(OtpVerificationActivity.this, "exception", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OtpVerificationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("order_id", orderid);


                return params;
            }
        };

        VolleySingleton.getInstance(OtpVerificationActivity.this).addToRequestQueue(stringRequest);


    }

}
