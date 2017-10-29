package ru.teosa.GUI.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MainInfo {

	private StringProperty horsesCount;
	private StringProperty farmsCount;
	private StringProperty userName;
	
	public MainInfo(String horses, String farms, String userName){
		this.horsesCount = new SimpleStringProperty(horses);
		this.farmsCount = new SimpleStringProperty(farms);
		this.userName = new SimpleStringProperty(userName);
	}

	public StringProperty getHorsesCount() {
		return horsesCount;
	}

	public void setHorsesCount(StringProperty horsesCount) {
		this.horsesCount = horsesCount;
	}

	public StringProperty getFarmsCount() {
		return farmsCount;
	}

	public void setFarmsCount(StringProperty farmsCount) {
		this.farmsCount = farmsCount;
	}

	public StringProperty getUserName() {
		return userName;
	}

	public void setUserName(StringProperty userName) {
		this.userName = userName;
	};
	
	
}
