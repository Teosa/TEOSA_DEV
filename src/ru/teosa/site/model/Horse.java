package ru.teosa.site.model;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import ru.teosa.utils.objects.MainAppHolderSingleton;

public class Horse {
	
	private WebDriver driver;
	private HorsePage pageContent;
	
	private String name;
	private double age;
	private char gender;
	
	
	public Horse( HorsePage page ) {
		driver = MainAppHolderSingleton.getInstance().getDriver();
		pageContent = page;
		
		setName(pageContent.getName());
		setAge(pageContent.getAge());
		setGender(pageContent.getGender());
	}
	
	public boolean startProgramm() 
	{
		
		return true;
	}
	

	
	@Override
	public String toString() {
		return ""
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
