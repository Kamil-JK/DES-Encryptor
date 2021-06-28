import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigInteger;
import java.util.BitSet;

public class Application implements ActionListener {

	private static String toEncrypt;
	private static DES_Encoder encoder = new DES_Encoder();
	private static JFrame frame = new JFrame();
	private static JPanel panel = new JPanel();
	private static JTextField textField = new JTextField(40);
	private static JTextArea keyField = new JTextArea("");
	private static JButton button = new JButton("Encrypt");
	private static JLabel errorLabel = new JLabel("");
	private static JLabel label = new JLabel("Input text (hex): ");
	private static JLabel label2 = new JLabel("Encrypted data (hex): ");
	private static JLabel label3 = new JLabel("Key (hex): ");
	private static JTextArea result = new JTextArea("");

	public static void main(String[] args) {
		button.addActionListener(new Application());
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		panel.setLayout(new GridLayout(10, 1));
		panel.add(label);
		panel.add(textField);
		panel.add(errorLabel);
		panel.add(button);
		panel.add(label2);
		panel.add(result);
		panel.add(label3);
		panel.add(keyField);
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("DES Encryptor");
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		toEncrypt = textField.getText();
		if (!isHexNumber(toEncrypt)) {
			errorLabel.setText("Error: incorrect input data!");
			return;
		} else
			errorLabel.setText("");
		result.setText(encoder.run(toEncrypt));
		keyField.setText(encoder.getKey());
	}

	private static boolean isHexNumber(String word) {
		try {
			Long.parseLong(word, 16);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
}
