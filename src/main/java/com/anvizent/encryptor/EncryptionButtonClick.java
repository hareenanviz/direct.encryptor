package com.anvizent.encryptor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Hareen Bejjanki
 *
 */
public class EncryptionButtonClick implements ActionListener {

	private JTextField privateTextField;
	private JTextField ivTextField;
	private JTextField textTextField;
	private JTextArea textArea;

	public EncryptionButtonClick(JTextField privateTextField, JTextField ivTextField, JTextField textTextField, JTextArea textArea) {
		this.privateTextField = privateTextField;
		this.ivTextField = ivTextField;
		this.textTextField = textTextField;
		this.textArea = textArea;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		AnvizentEncryptor encryptionUtil;
		try {
			String privateKey = privateTextField.getText();
			if (privateKey == null || privateKey.isEmpty()) {
				JOptionPane.showMessageDialog(null, "privateKey is not provided", "ERROR", JOptionPane.ERROR_MESSAGE);
			} else {
				String text = textTextField.getText();
				if (text == null || text.isEmpty()) {
					JOptionPane.showMessageDialog(null, "text to encrypt is not provided", "ERROR", JOptionPane.ERROR_MESSAGE);
				} else {
					encryptionUtil = new AnvizentEncryptor(privateKey, ivTextField.getText());
					textArea.setText(encryptionUtil.encrypt(text));
					textArea.requestFocus();
					textArea.selectAll();
				}
			}
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, exception.getClass() + ": " + exception.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

}
