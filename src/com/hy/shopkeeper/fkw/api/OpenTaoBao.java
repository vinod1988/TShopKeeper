package com.hy.shopkeeper.fkw.api;

/**
 * Interface specifying a basic set of operations for interacting with OpenTaoBao.
 * Implemented by InstagramTemplate. Not often used directly, but a useful option
 * to enhance testability, as it can easily be mocked or stubbed.
 */
public interface OpenTaoBao {
			
	/**
	 * Returns the portion of the OpenTaoBao API that handles user operations
	 */
	UserOperations userOperations();
	
	ShopOperations shopOperations();
	
}
