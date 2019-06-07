package ru.teosa.utils;

public class Msgs {
	public static final String DEFAULT_LOADING_TEXT  = "Загрузка..";
	
	// МОДУЛЬ 'КОНВЕРТАЦИЯ'
	public static final String CONVERT_TEXT          = "Конвертация";
	public static final String STOP_CONVERT_TEXT     = "Остановить";
	public static final String COPY_TEXT             = "Копировать текущий баланс";
	
	public static final String HERD_RUN_WINDOW_TITLE = "Прогон";
	public static final String HERD_RUN_STOP         = "Остановить";
	public static final String HERD_RUN_RESTART      = "Возобновить";
	
	
	// СИСТЕМНЫЕ СООБЩЕНИЯ И ОШИБКИ
	public static final String HERD_RUN_ADD_RECORD_ERROR_MSG            = "Данная программа для выбранного завода уже добавлена в прогон";
	public static final String HERD_RUN_NO_LIST_ERROR_MSG               = "Ошибка получения списка прогона";
	public static final String HERD_RUN_EMPTY_LIST_ERROR_MSG            = "Список прогона пуст";
	
	public static final String LOGIN_ACCOUNT_ADD_ERROR_MSG              = "Ошибка добавления нового аккаунта";
	public static final String LOGIN_ACCOUNT_UPD_ERROR_MSG              = "Ошибка обновления данных аккаунта";
	
	public static final String EC_REGISTER_ERROR_MSG                    = "Ошибка записи в КСК";
	public static final String EC_REGISTER_NO_STALLS_MSG                = "Не найдено подходящих стойл";
	public static final String EC_REGISTER_NO_RESERVED_STALLS_MSG       = "Нет зарезервированных стойл";
	public static final String EC_REGISTER_HORSE_ALREADY_REGISTERED_MSG = "Лошадь уже зарегестрирована в КСК";
	public static final String EC_REGISTER_REGISTER_SUCCESS_MSG         = "Регистрация в КСК";
	public static final String EC_REGISTER_FILTER_ERROR_MSG             = "Ошибка фильрации КСК";
	
	public static final String MAX_ERRORS_OCCURED                       = "Критическое количество ошибок. Выполнение остановлено.";
}
