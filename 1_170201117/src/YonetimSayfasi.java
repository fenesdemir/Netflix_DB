import java.awt.BorderLayout;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class YonetimSayfasi extends JFrame {

	private JPanel contentPane;
	Timer sayac;
	int sayacBasla = 0;
	
	public int progSecim;
	public static int kullaniciId;
	public int sure;
	public int izlenmisSure;
	public boolean izlemeyeBasildiMi = false;
	
	public String arayuz_tarih;
	public int arayuz_sure;
	public int arayuz_puan;
	public float arayuz_tPuan;
	public int arayuz_bolum;
	
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YonetimSayfasi frame = new YonetimSayfasi(kullaniciId);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection connection = null;
	private JTextField textField_1;
	private JTable table_1;
	private JTextField puanAlani;
	/**
	 * Create the frame.
	 */
	public YonetimSayfasi(int kullanici) {
		
		kullaniciId = kullanici;
		connection = DatabaseConnection.netflixCon();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1082, 673);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("HOSGELDINIZ");
		lblNewLabel.setBounds(10, 11, 149, 24);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Ture Gore Arama");
		lblNewLabel_1.setBounds(10, 73, 114, 24);
		contentPane.add(lblNewLabel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 151, 504, 173);
		contentPane.add(scrollPane);
		
		JLabel lblNewLabel_3 = new JLabel("");
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setVisible(false);
		
		JLabel puanLabel = new JLabel("Verdiginiz Puan:");
		puanLabel.setVisible(false);
		puanLabel.setBounds(813, 373, 124, 19);
		contentPane.add(puanLabel);
		
		JLabel sonZiyaret = new JLabel("");
		sonZiyaret.setBounds(602, 163, 304, 24);
		contentPane.add(sonZiyaret);
		
		JLabel verilenPuan = new JLabel("");
		verilenPuan.setBounds(919, 173, 114, 19);
		contentPane.add(verilenPuan);
		
		JLabel toplamPuan = new JLabel("");
		toplamPuan.setBounds(868, 203, 165, 19);
		contentPane.add(toplamPuan);
		
		JLabel sonBolum = new JLabel("");
		sonBolum.setBounds(602, 203, 199, 19);
		contentPane.add(sonBolum);
		
		JLabel harcananZaman = new JLabel("");
		harcananZaman.setBounds(602, 233, 199, 19);
		contentPane.add(harcananZaman);
		
		JButton izleme = new JButton("IZLE");
		izleme.setVisible(false);
		
		JButton durdurma = new JButton("DURDUR");
		durdurma.setVisible(false);
		
		puanAlani = new JTextField();
		puanAlani.setVisible(false);
		puanAlani.setBounds(947, 370, 86, 20);
		contentPane.add(puanAlani);
		puanAlani.setColumns(10);
		
		JLabel sayac_label = new JLabel("");
		sayac_label.setBounds(602, 348, 142, 16);
		contentPane.add(sayac_label);
		
		sayac = new Timer(1000, new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if(sayacBasla <= (sure*60)) {
				sayac_label.setText(Integer.toString(sayacBasla));
				sayacBasla++;
				}
			}
			
		});	
		
		JButton puanlaDugme = new JButton("PUANLA");
		puanlaDugme.setVisible(false);
		puanlaDugme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {																
				
				boolean izlenmisMi = false;
				
				try {
				String kontrol = "select * from kullaniciProgram where kullanici = ? and program = ? ";
				PreparedStatement k = connection.prepareStatement(kontrol);
				k.setInt(1, kullaniciId);
				k.setInt(2, progSecim);										
				
				ResultSet sonuc = k.executeQuery();										
				
				if(sonuc.next() == false) {
					izlenmisMi = false;
				}else{
					izlenmisMi = true;
				}
				
				k.close();
				sonuc.close();
				
				}catch(Exception izlenmeKontrol) {
					
					JOptionPane.showMessageDialog(null, izlenmeKontrol);
				}
				
				if(izlenmisMi == true) {					
					
					int puan = Integer.parseInt(puanAlani.getText());
				
				if(puan >= 0 && puan <= 10 && puanAlani.getText().equals("") == false) {
					
					try {											
							
						String guncelle = " update kullaniciProgram set puan = ? where kullanici = ? and program = ?";			
						PreparedStatement guncelk = connection.prepareStatement(guncelle);												
							
						guncelk.setInt(1, puan);
						guncelk.setInt(2, kullaniciId);
						guncelk.setInt(3, progSecim);
							
						guncelk.execute();	
						guncelk.close();													
					
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, e1);
				}
				
				}else {
					
					JOptionPane.showMessageDialog(null, "Lutfen 1-10 arasi gecerli bir tamsayi giriniz.");
					
				}
				
				}else {
					
					JOptionPane.showMessageDialog(null, "Programi izlemeden puan veremezsiniz.");
					
				}
				
			}
		});
		puanlaDugme.setBounds(944, 401, 89, 23);
		contentPane.add(puanlaDugme);			
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					
					sayac.stop();
					
					if(izlemeyeBasildiMi == true) {
						
						int varMiyokMu = 0;
						
						try {
							
							String kontrol = "select * from kullaniciProgram where kullanici = ? and program = ? ";
							PreparedStatement k = connection.prepareStatement(kontrol);
							k.setInt(1, kullaniciId);
							k.setInt(2, progSecim);										
							
							ResultSet sonuc = k.executeQuery();										
							
							if(sonuc.next() == false) {
								varMiyokMu = 0;
							}else{
								varMiyokMu = 1;
								izlenmisSure = sonuc.getInt("izlemeSure");
							}
							
							k.close();
							sonuc.close();
							
							if(varMiyokMu == 0) {

								String kayit = " insert into kullaniciProgram (kullanici, program, izlemeTarih, izlemeSure, hangiBolum) values (?, ?, ?, ?, ?) ";				
								PreparedStatement yoksa = connection.prepareStatement(kayit);													
								yoksa.setInt(1, kullaniciId);
								yoksa.setInt(2, progSecim);
								
								DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
								LocalDateTime now = LocalDateTime.now();  
								dtf.format(now);
								String tarih = now.toString();
								
								yoksa.setString(3, tarih);
								yoksa.setInt(4, (sayacBasla/60));
								yoksa.setInt(5, (comboBox_1.getSelectedIndex()+1));
								
								yoksa.execute();
								yoksa.close();
								
							}else{
								
								String kayit = " update kullaniciProgram set izlemeTarih = ?, izlemeSure = ?, hangiBolum = ? where kullanici = ? and program = ?";				
								PreparedStatement varsa = connection.prepareStatement(kayit);						
								
								DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
								LocalDateTime now = LocalDateTime.now();  
								dtf.format(now);
								String tarih = now.toString();
								
								varsa.setString(1, tarih);
								varsa.setInt(2, (sayacBasla/60));
								varsa.setInt(3, (comboBox_1.getSelectedIndex()+1));
								varsa.setInt(4, kullaniciId);
								varsa.setInt(5, progSecim);						
								
								varsa.execute();	
								varsa.close();
							
							}														
							
						}catch(Exception e1){
							JOptionPane.showMessageDialog(null, e1);
						}
						
						sayacBasla = 0;
						sayac_label.setText("");
						izlemeyeBasildiMi = false;
						
					}								
										
					int satir = table.getSelectedRow();
					String isim = (table.getModel().getValueAt(satir, 0)).toString();
					
					comboBox_1.setVisible(false);
					puanLabel.setVisible(false);
					puanAlani.setVisible(false);
					puanlaDugme.setVisible(false);
					izleme.setVisible(false);
					durdurma.setVisible(false);
					
					sonZiyaret.setText("");
					harcananZaman.setText("");
					verilenPuan.setText("");
					sonBolum.setText("");
					
					String query = "select programID, programAd, programBolumSayi, programUzunluk from program where programAd = ? ";
					PreparedStatement t_ps = connection.prepareStatement(query);
					t_ps.setString(1, isim);
					ResultSet t_rs = t_ps.executeQuery();
					
					sure = t_rs.getInt("programUzunluk");
					
					while(t_rs.next()) {
						lblNewLabel_3.setText(t_rs.getString("programAd"));
						progSecim = t_rs.getInt("programID");
						sure = t_rs.getInt("programUzunluk");
												
						ArrayList<String> bolumler = new ArrayList<String>();
						
						for(int i = 0; i < Integer.parseInt(t_rs.getString("programBolumSayi")) ; i++) {
							String kacinciBolum = (i+1) + ". Bolum";
							bolumler.add(kacinciBolum);
						}
											
						comboBox_1.setVisible(true);
						comboBox_1.setModel(new DefaultComboBoxModel(bolumler.toArray()));
						comboBox_1.setMaximumRowCount(15);
						comboBox_1.setBounds(602, 330, 142, 20);
						contentPane.add(comboBox_1);
						
						puanLabel.setVisible(true);
						puanAlani.setVisible(true);
						puanlaDugme.setVisible(true);
						izleme.setVisible(true);
						durdurma.setVisible(true);
						
					}
					
					t_ps.close();
					t_rs.close();
					
					String arayuzDoldur = "select izlemeTarih, izlemeSure, hangiBolum, puan from kullaniciProgram where kullanici = ? and program = ?";
					PreparedStatement arayuz_ps = connection.prepareStatement(arayuzDoldur);
					arayuz_ps.setInt(1, kullaniciId);
					arayuz_ps.setInt(2, progSecim);
					
					ResultSet arayuz_rs = arayuz_ps.executeQuery();
					
					if(arayuz_rs.next()) {
					
					arayuz_tarih = arayuz_rs.getString("izlemeTarih");
					arayuz_sure = arayuz_rs.getInt("izlemeSure");
					arayuz_puan = arayuz_rs.getInt("puan");
					arayuz_bolum = arayuz_rs.getInt("hangiBolum");
					
					sonZiyaret.setText("En son izlediginiz tarih: " + arayuz_tarih);
					harcananZaman.setText("Kaldiginiz yer: " + Integer.toString(arayuz_sure));
					verilenPuan.setText("Verdiginiz puan: " + Integer.toString(arayuz_puan));
					sonBolum.setText("Kaldiginiz bolum: " + Integer.toString(arayuz_bolum));
					
					}
					
					arayuz_ps.close();
					arayuz_rs.close();
					
					
					String arayuzPuan = "select avg(puan) as toplamPuan from kullaniciProgram where program = ?";
					PreparedStatement puan_ps = connection.prepareStatement(arayuzPuan);
					puan_ps.setInt(1, progSecim); 
					
					ResultSet puan_rs = puan_ps.executeQuery();
					
					if(puan_rs.next()) {
					arayuz_tPuan = puan_rs.getInt("toplamPuan");
					
					toplamPuan.setText("Kullanici puanlari: " +  Float.toString(arayuz_tPuan));
					
					}
					
					puan_ps.close();
					puan_rs.close();
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		scrollPane.setViewportView(table);
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 378, 114, 32);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("ARAMA");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String query = "select programAd, programTip, programBolumSayi, programUzunluk from program where programAd = ?";
					PreparedStatement ps = connection.prepareStatement(query);
					ps.setString(1, textField_1.getText());
					
					ResultSet rs = ps.executeQuery();
					table_1.setModel(DbUtils.resultSetToTableModel(rs));
					
					ps.close();
					rs.close();
					
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnNewButton_1.setBounds(134, 378, 124, 32);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel_2 = new JLabel("Isimle Arama");
		lblNewLabel_2.setBounds(10, 348, 149, 19);
		contentPane.add(lblNewLabel_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 433, 504, 173);
		contentPane.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					
					sayac.stop();
					
					if(izlemeyeBasildiMi == true) {
						
						int varMiyokMu = 0;
						
						try {
							
							String kontrol = "select * from kullaniciProgram where kullanici = ? and program = ? ";
							PreparedStatement k = connection.prepareStatement(kontrol);
							k.setInt(1, kullaniciId);
							k.setInt(2, progSecim);										
							
							ResultSet sonuc = k.executeQuery();										
							
							if(sonuc.next() == false) {
								varMiyokMu = 0;
							}else{
								varMiyokMu = 1;
								izlenmisSure = sonuc.getInt("izlemeSure");
							}
							
							k.close();
							sonuc.close();
							
							if(varMiyokMu == 0) {

								String kayit = " insert into kullaniciProgram (kullanici, program, izlemeTarih, izlemeSure, hangiBolum) values (?, ?, ?, ?, ?) ";				
								PreparedStatement yoksa = connection.prepareStatement(kayit);													
								yoksa.setInt(1, kullaniciId);
								yoksa.setInt(2, progSecim);
								
								DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
								LocalDateTime now = LocalDateTime.now();  
								dtf.format(now);
								String tarih = now.toString();
								
								yoksa.setString(3, tarih);
								yoksa.setInt(4, (sayacBasla/60));
								yoksa.setInt(5, (comboBox_1.getSelectedIndex()+1));
								
								yoksa.execute();
								yoksa.close();
								
							}else{
								
								String kayit = " update kullaniciProgram set izlemeTarih = ?, izlemeSure = ?, hangiBolum = ? where kullanici = ? and program = ?";				
								PreparedStatement varsa = connection.prepareStatement(kayit);						
								
								DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
								LocalDateTime now = LocalDateTime.now();  
								dtf.format(now);
								String tarih = now.toString();
								
								varsa.setString(1, tarih);
								varsa.setInt(2, (sayacBasla/60));
								varsa.setInt(3, (comboBox_1.getSelectedIndex()+1));
								varsa.setInt(4, kullaniciId);
								varsa.setInt(5, progSecim);						
								
								varsa.execute();	
								varsa.close();
							
							}														
							
						}catch(Exception e1){
							JOptionPane.showMessageDialog(null, e1);
						}
						
						sayacBasla = 0;
						sayac_label.setText("");
						izlemeyeBasildiMi = false;
						
					}										
					
					int satir = table_1.getSelectedRow();
					String isim = (table_1.getModel().getValueAt(satir, 0)).toString();
					
					comboBox_1.setVisible(false);
					puanLabel.setVisible(false);
					puanAlani.setVisible(false);
					puanlaDugme.setVisible(false);
					izleme.setVisible(false);
					durdurma.setVisible(false);
					
					sonZiyaret.setText("");
					harcananZaman.setText("");
					verilenPuan.setText("");
					sonBolum.setText("");
					
					String query = "select programID, programAd, programBolumSayi, programUzunluk from program where programAd = ? ";
					PreparedStatement t1_ps = connection.prepareStatement(query);
					t1_ps.setString(1, isim);
					ResultSet t1_rs = t1_ps.executeQuery();
					
					sure = t1_rs.getInt("programUzunluk");
					
					while(t1_rs.next()) {
						lblNewLabel_3.setText(t1_rs.getString("programAd"));
						progSecim = t1_rs.getInt("programID");
						sure = t1_rs.getInt("programUzunluk");
						
						ArrayList<String> bolumler = new ArrayList<String>();
						
						for(int i = 0; i < Integer.parseInt(t1_rs.getString("programBolumSayi")) ; i++) {
							String kacinciBolum = (i+1) + ". Bolum";
							bolumler.add(kacinciBolum);
						}
						
						
						comboBox_1.setVisible(true);
						comboBox_1.setModel(new DefaultComboBoxModel(bolumler.toArray()));
						comboBox_1.setMaximumRowCount(15);
						comboBox_1.setBounds(602, 330, 142, 20);
						contentPane.add(comboBox_1);
						
						puanLabel.setVisible(true);
						puanAlani.setVisible(true);
						puanlaDugme.setVisible(true);
						izleme.setVisible(true);
						durdurma.setVisible(true);
						
					}
					
					t1_ps.close();
					t1_rs.close();
					
					String arayuzDoldur = "select izlemeTarih, izlemeSure, hangiBolum, puan from kullaniciProgram where kullanici = ? and program = ?";
					PreparedStatement arayuz_ps = connection.prepareStatement(arayuzDoldur);
					arayuz_ps.setInt(1, kullaniciId);
					arayuz_ps.setInt(2, progSecim);
					
					ResultSet arayuz_rs = arayuz_ps.executeQuery();
					
					if(arayuz_rs.next()) {
					
					arayuz_tarih = arayuz_rs.getString("izlemeTarih");
					arayuz_sure = arayuz_rs.getInt("izlemeSure");
					arayuz_puan = arayuz_rs.getInt("puan");
					arayuz_bolum = arayuz_rs.getInt("hangiBolum");
					
					sonZiyaret.setText("En son izlediginiz tarih: " + arayuz_tarih);
					harcananZaman.setText("Kaldiginiz yer: " + Integer.toString(arayuz_sure));
					verilenPuan.setText("Verdiginiz puan: " + Integer.toString(arayuz_puan));
					sonBolum.setText("Kaldiginiz bolum: " + Integer.toString(arayuz_bolum));
					
					}
					
					arayuz_ps.close();
					arayuz_rs.close();
					
					String arayuzPuan = "select avg(puan) as toplamPuan from kullaniciProgram where program = ?";
					PreparedStatement puan_ps = connection.prepareStatement(arayuzPuan);
					puan_ps.setInt(1, progSecim); 
					
					ResultSet puan_rs = puan_ps.executeQuery();
					
					if(puan_rs.next()) {
					arayuz_tPuan = puan_rs.getInt("toplamPuan");
					
					toplamPuan.setText("Kullanici puanlari: " +  Float.toString(arayuz_tPuan));
					
					}
					
					puan_ps.close();
					puan_rs.close();					
					
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		scrollPane_1.setViewportView(table_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String query = "select programAd, programTip, programBolumSayi, programUzunluk from (program, tur, turProgram) where turAd = ? and turID = turProgram.tur and programID = turProgram.program ";
					PreparedStatement ps = connection.prepareStatement(query);
					String secim = (String) comboBox.getSelectedItem();
					
					if(secim.equals("--------") == true) {
						table.clearSelection();
						puanLabel.setVisible(false);
						puanAlani.setVisible(false);
						puanlaDugme.setVisible(false);
						izleme.setVisible(false);
						lblNewLabel_3.setText("");;
						comboBox_1.setVisible(false);
					}
					
					ps.setString(1, secim);					
					
					ResultSet rs = ps.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
					ps.close();
					rs.close();
					
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, e1);
				}

			}
		});
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"--------", "Aksiyon ve Macera", "Bilim Kurgu ve Fantastik", "Romantik", "Drama", "\u00C7ocuk ve Aile", "Belgesel", "Komedi", "Korku", "Bilim ve Do\u011Fa", "Gerilim", "Anime", "Reality Program"}));
		comboBox.setMaximumRowCount(12);
		comboBox.setBounds(10, 108, 114, 26);
		contentPane.add(comboBox);	
		
		lblNewLabel_3.setBounds(602, 128, 304, 24);
		contentPane.add(lblNewLabel_3);		
						
		durdurma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				sayac.stop();
				
				int varMiyokMu = 0;
				
				try {
					
					String kontrol = "select * from kullaniciProgram where kullanici = ? and program = ? ";
					PreparedStatement k = connection.prepareStatement(kontrol);
					k.setInt(1, kullaniciId);
					k.setInt(2, progSecim);										
					
					ResultSet sonuc = k.executeQuery();										
					
					if(sonuc.next() == false) {
						varMiyokMu = 0;
					}else{
						varMiyokMu = 1;
						izlenmisSure = sonuc.getInt("izlemeSure");
					}
					
					k.close();
					sonuc.close();
					
					if(varMiyokMu == 0) {

						String kayit = " insert into kullaniciProgram (kullanici, program, izlemeTarih, izlemeSure, hangiBolum) values (?, ?, ?, ?, ?) ";				
						PreparedStatement yoksa = connection.prepareStatement(kayit);													
						yoksa.setInt(1, kullaniciId);
						yoksa.setInt(2, progSecim);
						
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
						LocalDateTime now = LocalDateTime.now();  
						dtf.format(now);
						String tarih = now.toString();
						
						yoksa.setString(3, tarih);
						yoksa.setInt(4, (sayacBasla/60));
						yoksa.setInt(5, (comboBox_1.getSelectedIndex()+1));
						
						yoksa.execute();
						yoksa.close();
						
					}else{
						
						String kayit = " update kullaniciProgram set izlemeTarih = ?, izlemeSure = ?, hangiBolum = ? where kullanici = ? and program = ?";				
						PreparedStatement varsa = connection.prepareStatement(kayit);						
						
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
						LocalDateTime now = LocalDateTime.now();  
						dtf.format(now);
						String tarih = now.toString();
						
						varsa.setString(1, tarih);
						varsa.setInt(2, (sayacBasla/60));
						varsa.setInt(3, (comboBox_1.getSelectedIndex()+1));
						varsa.setInt(4, kullaniciId);
						varsa.setInt(5, progSecim);						
						
						varsa.execute();	
						varsa.close();
					
					}														
					
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, e1);
				}
				
				sayacBasla = 0;
				sayac_label.setText("");
				izlemeyeBasildiMi = false;

			}
		});
		durdurma.setBounds(602, 405, 142, 19);
		contentPane.add(durdurma);
		
		izleme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				sayac.start();
				izlemeyeBasildiMi = true;
				
			}
		});
		izleme.setBounds(602, 370, 142, 20);
		contentPane.add(izleme);
				
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
				sayac.stop();
				
				if(izlemeyeBasildiMi == true) {
					
					int varMiyokMu = 0;
					
					try {
						
						String kontrol = "select * from kullaniciProgram where kullanici = ? and program = ? ";
						PreparedStatement k = connection.prepareStatement(kontrol);
						k.setInt(1, kullaniciId);
						k.setInt(2, progSecim);										
						
						ResultSet sonuc = k.executeQuery();										
						
						if(sonuc.next() == false) {
							varMiyokMu = 0;
							System.out.println("Kayit yok");
						}else{
							varMiyokMu = 1;
							System.out.println("Kayit var");
							izlenmisSure = sonuc.getInt("izlemeSure");
						}
						
						k.close();
						sonuc.close();
						
						if(varMiyokMu == 0) {

							String kayit = " insert into kullaniciProgram (kullanici, program, izlemeTarih, izlemeSure, hangiBolum) values (?, ?, ?, ?, ?) ";				
							PreparedStatement yoksa = connection.prepareStatement(kayit);													
							yoksa.setInt(1, kullaniciId);
							yoksa.setInt(2, progSecim);
							
							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
							LocalDateTime now = LocalDateTime.now();  
							dtf.format(now);
							String tarih = now.toString();
							
							yoksa.setString(3, tarih);
							yoksa.setInt(4, (sayacBasla/60));
							yoksa.setInt(5, (comboBox_1.getSelectedIndex()+1));
							
							yoksa.execute();
							yoksa.close();
							
						}else{
							
							String kayit = " update kullaniciProgram set izlemeTarih = ?, izlemeSure = ?, hangiBolum = ? where kullanici = ? and program = ?";				
							PreparedStatement varsa = connection.prepareStatement(kayit);						
							
							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
							LocalDateTime now = LocalDateTime.now();  
							dtf.format(now);
							String tarih = now.toString();
							
							varsa.setString(1, tarih);
							varsa.setInt(2, (sayacBasla/60));
							varsa.setInt(3, (comboBox_1.getSelectedIndex()+1));
							varsa.setInt(4, kullaniciId);
							varsa.setInt(5, progSecim);						
							
							varsa.execute();	
							varsa.close();
						
						}														
						
					}catch(Exception e1){
						JOptionPane.showMessageDialog(null, e1);
					}
					
					sayacBasla = 0;
					sayac_label.setText("");
					izlemeyeBasildiMi = false;
					
				}
				
			}
		});
			
	}
}
