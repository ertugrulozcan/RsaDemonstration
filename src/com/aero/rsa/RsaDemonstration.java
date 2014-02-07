package com.aero.rsa;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.aero.json.Json;

public class RsaDemonstration
{
	public static void main(String[] args)
	{
		new RsaDemonstration();
	}

	final private String userA = "Alice";
	final private String userB = "Bob";
	
	// Kullanicilar
	private User Alice, Bob;
	
	// Arayuz bilesenleri
	private JFrame window;
	private RsaDemoPanel panelA;
	private RsaDemoPanel panelB;
	private JButton loadUsersButton;
	private JPanel demoPanel;
	// Mesaj kutularý
	private JTextField aliceMessageBox, bobMessageBox;
	private JTextArea aliceMessageHistory, bobMessageHistory;
	// Guvenilir olmayan iletisim kanali
	private JTextArea unsafeConnectionLine;
	private JButton sendButtonA, sendButtonB;
	
	String cwd = System.getProperty("user.dir");
    final JFileChooser jfc = new JFileChooser(cwd);

	private ActionListener loadButtonActionListener;
	private ActionListener sendButtonAliceActionListener;
	private ActionListener sendButtonBobActionListener;
	
	private MouseListener aboutLabelClickListener;
	
	private JLabel aboutLabel;
	
	public RsaDemonstration()
	{
		InitializeWindow();
		
		// Panellerin olusturulmasi
		this.panelA = new RsaDemoPanel(userA);
		this.window.getContentPane().add(panelA);
		this.panelA.setBounds(20, 20, this.panelA.getWidth(), this.panelA.getHeight());
		this.panelB = new RsaDemoPanel(userB);
		this.window.getContentPane().add(panelB);
		this.panelB.setBounds(20, 30 + this.panelA.getHeight() , this.panelB.getWidth(), this.panelB.getHeight());
		
		// Demonstration paneli
		this.SetDemonstrationPanel();
		
		// Dosyadan yukle butonu
		this.loadUsersButton = new JButton("Dosyadan yükle");
		this.loadUsersButton.addActionListener(this.loadButtonActionListener);
		this.loadUsersButton.setBounds(this.panelB.getWidth() - 130, this.panelA.getHeight() + this.panelB.getHeight() + 35, 150, 25);
		this.window.getContentPane().add(loadUsersButton);
		
		this.aboutLabel = new JLabel("<HTML><U>Hakkýnda<U><HTML>");
		this.aboutLabel.addMouseListener(aboutLabelClickListener);
		this.aboutLabel.setBounds(this.window.getWidth() - 80, this.window.getHeight() - 60, 80, 20);
		this.window.getContentPane().add(aboutLabel);
		
		// Son ayarlar
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.setVisible(true);
	}
	
