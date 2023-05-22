package com.anvizent.encryptor;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Hareen Bejjanki
 * @author Venkateswararao K
 *
 */
public class InputPane extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final int X_DELTA = 20;
	private static final int Y_DELTA = 10;
	private static final int TEXT_FIELD_WIDTH = 150;
	private JLabel privateLabel = new JLabel("Private Key: ");
	private JTextField privateTextField = new JTextField();
	private JLabel ivLabel = new JLabel("IV: ");
	private JTextField ivTextField = new JTextField();
	private JLabel textLabel = new JLabel("Text: ");
	private JTextField textTextField = new JTextField();
	private JButton encrypt = new JButton("▼ encrypt ▼");
	private JTextArea textArea = new JTextArea(4, 10);

	public InputPane() {
		setLayout(null);

		setBounds();
		setProperties();
		addComponents();

		encrypt.addActionListener(new EncryptionButtonClick(privateTextField, ivTextField, textTextField, textArea));
	}

	private void addComponents() {
		add(privateLabel);
		add(privateTextField);
		add(ivLabel);
		add(ivTextField);
		add(textLabel);
		add(textTextField);
		add(encrypt);
		add(textArea);
	}

	private void setProperties() {
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setCaretPosition(0);
		textArea.setEditable(false);
	}

	private void setBounds() {
		Dimension labelDimension = privateLabel.getPreferredSize();
		int row = 0;

		setBounds(labelDimension, privateLabel, privateTextField, row++);
		setBounds(labelDimension, ivLabel, ivTextField, row++);
		setBounds(labelDimension, textLabel, textTextField, row++);

		int width = (X_DELTA + labelDimension.width + X_DELTA + privateTextField.getWidth() + X_DELTA);
		int height = Y_DELTA + (row * (labelDimension.height + Y_DELTA / 2 + Y_DELTA));

		Dimension encryptDimension = encrypt.getPreferredSize();
		encrypt.setBounds(width / 2 - encryptDimension.width / 2, height, encryptDimension.width, encryptDimension.height);
		height += encryptDimension.height + Y_DELTA;

		textArea.setBounds(X_DELTA, height + Y_DELTA, width - (int) (2 * X_DELTA), height - 2 * Y_DELTA);
		height += textArea.getHeight() + Y_DELTA;

		this.setSize(width + X_DELTA, height + Y_DELTA);
	}

	private void setBounds(Dimension labelDimension, JLabel label, JTextField textField, int row) {
		label.setBounds(X_DELTA, Y_DELTA + (row * (labelDimension.height + Y_DELTA / 2 + Y_DELTA)), labelDimension.width, labelDimension.height + Y_DELTA / 2);
		textField.setBounds(X_DELTA + labelDimension.width + X_DELTA, Y_DELTA + (row * (labelDimension.height + Y_DELTA / 2 + Y_DELTA)), TEXT_FIELD_WIDTH,
				labelDimension.height + Y_DELTA / 2);
	}

	public static void main(String[] args) {
		
	}

}
