/**
 * 
 */
package com.anvizent.encryptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;

/**
 * @author rakesh.gajula
 * @author Hareen Bejjanki
 * @author Venkateswararao K
 *
 */
public class AnvizentEncryptionServiceImpl2 implements EncryptionService {

	private static final String CHARACTER_ENCODING = "UTF-8";
	private static final String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
	private static final String AES_ENCRYPTION_ALGORITHM = "AES";

	private String privateKey;
	private String iv;
	private IvParameterSpec ivParmSpec;

	public String getPrivateKey() {
		return privateKey;
	}

	public String getIv() {
		return iv;
	}

	public AnvizentEncryptionServiceImpl2(String privateKey) throws UnsupportedEncodingException {
		this.privateKey = privateKey;
	}

	public AnvizentEncryptionServiceImpl2(String privateKey, String iv) throws UnsupportedEncodingException {
		this.privateKey = privateKey;
		this.ivParmSpec = getIvParameterSpec(iv);
	}

	private byte[] getKeyBytes(String key) throws UnsupportedEncodingException {
		byte[] keyBytes = new byte[16];
		byte[] parameterKeyBytes = key.getBytes(CHARACTER_ENCODING);
		System.arraycopy(parameterKeyBytes, 0, keyBytes, 0, Math.min(parameterKeyBytes.length, keyBytes.length));
		return keyBytes;
	}

