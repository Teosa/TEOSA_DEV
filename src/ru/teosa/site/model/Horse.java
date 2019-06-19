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
	private String URL;
	
	
	public Horse( HorsePage page ) {
		driver = MainAppHolderSingleton.getInstance().getDriver();
		pageContent = page;
		
		name   = pageContent.getName();
		age    = pageContent.getAge();
		gender = pageContent.getGender();
		URL    = pageContent.getURL();
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
	public double getAge() {
		return age;
	}
	public char getGender() {
		return gender;
	}
	public String getURL() {
		return URL;
	}

	
	
}
