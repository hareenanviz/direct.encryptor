package com.anvizent.encryptor;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * @author Hareen Bejjanki
 *
 */
public class InputFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private InputPane inputPane = new InputPane();

	public InputFrame() {
		setTitle("Encryptor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(inputPane);
		setBounds();
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource("images/anvizent.png")).getImage());
		setVisible(true);
	}

	private void setBounds() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(inputPane.getSize().width, inputPane.getSize().height + 50);
		setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);
	}

}
