package com.yh.shopkeeper.adapter;

import com.yh.shopkeeper.R;
import com.yh.shopkeeper.activity.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ViewFlowImageAdapter extends BaseAdapter {

	private View.OnClickListener clickListner;
	
	private LayoutInflater mInflater;
	private static final int[] ids = { R.drawable.starting_guide_img_01, R.drawable.starting_guide_img_02, R.drawable.starting_guide_img_03};

	public ViewFlowImageAdapter(Context context) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return ids.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.image_item, null);
		}
		((ImageView) convertView.findViewById(R.id.imgView)).setImageResource(ids[position]);
		return convertView;
	}

}
