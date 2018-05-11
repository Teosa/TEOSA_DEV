package ru.teosa.utils.objects;

/** ������� ������ ��� ���������� ����������� � ���. �������� */
public class SimpleComboRecordExt extends SimpleComboRecord{
	/**  �������������� ������ */
	private Object data;
	
	public SimpleComboRecordExt() {};
	public SimpleComboRecordExt(Integer id, String name, Object data) {
        super(id, name);
		this.data = data;
	};
	
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}	
}
