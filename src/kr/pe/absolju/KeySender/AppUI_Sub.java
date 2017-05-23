package kr.pe.absolju.KeySender;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.lang3.time.StopWatch;

import kr.pe.absolju.KeySender.KeyValueProtos.KeyData;
import kr.pe.absolju.KeySender.KeyValueProtos.KeyInput;

public class AppUI_Sub {

	final static Image iconGIF = Toolkit.getDefaultToolkit().getImage("img/icon2.gif");
	private static DefaultListModel<String> macroListValues = new DefaultListModel<String>();
	
	private static class KeydataBuffer {
		private KeyData keydata;
		private boolean empty = true;
		synchronized KeyData get() {
			while(empty) {
				try {
					wait();
				} catch (InterruptedException e) {
					System.out.println("Error: ���ۿ��� ���� �������� ��, runKeyValue.keydataBuffer.get");
				}
			}
			empty = true;
			notifyAll();
			return keydata;
		}
		synchronized void put(KeyData keydata) {
			while(!empty) {
				try {
					wait();
				} catch (InterruptedException e) {
					System.out.println("Error: ���ۿ� ���� �ִ� ��, runKeyValue.keydataBuffer.put");
				}
			}
			empty = false;
			this.keydata = keydata;
			notifyAll();
		}
	}
	
