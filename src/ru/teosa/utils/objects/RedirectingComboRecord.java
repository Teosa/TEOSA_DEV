package ru.teosa.utils.objects;

/** ������ ��� ���������� ����������� � ���������� �� ������ */
public class RedirectingComboRecord extends SimpleComboRecord{

	/** ������ ��� �������� ��� ������ ������ */
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
	
	/** ��������� �������� �������� �� �������� ������ ��� ������ ������ */
	public void setRedirectByURL(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
}
