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
package com.yh.android.framework.security.crypto.encrypt;



import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.yh.android.framework.security.crypto.keygen.BytesKeyGenerator;


/**
 * Encryptor that uses 256-bit AES encryption.
 * 
 * @author Keith Donald
 * @author Roy Clarkson
 */
final class AndroidAesBytesEncryptor implements BytesEncryptor {

	private final SecretKey secretKey;

	private final Cipher encryptor;

	private final Cipher decryptor;

	private final BytesKeyGenerator ivGenerator;

	public AndroidAesBytesEncryptor(String password, CharSequence salt, BytesKeyGenerator ivGenerator) {
		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), Hex.decode(salt), 1024, 256);
		SecretKey secretKey = CipherUtils.newSecretKey("PBEWITHSHA256AND256BITAES-CBC-BC", keySpec);
		this.secretKey = new SecretKeySpec(secretKey.getEncoded(), "AES");
		encryptor = CipherUtils.newCipher(AES_ALGORITHM);
		decryptor = CipherUtils.newCipher(AES_ALGORITHM);
		this.ivGenerator = ivGenerator;
	}

	public byte[] encrypt(byte[] bytes) {
		synchronized (encryptor) {
			byte[] iv = ivGenerator.generateKey();
			CipherUtils.initCipher(encryptor, Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
			byte[] encrypted = CipherUtils.doFinal(encryptor, bytes);
			return EncodingUtils.concatenate(iv, encrypted);
		}
	}

	public byte[] decrypt(byte[] encryptedBytes) {
		synchronized (decryptor) {
			byte[] iv = ivPart(encryptedBytes);
			CipherUtils.initCipher(decryptor, Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
			return CipherUtils.doFinal(decryptor, cipherPart(encryptedBytes, iv));
		}
	}

	// internal helpers

	private byte[] ivPart(byte[] encrypted) {
		return EncodingUtils.subArray(encrypted, 0, ivGenerator.getKeyLength());
	}

	private byte[] cipherPart(byte[] encrypted, byte[] iv) {
		return EncodingUtils.subArray(encrypted, iv.length, encrypted.length);
	}

	private static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
}