package ru.teosa.utils;

public class XPathConstants {

	/** �������� ���� ���� (������� ����������, ���, ��������, ����������, ����������) */
	public static final String MAIN_HEADER = "//*[@id=\"header-menu\"]";
	
		/** ������� ����������� ��������� ���� */
		public static final String MAIN_HEADER_BREEDING_FARM = "//*[@id=\"header-menu\"]/div[2]/ul/li[1]";
		
			/** ������ ������ �� ������� ����������� */
			public static final String MAIN_HEADER_BREEDING_FARM_HORSES = "//*[@id=\"header-menu\"]/div[2]/ul/li[1]/ul/li[2]/a";
	
	
	
	/** ������ ������� � ������ */
	public static final String FARM_LIST = "//*[@id=\"horseList\"]";
	/** ������ ������ � ������ */
	public static final String FARM_LIST_FIRST_HORSE = "//*[@id=\"horseList\"]/div/div[2]/ul[1]/li[1]/div/div[1]/div/ul/li[1]/a";
	
	
	
	
	//*************************************
	//*        �������� ������            *
	//*************************************
	/** ������ "�������� ��� ������" �� ������ ��������������� ��������  */
	public static final String HORSE_PAGE_REGISTER_IN_EC_BUTTON = "//*[@id=\"cheval-inscription\"]";
	
	
	
	//*************************************
	//*   �������� ������ � ���           *
	//*************************************
	/** ������� ����������������� ����� */
	public static final String EC_PAGE_RESERVED_TABLE = "//*[@id=\"boxContent\"]";
	/** ������� "����" � ������� ����� */
	public static final String EC_PAGE_RESERVED_TABLE_PRICE_COLUMN = "//*[@id=\"table-0\"]/thead/tr/td[6]";
	/** ������ � ��������� ��� ������ ��� */
	public static final String EC_PAGE_FILTERS_PANEL = "//*[@id=\"cheval-centre-inscription\"]";
	/** ������� ��� */
	public static final String EC_PAGE_EC_TABLE = "//*[@id=\"table-0\"]";
	
	

	
	
	
	
	
	
}
