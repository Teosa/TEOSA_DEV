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
			+ "SELECT u.ID, u.ALIAS AS username, ac.PASSWORD, ac.LASTUSED, ac.ID AS accountid, ac.VERSION AS versionid "
			+ "FROM USERS u "
			+ "JOIN USERTOACCOUNT uta ON uta.USERID = u.ID "
			+ "JOIN ACCOUNTS ac ON ac.ID = uta.ACCOUNTID "
			+ "WHERE ac.VERSION = :versionid "
			+ "ORDER BY ac.LASTUSED DESC";
	
	/** Получение основной информации об аккаунте */
	public final static String GET_USER = ""
			+ "SELECT u.ID, u.ALIAS AS username, ac.PASSWORD, ac.LASTUSED, ac.ID AS accountid, ac.VERSION AS versionid "
			+ "FROM USERS u "
			+ "JOIN USERTOACCOUNT uta ON uta.USERID = u.ID "
			+ "JOIN ACCOUNTS ac ON ac.ID = uta.ACCOUNTID "
			+ "WHERE ac.VERSION = :versionid AND u.ID = :userid "
			+ "ORDER BY ac.LASTUSED DESC";
	
	/** Получение аффиксов */
	public final static String GET_AFFIXES = ""
			+ "SELECT a.ID, a.NAME "
			+ "FROM AFFIXTOACCOUNT ata "
			+ "JOIN AFFIXES a ON a.ID = ata.AFFIXID "
			+ "WHERE ata.ACCOUNTID = :accountid";
	
	/** Получение программ прогона */
	public final static String GET_HERD_RUN_PROGRAMS = "SELECT ID, NAME, SETTINGS FROM PROGRAMS";
	
//***********************************************************************************************************************	
//****************************            SAVE                 **********************************************************
//***********************************************************************************************************************			
	public final static String SAVE_ACCOUNT = "INSERT INTO ACCOUNTS VALUES (DEFAULT, :password, :gamever, 'Y')";
	public final static String SAVE_USER = "INSERT INTO USERS VALUES (DEFAULT, :login)";
	public final static String ATTACH_ACCOUNT_TO_USER = "INSERT INTO USERTOACCOUNT VALUES (:userid, :accid)";
	public final static String SAVE_AFFIX = "INSERT INTO AFFIXES VALUES (DEFAULT, :name)";
	public final static String ATTACH_AFFIX_TO_ACCOUNT = "INSERT INTO AFFIXTOACCOUNT VALUES (:accountid, :affixid)";
	public final static String SAVE_HERD_RUN_PROGRAM = "INSERT INTO PROGRAMS VALUES (DEFAULT, :name, :settings)";
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
	
	public final static String UPD_AFFIX = "UPDATE AFFIXES SET NAME = :name WHERE ID = :affixid";
	
	public final static String UPD_HERD_RUN_PROGRAM = "";
//***********************************************************************************************************************	
//****************************            REMOVE               **********************************************************
//***********************************************************************************************************************
	public final static String UNATTACH_AFFIX_FROM_ACCOUNT = ""
			+ "DELETE FROM AFFIXTOACCOUNT WHERE ACCOUNTID = :accountid AND AFFIXID = :affixid";
	public final static String REMOVE_AFFIX = ""
			+ "DELETE FROM AFFIXES WHERE ID = :affixid";
}
