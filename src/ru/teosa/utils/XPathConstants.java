package ru.teosa.utils;

public class XPathConstants {

	/** Основное меню игры (подменю Коневодсво, КСК, Торговля, Сообщество, Достижения) */
	public static final String MAIN_HEADER = "//*[@id=\"header-menu\"]";
	
		/** Подменю Коневодство основного меню */
		public static final String MAIN_HEADER_BREEDING_FARM = "//*[@id=\"header-menu\"]/div[2]/ul/li[1]";
		
			/** Кнопка Лошади из подменю Коневодство */
			public static final String MAIN_HEADER_BREEDING_FARM_HORSES = "//*[@id=\"header-menu\"]/div[2]/ul/li[1]/ul/li[2]/a";
	
	
	
	/** Список лошадей в заводе */
	public static final String FARM_LIST = "//*[@id=\"horseList\"]";
	/** Первая лошадь в заводе */
	public static final String FARM_LIST_FIRST_HORSE = "//*[@id=\"horseList\"]/div/div[2]/ul[1]/li[1]/div/div[1]/div/ul/li[1]/a";
	
	
	
	
	//*************************************
	//*        СТРАНИЦА ЛОШАДИ            *
	//*************************************
	/** Кнопка "Записать мою лошадь" на панели Конноспортивный комплекс  */
	public static final String HORSE_PAGE_REGISTER_IN_EC_BUTTON = "//*[@id=\"cheval-inscription\"]";
	
	
	
	//*************************************
	//*   СТРАНИЦА ЗАПИСИ В КСК           *
	//*************************************
	/** Таблица зарезервированных стойл */
	public static final String EC_PAGE_RESERVED_TABLE = "//*[@id=\"boxContent\"]";
	/** Колонка "Цена" в таблице стойл */
	public static final String EC_PAGE_RESERVED_TABLE_PRICE_COLUMN = "//*[@id=\"table-0\"]/thead/tr/td[6]";
	/** Панель с фильтрами для поиска КСК */
	public static final String EC_PAGE_FILTERS_PANEL = "//*[@id=\"cheval-centre-inscription\"]";
	/** Таблица КСК */
	public static final String EC_PAGE_EC_TABLE = "//*[@id=\"table-0\"]";
	
	

	
	
	
	
	
	
}
