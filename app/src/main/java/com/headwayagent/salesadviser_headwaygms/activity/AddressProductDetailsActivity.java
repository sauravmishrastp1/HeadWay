package com.headwayagent.salesadviser_headwaygms.activity;

import android.content.Intent;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.models.SpinnerItemModel;

import java.util.ArrayList;

public class AddressProductDetailsActivity extends AppCompatActivity{


    ArrayList<SpinnerItemModel>list;
    Toolbar toolbar;
    Button next;
    TextView price;
    TextView priceHeading;
    TextView priceTotal;
    ImageView product_img,nextcount,backcount;
    TextView countnumbertext;
    int count=1;
    EditText selling_price_Edittext;
    String sellingPrice,final_price;
    String productprice,producttitle;

    private String pid,quantity,name,email,phone,address1,address2,city,state,pin,alternatephone,user_id,Ain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_product_details);
        product_img=findViewById(R.id.productimg);
        nextcount=findViewById(R.id.nextarrow);
        backcount=findViewById(R.id.backarrow);
        countnumbertext=findViewById(R.id.quantity);
        priceHeading=findViewById(R.id.priceeheading);
        priceTotal=findViewById(R.id.pricetotal);
        price=findViewById(R.id.mrp);
        toolbar=findViewById(R.id.toolbar);
        next=findViewById(R.id.next_product_detail_btn);
        selling_price_Edittext=findViewById(R.id.selling_price_edittext);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Product Detail");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();

         productprice=extras.getString("price");
        String productimg=extras.getString("image");
        final String  pid=extras.getString("pid");
        final String  type=extras.getString("type");


        if (type.equals("dsr"))
        {
            name=extras.getString("name");
            email=extras.getString("email");
            phone=extras.getString("phone");
            address1=extras.getString("address1");
            address2=extras.getString("address2");
            city=extras.getString("city");
            state=extras.getString("state");
            pin=extras.getString("pin");
            alternatephone=extras.getString("alternate_phone");
            Ain = extras.getString("ain");
            producttitle = extras.getString("p_title");
        }


        else {

            name=extras.getString("name");
            email=extras.getString("email");
            phone=extras.getString("phone");
            address1=extras.getString("address1");
            address2=extras.getString("address2");
            city=extras.getString("city");
            state=extras.getString("state");
            pin=extras.getString("pin");
            alternatephone=extras.getString("alternate_phone");
            productimg=extras.getString("p_img");
            productprice=extras.getString("p_price");
            Ain = extras.getString("ain");
            producttitle = extras.getString("p_title");




        }


        selling_price_Edittext.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                    if(s.length() != 0)
                        priceTotal.setText("Rs "+s);

                sellingPrice=selling_price_Edittext.getText().toString();

            }
        });

        price.setText("Rs "+productprice+" /-");
        Glide.with(this).load(productimg).placeholder(R.drawable.headwaygmslogo).into(product_img);
        priceTotal.setText("Rs "+productprice);
        final_price=String.valueOf(productprice);
        priceHeading.setText("Total price of"+" ( "+String.valueOf(count)+" item )");
        countnumbertext.setText(String.valueOf(count));

        final String finalProductprice = productprice;

        nextcount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                sellingPrice=selling_price_Edittext.getText().toString();

                if (sellingPrice.equals(""))
                {
                    sellingPrice= finalProductprice;
                }

                if (count==1)
                {

                    countnumbertext.setText(String.valueOf(count));
                    backcount.setVisibility(View.VISIBLE);
                    count++;
                }
                else {
                    count=count+1;
                    backcount.setVisibility(View.VISIBLE);

                }

                countnumbertext.setText(String.valueOf(count));
                double totalprice=count*(Integer.parseInt(sellingPrice));
                countnumbertext.setText(String.valueOf(count));
                priceTotal.setText("Rs "+String.valueOf(totalprice));
                final_price=String.valueOf(totalprice);
                priceHeading.setText("Total price of"+" ( "+String.valueOf(count)+" item )");
            }
        });

        final String finalProductprice1 = productprice;
        backcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sellingPrice=selling_price_Edittext.getText().toString();
                if (sellingPrice.equals(""))
                {
                    sellingPrice= finalProductprice1;
                }

                if (count == 1 )
                {
                    backcount.setVisibility(View.GONE);

                }

                else {
                    backcount.setVisibility(View.VISIBLE);
                    count--;

                }

                countnumbertext.setText(String.valueOf(count));
                double totalprice=count*Integer.parseInt(sellingPrice);
                priceTotal.setText("Rs "+String.valueOf(totalprice));
                priceHeading.setText("Total price of"+" ( "+String.valueOf(count)+" item )");

                final_price=String.valueOf(totalprice);


            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equals("dsr"))
                {
                    Intent intent = new Intent(AddressProductDetailsActivity.this, PaymentDeatilsFormActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("email", email);
                    intent.putExtra("phone", phone);
                    intent.putExtra("address1", address1);
                    intent.putExtra("address2", address2);
                    intent.putExtra("city", city);
                    intent.putExtra("state", state);
                    intent.putExtra("pin", pin);
                    intent.putExtra("alternate_phone", alternatephone);
                    intent.putExtra("pid",pid);
                    intent.putExtra("qty",String.valueOf(count));
                    intent.putExtra("type","sales");
                    intent.putExtra("maintype","dsr");
                    intent.putExtra("p_price",final_price);
                    intent.putExtra("ain",Ain);
                    startActivity(intent);
                }

                if (count==1)
                {
                    if (!selling_price_Edittext.getText().toString().equals("")) {
                        final_price = selling_price_Edittext.getText().toString();
                    }
                }
            //    Toast.makeText(AddressProductDetailsActivity.this, "title"+producttitle, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AddressProductDetailsActivity.this,PaymentDeatilsFormActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("phone", phone);
                intent.putExtra("address1", address1);
                intent.putExtra("address2", address2);
                intent.putExtra("city", city);
                intent.putExtra("state", state);
                intent.putExtra("pin", pin);
                intent.putExtra("alternate_phone", alternatephone);
                intent.putExtra("pid",pid);
                intent.putExtra("qty",String.valueOf(count));
                intent.putExtra("type","sales");
                intent.putExtra("maintype","dsr");
                intent.putExtra("p_price",final_price);
                intent.putExtra("ain",Ain);
                intent.putExtra("p_title",producttitle);

                startActivity(intent);
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



}
