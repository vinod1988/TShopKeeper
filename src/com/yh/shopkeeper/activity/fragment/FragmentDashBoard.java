package com.yh.shopkeeper.activity.fragment;

import com.actionbarsherlock.app.SherlockFragment;
import com.yh.shopkeeper.R;
import com.yh.shopkeeper.activity.Flip;
import com.yh.shopkeeper.activity.ImageListActivity;
import com.yh.shopkeeper.utils.AnalyticsUtils;
import com.yh.shopkeeper.utils.UIUtils;
import com.yh.shopkeeper.view.BadgeView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentDashBoard extends SherlockFragment {

	BadgeView badgeOrder;
	BadgeView badgeBl;
	BadgeView badgeShipping;
	BadgeView badgeProduction;
	BadgeView badgeShowCase;
	
    public void fireTrackerEvent(String label) {
        AnalyticsUtils.getInstance(getActivity()).trackEvent(
                "Home Screen Dashboard", "Click", label, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container);

        // Attach event handlers
        Button btnOrder=(Button) root.findViewById(R.id.home_btn_order);
        badgeOrder=new BadgeView(this.getActivity(),btnOrder);
        badgeOrder.setText("5");
        badgeOrder.setBadgeBackgroundColor(Color.RED);
        badgeOrder.setTextSize(12);
        badgeOrder.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badgeOrder.show();
                
        btnOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fireTrackerEvent("Order");  
                Intent intent=new Intent();
                intent.setClass(getActivity(), SampleList.class);
                view.getContext().startActivity(intent);
                //overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                
            }
        });
        
        // Attach event handlers
        Button btnBl=(Button) root.findViewById(R.id.home_btn_bl);
        badgeBl=new BadgeView(this.getActivity(),btnBl);
        badgeBl.setText("32");
        badgeBl.setBadgeBackgroundColor(Color.RED);
        badgeBl.setTextSize(12);
        badgeBl.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badgeBl.show();
        
        btnBl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fireTrackerEvent("BL");  
                Intent intent=new Intent();
                intent.setClass(getActivity(), Flip.class);
                view.getContext().startActivity(intent);
            }
            
        });

        Button btnShipping=(Button) root.findViewById(R.id.home_btn_shipping);
        badgeShipping=new BadgeView(this.getActivity(),btnShipping);
        badgeShipping.setText("40");
        badgeShipping.setBadgeBackgroundColor(Color.RED);
        badgeShipping.setTextSize(12);
        badgeShipping.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badgeShipping.show();
        
        btnShipping.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fireTrackerEvent("Shipping");
                // Launch sessions list
                Intent intent=new Intent();
                intent.setClass(getActivity(), ImageListActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        
        Button btnProduction=(Button) root.findViewById(R.id.home_btn_production);
        badgeProduction=new BadgeView(this.getActivity(),btnProduction);
        badgeProduction.setText("230");
        badgeProduction.setBadgeBackgroundColor(Color.BLUE);
        badgeProduction.setTextSize(12);
        badgeProduction.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badgeProduction.show();
        
        btnProduction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fireTrackerEvent("Production");
                // Launch list of sessions and vendors the user has starred              
            }
        });
        
        Button btnShowCase=(Button) root.findViewById(R.id.home_btn_showcase);
        badgeShowCase=new BadgeView(this.getActivity(),btnShowCase);
        badgeShowCase.setText("230");
        badgeShowCase.setBadgeBackgroundColor(Color.BLUE);
        badgeShowCase.setTextSize(12);
        badgeShowCase.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badgeShowCase.show();
        btnShowCase.setOnClickListener(new View.OnClickListener() {
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
        root.findViewById(R.id.home_btn_comment).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fireTrackerEvent("Comment");
                // Launch list of sessions and vendors the user has starred              
            }
        });
        
        root.findViewById(R.id.home_btn_notification).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fireTrackerEvent("Notify");
                // Launch list of sessions and vendors the user has starred              
            }
        });
        
//        root.findViewById(R.id.home_btn_setting).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                fireTrackerEvent("Setting");
//                // Launch list of sessions and vendors the user has starred              
//            }
//        });
        return root;
    }
}
