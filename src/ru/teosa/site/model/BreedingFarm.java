package ru.teosa.site.model;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ru.teosa.utils.objects.MainAppHolderSingleton;

public class BreedingFarm {
	
	private WebDriver driver;
	private String lastRunedHorse = null;
	
	public BreedingFarm() {
		driver = MainAppHolderSingleton.getInstance().getDriver();
	}

	
	public boolean findFirstHorse() {
		List<WebElement> farms = driver.findElements(By.xpath("//*[@id=\"horseList\"]/div/div[2]/ul"));
		if(farms.size() > 0) {
			WebElement firstHorse = driver.findElement(By.xpath("//*[@id=\"horseList\"]/div/div[2]/ul/li[1]"));
			if(firstHorse != null) {
				try {
					firstHorse.findElement(By.xpath("//*[@id=\"horseList\"]/div/div[2]/ul/li[1]/div/div[1]/div/ul/li[1]/a")).click();
					return true;
				}
				catch(Exception e) {
					Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
					return false;
				}
			}
			else return false;
		}
		else return false;
		
	}
	
	public void herdRun() {
		if(!findFirstHorse()) return;
		boolean endOfFarm = false;
		try {
			while(!endOfFarm) {
				Horse horse = new Horse();
				lastRunedHorse = horse.getURL();
				endOfFarm = horse.run();
			}
		}
		catch(Exception e) {
			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
			System.out.println("HERD RUNNING ERROR");
			System.out.println("URL: " + lastRunedHorse);
			return;
		}
		
	}
}
