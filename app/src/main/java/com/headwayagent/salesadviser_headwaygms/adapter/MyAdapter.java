package com.headwayagent.salesadviser_headwaygms.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import fragment.Active_Fragment;
import fragment.Inactive_Fragement;


public class MyAdapter extends FragmentPagerAdapter {


    private Context myContext;
    int totalTabs;
    private String type;


    public MyAdapter(FragmentManager fm, Context myContext, int totalTabs, String type) {
        super(fm);
        this.myContext = myContext;
        this.totalTabs = totalTabs;
        this.type = type;
    }



    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        if (type.equals("2")) {
            switch (position) {

                case 0:

                    Active_Fragment myProducts = new Active_Fragment();
                    return myProducts;
                case 1:
                    Inactive_Fragement allProducts = new Inactive_Fragement();
                    return allProducts;


                default:


            }
        }

        if (type.equals("1")) {
            switch (position) {

                case 0:
                    Inactive_Fragement allProducts = new Inactive_Fragement();

                    return allProducts;
                case 1:

                    Active_Fragment myProducts = new Active_Fragment();
                    return myProducts;


                default:


            }
        }
        if (type.equals("teamzise")) {
            switch (position) {

                case 0:
                    Active_Fragment myProducts = new Active_Fragment();

                    return myProducts;
                case 1:


                    Inactive_Fragement allProducts = new Inactive_Fragement();
                    return allProducts;


                default:


            }
        }
        return null;

    }


    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
