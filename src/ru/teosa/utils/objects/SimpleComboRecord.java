package ru.teosa.utils.objects;

import ru.teosa.mainapp.pojo.BreedingFarm;

/** Простой объект для заполнения комбобоксов */
public class SimpleComboRecord {
	/**  Идентификатор записи */
	private Integer id;
	/**  Имя записи для отображения в выпадающем списке */
	private String name;
	
	private BreedingFarm farmData;
	
	
	public SimpleComboRecord() {};
	public SimpleComboRecord(Integer id, String name) {
		this.id = id;
		this.name = name;
	};
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BreedingFarm getFarmData() {
		return farmData;
	}
	public void setFarmData(BreedingFarm farmData) {
		this.farmData = farmData;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	
	@Override
	public String toString() {
		return getName();
	}
	
	public boolean isValueEmpty(Object value) {
		if(value != null) 
			return value.getClass() == String.class && value.toString().trim().length() == 0;
		else return true;
	}
}
