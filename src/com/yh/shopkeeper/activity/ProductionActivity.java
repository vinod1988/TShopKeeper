package com.yh.shopkeeper.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Shop;
import com.taobao.api.response.ItemsInventoryGetResponse;
import com.yh.android.framework.social.connect.ConnectionRepository;
import com.yh.android.taobao.fkw.api.OpenTaoBao;
import com.yh.shopkeeper.AbstractAsyncActivity;
import com.yh.shopkeeper.R;
import com.yh.shopkeeper.adapter.ProductionItemAdapter;
import com.yh.shopkeeper.service.TaoBaoAPIConstant;

public class ProductionActivity extends AbstractAsyncActivity{
	private ConnectionRepository connectionRepository;
	private OpenTaoBao openTaoBao;
	private ListView mListView;
	
    private final Handler handler = new Handler();
    private boolean useLogo = false;
    private boolean showHomeUp = false;
    private long pageNo=0;
    
    private ProductionItemAdapter dataAdapter;
    

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.connectionRepository = getApplicationContext().getConnectionRepository();        
        setContentView(R.layout.activity_items_inventory_list);
        final ActionBar ab = getSupportActionBar();
        // set defaults for logo & home up
        ab.setDisplayHomeAsUpEnabled(showHomeUp);
        ab.setDisplayUseLogoEnabled(useLogo);
        mListView=(ListView)this.findViewById(R.id.item_list);
        new ItemsInventoryTask(this,"for_shelved").execute();
        
    }
    
	private void displayList(ItemsInventoryGetResponse shop) {
		if (shop != null) {
			if(shop.getTotalResults()>0){
				dataAdapter=new ProductionItemAdapter(this,shop);
				mListView.setAdapter(dataAdapter);
				dataAdapter.notifyDataSetChanged();
			}
		}
	}
	
    private class ItemsInventoryTask extends AsyncTask<Void, Integer, ItemsInventoryGetResponse> {

		private Context mContext;
		private String mBanner;
		private Long mCid;

		public ItemsInventoryTask(Context context,String banner) {
			this.mContext = context;
			this.mBanner=banner;
		}

		@Override
		protected void onPreExecute() {
			//showProgressDialog(mContext.getResources().getString(R.string.app_loading_data));
		}

		@Override
		protected ItemsInventoryGetResponse doInBackground(Void... arg0) {
			ItemsInventoryGetResponse shop = null;
			String title="";
			String seller_cids="";
			try {
				shop = openTaoBao.shopOperations().inventoryGetRequest(title,mBanner,mCid,seller_cids,pageNo,TaoBaoAPIConstant.PAGE_SIZE);
			} catch (ApiException ex) {
				ex.printStackTrace();
				shop = null;
			}
			return shop;
		}

		@Override
		protected void onPostExecute(ItemsInventoryGetResponse result) {
			//dismissProgressDialog();
			displayList(result);
		}
	}
    
}
