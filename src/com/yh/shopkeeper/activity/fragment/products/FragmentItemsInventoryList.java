package com.yh.shopkeeper.activity.fragment.products;

import com.actionbarsherlock.app.SherlockFragment;
import com.yh.shopkeeper.R;
import com.yh.shopkeeper.utils.AnalyticsUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentItemsInventoryList extends SherlockFragment {
	
	
    public void fireTrackerEvent(String label) {
        AnalyticsUtils.getInstance(getActivity()).trackEvent(
                "FragmentItemsInventoryList", "Click", label, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container);

        
        return root;
    }
}
