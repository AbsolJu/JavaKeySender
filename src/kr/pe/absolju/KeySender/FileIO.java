package kr.pe.absolju.KeySender;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import kr.pe.absolju.KeySender.KeyValueProtos.KeyData;
import kr.pe.absolju.KeySender.KeyValueProtos.SaveData;
import kr.pe.absolju.KeySender.KeyValueProtos.SaveSetting;

public class FileIO {
	private static String macroFileName = "JKSmacro.dat";
	private static String settingFileName = "JKSset.dat";
	
	private static SaveData savedata;
	public static SaveData getSavedata() {
		return savedata;
	}
	
	private static String addressName = "localhost"; //첫 실행 후 설정해야 함
	private static int socketNumber = 8080; //초기값
	public static String getAddressName() {
		return addressName;
	}
	public static void setAddressName(String addressName) {
		FileIO.addressName = addressName;
	}
	public static int getSocketNumber() {
		return socketNumber;
	}
	public static void setSocketNumber(int socketNumber) {
		FileIO.socketNumber = socketNumber;
	}
	
	
	public static void SaveSetting(String address, int socketNumber) {
		SaveSetting.Builder savesetTemp = SaveSetting.newBuilder();
		
		//불러오기
		savesetTemp.setAddress(address);
		savesetTemp.setPortNumber(socketNumber);
		
		//저장
		BufferedOutputStream buffer = null;
		try {
			buffer = new BufferedOutputStream(new FileOutputStream(settingFileName));
			savesetTemp.build().writeTo(buffer);
		} catch (FileNotFoundException e) {
			System.out.println("Error: 파일 생성 중 오류, FileIO.SaveSetting");
		} catch (IOException e) {
			System.out.println("Error: 파일 쓰는 중 오류, FileIO.SaveSetting");
		} finally {
			if(buffer!=null) {
				try {
					buffer.close();
				} catch (IOException e) {
					System.out.println("Error: 파일 닫는 중 오류, FileIO.SaveSetting");
				}
			}
		}
	}
	
	public static void LoadSetting() {
		try {
			BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(settingFileName));
			SaveSetting setting = SaveSetting.parseFrom(buffer);
			addressName = setting.getAddress();
			socketNumber = setting.getPortNumber();
		} catch (IOException e) {
			System.out.println("Error: 설정 파일이 없거나 읽는 중 오류, 초기화 필요, FileIO.LoadSetting");
			SaveSetting(addressName, socketNumber);
		}
	}
	
	public static void SaveMacro(String name, KeyData keydata) {
		SaveData.Builder savedataTemp = SaveData.newBuilder();
		
		//null로 입력 시에는 초기화, 아닐 경우 추가해서 저장
		if(name!=null && keydata!=null) {
			//불러오기
			for(int i=0;i<savedata.getKeydataCount();++i) {
				savedataTemp.addName(savedata.getName(i));
				savedataTemp.addKeydata(savedata.getKeydata(i));
			}
			//이름 중복이 없다면, 추가할 내용 붙임
			boolean isDuplicate = false;
			for(int i=0;i<savedata.getKeydataCount();++i) {
				if(name==savedata.getName(i)) {
					isDuplicate = true;
				}
			}
			if(isDuplicate==false) {
				savedataTemp.addName(name);
				savedataTemp.addKeydata(keydata);
			}
		}
		
		//저장
		BufferedOutputStream buffer = null;
		try {
			buffer = new BufferedOutputStream(new FileOutputStream(macroFileName));
			savedataTemp.build().writeTo(buffer);
		} catch (FileNotFoundException e) {
			System.out.println("Error: 파일 생성 중 오류, FileIO.SaveMacro");
		} catch (IOException e) {
			System.out.println("Error: 파일 쓰는 중 오류, FileIO.SaveMacro");
		} finally {
			if(buffer!=null) {
				try {
					buffer.close();
				} catch (IOException e) {
					System.out.println("Error: 파일 닫는 중 오류, FileIO.SaveMacro");
				}
			}
		}
	}
	
	public static void DeleteMacro(int macroIndex) {
		SaveData.Builder savedataTemp = SaveData.newBuilder();
		
		//불러오기, 단 지정된 인덱스 제외
		for(int i=0;i<savedata.getKeydataCount();++i) {
			if(i!=macroIndex) {
				savedataTemp.addName(savedata.getName(i));
				savedataTemp.addKeydata(savedata.getKeydata(i));
			}
		}
		
		//저장
		BufferedOutputStream buffer = null;
		try {
			buffer = new BufferedOutputStream(new FileOutputStream(macroFileName));
			savedataTemp.build().writeTo(buffer);
		} catch (FileNotFoundException e) {
			System.out.println("Error: 파일 생성 중 오류, FileIO.SaveMacro");
		} catch (IOException e) {
			System.out.println("Error: 파일 쓰는 중 오류, FileIO.SaveMacro");
		} finally {
			if(buffer!=null) {
				try {
					buffer.close();
				} catch (IOException e) {
					System.out.println("Error: 파일 닫는 중 오류, FileIO.SaveMacro");
				}
			}
		}
	}
	
	public static void LoadMacro() {
		try {
			BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(macroFileName));
			savedata = SaveData.parseFrom(buffer);
		} catch (IOException e) {
			System.out.println("Error: 설정 파일이 없거나 읽는 중 오류, 초기화 필요, FileIO.LoadMacro");
			SaveMacro(null, null);
		}
	}
	
}
