package ru.teosa.herdSettings;

/** ��������� ������� ��� TabPanel.<br>
 * � - ����� ������� ��� �������� ��������
 *  */
public interface SettingTabsInterface <T>{

	/**
	 * �������� � ����� ����������� ��������
	 * */
	public void loadSettings();
	
	/**
	 * �������� � ����� �������� ������� �� �������
	 * @param settings ������ � �����������
	 * */
	public void loadSettings(T settings);
	
	/**
	 * ���� ���������� � ����� � ������ ��������.
	 * @param settings ������ ��� ����������
	 * @return ����������� ������
	 * */
	public T getTabSettings(final T settings);
}
