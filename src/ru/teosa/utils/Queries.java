package ru.teosa.utils;

public class Queries {

	/** ������ ����� */
	public final static String GET_GAME_VERSIONS = ""
	        + "SELECT * FROM GAMEVERSIONS ORDER BY LASTUSED DESC, FULLNAME ASC";
	
	/** ������ ������������� ��� ��������� ������ ����� */
	public final static String GET_USERS_BY_VERSION = ""
			+ "SELECT ID, ALIAS, PASSWORD "
			+ "FROM USERS "
			+ "WHERE VERSION = :versionid " 
			+ "ORDER BY LASTUSED DESC";
	
	
	
	
}