	public static void LiveInput() {
		sendKeyValue key = new sendKeyValue();
		
		JDialog frame = new JDialog();
		frame.setTitle("�Է¹޴� ��...");
		frame.setSize(200, 75);
		//frame.setIconImage(iconGIF);
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		frame.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JLabel text = new JLabel("�� â�� �Է��ϼ���.");
		
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
			public void keyTyped(KeyEvent e) {}
		});
	}
	
	public static void Macro() {
		JFrame frame = new JFrame();
		frame.setTitle("��ũ��");
		frame.setSize(400, 300);
		frame.setIconImage(iconGIF);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		frame.setLayout(new BorderLayout());
		
		//-UI ���� ����
		LoadMacroListValues();
		JList<String> macroList = new JList<String>();
		macroList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		macroList.setLayoutOrientation(JList.VERTICAL);
		macroList.setModel(macroListValues);
		JScrollPane macroListScrollPane = new JScrollPane(macroList);
		
		JButton makeMacro = new JButton("���� �����");
		JButton sendMacro = new JButton("������");
		sendMacro.setEnabled(false);
		JButton deleteMacro = new JButton("����");
		deleteMacro.setEnabled(false);
		JPanel macroButtonPanel = new JPanel();
		macroButtonPanel.setLayout(new GridLayout(0, 3));
		macroButtonPanel.add(makeMacro);
		macroButtonPanel.add(sendMacro);
		macroButtonPanel.add(deleteMacro);
		
		frame.add(macroListScrollPane, BorderLayout.CENTER);
		frame.add(macroButtonPanel, BorderLayout.PAGE_END);
		frame.setVisible(true);
		//-UI ���� ��
		
		macroList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(macroList.getSelectedIndex() == -1) {
					sendMacro.setEnabled(false);
					deleteMacro.setEnabled(false);
				} else {
					sendMacro.setEnabled(true);
					deleteMacro.setEnabled(true);
				}
			}
		});
		
		makeMacro.addActionListener(new ActionListener() { //Macro ���� �����
			@Override
			public void actionPerformed(ActionEvent e) {
				KeydataBuffer buffer = new KeydataBuffer();
				new Thread(new MacroSave(buffer)).start();
				new Thread(new MacroInput(buffer)).start();
			}
		});
		sendMacro.addActionListener(new ActionListener() { //JList���� ���õ� �׸� ������
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new MacroSend(macroList.getSelectedIndex())).start();
			}
		});
		deleteMacro.addActionListener(new ActionListener() { //JList���� ���õ� �׸� ����
			@Override
			public void actionPerformed(ActionEvent e) {
				//macroListValues.remove(macroList.getSelectedIndex());
				FileIO.DeleteMacro(macroList.getSelectedIndex());
				LoadMacroListValues();
			}
		});
		
	}
	
	private static void LoadMacroListValues() {
		FileIO.LoadMacro();
		
		//macroListValues�� FileIO���� �ҷ��� ��ũ�� ����
		macroListValues.clear();
		for(int i=0;i<FileIO.getSavedata().getKeydataCount();++i) {
			macroListValues.addElement(FileIO.getSavedata().getName(i));
		}
	}
	
	private static class MacroInput implements Runnable { //Macro ���� ����� �� �Է� ����
		KeydataBuffer buffer;
		MacroInput(KeydataBuffer buffer) { this.buffer = buffer; }
		
		@Override
		public void run() {
			JDialog frame = new JDialog();
			frame.setTitle("�Է¹޴� ��...");
			frame.setSize(200, 75);
			//frame.setIconImage(iconGIF);
			frame.setAlwaysOnTop(true);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			
			frame.setLayout(new FlowLayout(FlowLayout.CENTER));
			
			JLabel text = new JLabel("�� â�� �Է��ϼ���.");
			
			frame.add(text);
			frame.setVisible(true);
			
			//KeyData ����
			KeyData.Builder keydata = KeyData.newBuilder();
			keydata.setSenderid("absolju");
			keydata.setMacro(true);

			KeyInput.Builder keyinput = KeyInput.newBuilder();
			StopWatch stopwatch = new StopWatch();
			stopwatch.start();
			
			frame.addKeyListener(new KeyListener() {
				@Override
				public void keyPressed(KeyEvent e) {
					keyinput.setWait((int)stopwatch.getTime());
					stopwatch.reset();
					stopwatch.start();
					keyinput.setValue(e.getKeyCode());
					keyinput.setPress(true);
					keydata.addKeyinput(keyinput);
				}
				@Override
				public void keyReleased(KeyEvent e) {
					keyinput.setWait((int)stopwatch.getTime());
					stopwatch.reset();
					stopwatch.start();
					keyinput.setValue(e.getKeyCode());
					keyinput.setPress(false);
					keydata.addKeyinput(keyinput);
				}
				@Override
				public void keyTyped(KeyEvent e) {}
			});
			
			frame.addWindowListener(new WindowListener() {
				@Override
				public void windowActivated(WindowEvent arg0) {}
				@Override
				public void windowClosed(WindowEvent arg0) {}
				@Override
				public void windowClosing(WindowEvent arg0) {
					buffer.put(keydata.build());
				}
				@Override
				public void windowDeactivated(WindowEvent arg0) {}
				@Override
				public void windowDeiconified(WindowEvent arg0) {}
				@Override
				public void windowIconified(WindowEvent arg0) {}
				@Override
				public void windowOpened(WindowEvent arg0) {}
			});
		}
	}
	
	private static class MacroSave implements Runnable { //Macro ���� ����� �� Input�� ������ ����
		KeydataBuffer buffer;
		MacroSave(KeydataBuffer buffer) { this.buffer=buffer; }
		
		@Override
		public void run() {
			//testSave�� String�� �̸�. â�� ��� �޾ƾ� ��
			KeyData keydata = buffer.get(); //���� �޾ƿ���, ���⼭ MacroInput�� �Ϸ�Ǳ� ������ wait ��.
			String name;
			
			JDialog frame = new JDialog();
			frame.setTitle("��ũ�� ����");
			frame.setSize(200, 75);
			//frame.setIconImage(iconGIF);
			frame.setAlwaysOnTop(true);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			
			frame.setLayout(new FlowLayout(FlowLayout.CENTER));
			
			JLabel nameLabel = new JLabel("�̸�:");
			JTextField nameField = new JTextField(10);
			
			frame.add(nameLabel);
			frame.add(nameField);
			frame.setVisible(true);
			
			frame.addWindowListener(new WindowListener() {
				@Override
				public void windowActivated(WindowEvent arg0) {}
				@Override
				public void windowClosed(WindowEvent arg0) {}
				@Override
				public void windowClosing(WindowEvent arg0) {
					FileIO.SaveMacro(nameField.getText(), keydata);
					LoadMacroListValues();
				}
				@Override
				public void windowDeactivated(WindowEvent arg0) {}
				@Override
				public void windowDeiconified(WindowEvent arg0) {}
				@Override
				public void windowIconified(WindowEvent arg0) {}
				@Override
				public void windowOpened(WindowEvent arg0) {}
			});
		}
	}
	
	private static class MacroSend implements Runnable { //��ư�� ���� �� ���õ� ��ũ�� ������
		int macroIndex;
		MacroSend(int index) { macroIndex = index; }
		
		@Override
		public void run() {
			new sendKeyValue().multi(FileIO.getSavedata().getKeydata(macroIndex));
		}
	}
	
}
