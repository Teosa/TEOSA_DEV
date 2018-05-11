package ru.teosa.utils;

public class Queries {

//***********************************************************************************************************************	
//****************************            GET                  **********************************************************
//***********************************************************************************************************************	
	/** ������ ����� */
	public final static String GET_GAME_VERSIONS = ""
	        + "SELECT ID, FULLNAME AS name, URL FROM GAMEVERSIONS ORDER BY LASTUSED DESC, FULLNAME ASC";
	
	/** ������ ������������� ��� ��������� ������ ����� */
	public final static String GET_USERS_BY_VERSION = ""
			+ "SELECT ID, ALIAS AS username, PASSWORD, LASTUSED "
			+ "FROM USERS "
			+ "WHERE VERSION = :versionid " 
			+ "ORDER BY LASTUSED DESC";
	
	
	
//***********************************************************************************************************************	
//****************************            SAVE                 **********************************************************
//***********************************************************************************************************************		
	
	public final static String SAVE_USER = ""
			+ "INSERT INTO USERS VALUES (DEFAULT, :login, :password, 'Y', :gamever )";
	
//***********************************************************************************************************************	
//****************************            UPDATE               **********************************************************
//***********************************************************************************************************************
	public final static String UPD_USER_LASTUSED = ""
			+ "UPDATE USERS SET LASTUSED = 'Y' WHERE ID = :id";
}