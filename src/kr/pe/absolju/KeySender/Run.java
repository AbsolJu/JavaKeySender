package kr.pe.absolju.KeySender;

import java.awt.event.KeyEvent;

public class Run {

	public static void main(String[] args) {
		sendKeyValue key = new sendKeyValue();
		
		key.single("absolju", KeyEvent.VK_WINDOWS, true);
		try { Thread.sleep(100); } catch (InterruptedException e) { }
		key.single("absolju", KeyEvent.VK_WINDOWS, false);

		try { Thread.sleep(100); } catch (InterruptedException e) { }
		
		key.single("absolju", KeyEvent.VK_C, true);
		try { Thread.sleep(100); } catch (InterruptedException e) { }
		key.single("absolju", KeyEvent.VK_C, false);
	}

}
