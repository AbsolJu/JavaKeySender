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
			Socket socket = new Socket("localhost", 8080);
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			keydata.build().writeTo(output);
			socket.close();
		} catch (IOException e) {
			System.out.println("Error: KeyData 송신 중, sendKeyValue.single");
		}
	}
	
	public void multi() {
		//내용 없음. 여러 키를 받아 한 번에 처리, 매크로?
	}
}
