package ru.teosa.utils.objects;

/** ������ ��� ���������� ����������� � ���������� �� ������ � ���. �������� */
public class RedirectingComboRecordExt extends RedirectingComboRecord{

	/**  �������������� ������ */
	private Object data;
	
	public RedirectingComboRecordExt() {};
	public RedirectingComboRecordExt(Integer id, String name, String URL, Object data) {
        super(id, name, URL);
		this.data = data;
	};
		
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
