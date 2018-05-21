package ru.teosa.utils;

public class Queries {

//***********************************************************************************************************************	
//****************************            GET                  **********************************************************
//***********************************************************************************************************************	
	/** Версии сайта */
	public final static String GET_GAME_VERSIONS = ""
	        + "SELECT ID, FULLNAME AS name, URL FROM GAMEVERSIONS ORDER BY LASTUSED DESC, FULLNAME ASC";
	
	/** Список пользователей для выбранной версии сайта */
	public final static String GET_USERS_BY_VERSION = ""
			+ "SELECT u.ID, u.ALIAS AS username, ac.PASSWORD, ac.LASTUSED, ac.ID AS accountid "
			+ "FROM USERS u "
			+ "JOIN USERTOACCOUNT uta ON uta.USERID = u.ID "
			+ "JOIN ACCOUNTS ac ON ac.ID = uta.ACCOUNTID "
			+ "WHERE ac.VERSION = :versionid "
			+ "ORDER BY ac.LASTUSED DESC";
	
//***********************************************************************************************************************	
//****************************            SAVE                 **********************************************************
//***********************************************************************************************************************			
	public final static String SAVE_ACCOUNT = "INSERT INTO ACCOUNTS VALUES (DEFAULT, :password, :gamever, 'Y')";
	public final static String SAVE_USER = "INSERT INTO USERS VALUES (DEFAULT, :login)";
	public final static String ATTACH_ACCOUNT_TO_USER = "INSERT INTO USERTOACCOUNT VALUES (:userid, :accid)";
//***********************************************************************************************************************	
//****************************            UPDATE               **********************************************************
//***********************************************************************************************************************
	public final static String UPD_USER_LASTUSED = ""
			+ "UPDATE ACCOUNTS "
			+ "SET LASTUSED = 'Y' "
			+ "WHERE ID IN ( "
			+ "  SELECT ac.ID "
			+ "  FROM USERS u "
			+ "  JOIN USERTOACCOUNT uta ON uta.USERID = u.ID "
			+ "  JOIN ACCOUNTS ac ON ac.ID = uta.ACCOUNTID "
			+ "  WHERE ac.VERSION = :versionid AND u.ID = :id " 
			+ ")";
}
