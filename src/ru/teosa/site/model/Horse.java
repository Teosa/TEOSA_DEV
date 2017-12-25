package ru.teosa.site.model;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import ru.teosa.utils.objects.MainAppHolderSingleton;

public class Horse {
	
	private WebDriver driver;
	private HorsePage pageContent;
	
	private String URL;
	private String name;
	private double age;
	private char gender;
	
	
	public Horse() {
		driver = MainAppHolderSingleton.getInstance().getDriver();
		pageContent = new HorsePage(this);
		
		setURL(driver.getCurrentUrl());
		
		setName(pageContent.getName());
		setAge(pageContent.getAge());
		setGender(pageContent.getGender());
	}
	
	
	
	public boolean run() throws Exception{		
		Logger.getLogger("debug").debug(toString());
		
//		if(pageContent.isHorseProcessed()) return true;
		pageContent.registerInAnEquestrianCenter();
		
//		pageContent.groom();
//		pageContent.drink();
//		pageContent.mission();
//		pageContent.feed();
//		pageContent.putToBed();
//		pageContent.switchToNextHorse();
		
		Logger.getLogger("debug").debug("RUNING SUCCESS");
		
		return false;
	}
	
	@Override
	public String toString() {
		return ""
				+ "URL: " + getURL() + "; "
				+ "NAME: " + getName() + "; "
				+ "AGE: " + getAge() + "; "
				+ "GENDER: " + getGender() + "; ";
	}
	
	
	
//*****************************************************************************************************************
//*****************************************************************************************************************
//*****************************************************************************************************************	

	
//*****************************************************************************************************************
//*****************************************************************************************************************
//*****************************************************************************************************************
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getAge() {
		return age;
	}
	public void setAge(double age) {
		this.age = age;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	
	
}
