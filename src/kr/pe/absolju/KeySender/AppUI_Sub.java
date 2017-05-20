package kr.pe.absolju.KeySender;

import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AppUI_Sub {
	
	public static void LiveInput() {
		sendKeyValue key = new sendKeyValue();
		
		JDialog frame = new JDialog();
		frame.setTitle("입력받는 중...");
		frame.setSize(200, 75);
		//frame.setIconImage(iconGIF);
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		frame.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JLabel text = new JLabel("이 창에 입력하세요.");
		
		frame.add(text);
		frame.setVisible(true);
		
		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				key.single("absolju", e.getKeyCode(), true);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				key.single("absolju", e.getKeyCode(), false);
			}
			@Override
			public void keyTyped(KeyEvent e) {
				//없음
			}
		});
	}
	
	public static void Macro() {
		sendKeyValue key = new sendKeyValue();
		
		JFrame frame = new JFrame();
		frame.setTitle("매크로");
		frame.setSize(400, 300);
		//frame.setIconImage(iconGIF);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		
		
		frame.setVisible(true);
	}
	
}
