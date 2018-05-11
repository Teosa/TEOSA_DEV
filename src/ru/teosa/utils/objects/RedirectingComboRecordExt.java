package ru.teosa.utils.objects;

/** Объект для заполнения комбобоксов с редиректом по ссылке и доп. объектом */
public class RedirectingComboRecordExt extends RedirectingComboRecord{

	/**  Дополнительный объект */
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
