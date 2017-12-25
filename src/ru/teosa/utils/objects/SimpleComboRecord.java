package ru.teosa.utils.objects;

/** Объект для заполнения комбобоксов */
public class SimpleComboRecord {
	
	/**  Имя записи для отображения в выпадающем списке */
	private String name;
	/** Ссылка для перехода при выборе записи */
	private String URL;
	/**  Дополнительный объект */
	private Object data;
	
	
	public SimpleComboRecord() {};
	public SimpleComboRecord(String name, String URL, Object data) {
		this.name = name;
		this.URL = URL;
		this.data = data;
	};
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
