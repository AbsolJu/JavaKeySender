package kr.pe.absolju.KeySender;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import kr.pe.absolju.KeySender.KeyValueProtos.KeyData;
import kr.pe.absolju.KeySender.KeyValueProtos.KeyInput;

public class sendKeyValue {

	public void single(String senderid, int keyValue, boolean isPress) {
		
		KeyData.Builder keydata = KeyData.newBuilder();
		
		keydata.setSenderid(senderid);
		keydata.setMacro(false);
		
		//--���ε�����
		KeyInput.Builder keyinput = KeyInput.newBuilder();
		keyinput.setValue(keyValue);
		keyinput.setPress(isPress);
		//--��
		
		keydata.addKeyinput(keyinput);
		
		//--(�ӽ� �׽�Ʈ. ���� ���常 ��)
		/*
		FileOutputStream output = new FileOutputStream("output.prbuf");
		keydata.build().writeTo(output);
		output.close();
		*/
		
		//Ŭ���̾�Ʈ ��Ʈ(Ŭ���̾�Ʈ���� ������ Ű �� ����)
		try {
			Socket socket = new Socket("localhost", 8080);
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			keydata.build().writeTo(output);
			socket.close();
		} catch (IOException e) {
			System.out.println("Error: KeyData �۽� ��, sendKeyValue.single");
		}
	}
	
	public void multi() {
		//���� ����. ���� Ű�� �޾� �� ���� ó��, ��ũ��?
	}
}