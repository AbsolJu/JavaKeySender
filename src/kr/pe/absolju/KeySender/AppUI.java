package kr.pe.absolju.KeySender;

import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AppUI {
	
	final static Image iconGIF = Toolkit.getDefaultToolkit().getImage("img/icon2.gif");
	
	public static void Create() {
		
		final TrayIcon trayIcon = new TrayIcon(iconGIF, "JavaKeySender");
		trayIcon.setImageAutoSize(true);
		final SystemTray tray = SystemTray.getSystemTray();
		try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("Error: Tray 아이콘 생성 중, TrayUI.TrayUI");
        }
		
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
		
		JButton liveInput = new JButton("실시간으로 보내기");
		JButton macro = new JButton("매크로");
		JButton setting = new JButton("설정");
		JButton about = new JButton("정보");
		
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
		aboutDialog.setTitle("정보");
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
		settingFrame.setTitle("설정");
		settingFrame.setSize(400, 450);
		settingFrame.setIconImage(iconGIF);
		settingFrame.setResizable(false);
		settingFrame.setLocationRelativeTo(null);
		
		settingFrame.setVisible(true);
	}
	
}
