/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yh.android.framework.social.oauth1;

/**
 * Various versions ofthe OAuth1 Core specification.
 * Used by {@link OAuth1Template} to vary behavior its by the configured version.
 * @author Keith Donald
 */
public enum OAuth1Version {
	
	/**
	 * OAuth Core Version 1.0.
	 */
	CORE_10, 
	
	/**
	 * OAuth Core Version 1.0 Revision A.
	 */
	CORE_10_REVISION_A
	
}
