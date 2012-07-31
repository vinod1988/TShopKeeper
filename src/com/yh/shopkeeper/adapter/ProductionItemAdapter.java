package com.yh.shopkeeper.adapter;

import com.taobao.api.response.ItemsInventoryGetResponse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taobao.api.domain.Item;
import com.yh.shopkeeper.R;
import com.yh.shopkeeper.utils.ImageLoader;

public class ProductionItemAdapter extends BaseAdapter{

	private ItemsInventoryGetResponse mResponse;
	private Context mContext;
	private ImageLoader mImageLoader;
	private LayoutInflater mLayoutInflater;
	public ProductionItemAdapter(Context context,ItemsInventoryGetResponse response){
		mResponse=response;
		mContext=context;
		mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mImageLoader=new ImageLoader(context.getApplicationContext());
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mResponse.getItems().size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mResponse.getItems().get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View containerView, ViewGroup parent) {
		Item item=mResponse.getItems().get(position);
		PostViewHolder holder=null;
		if(containerView==null){
			containerView=(LinearLayout)mLayoutInflater.inflate(R.layout.list_item_text_arrow,null,true);
			if(holder==null){
				holder=new PostViewHolder();
				holder.item_image=(ImageView)containerView.findViewById(R.id.item_image);
				holder.item_title=(TextView)containerView.findViewById(R.id.item_title);
				holder.item_content=(TextView)containerView.findViewById(R.id.item_content);
			}
			containerView.setTag(holder);
		}else{
			holder=(PostViewHolder)containerView.getTag();
		}
		
		
		holder.item_title.setText(mResponse.getItems().get(position).getDesc());
		holder.item_content.setText(mResponse.getItems().get(position).getPrice());
		if(holder.item_image!=null){
			mImageLoader.DisplayImage(mResponse.getItems().get(position).getPicUrl(), holder.item_image);
		}
		return containerView;
	}

	class PostViewHolder{
		TextView item_title;
		TextView item_content;
		ImageView item_image;	
	}
}
