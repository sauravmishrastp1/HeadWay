package com.headwayagent.salesadviser_headwaygms.activity;

import android.Manifest;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.BaseUrl;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.VolleySingleton;
import com.headwayagent.salesadviser_headwaygms.MainActivity;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.Registration_phase_three;
import com.headwayagent.salesadviser_headwaygms.SalesDashbBoard;
import com.headwayagent.salesadviser_headwaygms.SharedPreference.SharedPrefManager;
import com.headwayagent.salesadviser_headwaygms.Updatee_profile_Details;
import com.headwayagent.salesadviser_headwaygms.adapter.TotalDownlineAdapter;
import com.headwayagent.salesadviser_headwaygms.models.TotalDownlineModel;
import com.headwayagent.salesadviser_headwaygms.models.UserDetails;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fragment.Inactive_Fragement;
import util.FileUtils;
import util.VolleyMultipartRequest;

public class MyProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button checkout_btn, downloadOfferlater;
    private String ain_number, profileimg_url;
    private TextView userdateofbirth,acounttype,Aintv, profilestatus_tv, phonetv, addess_tv, usernametv, useremailtv,Teamsize;
    private TextView AcTv, AcHolderNameTv, bankNameTv, IfscTv;
    private TextView nominee_name_tv, relationshiptv, dob_tv;
    private ImageView profile_imageview;
    private Uri Download_Uri;
    private String rankstars;
    private long downloadID;
    ArrayList<Long> list = new ArrayList<>();
    private DownloadManager downloadManager;
    private BroadcastReceiver onComplete;
    public static String type="";
    public static String ain;
    private String offerlater;
    private TextView designationTv;
    public static String userDateofBirth,Accounttype,profilestatus, phone,designation, address, nomineename, username, useremail, Ac, acholdername, bankname, ifsc,teamsize;
    public static  String relationship, dob;
    private ProgressBar progressBar;
   private  static  int CAMERA_PERMISSION_CODE =100;
   private static int STORAGE_PERMISSION_CODE =101;
   private  View view;
   private ImageView profileimg;
   private int GALLERY_IMAGE =1;
    private String filePathpic="";
    private String extension;
    private Button update;
    private RatingBar ratingbar;
    private Button activeprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        checkout_btn = findViewById(R.id.checkout_button);
        Aintv = findViewById(R.id.ain_tv);
        Teamsize = findViewById(R.id.aintv);
        profilestatus_tv = findViewById(R.id.profile_status_tv);
        phonetv = findViewById(R.id.phone_tv);
        addess_tv = findViewById(R.id.addresstv);
        usernametv = findViewById(R.id.user_name);
        useremailtv = findViewById(R.id.emailtv);
        AcTv = findViewById(R.id.bank_account_tv);
        AcHolderNameTv = findViewById(R.id.account_holder_name_tv);
        bankNameTv = findViewById(R.id.bank_name_tv);
        designationTv=findViewById(R.id.designation);
        IfscTv = findViewById(R.id.ifsc_tv);
        nominee_name_tv = findViewById(R.id.nominee_namee_tv);
        relationshiptv = findViewById(R.id.relationship_tv);
        dob_tv = findViewById(R.id.dob_tv);
        profile_imageview = findViewById(R.id.profile_image);
        downloadOfferlater = findViewById(R.id.dwnld_offer_letter_btn);
        progressBar = findViewById(R.id.progressBar);
        view = findViewById(R.id.teamzsizelayout);
        profileimg = findViewById(R.id.profile_image);
        update = findViewById(R.id.updatebutoon);
        acounttype = findViewById(R.id.actype);
        ratingbar = findViewById(R.id.rating3);
        userdateofbirth = findViewById(R.id.dobtv);
        activeprofile = findViewById(R.id.activeprofille);
      //  getProfileData();
        ain_number=SharedPrefManager.getInstance(this).getUser().getAin_number();
       // Toast.makeText(this, ""+ain, Toast.LENGTH_SHORT).show();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this, Updatee_profile_Details.class);
              intent.putExtra("ain",ain);
                startActivity(intent);
            }
        });
        activeprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this, MainActivity.class);
                intent.putExtra("ain", Inactive_Fragement.Ain);
                intent.putExtra("type","success");
                startActivity(intent);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               Intent intent=new Intent(MyProfileActivity.this,Activ_Inactiv.class);
