package ru.teosa.herdSettings;

/** Интерфейс вкладок для TabPanel.<br>
 * Т - класс объекта для хранения настроек
 *  */
public interface SettingTabsInterface <T>{

	/**
	 * Загрузка в форму стандартных настроек
	 * */
	public void loadSettings();
	
	/**
	 * Загрузка в форму настроек прогона из объекта
	 * @param settings объект с настройками
	 * */
	public void loadSettings(T settings);
	
	/**
	 * Сбор информации с формы в объект настроек.
	 * @param settings объект для заполнения
	 * @return Заполненный объект
	 * */
	public T getTabSettings(final T settings);
}
