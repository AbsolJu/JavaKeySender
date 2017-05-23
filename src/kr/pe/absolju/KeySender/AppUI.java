package kr.pe.absolju.KeySender;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AppUI {
	
	final static Image iconGIF = Toolkit.getDefaultToolkit().getImage("img/icon2.gif");
	
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
		
		aboutDialog.setVisible(true);
		
		Label M1 = new Label("Protocol Buffer");
		Label M2 = new Label("Java KeyPresser&Sender");
		
		M1.setBounds(10,10,1000,20);
		M2.setBounds(10, 30, 1000, 20);
		aboutDialog.add(M1);
		aboutDialog.add(M2);
		
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
		JTextField addressField = new JTextField(10);
		
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
		
	}
}
	
	