	public byte[] decrypt(byte[] cipherText) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		return getCipherText(Cipher.DECRYPT_MODE, cipherText);
	}

	public byte[] encrypt(byte[] plainText) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		return getCipherText(Cipher.ENCRYPT_MODE, plainText);
	}

	private byte[] getCipherText(int mode, byte[] plainText) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		Cipher cipher = getCipher(mode);
		plainText = cipher.doFinal(plainText);
		return plainText;
	}

	private Cipher getCipher(int mode)
			throws InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException {
		byte[] keyBytes = getKeyBytes(privateKey);

		if (ivParmSpec != null) {
			Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
			SecretKeySpec secretKeySpecy = new SecretKeySpec(keyBytes, AES_ENCRYPTION_ALGORITHM);
			cipher.init(mode, secretKeySpecy, ivParmSpec);
			return cipher;
		} else {
			Cipher cipher = Cipher.getInstance(AES_ENCRYPTION_ALGORITHM);
			SecretKeySpec secretKeySpecy = new SecretKeySpec(keyBytes, AES_ENCRYPTION_ALGORITHM);
			cipher.init(mode, secretKeySpecy);
			return cipher;
		}

	}

	private IvParameterSpec getIvParameterSpec(String iv) throws UnsupportedEncodingException {
		IvParameterSpec ivParameterSpec = null;
		if (iv != null && !iv.isEmpty()) {
			byte[] initIV = null;
			initIV = getKeyBytes(iv);
			ivParameterSpec = new IvParameterSpec(initIV);
		} else {
			return null;
		}

		return ivParameterSpec;
	}

	@Override
	public String encrypt(String data) throws Exception {
		if (ivParmSpec == null) {
			byte[] plainTextbytes = data.getBytes();
			return DatatypeConverter.printBase64Binary(encrypt(plainTextbytes));
		} else {
			byte[] plainTextbytes = data.getBytes(CHARACTER_ENCODING);
			return Base64.encodeBase64URLSafeString(encrypt(plainTextbytes));
		}
	}

	@Override
	public String decrypt(String encryptedData) throws Exception {
		byte[] cipheredBytes;

		if (ivParmSpec != null) {
			cipheredBytes = Base64.decodeBase64(encryptedData);
		} else {
			cipheredBytes = DatatypeConverter.parseBase64Binary(encryptedData);
		}

		return new String(decrypt(cipheredBytes));
	}

	@Override
	public File decryptFile(File encryptedFile, String destinationFilePath) throws Exception {
		File decryptedFile = null;
		if (encryptedFile != null) {
			FileInputStream fielInputStream = null;
			FileOutputStream fileOutputStream = null;
			CipherInputStream cipherInputStream = null;
			try {
				Cipher cipher = getCipher(Cipher.DECRYPT_MODE);

				fielInputStream = new FileInputStream(encryptedFile);
				decryptedFile = new File(destinationFilePath);

				fileOutputStream = new FileOutputStream(decryptedFile);
				cipherInputStream = new CipherInputStream(fielInputStream, cipher);
				int read;
				byte buf[] = new byte[4096];
				while ((read = cipherInputStream.read(buf)) != -1) // reading
																	// from file
					fileOutputStream.write(buf, 0, read); // decrypting and
															// writing to file
			} finally {
				closeAll(cipherInputStream, fielInputStream, fileOutputStream);
			}
		}
		return decryptedFile;
	}

	@Override
	public File encryptFile(File originalFile, String destinationFilePath) throws Exception {

		if (destinationFilePath == null) {
			throw new IllegalAccessException("Destination file Path must not be empty");
		}
		File encryptedFile = null;
		if (originalFile != null) {
			FileInputStream fis = null;
			FileOutputStream fos = null;
			CipherInputStream cis = null;
			try {
				Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
				fis = new FileInputStream(originalFile);

				cis = new CipherInputStream(fis, cipher);
				encryptedFile = new File(destinationFilePath);
				fos = new FileOutputStream(encryptedFile);
				int read;
				byte buf[] = new byte[4096];
				while ((read = cis.read(buf)) != -1) { // reading from file
					fos.write(buf, 0, read);
				} // encrypting and writing to file
			} finally {
				closeAll(cis, fis, fos);
			}
		}

		return encryptedFile;
	}

	@Override
	public File decryptFileStream(InputStream inputStream) throws Exception {
		File decryptedFile = null;
		if (inputStream != null) {
			FileOutputStream fileOutputStream = null;
			CipherInputStream cipherInputStream = null;
			try {
				Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
				decryptedFile = Files.createTempFile("decryptedTemp", ".properties").toFile();
				fileOutputStream = new FileOutputStream(decryptedFile);
				cipherInputStream = new CipherInputStream(inputStream, cipher);
				int read;
				byte buf[] = new byte[4096];
				while ((read = cipherInputStream.read(buf)) != -1) // reading
																	// from file
					fileOutputStream.write(buf, 0, read); // decrypting and
															// writing to file
			} finally {
				closeAll(cipherInputStream, inputStream, fileOutputStream);
			}
		}
		return decryptedFile;
	}

	@Override
	public File encryptFileStream(InputStream inputStream) throws Exception {
		if (inputStream == null) {
			throw new IllegalAccessException("Destination file Path must not be empty");
		}
		File encryptedFile = null;
		if (inputStream != null) {
			FileOutputStream fileOutputStream = null;
			CipherInputStream cipherInputStream = null;

			try {
				Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
				cipherInputStream = new CipherInputStream(inputStream, cipher);
				encryptedFile = Files.createTempFile("encryptedTemp", ".properties").toFile();
				fileOutputStream = new FileOutputStream(encryptedFile);

				int read;
				byte buf[] = new byte[4096];
				while ((read = cipherInputStream.read(buf)) != -1) {
					fileOutputStream.write(buf, 0, read);
				}
			} finally {
				closeAll(cipherInputStream, inputStream, fileOutputStream);
			}
		}

		return encryptedFile;
	}

	private void closeAll(CipherInputStream cipherInputStream, InputStream fileInputStream, FileOutputStream fileOutputStream) throws IOException {
		if (cipherInputStream != null) {
			cipherInputStream.close();
		}

		if (fileInputStream != null) {
			fileInputStream.close();
		}

		if (fileOutputStream != null) {
			fileOutputStream.flush();
		}

		if (fileOutputStream != null) {
			fileOutputStream.close();
		}
	}
}
