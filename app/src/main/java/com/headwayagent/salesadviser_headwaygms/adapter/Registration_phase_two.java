package com.headwayagent.salesadviser_headwaygms.adapter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.VolleySingleton;
import com.headwayagent.salesadviser_headwaygms.MainActivity;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.Registeration_Activity;
import com.headwayagent.salesadviser_headwaygms.Registration_phase_three;
import com.headwayagent.salesadviser_headwaygms.SalesDashbBoard;
import com.headwayagent.salesadviser_headwaygms.SharedPreference.SharedPrefManager;
import com.headwayagent.salesadviser_headwaygms.activity.SalesDashBoardActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import util.VolleyMultipartRequest;

public class Registration_phase_two extends AppCompatActivity {
    EditText  nomineenameEt,
            relationshpEt, nomineedobEt, bankholdernameEt, banknameEt, banacountnoEt, branchEt, ifscEt,acounttypeet;
    Calendar calendar;
    int year, date, month;
    String account_type="";
    Button registerbutton;
    private String ainNumber;
    private  RadioGroup radioGroup;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_phase_two);

        nomineenameEt = findViewById(R.id.nomeniname);
        relationshpEt = findViewById(R.id.relationship);
        nomineedobEt = findViewById(R.id.nomeneedob);
         bankholdernameEt = findViewById(R.id.AccountHolderName);
         banknameEt = findViewById(R.id.BankName);
         banacountnoEt = findViewById(R.id.BankAccountNumber);
         branchEt = findViewById(R.id.Branch);
         ifscEt = findViewById(R.id.ifsccode);
        calendar = Calendar.getInstance();
        date = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        radioGroup=findViewById(R.id.groupradio);
        registerbutton = findViewById(R.id.submit_datafile);


        ainNumber = SharedPrefManager.getInstance(getApplicationContext()).getUser().getAin_number();

        radioGroup.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() {
                    @Override

                    // The flow will come here when
                    // any of the radio buttons in the radioGroup
                    // has been clicked

                    // Check which radio button has been clicked
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId)
                    {
                        RadioButton
                                radioButton
                                = (RadioButton)group
                                .findViewById(checkedId);
                        account_type=  radioButton.getText().toString();
                        Toast.makeText(Registration_phase_two.this, ""+account_type, Toast.LENGTH_SHORT).show();

                    }
                });

        nomineedobEt.setText(date + "-" + month + "-" + year);

        nomineedobEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Registration_phase_two.this,
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

                String date =+ day + "/"+ month + "/"  + year;
                nomineedobEt.setText(date);
            }
        };
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();

            }
        });

    }

    private void register(){


        final String nominee_name = nomineenameEt.getText().toString();
         final String relationship = relationshpEt.getText().toString();
         final String namineedob = nomineedobEt.getText().toString();
         final String acountholdername = bankholdernameEt.getText().toString();
        final String bankename = banknameEt.getText().toString();
         final String branch = branchEt.getText().toString();
         final String ifsc = ifscEt.getText().toString();
         final String acountno = banacountnoEt.getText().toString();

       // final String sate = stateEt.getText().toString();
         int selectedId = radioGroup.getCheckedRadioButtonId();


        // Toast.makeText(this, ""+account_type, Toast.LENGTH_SHORT).show();





            // Toast.makeText(this, "" +account_type, Toast.LENGTH_SHORT).show();
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setIcon(R.drawable.headwaygmslogo);
            progressDialog.setTitle("Please wait.....");
           // progressDialog.setMessage("Please wait......");
            progressDialog.show();

            String url = "https://www.headwaygms.com/test/api/register-cda-step-two";


            VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
                @Override
                public void onResponse(NetworkResponse response) {
                    String resultResponse = new String(response.data);
                    try {
                        JSONObject result = new JSONObject(resultResponse);
                        // Toast.makeText(Registeration_Activity.this, ""+result, Toast.LENGTH_SHORT).show();
                        String status = result.getString("status");
                        String message = result.getString("msg");

                        if (status.equals("201")) {
                            Toast.makeText(Registration_phase_two.this, ""+message, Toast.LENGTH_SHORT).show();
                            // Toast.makeText(Registeration_Activity.this, "msg"+resultResponse, Toast.LENGTH_SHORT).show();
                            Intent inten=new Intent(Registration_phase_two.this, Registration_phase_three.class);
                            inten.putExtra("type","success");
                            startActivity(inten);
                            progressDialog.dismiss();
                        } else {
                            Log.i("Unexpected", message);
                            progressDialog.dismiss();
//                            Intent  inten=new Intent(Registeration_Activity.this,MainActivity.class);
//                            inten.putExtra("type","success");
//                            startActivity(inten);
                            Toast.makeText(Registration_phase_two.this, ""+message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Registration_phase_two.this, "something went wrong"+e, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    NetworkResponse networkResponse = error.networkResponse;
                    String errorMessage = "Unknown error";
                    if (networkResponse == null) {
                        if (error.getClass().equals(TimeoutError.class)) {
                            errorMessage = "Request timeout";
                            progressDialog.dismiss();
                            Toast.makeText(Registration_phase_two.this, "Timeout Error", Toast.LENGTH_SHORT).show();
                        } else if (error.getClass().equals(NoConnectionError.class)) {
                            errorMessage = "Failed to connect server";
                            progressDialog.dismiss();
                            Toast.makeText(Registration_phase_two.this, "Server Not responding", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        String result = new String(networkResponse.data);
                        try {
                            Toast.makeText(Registration_phase_two.this, ""+result, Toast.LENGTH_SHORT).show();
                            // JSONObject response = new JSONObject(result);
//                            String status = response.getString("status");
//                            String message = response.getString("msg");

                            String status="";
                            String message="";

                            if (status.equals("200"))
                            {
                                //Toast.makeText(Registeration_Activity.this, "msg", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(Registeration_Activity.this,MainActivity.class));
                                // finish();
                                progressDialog.dismiss();
                            }

                            if (networkResponse.statusCode == 404) {
                                errorMessage = "Resource not found";
                                progressDialog.dismiss();
                                Toast.makeText(Registration_phase_two.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                            } else if (networkResponse.statusCode == 401) {
                                errorMessage = message+" Please login again";
                                progressDialog.dismiss();
                                Toast.makeText(Registration_phase_two.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                            } else if (networkResponse.statusCode == 400) {
                                errorMessage = message+ " Check your inputs";
                                progressDialog.dismiss();
                                Toast.makeText(Registration_phase_two.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                            } else if (networkResponse.statusCode == 500) {
                                errorMessage = message+" Something is getting wrong";
                                progressDialog.dismiss();
                                Toast.makeText(Registration_phase_two.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(Registration_phase_two.this, "Something went wrong"+e, Toast.LENGTH_SHORT).show();

                        }
                    }
                    Log.i("Error", errorMessage);
                    progressDialog.dismiss();
                    Toast.makeText(Registration_phase_two.this, ""+errorMessage, Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            }) {
//                @Override
//                protected Map<String, DataPart> getByteData() {
//                    Map<String, DataPart> params = new HashMap<>();
//                    params.put("pic",new DataPart("pan_doc"+extension ,Pic,type));
//                    params.put("pan_doc",new DataPart("pan_doc"+extension ,Pancard,type));
//                    params.put("aadhaar_doc",new DataPart("pan_doc"+extension, Adhardata,type));
//                    params.put("cheque_doc",new DataPart("pan_doc"+extension, Cheque,type));
//                    params.put("sign_doc",new DataPart("pan_doc"+extension, Sign,type));
//                    params.put("other_doc",new DataPart("pan_doc"+extension, Other,type));
//                    return params;



                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                     params.put("nominee_name",nominee_name);
                    params.put("relationship", relationship);
                     params.put("nominee_dob", namineedob);
                     params.put("account_holder", acountholdername);
                     params.put("bank", bankename);
                     params.put("account_number", acountno);
                     params.put("branch", branch);
                     params.put("ifsc", ifsc);
                     params.put("account_type",account_type);
                     params.put("ain",ainNumber);
                    return params;
                }

            };


            VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();

            multipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);

        }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent =  new Intent(Registration_phase_two.this, SalesDashbBoard.class);
        startActivity(intent);
        finish();
    }

}

