package com.yh.shopkeeper.activity.fragment;

import com.actionbarsherlock.app.SherlockFragment;
import com.yh.shopkeeper.R;
import com.yh.shopkeeper.utils.AnalyticsUtils;
import com.yh.shopkeeper.utils.UIUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentDashBoard extends SherlockFragment {

    public void fireTrackerEvent(String label) {
        AnalyticsUtils.getInstance(getActivity()).trackEvent(
                "Home Screen Dashboard", "Click", label, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container);

        // Attach event handlers
        root.findViewById(R.id.home_btn_bl).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fireTrackerEvent("BL");  
                
            }
            
        });

        root.findViewById(R.id.home_btn_shipping).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fireTrackerEvent("Shipping");
                // Launch sessions list
            }
        });

        root.findViewById(R.id.home_btn_production).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fireTrackerEvent("Production");
                // Launch list of sessions and vendors the user has starred              
            }
        });
        
        root.findViewById(R.id.home_btn_showcase).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fireTrackerEvent("Showcase");
                // Launch list of sessions and vendors the user has starred              
            }
        });
        
        root.findViewById(R.id.home_btn_instock).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fireTrackerEvent("InStock");
                // Launch list of sessions and vendors the user has starred              
            }
        });
//        root.findViewById(R.id.home_btn_comment).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                fireTrackerEvent("Comment");
//                // Launch list of sessions and vendors the user has starred              
//            }
//        });
//        
//        root.findViewById(R.id.home_btn_notification).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                fireTrackerEvent("Notify");
//                // Launch list of sessions and vendors the user has starred              
//            }
//        });
//        
//        root.findViewById(R.id.home_btn_setting).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                fireTrackerEvent("Setting");
//                // Launch list of sessions and vendors the user has starred              
//            }
//        });

        return root;
    }
}
