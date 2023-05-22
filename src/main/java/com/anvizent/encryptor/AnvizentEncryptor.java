package com.anvizent.encryptor;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.swing.JOptionPane;

/**
 * @author Hareen Bejjanki
 * @author Venkateswararao K
 *
 */
final public class AnvizentEncryptor {

	private AnvizentEncryptionServiceImpl2 encryptionService;

	public AnvizentEncryptor(String privateKey, String iv) throws UnsupportedEncodingException {
		encryptionService = new AnvizentEncryptionServiceImpl2(privateKey, iv);
	}

	public AnvizentEncryptor(String privateKey) throws UnsupportedEncodingException {
		encryptionService = new AnvizentEncryptionServiceImpl2(privateKey);
	}

	public static void main(String[] args) throws UnsupportedEncodingException, Exception {
		System.out.println(new AnvizentEncryptor("anvizent", "AnvizentDMT IV16").decrypt("DZ7IfB7UEvTW9Gqrbz9sqA"));
		System.out.println(new AnvizentEncryptor("anvizent", "AnvizentDMT IV16").decrypt("bwSNd4_w0suWm5YngBoA0DGKXhCll9DCCXVWunGhtEU"));
		System.out.println(new AnvizentEncryptor("anvizent", "AnvizentDMT IV16").decrypt("nH2cUzIEdmrqL0OmNIGotrPySkqMlLz_n5DoNRigeT8"));
		System.out.println(new AnvizentEncryptor("anvizent", "AnvizentDMT IV16").decrypt("tGXmttSP_fUah2qFaU4VfjMg0hBmpvyc5XPTK8JwfhbZm0X_6q6CXofLxjy3Her-"));
		System.out.println(new AnvizentEncryptor("anvizent", "AnvizentDMT IV16").decrypt("iuiCTR5-hj6esvm8TqINHw"));
		System.out.println(new AnvizentEncryptor("anvizent", "AnvizentDMT IV16").decrypt("VO51CO-IadxWUuZizPnwSnHKkwR1_BwDEEnK5xqz2_kNRJs75MzuUhc9ZOkiZwhJ"));
		System.out.println(new AnvizentEncryptor("anvizent", "AnvizentDMT IV16").encrypt("test@123"));
		System.out.println(new AnvizentEncryptor("anvizent", "AnvizentDMT IV16").decrypt("bwSNd4_w0suWm5YngBoA0DGKXhCll9DCCXVWunGhtEU"));
		System.out.println(new AnvizentEncryptor("anvizent", "AnvizentDMT IV16").decrypt("VO51CO-IadxWUuZizPnwSnHKkwR1_BwDEEnK5xqz2_kNRJs75MzuUhc9ZOkiZwhJ"));
		System.out.println(new AnvizentEncryptor("anvizent", "AnvizentDMT IV16").encrypt("Explore@09"));
		System.out.println(new AnvizentEncryptor("anvizent", "AnvizentDMT IV16").decrypt("kZjx4KfgJwHRY0bwO3xLXQ"));
	}

	public String encrypt(String data) throws Exception {
		return encrypt(data, null);
	}

	public String encrypt(String data, Boolean console) throws Exception {
		if (console != null) {
			if (console) {
				System.out.println("privateKey: " + encryptionService.getPrivateKey() + ", iv: " + encryptionService.getIv());
			} else {
				JOptionPane.showMessageDialog(null, "privateKey: " + encryptionService.getPrivateKey() + ", iv: " + encryptionService.getIv());
			}
		}

		return encryptionService.encrypt(data);
	}

	public String decrypt(String data) throws Exception {
		return decrypt(data, null);
	}

	public String decrypt(String data, Boolean console) throws Exception {
		if (console != null) {
			if (console) {
				System.out.println("privateKey: " + encryptionService.getPrivateKey() + ", iv: " + encryptionService.getIv());
			} else {
				JOptionPane.showMessageDialog(null, "privateKey: " + encryptionService.getPrivateKey() + ", iv: " + encryptionService.getIv());
			}
		}

		return encryptionService.decrypt(data);
	}

	public File encryptFile(File originalFile, String destinationFilePath) throws Exception {
		return encryptionService.encryptFile(originalFile, destinationFilePath);
	}

	public File decryptFile(File encryptedFile, String destinationFilePath) throws Exception {
		return encryptionService.decryptFile(encryptedFile, destinationFilePath);
	}

	public File decryptFileStream(InputStream inputStream) throws Exception {
		return encryptionService.decryptFileStream(inputStream);
	}

	public File encryptFileStream(InputStream inputStream) throws Exception {
		return encryptionService.encryptFileStream(inputStream);
	}

}