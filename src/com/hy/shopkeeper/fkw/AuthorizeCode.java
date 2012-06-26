package com.hy.shopkeeper.fkw;

import com.hy.shopkeeper.org.json.JSONException;
import com.hy.shopkeeper.org.json.JSONObject;


public class AuthorizeCode extends TaoBaoResponse implements java.io.Serializable {

	private String code;
	private String error;
	private String errorDescription;
	  
	/**
	 * 
	 */
	private static final long serialVersionUID = 3473349966713163765L;


	/*package*/AuthorizeCode(JSONObject json) throws TaoBaoException {
		super();
		init(json);
	}

	private void init(JSONObject json) throws TaoBaoException {
		if(json!=null){
			try {
				code = json.getString("code");
				error = json.getString("error");
				errorDescription = json.getString("error_description");
			} catch (JSONException jsone) {
				throw new TaoBaoException(jsone.getMessage() + ":" + json.toString(), jsone);
			}
		}
	}

	public String getCode() {
		return code;
	}


	public String getError() {
		return error;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

}
