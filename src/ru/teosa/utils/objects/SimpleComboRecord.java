package ru.teosa.utils.objects;

import java.io.FileInputStream;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.teosa.site.model.BreedingFarm;

/** Объект для заполнения комбобоксов */
public class SimpleComboRecord {
	
	/**  Имя записи для отображения в выпадающем списке */
	private String name;
	/** Ссылка для перехода при выборе записи */
	private String URL;
	/**  Дополнительный объект */
	private Object data;
	
	private BreedingFarm farmData;
	
	
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
	public BreedingFarm getFarmData() {
		return farmData;
	}
	public void setFarmData(BreedingFarm farmData) {
		this.farmData = farmData;
	}
	
	@Override
	public String toString() {
		return getName();
	}


}
