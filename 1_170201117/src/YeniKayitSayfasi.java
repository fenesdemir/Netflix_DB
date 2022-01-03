import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class YeniKayitSayfasi extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	public boolean kontrol;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YeniKayitSayfasi frame = new YeniKayitSayfasi();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection connection = null;
	private JTable table;
	private JTable table_1;
	private JTable table_2;

	/**
	 * Create the frame.
	 */
	public YeniKayitSayfasi() {
		
		connection = DatabaseConnection.netflixCon();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 649, 489);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Yeni Kayit");
		lblNewLabel.setBounds(10, 11, 114, 25);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Kullanici Adi");
		lblNewLabel_1.setBounds(10, 47, 89, 25);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Email");
		lblNewLabel_2.setBounds(10, 83, 89, 25);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Sifre");
		lblNewLabel_3.setBounds(10, 119, 89, 25);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Dogum Tarihi");
		lblNewLabel_4.setBounds(10, 155, 89, 25);
		contentPane.add(lblNewLabel_4);
		
		textField = new JTextField();
		textField.setBounds(109, 49, 114, 23);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(109, 85, 114, 23);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(109, 121, 114, 23);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(109, 157, 114, 23);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		textField_3.setText("YYYY-MM-DD");
		
		JButton btnNewButton = new JButton("KAYDOL");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				
				if(textField.getText().equals("") == false && textField_1.getText().equals("") == false && textField_2.getText().equals("") == false && textField_3.getText().equals("") == false && textField_3.getText().equals("YYYY-MM-DD") == false) {		
					kontrol = true;
				}else {
					kontrol = false;
				}
					
				if(kontrol == true) {					
				
					try {					
			
						String query = "insert into kullanici (kullaniciAd, kullaniciEmail, kullaniciSifre, kullaniciDogum) values (?, ?, ?, ?)";
						PreparedStatement ps = connection.prepareStatement(query);
						ps.setString(1, textField.getText());
						ps.setString(2, textField_1.getText());
						ps.setString(3, textField_2.getText());
						ps.setString(4, textField_3.getText());
						
						ps.execute();				
						
						ps.close();
						
						contentPane.setVisible(false);
					    dispose(); 
					    GirisSayfasi.main(null);					
					
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, e1);
				}
			}else {
				JOptionPane.showMessageDialog(null, "Lutfen gecerli bir deger giriniz.");
			}

			}
			
		});
		btnNewButton.setBounds(109, 202, 114, 25);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_5 = new JLabel("Tercihleriniz");
		lblNewLabel_5.setBounds(256, 16, 114, 20);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("1. Tercihiniz");
		lblNewLabel_6.setBounds(256, 47, 89, 19);
		contentPane.add(lblNewLabel_6);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String query = "select programAd, avg(puan) from (program, tur, turProgram, kullaniciProgram) where turAd = ? and turID = turProgram.tur and programID = turProgram.program and kullaniciProgram.program = programID group by programAd  limit 2";
					PreparedStatement ps = connection.prepareStatement(query);
					String secim = (String) comboBox.getSelectedItem();
					
					if(secim.equals("--------") == false) {
						table.clearSelection();
					}
					
					ps.setString(1, secim);
					
					ResultSet rs = ps.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"--------", "Aksiyon ve Macera", "Bilim Kurgu ve Fantastik", "Romantik", "Drama", "\u00C7ocuk ve Aile", "Belgesel", "Komedi", "Korku", "Bilim ve Do\u011Fa", "Gerilim", "Anime", "Reality Program"}));
		comboBox.setMaximumRowCount(12);
		comboBox.setBounds(256, 77, 132, 20);
		contentPane.add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(256, 107, 277, 62);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_7 = new JLabel("2. Tercihiniz");
		lblNewLabel_7.setBounds(256, 180, 107, 19);
		contentPane.add(lblNewLabel_7);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String query = "select programAd, avg(puan) from (program, tur, turProgram, kullaniciProgram) where turAd = ? and turID = turProgram.tur and programID = turProgram.program and kullaniciProgram.program = programID group by programAd  limit 2";;
					PreparedStatement ps = connection.prepareStatement(query);
					String secim = (String) comboBox_1.getSelectedItem();
					
					if(secim.equals("--------") == false) {
						table.clearSelection();
					}
						
					ps.setString(1, secim);
						
					ResultSet rs = ps.executeQuery();
					table_1.setModel(DbUtils.resultSetToTableModel(rs));
						
					
					
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, e1);
				}	
			}
		});
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"--------", "Aksiyon ve Macera", "Bilim Kurgu ve Fantastik", "Romantik", "Drama", "\u00C7ocuk ve Aile", "Belgesel", "Komedi", "Korku", "Bilim ve Do\u011Fa", "Gerilim", "Anime", "Reality Program"}));
		comboBox_1.setMaximumRowCount(12);
		comboBox_1.setBounds(256, 204, 132, 20);
		contentPane.add(comboBox_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(256, 235, 277, 62);
		contentPane.add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String query = "select programAd, avg(puan) from (program, tur, turProgram, kullaniciProgram) where turAd = ? and turID = turProgram.tur and programID = turProgram.program and kullaniciProgram.program = programID group by programAd  limit 2";;
					PreparedStatement ps = connection.prepareStatement(query);
					String secim = (String) comboBox_2.getSelectedItem();
					
					if(secim.equals("--------") == false) {
						table.clearSelection();
					}
						
					ps.setString(1, secim);
						
					ResultSet rs = ps.executeQuery();
					table_2.setModel(DbUtils.resultSetToTableModel(rs));
						
					
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, e1);
				}
				
			}
		});
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"--------", "Aksiyon ve Macera", "Bilim Kurgu ve Fantastik", "Romantik", "Drama", "\u00C7ocuk ve Aile", "Belgesel", "Komedi", "Korku", "Bilim ve Do\u011Fa", "Gerilim", "Anime", "Reality Program"}));
		comboBox_2.setMaximumRowCount(12);
		comboBox_2.setBounds(256, 338, 132, 20);
		contentPane.add(comboBox_2);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(256, 369, 277, 62);
		contentPane.add(scrollPane_2);
		
		table_2 = new JTable();
		scrollPane_2.setViewportView(table_2);
		
		JLabel lblNewLabel_8 = new JLabel("3. Tericihiniz");
		lblNewLabel_8.setBounds(256, 308, 114, 19);
		contentPane.add(lblNewLabel_8);
	}
}