//               intent.putExtra("ain",ain);
//               intent.putExtra("type","teamsize");
//               startActivity(intent);
            }
        });
        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosepic();
            }
        });


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            type = bundle.getString("type");
            ain = bundle.getString("ain");

        }

        if (type.equals("Active")) {
            downloadOfferlater.setVisibility(View.GONE);
            checkout_btn.setVisibility(View.GONE);
            update.setVisibility(View.GONE);
            activeprofile.setVisibility(View.GONE);
            getProfiledata();
        }
        if (type.equals("InActive")) {
            downloadOfferlater.setVisibility(View.GONE);
            checkout_btn.setVisibility(View.GONE);
            update.setVisibility(View.GONE);
            activeprofile.setVisibility(View.VISIBLE);
            getProfiledata();
        }

        else if (type.equals("indirect"))
        {
            downloadOfferlater.setVisibility(View.GONE);
            checkout_btn.setVisibility(View.GONE);
           // Toast.makeText(this, "ain=" + ain, Toast.LENGTH_SHORT).show();
            getProfiledata();
        }else if(type.equals("profile")){
            activeprofile.setVisibility(View.GONE);

            getProfileData();

//            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//            UserDetails userDetails = SharedPrefManager.getInstance(this).getUser();
//            ain_number = userDetails.getAin_number();
//            profilestatus = userDetails.getProfile_status();
//            phone = userDetails.getPhone();
//            address = userDetails.getAddress();
//            nomineename = userDetails.getNominee_name();
//            username = userDetails.getFull_name();
//            useremail = userDetails.getEmail();
//            designation=userDetails.getDesignation();
//            Ac = userDetails.getAccount_number();
//            acholdername = userDetails.getAccount_holder_name();
//            bankname = userDetails.getBank_name();
//            ifsc = userDetails.getIfsc();
//            relationship = userDetails.getNomibee_relation();
//            dob = userDetails.getNominee_dob();
//            profileimg_url = userDetails.getProfile_image();
//            offerlater = userDetails.getOfferLater();
//            Aintv.setText(ain_number);
//            if (profilestatus.equals("1")) {
//                profilestatus_tv.setText("Not Approved");
//            } else {
//                profilestatus_tv.setText("Approved");
//
//            }
//            phonetv.setText(phone);
//            addess_tv.setText(address);
//            nominee_name_tv.setText(nomineename);
//            usernametv.setText(username);
//            useremailtv.setText(useremail);
//            AcTv.setText(Ac);
//            AcHolderNameTv.setText(acholdername);
//            bankNameTv.setText(bankname);
//            IfscTv.setText(ifsc);
//            designationTv.setText(designation);
//            relationshiptv.setText(relationship);
//            dob_tv.setText(dob);
//            nominee_name_tv.setText(nomineename);
//            // sponser_nameTv.setText(sponserName);
//            // sponserId_tv.setText(sponserId);


            Picasso.get()
                    .load("https://www.headwaygms.com/" + profileimg_url)
                    .placeholder(R.drawable.profileimageicon)
                    .error(R.drawable.profileimageicon)
                    .into(profile_imageview);

            checkout_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPrefManager.getInstance(getApplicationContext()).logout();
                }


            });


            this.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

            downloadOfferlater.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View view) {

              checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE);



                }
            });


            onComplete = new BroadcastReceiver() {

                public void onReceive(Context ctxt, Intent intent) {

                    // get the refid from the download manager
                    long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

// remove it from our list
                    list.remove(referenceId);

// if list is empty means all downloads completed
                    if (list.isEmpty()) {

// show a notification
                        Log.e("INSIDE", "" + referenceId);
                        NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(MyProfileActivity.this)
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setContentTitle("Offer Later's" + username)
                                        .setContentText("Download completed");


                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(455, mBuilder.build());


                    }

                }
            };


        }
    }

    private void updateprofilepic(){
        String url = "https://www.headwaygms.com/api/profile-update?ain="+ain;


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
                        Toast.makeText(MyProfileActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                        // Toast.makeText(Registeration_Activity.this, "msg"+resultResponse, Toast.LENGTH_SHORT).show();
                        Intent inten=new Intent(MyProfileActivity.this, MainActivity.class);
                        inten.putExtra("type","success");
                        startActivity(inten);
                       // progressDialog.dismiss();
                    } else {
                        Log.i("Unexpected", message);
                       // progressDialog.dismiss();
//                            Intent  inten=new Intent(Registeration_Activity.this,MainActivity.class);
//                            inten.putExtra("type","success");
//                            startActivity(inten);
                        Toast.makeText(MyProfileActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MyProfileActivity.this, "something went wrong"+e, Toast.LENGTH_SHORT).show();
                   // progressDialog.dismiss();
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
                     //   progressDialog.dismiss();
                        Toast.makeText(MyProfileActivity.this, "Timeout Error", Toast.LENGTH_SHORT).show();
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                       // progressDialog.dismiss();
                        Toast.makeText(MyProfileActivity.this, "Server Not responding", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        Toast.makeText(MyProfileActivity.this, ""+result, Toast.LENGTH_SHORT).show();
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
                           // progressDialog.dismiss();
                        }

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                           // progressDialog.dismiss();
                            Toast.makeText(MyProfileActivity.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message+" Please login again";
                            //progressDialog.dismiss();
                            Toast.makeText(MyProfileActivity.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message+ " Check your inputs";
                           // progressDialog.dismiss();
                            Toast.makeText(MyProfileActivity.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message+" Something is getting wrong";
                         //   progressDialog.dismiss();
                            Toast.makeText(MyProfileActivity.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        //progressDialog.dismiss();
                      //  Toast.makeText(MyProfileActivity.this, "Something went wrong"+e, Toast.LENGTH_SHORT).show();

                    }
                }
                Log.i("Error", errorMessage);
             //   progressDialog.dismiss();
                Toast.makeText(MyProfileActivity.this, ""+errorMessage, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                //   params.put("pic", new DataPart());
             //   params.put("pan_doc", new DataPart("pan_doc" + extension, Pancard, type));
                // params.put("aadhaar_doc", new DataPart());
               // params.put("cheque_doc", new DataPart("pan_doc" + extension, Cheque, type));
                // params.put("sign_doc", new DataPart());
                // params.put("other_doc", new DataPart());
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
                //getting image from gallery
                //getting image from gallery
                filePathpic = FileUtils.getPath(this, uri);
                // imageprofile.setImageDrawable(filePathpic);
                type = FileUtils.getMimeType(this, uri);

                try {
                    ContentResolver cr = getBaseContext().getContentResolver();
                    InputStream inputStream = cr.openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeFile(filePathpic);
                    profileimg.setImageBitmap(bitmap);
                    // Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                }
            } catch (Exception e) {
                e.printStackTrace();
                //  Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(MyProfileActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(MyProfileActivity.this,
                    new String[] { permission },
                    requestCode);
        }
        else {

            downloadOfferleter();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MyProfileActivity.this,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(MyProfileActivity.this,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MyProfileActivity.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
                downloadOfferleter();
            } else {
                Toast.makeText(MyProfileActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    private void downloadOfferleter(){
        Download_Uri = Uri.parse("http://www.headwaygms.com/" + offerlater);
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle("offer later " + username + ".png");
        request.setDescription("Downloading " + "offer later" + ".pdf");
        request.setVisibleInDownloadsUi(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Headway/" + "/OFFER LATER/" + "/" + username + ".pdf");
        downloadID = downloadManager.enqueue(request);
        list.add(downloadID);

    }
    private void checkout(String ain_number) {

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setIcon(R.drawable.headwaygmslogo);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.URL_CHECKOUT+ain_number,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);
                            String message = obj.getString("msg");

                            if (message.equals("Success")) {

                                progressDialog.dismiss();
//                                SharedPrefManager.getInstance(MyProfileActivity.this).checkOut();
                                startActivity(new Intent(MyProfileActivity.this,LoginActivity.class));
                                finish();


                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Server Not Responding", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {

        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private void getProfileData()
    {
        progressBar.setVisibility(View.VISIBLE);
        String url ="https://www.headwaygms.com/api/view-profile?ain="+ain_number;
      //  Toast.makeText(this, ""+ain_number, Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);
                            String message = obj.getString("msg");
                            JSONObject object=obj.getJSONObject("profile");
                            String teamSize = obj.getString("team_size");
                            rankstars=obj.getString("rank");
                          //  Toast.makeText(MyProfileActivity.this, "ran="+rankstars, Toast.LENGTH_SHORT).show();

                            if (message.equals("Success")) {

                                profilestatus=object.getString("status");
                                phone=object.getString("phone");
                                address=object.getString("address");
                                Ac=object.getString("account_number");
                                acholdername=object.getString("account_holder");
                                bankname=object.getString("bank");
                                ifsc=object.getString("ifsc");
                                nomineename=object.getString("nominee_name");
                                relationship=object.getString("relationship");
                                dob=object.getString("nominee_dob");
                                userDateofBirth =object.getString("dob");
                                profileimg_url=object.getString("pic");
                                username=object.getString("full_name");
                                useremail=object.getString("email");
                                designation=object.getString("designation");
                                Accounttype = object.getString("account_type");
                                Teamsize.setText(teamSize);
                                userdateofbirth.setText(userDateofBirth);
                                ratingbar.setRating(Float.parseFloat(rankstars));
                                Aintv.setText(ain);
                                acounttype.setText(Accounttype);
                                phonetv.setText(phone);
                                addess_tv.setText(address);
                                nominee_name_tv.setText(nomineename);
                                usernametv.setText(username);
                                useremailtv.setText(useremail);
                                AcTv.setText(Ac);
                                AcHolderNameTv.setText(acholdername);
                                bankNameTv.setText(bankname);
                                IfscTv.setText(ifsc);
                                designationTv.setText(ain_number);
                                relationshiptv.setText(relationship);
                                dob_tv.setText(dob);
                                nominee_name_tv.setText(nomineename);
                                usernametv.setText(username);
                                useremailtv.setText(useremail);
                                Picasso.get()
                                        .load("https://www.headwaygms.com/" + profileimg_url)
                                        .placeholder(R.drawable.profileimageicon)
                                        .error(R.drawable.profileimageicon)
                                        .into(profile_imageview);

                                if (profilestatus.equals("1")) {
                                    profilestatus_tv.setText("Not Approved");
                                }
                                else {
                                    profilestatus_tv.setText("Approved");

                                }

                             progressBar.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                               progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MyProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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

    private void getProfiledata()
    {
        progressBar.setVisibility(View.VISIBLE);
        String url ="https://www.headwaygms.com/api/view-profile?ain="+ain;
        //  Toast.makeText(this, ""+ain_number, Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);
                            String message = obj.getString("msg");
                            JSONObject object=obj.getJSONObject("profile");
                            String teamSize = obj.getString("team_size");
                            rankstars=obj.getString("rank");
                           // Toast.makeText(MyProfileActivity.this, "ran="+rankstars, Toast.LENGTH_SHORT).show();

                            if (message.equals("Success")) {

                                profilestatus=object.getString("status");
                                phone=object.getString("phone");
                                address=object.getString("address");
                                Ac=object.getString("account_number");
                                acholdername=object.getString("account_holder");
                                bankname=object.getString("bank");
                                ifsc=object.getString("ifsc");
                                nomineename=object.getString("nominee_name");
                                relationship=object.getString("relationship");
                                dob=object.getString("nominee_dob");
                                profileimg_url=object.getString("pic");
                                username=object.getString("full_name");
                                useremail=object.getString("email");
                                designation=object.getString("designation");
                              userDateofBirth =object.getString("dob");
                                Accounttype = object.getString("account_type");
                                userdateofbirth.setText(userDateofBirth);
                                Teamsize.setText(teamSize);
                                Aintv.setText(ain);
                                ratingbar.setRating(Float.parseFloat(rankstars));
                                acounttype.setText(Accounttype);
                                phonetv.setText(phone);
                                addess_tv.setText(address);
                                nominee_name_tv.setText(nomineename);
                                usernametv.setText(username);
                                useremailtv.setText(useremail);
                                AcTv.setText(Ac);
                                AcHolderNameTv.setText(acholdername);
                                bankNameTv.setText(bankname);
                                IfscTv.setText(ifsc);
                                designationTv.setText(ain);
                                relationshiptv.setText(relationship);
                                dob_tv.setText(dob);
                                nominee_name_tv.setText(nomineename);
                                usernametv.setText(username);
                                useremailtv.setText(useremail);
                                Picasso.get()
                                        .load("https://www.headwaygms.com/" + profileimg_url)
                                        .placeholder(R.drawable.profileimageicon)
                                        .error(R.drawable.profileimageicon)
                                        .into(profile_imageview);

                                if (profilestatus.equals("1")) {
                                    profilestatus_tv.setText("Not Approved");
                                }
                                else {
                                    profilestatus_tv.setText("Approved");

                                }

                                progressBar.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MyProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                Intent intent =  new Intent(MyProfileActivity.this, SalesDashbBoard.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent =  new Intent(MyProfileActivity.this, SalesDashbBoard.class);
        startActivity(intent);
        finish();
    }
}
