package ru.teosa.utils.objects;

/** Простой объект для заполнения комбобоксов с доп. объектом */
public class SimpleComboRecordExt extends SimpleComboRecord{
	/**  Дополнительный объект */
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
