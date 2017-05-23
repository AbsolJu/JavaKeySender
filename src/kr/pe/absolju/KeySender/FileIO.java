package kr.pe.absolju.KeySender;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import kr.pe.absolju.KeySender.KeyValueProtos.KeyData;
import kr.pe.absolju.KeySender.KeyValueProtos.SaveData;

public class FileIO {
	private static String macroFileName = "JKSmacro.dat";
	private static String settingFileName = "JKSset.dat";
	private static SaveData savedata;
	public static SaveData getSavedata() {
		return savedata;
	}

	public static void SaveSetting() {
		
	}
	
	public static void LoadSetting() {
		
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
