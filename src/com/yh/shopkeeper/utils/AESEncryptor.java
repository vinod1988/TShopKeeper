package com.yh.shopkeeper.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

public class AESEncryptor {
	private static final String ENCRYPT_TYPE = "AES";

	public static String encrypt(String seed, String content) throws Exception {
		byte[] result = encrypt(getRawKey(seed), content.getBytes("UTF8"));
		return new String(Base64.encode(result, Base64.DEFAULT));
	}

	public static String decrypt(String seed, String content) throws Exception {
		byte[] result = decrypt(getRawKey(seed), Base64.decode(content, Base64.DEFAULT));
		return new String(result, "UTF8");
	}

	private static byte[] getRawKey(String seed) throws Exception {
		KeyGenerator generator = KeyGenerator.getInstance(ENCRYPT_TYPE);
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(seed.getBytes());
		generator.init(128, random);
		return generator.generateKey().getEncoded();
	}

	private static byte[] encrypt(byte[] key, byte[] clear) throws Exception {
		SecretKeySpec spec = new SecretKeySpec(key, ENCRYPT_TYPE);
		Cipher cipher = Cipher.getInstance(ENCRYPT_TYPE);
		cipher.init(Cipher.ENCRYPT_MODE, spec);
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	private static byte[] decrypt(byte[] key, byte[] encrypted) throws Exception {
		SecretKeySpec spec = new SecretKeySpec(key, ENCRYPT_TYPE);
		Cipher cipher = Cipher.getInstance(ENCRYPT_TYPE);
		cipher.init(Cipher.DECRYPT_MODE, spec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

}
