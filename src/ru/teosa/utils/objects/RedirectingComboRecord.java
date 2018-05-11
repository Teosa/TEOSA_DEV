package ru.teosa.utils.objects;

/** Объект для заполнения комбобоксов с редиректом по ссылке */
public class RedirectingComboRecord extends SimpleComboRecord{

	/** Ссылка для перехода при выборе записи */
	private String URL;
	private boolean isRedirect;

	public RedirectingComboRecord() {};
	public RedirectingComboRecord(Integer id, String name, String URL) {
		super(id, name);
		this.URL = URL;
		this.isRedirect = true;
	};
	
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	
	/** Установка признака перехода по хранимой ссылке при выборе записи */
	public void setRedirectByURL(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
}
