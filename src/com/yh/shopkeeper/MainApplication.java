package com.yh.shopkeeper;

import org.springframework.security.crypto.encrypt.AndroidEncryptors;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.sqlite.SQLiteConnectionRepository;
import org.springframework.social.connect.sqlite.support.SQLiteConnectionRepositoryHelper;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;

import com.yh.taobao.fkw.Constants;
import com.yh.taobao.fkw.api.OpenTaoBao;
import com.yh.taobao.fkw.connect.OpenTaoBaoConnectionFactory;

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
	// Public methods
	// ***************************************
	public ConnectionRepository getConnectionRepository() {
		return this.connectionRepository;
	}

	public OpenTaoBaoConnectionFactory getOpenTaoBaoConnectionFactory() {
		return (OpenTaoBaoConnectionFactory) this.connectionFactoryRegistry.getConnectionFactory(OpenTaoBao.class);
	}

}
