package com.headwayagent.salesadviser_headwaygms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.headwayagent.salesadviser_headwaygms.activity.SalesDashBoardActivity;
import com.headwayagent.salesadviser_headwaygms.adapter.Registration_phase_two;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import util.FileUtils;
import util.VolleyMultipartRequest;

public class Registration_phase_three extends AppCompatActivity {

    private ImageView imageprofile,imagepancard,imagecheque,imageother,imageaddharcard,imagesign;
    private String filePathpic="";
    private String filePatadhar="";
    private String filePathpan="";
    private String filePathcheque="";
    private String filePathsign="";
    private String filePathother="";
    private static final int GALLERY_IMAGE = 1;
    private static final int GALLERY_SIGN = 2;
    private static final int GALLERY_ADHAR = 3;
    private static final int GALLERY_OTHER = 4;
    private static final int GALLERY_PAN = 5;
    private static final int GALLERY_CHEQUE = 6;
    private String type,extension="null";
    private byte Pic[];
    private byte Pancard[]="00.00.00".getBytes();
    private byte Adhardata[];
    private byte Cheque[]="00.00.00".getBytes();
    private byte Other[];
    private byte Sign[];
    private Button button;
    private String ainNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_phase_three);

        imageprofile = findViewById(R.id.pickdoc);
        imagepancard = findViewById(R.id.pandoc);
        imagecheque = findViewById(R.id.chequdoc);
        imageother = findViewById(R.id.otherdoc);
        imageaddharcard = findViewById(R.id.adhardoc);
        imagesign = findViewById(R.id.sign);
        button = findViewById(R.id.submit_datafile);
        ainNumber = SharedPrefManager.getInstance(getApplicationContext()).getUser().getAin_number();

        imageprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // choosepic();
            }
        });
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
        imageother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  chooseother();
            }
        });
        imageaddharcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  chooseaadhar();
            }
        });

        imagesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // choosesign();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UploadTask(new File(filePathcheque,filePathpan), "https://www.headwaygms.com/api/register-cda-step-three?ain="+ainNumber).execute();//call it in activity's onCreate

                register();


            }
        });


    }

    private class UploadTask extends AsyncTask<Void,Integer,String>{
        File file;
        String url="https://www.headwaygms.com/api/register-cda-step-three?ain="+ainNumber;
        ProgressDialog dialog;

        public UploadTask(File file,String url) {
            this.file = file;
            this.url = url;
        }

        @Override
        protected String doInBackground(Void... voids) {

            String res = "fail";
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection connection = null;
            //  String fileName = file.getName();
            String fileName = "";
            if (file.getName().toLowerCase().endsWith(".jpg")) {
                fileName = System.currentTimeMillis() + ".jpg";
            } else if (file.getName().toLowerCase().endsWith(".png")) {
                fileName = System.currentTimeMillis() + ".png";
            } else if (file.getName().toLowerCase().endsWith(".bmp")) {
                fileName = System.currentTimeMillis() + ".bmp";
            }
            try {
                connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("POST");
                String boundary = "---------------------------boundary";
                String tail = "\r\n--" + boundary + "--\r\n";
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                connection.setDoOutput(true);

                String metadataPart = "--" + boundary + "\r\n"
                        + "Content-Disposition: form-data; name=\"metadata\"\r\n\r\n"
                        + "" + "\r\n";

                String fileHeader1 = "--" + boundary + "\r\n"
                        + "Content-Disposition: form-data; name=\"uploaded_file\"; filename=\""
                        + fileName + "\"\r\n"
                        + "Content-Type: application/octet-stream\r\n"
                        + "Content-Transfer-Encoding: binary\r\n";

                long fileLength = file.length() + tail.length();
                String fileHeader2 = "Content-length: " + fileLength + "\r\n";
                String fileHeader = fileHeader1 + fileHeader2 + "\r\n";
                String stringData = metadataPart + fileHeader;

                long requestLength = stringData.length() + fileLength;
                connection.setRequestProperty("Content-length", "" + requestLength);
                connection.setFixedLengthStreamingMode((int) requestLength);
                connection.connect();

                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(stringData);
                out.flush();

                int progress = 0;
                int bytesRead = 0;
                byte buf[] = new byte[1024];
                BufferedInputStream bufInput = new BufferedInputStream(new FileInputStream(file));
                while ((bytesRead = bufInput.read(buf)) != -1) {
                    // write output
                    out.write(buf, 0, bytesRead);
                    out.flush();
                    progress += bytesRead;
                    // update progress bar
                    publishProgress((int) ((progress * 100) / (file.length())));
                    //  publishProgress(progress);
                }

                // Write closing boundary and close stream
                out.writeBytes(tail);
                out.flush();
                out.close();
                if (connection.getResponseCode() == 200 || connection.getResponseCode() == 201) {

                }
            } catch (Exception e) {
                // Exception
            } finally {
                if (connection != null) connection.disconnect();
            }
            return res;
        }

        @Override
        protected void onPreExecute() {
          dialog = new ProgressDialog(Registration_phase_three.this);
          dialog.setMessage("uploading");
          dialog.setMax(100);
          dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
              @Override
              public void onCancel(DialogInterface dialogInterface) {
                  cancel(true);
              }
          });
        }

        @Override
        protected void onPostExecute(String s) {
           if(dialog.isShowing()){
               dialog.dismiss();
           }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
           dialog.setProgress(values[0]);
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(Registration_phase_three.this, "Uploading cancle", Toast.LENGTH_SHORT).show();
        }
    }

    private void register(){
        //Toast.makeText(th is, "" +account_type, Toast.LENGTH_SHORT).show();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.drawable.headwaygmslogo);
        progressDialog.setTitle("Register.....");
         progressDialog.setMessage("Please wait......");
        progressDialog.show();


        String url = "https://www.headwaygms.com/api/register-cda-step-three?ain="+ainNumber;


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
                            Toast.makeText(Registration_phase_three.this, ""+message, Toast.LENGTH_SHORT).show();
                            // Toast.makeText(Registeration_Activity.this, "msg"+resultResponse, Toast.LENGTH_SHORT).show();
                            Intent inten=new Intent(Registration_phase_three.this, MainActivity.class);
                            inten.putExtra("type","success");
                            inten.putExtra("ain",Registeration_Activity.ain);
                            startActivity(inten);
                            progressDialog.dismiss();
                        } else {
                            Log.i("Unexpected", message);
                            progressDialog.dismiss();
//                            Intent  inten=new Intent(Registeration_Activity.this,MainActivity.class);
//                            inten.putExtra("type","success");
//                            startActivity(inten);
                            Toast.makeText(Registration_phase_three.this, ""+message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Registration_phase_three.this, "something went wrong"+e, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(Registration_phase_three.this, "Timeout Error", Toast.LENGTH_SHORT).show();
                        } else if (error.getClass().equals(NoConnectionError.class)) {
                            errorMessage = "Failed to connect server";
                            progressDialog.dismiss();
                            Toast.makeText(Registration_phase_three.this, "Server Not responding", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        String result = new String(networkResponse.data);
                        try {
                            Toast.makeText(Registration_phase_three.this, ""+result, Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(Registration_phase_three.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                            } else if (networkResponse.statusCode == 401) {
                                errorMessage = message+" Please login again";
                                progressDialog.dismiss();
                                Toast.makeText(Registration_phase_three.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                            } else if (networkResponse.statusCode == 400) {
                                errorMessage = message+ " Check your inputs";
                                progressDialog.dismiss();
                                Toast.makeText(Registration_phase_three.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                            } else if (networkResponse.statusCode == 500) {
                                errorMessage = message+" Something is getting wrong";
                                progressDialog.dismiss();
                                Toast.makeText(Registration_phase_three.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(Registration_phase_three.this, "Something went wrong"+e, Toast.LENGTH_SHORT).show();

                        }
                    }
                    Log.i("Error", errorMessage);
                    progressDialog.dismiss();
                    Toast.makeText(Registration_phase_three.this, ""+errorMessage, Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            }) {
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                 //   params.put("pic", new DataPart());
                    params.put("pan_doc", new DataPart("pan_doc" + extension, Pancard, type));
                   // params.put("aadhaar_doc", new DataPart());
                    params.put("cheque_doc", new DataPart("pan_doc" + extension, Cheque, type));
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
    public void choosesign () {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_SIGN);


    }
    public void chooseother () {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_OTHER);


    }
    public void chossecheuqe () {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_CHEQUE);


    }


    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            //Toast.makeText(this, "you selected="+filePathpic+uri, Toast.LENGTH_SHORT).show();

            try {
                //getting image from gallery
                //getting image from gallery
                filePathpic = FileUtils.getPath(this,uri);
               // imageprofile.setImageDrawable(filePathpic);
                type=FileUtils.getMimeType(this,uri);
                extension= (String) FileUtils.getExtension(String.valueOf(uri));

                try {
                    ContentResolver cr = getBaseContext().getContentResolver();
                    InputStream inputStream = cr.openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeFile(filePathpic);
                    imageprofile.setImageBitmap(bitmap);
                    // Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    Pic = baos.toByteArray();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                }
            } catch (Exception e) {
                e.printStackTrace();
                //  Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();

            }
        } else if (requestCode == GALLERY_PAN && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            // Toast.makeText(this, "you selected="+filePathpan+uri, Toast.LENGTH_SHORT).show();

            try {
                //getting image from gallery

                filePathpan = FileUtils.getPath(this,uri);
                type=FileUtils.getMimeType(this,uri);
              //  choosepancard.setText(filePathpan);
                extension= (String) FileUtils.getExtension(String.valueOf(uri));
                try {
                    ContentResolver cr = getBaseContext().getContentResolver();
                    InputStream inputStream = cr.openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeFile(filePathpan);
                    // Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();
                    imagepancard.setImageBitmap(bitmap);
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
        } else if (requestCode == GALLERY_ADHAR && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            // Toast.makeText(this, "you selected="+filePatadhar+uri, Toast.LENGTH_SHORT).show();
            try {
                //getting image from gallery
                filePatadhar = FileUtils.getPath(this,uri);
                //chooseadhhar.setText(filePatadhar);
                type=FileUtils.getMimeType(this,uri);
                extension= (String) FileUtils.getExtension(String.valueOf(uri));
                try {
                    ContentResolver cr = getBaseContext().getContentResolver();
                    InputStream inputStream = cr.openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeFile(filePatadhar);
                    // Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();
                    imageaddharcard.setImageBitmap(bitmap);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    Adhardata = baos.toByteArray();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == GALLERY_OTHER && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            // Toast.makeText(this, "you selected="+filePathother+uri, Toast.LENGTH_SHORT).show();

            try {
                //getting image from gallery
                //getting image from gallery
                filePathother = FileUtils.getPath(this,uri);
               // other.setText(filePathother);
                type=FileUtils.getMimeType(this,uri);
                extension= (String) FileUtils.getExtension(String.valueOf(uri));
                try {
                    ContentResolver cr = getBaseContext().getContentResolver();
                    InputStream inputStream = cr.openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeFile(filePathother);
                    // Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();

                    imageother.setImageBitmap(bitmap);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    Other = baos.toByteArray();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == GALLERY_SIGN && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            // Toast.makeText(this, "you selected="+filePathsign+uri, Toast.LENGTH_SHORT).show();


            try {

                //getting image from gallery
                filePathsign = FileUtils.getPath(this,uri);
               // choosesignature.setText(filePathsign);
                type=FileUtils.getMimeType(this,uri);
                extension= (String) FileUtils.getExtension(String.valueOf(uri));
                try {
                    ContentResolver cr = getBaseContext().getContentResolver();
                    InputStream inputStream = cr.openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeFile(filePathpan);
                    //  Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagesign.setImageBitmap(bitmap);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    Sign = baos.toByteArray();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                }
                //   Toast.makeText(this, "You selected=" + Sign, Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
                // Toast.makeText(this, "selected=" +e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == GALLERY_CHEQUE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            //  Toast.makeText(this, "you selected="+filePathcheque+uri, Toast.LENGTH_SHORT).show();

            try {
                //getting image from gallery
                filePathcheque = FileUtils.getPath(this,uri);
               // choosecancle.setText(filePathcheque);
                type=FileUtils.getMimeType(this,uri);
                extension= (String) FileUtils.getExtension(String.valueOf(uri));
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
                    InputStream inputStream = cr.openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeFile(filePathcheque);
                    // Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();
                    imagecheque.setImageBitmap(bitmap);
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
    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent =  new Intent(Registration_phase_three.this, SalesDashbBoard.class);
        startActivity(intent);
        finish();
    }

}