	private void InitializeWindow()
	{
		this.window = new JFrame("RSA Messenger Demonstration");
		this.window.setSize(1200, Toolkit.getDefaultToolkit().getScreenSize().height - 60);
		this.window.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - window.getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - window.getSize().height) / 2 - 10);
		this.window.setResizable(false);
		this.window.setLayout(null);
		
		this.SetButtons();
	}
	
	private void SetDemonstrationPanel()
	{
		this.demoPanel = new JPanel();
		this.demoPanel.setBorder(BorderFactory.createTitledBorder("RSA Messenger DEMO"));
		this.demoPanel.setSize(this.window.getWidth() - 40, 340);
		this.demoPanel.setBounds(20, 300, this.demoPanel.getWidth(), this.demoPanel.getHeight());
		this.demoPanel.setLayout(null);
		this.window.getContentPane().add(this.demoPanel);
		
		JLabel aliceNameLabel = new JLabel(userA);
		aliceNameLabel.setBounds(20, 25, 100, 20);
		this.demoPanel.add(aliceNameLabel);
		
		this.aliceMessageHistory = new JTextArea();
		this.aliceMessageHistory.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.aliceMessageHistory.setSize(200, 210);
		this.aliceMessageHistory.setBounds(20, 45, this.aliceMessageHistory.getWidth(), this.aliceMessageHistory.getHeight());
		this.demoPanel.add(this.aliceMessageHistory);
		
		this.aliceMessageBox = new JTextField("Mesaj");
		this.aliceMessageBox.setSize(200, 30);
		this.aliceMessageBox.setBounds(20, 265, this.aliceMessageBox.getWidth(), this.aliceMessageBox.getHeight());
		this.aliceMessageBox.addKeyListener(new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent arg0)
			{
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
				{
					SendMessage(Alice, Bob);
				}
			}
			
			@Override
			public void keyReleased(KeyEvent arg0)
			{}
			@Override
			public void keyTyped(KeyEvent arg0)
			{}
		});
		this.aliceMessageBox.addFocusListener(new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent arg0)
			{
				aliceMessageBox.selectAll();
			}

			@Override
			public void focusLost(FocusEvent arg0)
			{}
		});
		this.demoPanel.add(this.aliceMessageBox);
		
		JLabel bobNameLabel = new JLabel(userB);
		bobNameLabel.setBounds(this.demoPanel.getWidth() - 225, 25, 100, 20);
		this.demoPanel.add(bobNameLabel);
		
		this.bobMessageHistory = new JTextArea();
		this.bobMessageHistory.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.bobMessageHistory.setSize(200, 210);
		this.bobMessageHistory.setBounds(this.demoPanel.getWidth() - 225, 45, this.bobMessageHistory.getWidth(), this.bobMessageHistory.getHeight());
		this.demoPanel.add(this.bobMessageHistory);
		
		this.bobMessageBox = new JTextField("Mesaj");
		this.bobMessageBox.setSize(200, 30);
		this.bobMessageBox.setBounds(this.demoPanel.getWidth() - 225, 265, this.bobMessageBox.getWidth(), this.bobMessageBox.getHeight());
		this.bobMessageBox.addKeyListener(new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent arg0)
			{
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
				{
					SendMessage(Bob, Alice);
				}
			}
			
			@Override
			public void keyReleased(KeyEvent arg0)
			{}
			@Override
			public void keyTyped(KeyEvent arg0)
			{}
		});
		this.bobMessageBox.addFocusListener(new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent arg0)
			{
				bobMessageBox.selectAll();
			}

			@Override
			public void focusLost(FocusEvent arg0)
			{}
		});
		this.demoPanel.add(this.bobMessageBox);
		
		this.sendButtonA = new JButton("Gönder");
		this.sendButtonA.addActionListener(sendButtonAliceActionListener);
		this.sendButtonA.setBounds(140, 300, 80, 25);
		this.demoPanel.add(sendButtonA);
		
		this.sendButtonB = new JButton("Gönder");
		this.sendButtonB.addActionListener(sendButtonBobActionListener);
		this.sendButtonB.setBounds(this.demoPanel.getWidth() - 225, 300, 80, 25);
		this.demoPanel.add(sendButtonB);
		
		JLabel unsafeLabel = new JLabel("Güvenli olmayan iletiþim kanalý");
		unsafeLabel.setBounds(240, 125, 300, 20);
		this.demoPanel.add(unsafeLabel);
		
		this.unsafeConnectionLine = new JTextArea();
		this.unsafeConnectionLine.setBorder(BorderFactory.createLineBorder(Color.RED));
		this.unsafeConnectionLine.setSize(680, 150);
		this.unsafeConnectionLine.setBounds(240, 145, this.unsafeConnectionLine.getWidth(), this.unsafeConnectionLine.getHeight());
		this.demoPanel.add(this.unsafeConnectionLine);
	}
	
	private void SetButtons()
	{
		this.loadButtonActionListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (jfc.showOpenDialog(window) == JFileChooser.APPROVE_OPTION)
				{
					File file = jfc.getSelectedFile();
					String filename = file.getAbsolutePath();
					BufferedReader in = null;
					try
					{
						// Dosyadan json stringinin okunmasi
						in = new BufferedReader(new FileReader(filename));
						String jsonDataString = in.readLine();
						
						// JsonStringinin User nesnesine parse edilmesi
						List<User> userList = Json.DeserializeUserList(jsonDataString);
						Alice = userList.get(0);
						panelA.SetFields(Alice);
						Bob = userList.get(1);
						panelB.SetFields(Bob);
					}
					catch(IOException e)
					{
						System.err.println(e.getMessage());
					}
					finally
					{
						try
						{
							in.close();
						}
						catch (IOException e)
						{
							System.err.println(e.getMessage());
						}
					}
				}

				SwingUtilities.invokeLater(new Runnable()
				{
					public void run()
					{
						window.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				});
			}
		};
		
		this.sendButtonAliceActionListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				SendMessage(Alice, Bob);
			}
		};
		
		this.sendButtonBobActionListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				SendMessage(Bob, Alice);
			}
		};
		
		this.aboutLabelClickListener = new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				JOptionPane.showMessageDialog(null, "<html><body><p>Hazýrlayan : <br/>Ahmet Ertuðrul Özcan<br/><br/>Ondokuz Mayýs Üniversitesi<br/>ertugrul.ozcan@bil.omu.edu.tr<br/></p></body></html>", "Hakkýnda", JOptionPane.INFORMATION_MESSAGE);
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0)
			{}
			@Override
			public void mouseExited(MouseEvent arg0)
			{}
			@Override
			public void mousePressed(MouseEvent arg0)
			{}
			@Override
			public void mouseReleased(MouseEvent arg0)
			{}
		};
	}
	
	private void SendMessage(User from, User to)
	{
		if(from.phoneNumber.equals(Alice.phoneNumber) && to.phoneNumber.equals(Bob.phoneNumber))
		{
			// Alice Bob'a mesaj iletmek istediginde;
			String message = aliceMessageBox.getText();
			
			// Alice'in mesaj gecmisine bir kayit dusulur.
			aliceMessageHistory.setText(aliceMessageHistory.getText() + "\n\r" + "BEN >> " + message);
			
			// Mesaj Bob'un acik anahtari ile sifrelenir.
			Encoder encoder = new Encoder(Bob.publicKey);
			String crypto = encoder.Encrypt(message);
			
			// Sifrelenmis veri guvenli olmayan iletisim kanalina yonlendirilerek Bob'a yollanir.
			unsafeConnectionLine.setText(unsafeConnectionLine.getText() + "\n\r" + userA + " >> " + userB + " : " + crypto);
			
			// Bob'a ulasan mesaj Bob'un kendi gizli anahtari ile desifre edilerek okunur.
			Decoder decoder = new Decoder(Bob.privateKey);
			String cryptedMessage = decoder.Decrypt(crypto);
			
			// Bob'un mesaj gecmisine bir kayit dusulur.
			bobMessageHistory.setText(bobMessageHistory.getText() + "\n\r" + userA + " >> " + cryptedMessage);
			
			aliceMessageBox.setText("");
		}
		else if(from.phoneNumber.equals(Bob.phoneNumber) && to.phoneNumber.equals(Alice.phoneNumber))
		{
			// Bob Alice'e mesaj iletmek istediginde;
			String message = bobMessageBox.getText();
			
			// Bob'un mesaj gecmisine bir kayit dusulur.
			bobMessageHistory.setText(bobMessageHistory.getText() + "\n\r" + "BEN >> " + message);
			
			// Mesaj Alice'in acik anahtari ile sifrelenir.
			Encoder encoder = new Encoder(Alice.publicKey);
			String crypto = encoder.Encrypt(message);
			
			// Sifrelenmis veri guvenli olmayan iletisim kanalina yonlendirilerek Bob'a yollanir.
			unsafeConnectionLine.setText(unsafeConnectionLine.getText() + "\n\r" + userB + " >> " + userA + " : " + crypto);
			
			// Alice'e ulasan mesaj Alice'in kendi gizli anahtari ile desifre edilerek okunur.
			Decoder decoder = new Decoder(Alice.privateKey);
			String cryptedMessage = decoder.Decrypt(crypto);
			
			// Alice'in mesaj gecmisine bir kayit dusulur.
			aliceMessageHistory.setText(aliceMessageHistory.getText() + "\n\r" + userB + " >> " + cryptedMessage);
			
			bobMessageBox.setText("");
		}
	}
	
	private class RsaDemoPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;
		
		JLabel phoneNumber;
		JLabel emailAdress;
		JTextField publicKeyN;
		JTextField publicKeyE;
		
		public RsaDemoPanel(String userName)
		{
			super();
			
			this.setSize(window.getWidth() - 40, 120);
			this.setLayout(null);
			this.setBorder(BorderFactory.createTitledBorder(userName));
			this.SetComponents();
		}
		
		private void SetComponents()
		{
			JLabel phoneNumberLabel = new JLabel("Telefon numarasý :");
			phoneNumberLabel.setBounds(10, 20, 200, 20);
			this.add(phoneNumberLabel);
			
			this.phoneNumber = new JLabel();
			phoneNumber.setBounds(130, 20, 200, 20);
			this.add(phoneNumber);
			
			JLabel emailAdressLabel = new JLabel("E-posta adresi :");
			emailAdressLabel.setBounds(10, 35, 200, 20);
			this.add(emailAdressLabel);
			
			this.emailAdress = new JLabel();
			emailAdress.setBounds(130, 35, 200, 20);
			this.add(emailAdress);

			JLabel publicKeyNLabel = new JLabel("PublicKey-N");
			publicKeyNLabel.setBounds(10, 60, 100, 20);
			this.add(publicKeyNLabel);
			
			this.publicKeyN = new JTextField();
			publicKeyN.setBounds(120, 60, window.getWidth() - 180, 20);
			this.add(publicKeyN);
			
			JLabel publicKeyELabel = new JLabel("PublicKey-E");
			publicKeyELabel.setBounds(10, 85, 100, 20);
			this.add(publicKeyELabel);
			
			this.publicKeyE = new JTextField();
			publicKeyE.setBounds(120, 85, window.getWidth() - 180, 20);
			this.add(publicKeyE);
		}
		
		public void SetFields(User user)
		{
			this.phoneNumber.setText(user.phoneNumber);
			this.emailAdress.setText(user.emailAdress);
			this.publicKeyN.setText(user.publicKey.N().toString());
			this.publicKeyE.setText(user.publicKey.E().toString());
		}
	}
}
