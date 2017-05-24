package kr.pe.absolju.KeySender;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import kr.pe.absolju.KeySender.KeyValueProtos.KeyData;
import kr.pe.absolju.KeySender.KeyValueProtos.KeyInput;

public class sendKeyValue {

	public void single(String senderid, int keyValue, boolean isPress) {
		
		KeyData.Builder keydata = KeyData.newBuilder();
		
		//-��ü������
		keydata.setSenderid(senderid);
		keydata.setMacro(false);
		
		//--���ε�����
		KeyInput.Builder keyinput = KeyInput.newBuilder();
		keyinput.setValue(keyValue);
		keyinput.setPress(isPress);
		//--��
		
		keydata.addKeyinput(keyinput);
		//-��
		
		//Ŭ���̾�Ʈ ��Ʈ(Ŭ���̾�Ʈ���� ������ Ű �� ����)
		try {
			Socket socket = new Socket(FileIO.getAddressName(), FileIO.getSocketNumber());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			keydata.build().writeTo(output);
			socket.close();
		} catch (IOException e) {
			System.out.println("Error: KeyData �۽� ��, sendKeyValue.single");
		}
	}
	
	public void multi(KeyData keydata) {
		try {
			Socket socket = new Socket(FileIO.getAddressName(), FileIO.getSocketNumber());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			keydata.writeTo(output);
			socket.close();
		} catch (IOException e) {
			System.out.println("Error: KeyData �۽� ��, sendKeyValue.multi");
		}
	}
}
