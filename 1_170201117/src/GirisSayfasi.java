import java.awt.EventQueue;
import java.sql.*;
import javax.swing.*;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GirisSayfasi {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	int kullaniciId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GirisSayfasi window = new GirisSayfasi();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection connection = null;
	
	/**
	 * Create the application.
	 */
	public GirisSayfasi() {
		initialize();
		connection = DatabaseConnection.netflixCon();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
			
		frame = new JFrame();
		frame.setBounds(100, 100, 423, 340);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(177, 39, 181, 45);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(177, 95, 181, 45);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("GIRIS");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String query = "select kullaniciID from kullanici where kullaniciAd = ? and kullaniciSifre = ? ";
					PreparedStatement ps = connection.prepareStatement(query);
					ps.setString(1, textField.getText());
					ps.setString(2, textField_1.getText());					
					
					ResultSet rs = ps.executeQuery();
					kullaniciId = rs.getInt("kullaniciID");
					
					rs.close();
					ps.close();
					
					frame.dispose();
					YonetimSayfasi ys = new YonetimSayfasi(kullaniciId);
					ys.setVisible(true);
					
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "Kullanici adi veya sifre hatali");
				}		
				
			}
		});
		btnNewButton.setBounds(177, 163, 181, 45);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Kullanici Adi");
		lblNewLabel.setBounds(41, 39, 140, 45);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Sifre");
		lblNewLabel_1.setBounds(41, 95, 140, 45);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton_1 = new JButton("Yeni Kayit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				YeniKayitSayfasi yks = new YeniKayitSayfasi();
				yks.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(177, 219, 181, 45);
		frame.getContentPane().add(btnNewButton_1);
	}
}
