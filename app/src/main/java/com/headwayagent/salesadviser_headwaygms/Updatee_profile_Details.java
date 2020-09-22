package com.headwayagent.salesadviser_headwaygms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.VolleySingleton;
import com.headwayagent.salesadviser_headwaygms.SharedPreference.SharedPrefManager;
import com.headwayagent.salesadviser_headwaygms.activity.MyProfileActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import util.FileUtils;
import util.VolleyMultipartRequest;

public class Updatee_profile_Details extends AppCompatActivity {

    private EditText dateofbirth,acounttypeet,name ,acountholderet,phoneet,addresset,emailet,acountnumberet,acountholdername,banket,ifscet,nomemenamet,relationship,dob;
    private ImageView imageView;
    private static final int GALLERY_IMAGE = 1;
    private static final int GALLERY_SIGN = 2;
    private static final int GALLERY_ADHAR = 3;
    private static final int GALLERY_OTHER = 4;
    private static final int GALLERY_PAN = 5;
    private static final int GALLERY_CHEQUE = 6;
    private String filePathpic="null";
    private String filePatadhar="null";
    private String filePathpan="null";
    private String filePathcheque="null";
    private String filePathsign="";
    private String filePathother="";
    private String extension;
    private String ain,type="";
    private byte pic[]="00.00.00".getBytes();
    private byte Pancard[]="00.00.00".getBytes();
    private byte Adhardata[]="00.00.00".getBytes();
    private byte Cheque[]="00.00.00".getBytes();
    private byte Other[];
    private byte Sign[];
    private Button updatel;
    private String acounttypenull ="";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener getmDateSetListener;
    private String empdob ="null";
    private String dodetnull ="null";
    private ImageView imageprofile,imagepancard,imagecheque,imageother,imageaddharcard,imagesign;
    private TextView pancard,cheque,addhar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatee_profile__details);
        name = findViewById(R.id.fullname);
        phoneet = findViewById(R.id.phoneno);
        addresset = findViewById(R.id.address);
        emailet = findViewById(R.id.email1);
        acountnumberet = findViewById(R.id.city);
        banket = findViewById(R.id.pincode);
        acountholderet = findViewById(R.id.Statee);
        ifscet = findViewById(R.id.email);
        nomemenamet= findViewById(R.id.dob);
        dateofbirth=findViewById(R.id.DateofBirteh);
        relationship = findViewById(R.id.pancardno);
        dob= findViewById(R.id.mobileno);
        imageView =findViewById(R.id.dp);
        updatel = findViewById(R.id.submit_datafile);
        acounttypeet = findViewById(R.id.acountype);
        phoneet.setText(MyProfileActivity.phone);
        addresset.setText(MyProfileActivity.address);
        nomemenamet.setText(MyProfileActivity.nomineename);
       name.setText(MyProfileActivity.username);
        emailet.setText(MyProfileActivity.useremail);
        acountnumberet.setText(MyProfileActivity.Ac);
        acountholderet.setText(MyProfileActivity.acholdername);
        banket.setText(MyProfileActivity.bankname);
        ifscet.setText(MyProfileActivity.ifsc);
        relationship.setText(MyProfileActivity.relationship);
        acounttypeet.setText(MyProfileActivity.Accounttype);
        dateofbirth.setText(MyProfileActivity.userDateofBirth);
        dob.setText(MyProfileActivity.dob);
        imageprofile = findViewById(R.id.pickdoc);
        imagepancard = findViewById(R.id.pandoc);
        imagecheque = findViewById(R.id.chequdoc);
        imageother = findViewById(R.id.otherdoc);
        imageaddharcard = findViewById(R.id.adhardoc);
        imagesign = findViewById(R.id.sign);
        pancard = findViewById(R.id.pancard);
        cheque = findViewById(R.id.cheque);
        addhar = findViewById(R.id.adhar);

       // nominee_name_tv.setText(nomineename);


        Bundle bundle=getIntent().getExtras();

        if (bundle!=null)
        {
            ain=bundle.getString("ain");
        }

        imagepancard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosepanr();
            }
        });
        imagecheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chossecheuqe();
            }
        });

        imageaddharcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  chooseaadhar();
            }
        });


        updatel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateprofile();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            choosepic();
        }
    });
        dateofbirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal1 = Calendar.getInstance();
                int yearr = cal1.get(Calendar.YEAR);
                int monthr = cal1.get(Calendar.MONTH);
                int dayr = cal1.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Updatee_profile_Details.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        yearr,monthr,dayr);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
          mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePickerr, int yearr, int monthr, int dayr) {
                monthr = monthr + 1;
                //  Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                  empdob =+ dayr + "/"+ monthr + "/"  + yearr;
                dateofbirth.setText(empdob);
               // Toast.makeText(Updatee_profile_Details.this, ""+dateofbirth, Toast.LENGTH_SHORT).show();
             }
        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Updatee_profile_Details.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        getmDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }


        });
         getmDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                //  Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                 dodetnull =+ day + "/"+ month + "/"  + year;
                dob.setText(dodetnull);

            }
        };
            }


    public void chooseaadhar () {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_ADHAR);


    }
    public void choosepanr () {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_PAN);


    }
    public void chossecheuqe () {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_CHEQUE);


    }

    public void choosepic () {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_IMAGE);


    }
    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            //Toast.makeText(this, "you selected="+filePathpic+uri, Toast.LENGTH_SHORT).show();

            try {
                filePathpic = FileUtils.getPath(this, uri);
                // imageprofile.setImageDrawable(filePathpic);
                type = FileUtils.getMimeType(this, uri);
                extension= (String) FileUtils.getExtension(String.valueOf(uri));


                try {
                    ContentResolver cr = getBaseContext().getContentResolver();
                    InputStream inputStream = cr.openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeFile(filePathpic);
                    imageView.setImageBitmap(bitmap);
                    // Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    pic = baos.toByteArray();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                }
            } catch (Exception e) {
                e.printStackTrace();
                //  Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == GALLERY_PAN && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            // Toast.makeText(this, "you selected="+filePathpan+uri, Toast.LENGTH_SHORT).show();

            try {
                //getting image from gallery

                filePathpan = FileUtils.getPath(this, uri);
                type = FileUtils.getMimeType(this, uri);
                //  choosepancard.setText(filePathpan);
                extension = (String) FileUtils.getExtension(String.valueOf(uri));
                try {
                    ContentResolver cr = getBaseContext().getContentResolver();
                    InputStream inputStream = cr.openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeFile(filePathpan);
                    // Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();
                    imagepancard.setImageBitmap(bitmap);
                    pancard.setTextColor(Color.GREEN);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    Pancard = baos.toByteArray();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                }


            } catch (Exception e) {
                e.printStackTrace();
                //  Toast.makeText(this, "somthin" Toast.LENGTH_SHORT).show();
            }
        }
            else if (requestCode == GALLERY_CHEQUE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uriii = data.getData();
            //  Toast.makeText(this, "you selected="+filePathcheque+uri, Toast.LENGTH_SHORT).show();

            try {
                //getting image from gallery
                filePathcheque = FileUtils.getPath(this, uriii);
                // choosecancle.setText(filePathcheque);
                type = FileUtils.getMimeType(this, uriii);
                extension = (String) FileUtils.getExtension(String.valueOf(uriii));
//                        Toast.makeText(this, "path="+filePathcheque+uri, Toast.LENGTH_SHORT).show();
//                        type=FileUtils.getMimeType(this,uri);
//                        extension= (String) FileUtils.getExtension(String.valueOf(uri));
//
//                        Bitmap src = BitmapFactory.decodeFile(filePathcheque);
//                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                        src.compress(Bitmap.CompressFormat.PNG,100,baos);
//                        Cheque=baos.toByteArray();
                // Toast.makeText(this, "" + Pic + Adhardata + Cheque + Other + Sign + Pancard, Toast.LENGTH_SHORT).show();
                try {
                    ContentResolver cr = getBaseContext().getContentResolver();
                    InputStream inputStream = cr.openInputStream(uriii);
                    Bitmap bitmap = BitmapFactory.decodeFile(filePathcheque);
                    // Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();
                    imagecheque.setImageBitmap(bitmap);
                    cheque.setTextColor(Color.GREEN);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    Cheque = baos.toByteArray();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                }

                //Setting image to ImageVie

                //   Toast.makeText(this, "You selected=" + Cheque, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
                 else if (requestCode == GALLERY_ADHAR && resultCode == RESULT_OK && data != null && data.getData() != null) {
                    Uri uriiii = data.getData();

                    // Toast.makeText(this, "you selected="+filePatadhar+uri, Toast.LENGTH_SHORT).show();
                    try {
                        //getting image from gallery
                        filePatadhar = FileUtils.getPath(this,uriiii);
                        //chooseadhhar.setText(filePatadhar);
                        type=FileUtils.getMimeType(this,uriiii);
                        extension= (String) FileUtils.getExtension(String.valueOf(uriiii));
                        try {
                            ContentResolver cr = getBaseContext().getContentResolver();
                            InputStream inputStream = cr.openInputStream(uriiii);
                            Bitmap bitmap = BitmapFactory.decodeFile(filePatadhar);
                            // Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();
                            imageaddharcard.setImageBitmap(bitmap);
                            addhar.setTextColor(Color.GREEN);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                            Adhardata = baos.toByteArray();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }



private void updateprofile(){
    final String fullnamenull = name.getText().toString();
    final String phonetnull = phoneet.getText().toString();
    final String addetnull = addresset.getText().toString();
    final String emaietnull = emailet.getText().toString();
    final String accountetnull = acountnumberet.getText().toString();
    final String acountholderettnull = acountholderet.getText().toString();
    final String banetnull = banket.getText().toString();
    final String Ifscnull = ifscet.getText().toString();
    final String Relationshipnull = relationship.getText().toString();
    final  String nomenamenull = nomemenamet.getText().toString();
    dodetnull = dob.getText().toString();
    empdob = dateofbirth.getText().toString();
    acounttypenull = acounttypeet.getText().toString();
  //  Toast.makeText(this, ""+dodetnull+empdob, Toast.LENGTH_SHORT).show();


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.drawable.headwaygmslogo);
        progressDialog.setTitle("Update.....");
        progressDialog.setMessage("Please wait......");
        progressDialog.show();


        String url = "https://www.headwaygms.com/api/profile-update";


        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    // Toast.makeText(Registeration_Activity.this, ""+result, Toast.LENGTH_SHORT).show();
                    String status = result.getString("status");
                    String message = result.getString("msg");

                    if (message.equals("Success")) {

                        Intent intent=new Intent(Updatee_profile_Details.this, MyProfileActivity.class);
                        intent.putExtra("type","profile");
                        intent.putExtra("ain",ain);
                        startActivity(intent);
                        finish();
                        progressDialog.dismiss();
                    } else {
                        Log.i("Unexpected", message);
                        progressDialog.dismiss();
//                            Intent  inten=new Intent(Registeration_Activity.this,MainActivity.class);
//                            inten.putExtra("type","success");
//                            startActivity(inten);
                        Toast.makeText(Updatee_profile_Details.this, ""+message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Updatee_profile_Details.this, "something went wrong"+e, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error"+networkResponse;
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                        progressDialog.dismiss();
                        Toast.makeText(Updatee_profile_Details.this, "Timeout Error", Toast.LENGTH_SHORT).show();
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                        progressDialog.dismiss();
                        Toast.makeText(Updatee_profile_Details.this, "Server Not responding", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        Toast.makeText(Updatee_profile_Details.this, ""+result, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(Updatee_profile_Details.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message+" Please login again";
                            progressDialog.dismiss();
                            Toast.makeText(Updatee_profile_Details.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message+ " Check your inputs";
                            progressDialog.dismiss();
                            Toast.makeText(Updatee_profile_Details.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message+" Something is getting wrong";
                            progressDialog.dismiss();
                            Toast.makeText(Updatee_profile_Details.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(Updatee_profile_Details.this, "Something went wrong"+e, Toast.LENGTH_SHORT).show();

                    }
                }
                Log.i("Error", errorMessage);
                progressDialog.dismiss();
                Toast.makeText(Updatee_profile_Details.this, ""+errorMessage, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("pic", new DataPart("pan_doc" + extension, pic, type));
                params.put("aadhaar_doc", new DataPart("pan_doc" + extension, Adhardata, type));
                params.put("pan_doc", new DataPart("pan_doc" + extension, Pancard, type));
                params.put("cheque_doc", new DataPart("pan_doc" + extension, Cheque, type));

                return params;


            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("ain",ain);
                params.put("nominee_name",nomenamenull);
                params.put("relationship",Relationshipnull);
                params.put("nominee_dob",dodetnull);
                params.put("account_holder",acountholderettnull);
                params.put("bank",banetnull);
                params.put("account_number",accountetnull);
                params.put("branch","");
                params.put("ifsc", Ifscnull);
                params.put("account_type",acounttypenull);
                params.put("dob",empdob);
                params.put("email",emaietnull);
                params.put("address",addetnull);
                params.put("name",fullnamenull);
                params.put("phone",phonetnull);
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
        Intent intent =  new Intent(Updatee_profile_Details.this, MyProfileActivity.class);
        intent.putExtra("type","profile");
        startActivity(intent);
        finish();
    }


}