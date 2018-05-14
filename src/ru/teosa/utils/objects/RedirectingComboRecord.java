package ru.teosa.utils.objects;

/** ������ ��� ���������� ����������� � ���������� �� ������ */
public class RedirectingComboRecord extends SimpleComboRecord{

	/** ������ ��� �������� ��� ������ ������ */
	private String url;
	private boolean isRedirect;

	public RedirectingComboRecord() {};
	public RedirectingComboRecord(Integer id, String name, String URL) {
		super(id, name);
		this.url = URL;
		this.isRedirect = true;
	};
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	/** ��������� �������� �������� �� �������� ������ ��� ������ ������ */
	public void setRedirectByURL(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
}
