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
	
	private static String addressName = "localhost"; //ù ���� �� �����ؾ� ��
	private static int socketNumber = 8080; //�ʱⰪ
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
		
		//�ҷ�����
		savesetTemp.setAddress(address);
		savesetTemp.setPortNumber(socketNumber);
		
		//����
		BufferedOutputStream buffer = null;
		try {
			buffer = new BufferedOutputStream(new FileOutputStream(settingFileName));
			savesetTemp.build().writeTo(buffer);
		} catch (FileNotFoundException e) {
			System.out.println("Error: ���� ���� �� ����, FileIO.SaveSetting");
		} catch (IOException e) {
			System.out.println("Error: ���� ���� �� ����, FileIO.SaveSetting");
		} finally {
			if(buffer!=null) {
				try {
					buffer.close();
				} catch (IOException e) {
					System.out.println("Error: ���� �ݴ� �� ����, FileIO.SaveSetting");
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
			System.out.println("Error: ���� ������ ���ų� �д� �� ����, �ʱ�ȭ �ʿ�, FileIO.LoadSetting");
			SaveSetting(addressName, socketNumber);
		}
	}
	
	public static void SaveMacro(String name, KeyData keydata) {
		SaveData.Builder savedataTemp = SaveData.newBuilder();
		
		//null�� �Է� �ÿ��� �ʱ�ȭ, �ƴ� ��� �߰��ؼ� ����
		if(name!=null && keydata!=null) {
			//�ҷ�����
			for(int i=0;i<savedata.getKeydataCount();++i) {
				savedataTemp.addName(savedata.getName(i));
				savedataTemp.addKeydata(savedata.getKeydata(i));
			}
			//�̸� �ߺ��� ���ٸ�, �߰��� ���� ����
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
		
		//����
		BufferedOutputStream buffer = null;
		try {
			buffer = new BufferedOutputStream(new FileOutputStream(macroFileName));
			savedataTemp.build().writeTo(buffer);
		} catch (FileNotFoundException e) {
			System.out.println("Error: ���� ���� �� ����, FileIO.SaveMacro");
		} catch (IOException e) {
			System.out.println("Error: ���� ���� �� ����, FileIO.SaveMacro");
		} finally {
			if(buffer!=null) {
				try {
					buffer.close();
				} catch (IOException e) {
					System.out.println("Error: ���� �ݴ� �� ����, FileIO.SaveMacro");
				}
			}
		}
	}
	
	public static void DeleteMacro(int macroIndex) {
		SaveData.Builder savedataTemp = SaveData.newBuilder();
		
		//�ҷ�����, �� ������ �ε��� ����
		for(int i=0;i<savedata.getKeydataCount();++i) {
			if(i!=macroIndex) {
				savedataTemp.addName(savedata.getName(i));
				savedataTemp.addKeydata(savedata.getKeydata(i));
			}
		}
		
		//����
		BufferedOutputStream buffer = null;
		try {
			buffer = new BufferedOutputStream(new FileOutputStream(macroFileName));
			savedataTemp.build().writeTo(buffer);
		} catch (FileNotFoundException e) {
			System.out.println("Error: ���� ���� �� ����, FileIO.SaveMacro");
		} catch (IOException e) {
			System.out.println("Error: ���� ���� �� ����, FileIO.SaveMacro");
		} finally {
			if(buffer!=null) {
				try {
					buffer.close();
				} catch (IOException e) {
					System.out.println("Error: ���� �ݴ� �� ����, FileIO.SaveMacro");
				}
			}
		}
	}
	
	public static void LoadMacro() {
		try {
			BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(macroFileName));
			savedata = SaveData.parseFrom(buffer);
		} catch (IOException e) {
			System.out.println("Error: ���� ������ ���ų� �д� �� ����, �ʱ�ȭ �ʿ�, FileIO.LoadMacro");
			SaveMacro(null, null);
		}
	}
	
}
