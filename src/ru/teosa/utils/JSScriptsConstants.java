package ru.teosa.utils;

public class JSScriptsConstants {
	/** Переключение во вкладку "Зарезервированные стойла" */
	public static final String EC_PAGE_RESERVED_TAB = "document.getElementById(\"tab-box-reserve\").getElementsByClassName(\"tab-action\")[0].click()";
	

	
	/** Сортирвка длительности проживания в зарезервированных КСК */
	public static final String EC_PAGE_RESERVED_TABLE_DAYS_SORT_3  = "document.getElementById(\"boxContent\").childNodes[3].getElementsByTagName(\"thead\")[0].getElementsByTagName(\"td\")[5].getElementsByClassName(\"grid-cell spacer-small-top spacer-small-bottom\")[1].childNodes[0].click()";
	public static final String EC_PAGE_RESERVED_TABLE_DAYS_SORT_10 = "document.getElementById(\"boxContent\").childNodes[3].getElementsByTagName(\"thead\")[0].getElementsByTagName(\"td\")[5].getElementsByClassName(\"grid-cell spacer-small-top spacer-small-bottom\")[2].childNodes[0].click()";
	public static final String EC_PAGE_RESERVED_TABLE_DAYS_SORT_30 = "document.getElementById(\"boxContent\").childNodes[3].getElementsByTagName(\"thead\")[0].getElementsByTagName(\"td\")[5].getElementsByClassName(\"grid-cell spacer-small-top spacer-small-bottom\")[3].childNodes[0].click()";
	public static final String EC_PAGE_RESERVED_TABLE_DAYS_SORT_60 = "document.getElementById(\"boxContent\").childNodes[3].getElementsByTagName(\"thead\")[0].getElementsByTagName(\"td\")[5].getElementsByClassName(\"grid-cell spacer-small-top spacer-small-bottom\")[4].childNodes[0].click()";
}
