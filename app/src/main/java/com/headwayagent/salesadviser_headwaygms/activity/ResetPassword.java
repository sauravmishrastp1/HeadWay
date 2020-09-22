package com.headwayagent.salesadviser_headwaygms.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.VolleySingleton;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.Registeration_Activity;
import com.headwayagent.salesadviser_headwaygms.SalesDashbBoard;
import com.headwayagent.salesadviser_headwaygms.adapter.Registration_phase_two;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResetPassword extends AppCompatActivity {

    EditText oldpasset, newpasset, confpasdet,REGISTERAIN;
    Button reserbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        oldpasset = findViewById(R.id.oldpass);
        newpasset = findViewById(R.id.newpassword);
        confpasdet = findViewById(R.id.confpass);
        reserbtn = findViewById(R.id.loginbtn);
        REGISTERAIN = findViewById(R.id.ain);
        reserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restepass();
            }
        });
        REGISTERAIN.setText("HGMS-CDA-");
    }

    private void restepass() {
        final  String enterregisterain = REGISTERAIN.getText().toString();
        final String oldpassetet = oldpasset.getText().toString();
        final String newpassetet = newpasset.getText().toString();
        final String confpassetet = confpasdet.getText().toString();

        if (TextUtils.isEmpty(enterregisterain)) {
            REGISTERAIN.setError("Please enter your register cad-code");
            REGISTERAIN.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(oldpassetet)) {
            oldpasset.setError("Please enter your old password");
            oldpasset.requestFocus();
            return;
        } else if (TextUtils.isEmpty(newpassetet)) {
            newpasset.setError("Please enter new password");
            newpasset.requestFocus();

        } else if (TextUtils.isEmpty(confpassetet)) {
            confpasdet.setError("Please confrim  password");
            confpasdet.requestFocus();

        } else {

            //Toast.makeText(this, "" + account_type, Toast.LENGTH_SHORT).show();
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setIcon(R.drawable.headwaygmslogo);
            progressDialog.setTitle("Please wait.....");
            // progressDialog.setMessage("register wait......");
            progressDialog.show();

            String url = "https://www.headwaygms.com/api/change-password";


            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                //converting response to json object

                                JSONObject obj = new JSONObject(response);

                                String message = obj.getString("msg");
                                String status = obj.getString("status");
                                // ain = obj.getString("ain");


                                if (status.equals("200")) {


                                    Toast.makeText(ResetPassword.this, "" + message, Toast.LENGTH_SHORT).show();
                                    // Toast.makeText(Registeration_Activity.this, "msg"+obj, Toast.LENGTH_SHORT).show();
                                    Intent inten = new Intent(ResetPassword.this, SalesDashbBoard.class);

                                    startActivity(inten);
                                    progressDialog.dismiss();
                                } else {
                                    Toast.makeText(getApplicationContext(), "" + obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(ResetPassword.this, "Something went wrong" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Server Not Respondin Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }) {
//
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("old_pwd", oldpassetet);
                    params.put("new_pwd", newpassetet);
                    params.put("cnf_pwd", confpassetet);
                    params.put("ain",enterregisterain);
                    return params;
                }

            };


            VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);

        }

    }
}
