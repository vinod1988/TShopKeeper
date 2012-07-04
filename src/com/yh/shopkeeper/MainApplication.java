package com.yh.shopkeeper;

import com.yh.android.framework.security.crypto.encrypt.AndroidEncryptors;
import com.yh.android.framework.social.connect.ConnectionRepository;
import com.yh.android.framework.social.connect.sqlite.SQLiteConnectionRepository;
import com.yh.android.framework.social.connect.sqlite.support.SQLiteConnectionRepositoryHelper;
import com.yh.android.framework.social.connect.support.ConnectionFactoryRegistry;
import com.yh.android.taobao.fkw.Constants;
import com.yh.android.taobao.fkw.api.OpenTaoBao;
import com.yh.android.taobao.fkw.connect.OpenTaoBaoConnectionFactory;
import com.yh.android.weibo.Weibo;
import com.yh.android.weibo.fkw.api.OpenWeiBo;
import com.yh.android.weibo.fkw.connect.OpenWeiBoConnectionFactory;


import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Timo Jiang
 */
public class MainApplication extends Application {
	private ConnectionFactoryRegistry connectionFactoryRegistry;
	private SQLiteOpenHelper repositoryHelper;
	private ConnectionRepository connectionRepository;

	// ***************************************
	// Application Methods
	// ***************************************
	@Override
	public void onCreate() {
		// create a new ConnectionFactoryLocator and populate it with Facebook ConnectionFactory
		this.connectionFactoryRegistry = new ConnectionFactoryRegistry();
		this.connectionFactoryRegistry.addConnectionFactory(new OpenTaoBaoConnectionFactory(getOpenTaoBaoAppId(),
				getOpenTaoBaoAppSecret()));
		
		this.connectionFactoryRegistry.addConnectionFactory(new OpenWeiBoConnectionFactory(getOpenWeiBoAppId(),
				getOpenWeiBoAppSecret()));

		// set up the database and encryption
		this.repositoryHelper = new SQLiteConnectionRepositoryHelper(this);
		this.connectionRepository = new SQLiteConnectionRepository(this.repositoryHelper,
				this.connectionFactoryRegistry, AndroidEncryptors.text("password", "5c0744940b5c369b"));
	}

	// ***************************************
	// Private methods
	// ***************************************
	private String getOpenTaoBaoAppId() {
		return Constants.CONSUMER_KEY;
	}

	private String getOpenTaoBaoAppSecret() {
		return Constants.CONSUMER_SECRET;
	}
	
	// ***************************************
	// Private methods
	// ***************************************
	private String getOpenWeiBoAppId() {
		return Weibo.CONSUMER_KEY;
	}

	private String getOpenWeiBoAppSecret() {
		return Weibo.CONSUMER_SECRET;
	}

	// ***************************************
	// Public methods
	// ***************************************
	public ConnectionRepository getConnectionRepository() {
		return this.connectionRepository;
	}

	public OpenTaoBaoConnectionFactory getOpenTaoBaoConnectionFactory() {
		return (OpenTaoBaoConnectionFactory) this.connectionFactoryRegistry.getConnectionFactory(OpenTaoBao.class);
	}
	
	public OpenWeiBoConnectionFactory getOpenWeiBoConnectionFactory() {
		return (OpenWeiBoConnectionFactory) this.connectionFactoryRegistry.getConnectionFactory(OpenWeiBo.class);
	}

}
