package com.anvizent.encryptor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Hareen Bejjanki
 * @author Venkateswararao K
 *
 */
public class App {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		if (args != null && args.length > 1) {
			Scanner scanner = null;
			String text;
			if (args.length < 3) {
				System.out.print("Enter text to encrypt: ");
				scanner = new Scanner(System.in);
				text = scanner.nextLine();
			} else {
				text = args[2];
			}
			try {
				AnvizentEncryptor encryptionUtility = new AnvizentEncryptor(args[0], args[1]);
				System.out.println("Result: " + encryptionUtility.encrypt(text, true));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (scanner != null) {
					scanner.close();
				}
			}
		} else {
			new InputFrame();
		}
	}
}