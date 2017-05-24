package kr.pe.absolju.KeySender;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import kr.pe.absolju.KeySender.KeyValueProtos.KeyData;
import kr.pe.absolju.KeySender.KeyValueProtos.KeyInput;

public class sendKeyValue {

	public void single(String senderid, int keyValue, boolean isPress) {
		
		KeyData.Builder keydata = KeyData.newBuilder();
		
		//-전체데이터
		keydata.setSenderid(senderid);
		keydata.setMacro(false);
		
		//--내부데이터
		KeyInput.Builder keyinput = KeyInput.newBuilder();
		keyinput.setValue(keyValue);
		keyinput.setPress(isPress);
		//--끝
		
		keydata.addKeyinput(keyinput);
		//-끝
		
		//클라이언트 파트(클라이언트에서 서버로 키 값 전달)
		try {
			Socket socket = new Socket(FileIO.getAddressName(), FileIO.getSocketNumber());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			keydata.build().writeTo(output);
			socket.close();
		} catch (IOException e) {
			System.out.println("Error: KeyData 송신 중, sendKeyValue.single");
		}
	}
	
	public void multi(KeyData keydata) {
		try {
			Socket socket = new Socket(FileIO.getAddressName(), FileIO.getSocketNumber());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			keydata.writeTo(output);
			socket.close();
		} catch (IOException e) {
			System.out.println("Error: KeyData 송신 중, sendKeyValue.multi");
		}
	}
}
