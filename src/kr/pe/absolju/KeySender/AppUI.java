package kr.pe.absolju.KeySender;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AppUI {
	
	final static URL imageUrl = ClassLoader.getSystemResource("kr/pe/absolju/KeySender/img/icon2.gif");
	final static Image iconGIF = Toolkit.getDefaultToolkit().getImage(imageUrl);
	
	public static void Create() {
		
		JFrame frame = new JFrame();
		frame.setTitle("JavaKeySender");
		frame.setSize(390, 75);
		frame.setIconImage(iconGIF);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLayout(new FlowLayout());
		
		JPanel mainPanel = new JPanel();
		JPanel subPanel = new JPanel();
		
		JButton liveInput = new JButton("�ǽð����� ������");
		JButton macro = new JButton("��ũ��");
		JButton setting = new JButton("����");
		JButton about = new JButton("����");
		
		mainPanel.add(liveInput);
		mainPanel.add(macro);
		subPanel.add(setting);
		subPanel.add(about);
		
		frame.add(mainPanel);
		frame.add(subPanel);
		
		frame.setVisible(true);
		
		
		liveInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AppUI_Sub.LiveInput();
			}
		});
		macro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AppUI_Sub.Macro();
			}
		});
		
		setting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SettingFrame();
			}
		});
		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AboutDialog();
			}
		});
	}
	
	public static void AboutDialog() {
		JDialog aboutDialog = new JDialog();
		
		aboutDialog.setTitle("����");
		aboutDialog.setSize(380, 150);
		aboutDialog.setIconImage(iconGIF);
		aboutDialog.setResizable(false);
		aboutDialog.setLocationRelativeTo(null);
		
		aboutDialog.setLayout(new BorderLayout());
		
		JLabel M1 = new JLabel("<html>Protocol Buffer<br>Java KeyPresser&Sender<br></html>");
		JLabel M2 = new JLabel(new ImageIcon(imageUrl));
		aboutDialog.add(M1,BorderLayout.CENTER);
		aboutDialog.add(M2,BorderLayout.WEST);
		
		aboutDialog.setVisible(true);
	}
	
	public static void SettingFrame() {
		JFrame settingFrame = new JFrame();
		settingFrame.setTitle("����");
		settingFrame.setSize(230, 130);
		settingFrame.setIconImage(iconGIF);
		settingFrame.setResizable(false);
		settingFrame.setLocationRelativeTo(null);
		
		settingFrame.setLayout(new BorderLayout());
		
		//���� ����� �� �ִ� Panel ����
		JPanel optionList = new JPanel();
		JLabel portLabel = new JLabel("��Ʈ ��ȣ:");
		JLabel addressLabel = new JLabel("���� �ּ�:");
		JTextField portField = new JTextField(10);
		portField.setText(String.valueOf(FileIO.getSocketNumber()));
		JTextField addressField = new JTextField(10);
		addressField.setText(FileIO.getAddressName());
		
		optionList.add(addressLabel);
		optionList.add(addressField);
		optionList.add(portLabel);
		optionList.add(portField);
		
		
		//Ȯ�� ��ư
		JButton ok = new JButton("OK");
		
		//���� ��ϰ� Ȯ�� ��ư ����
		settingFrame.add(optionList, BorderLayout.CENTER);
		settingFrame.add(ok, BorderLayout.PAGE_END);
		
		settingFrame.setVisible(true);
		
		
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileIO.SaveSetting(addressField.getText(), Integer.parseInt(portField.getText()));
				FileIO.LoadSetting();
			}
		});
	}
}
	
	
