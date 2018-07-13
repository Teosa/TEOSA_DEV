package ru.teosa.lang;

import ru.teosa.account.Account;

public class Lang{
	
	public static LangObject lang = new LangObject();
	

	public static void loadMap() {
		Integer versionID = Account.getUser().getVersionid();
		
		switch( versionID ) 
		{
			case 2:  lang = lang.loadFromMap(LangMap.LANG_RU); break;
			default: lang = lang.loadFromMap(LangMap.LANG_EN); break;
		}
	}
	
}
