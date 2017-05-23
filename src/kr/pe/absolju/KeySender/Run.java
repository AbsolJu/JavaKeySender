package kr.pe.absolju.KeySender;

public class Run {

	public static void main(String[] args) {
		//FileIO.SaveMacro(null, null);
		FileIO.LoadSetting();
		FileIO.LoadMacro();
		AppUI.Create();
	}

}
