package ru.teosa.site.model;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javafx.scene.control.Button;
import ru.teosa.herdSettings.HerdRunSettings;
import ru.teosa.utils.objects.MainAppHolderSingleton;

public class BreedingFarm implements Runnable{
	
	private WebDriver driver;
	private String URL;
	private String lastRunedHorse = null;
	private HerdRunSettings settings;
	
	private static boolean runInterupted = false;
	
	public BreedingFarm() {
		driver = MainAppHolderSingleton.getInstance().getDriver();
	}	
	public static boolean isRunInterupted() {
		return runInterupted;
	}
	public static void setRunInterupted(boolean runInterupted) {
		BreedingFarm.runInterupted = runInterupted;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public HerdRunSettings getSettings() {
		return settings;
	}
	public void setSettings(HerdRunSettings settings) {
		this.settings = settings;
	}
	
	
	public boolean findFirstHorse() {
		List<WebElement> farms = driver.findElements(By.xpath("//*[@id=\"horseList\"]/div/div[2]/ul"));
		if(farms.size() > 0) {
			WebElement firstHorse = driver.findElement(By.xpath("//*[@id=\"horseList\"]/div/div[2]/ul/li[1]"));
			if(firstHorse != null) {
				try {
					firstHorse.findElement(By.xpath("//*[@id=\"horseList\"]/div/div[2]/ul/li[1]/div/div[1]/div/ul/li[1]"))
					.findElement(By.className("horsename")).click();
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


	@Override
	public void run() {
		Button start = (Button)MainAppHolderSingleton.getInstance().getMainApp().getPrimaryStage().getScene().lookup("#startButton");
		Button stop = (Button)MainAppHolderSingleton.getInstance().getMainApp().getPrimaryStage().getScene().lookup("#stopButton");
		
		start.setDisable(true);
		stop.setDisable(false);
		
		Logger.getLogger("debug").debug(Thread.currentThread().getName());
		Logger.getLogger("debug").debug("lastRunedHorse: " + lastRunedHorse);
		Logger.getLogger("debug").debug("Breeding farm URL: " + URL);
		
		if(lastRunedHorse != null) driver.navigate().to(lastRunedHorse);
		else {
			driver.navigate().to(URL);
			if(!findFirstHorse()) return;
		}
			
		boolean endOfFarm = false;
		
		try {
			while(!endOfFarm && !runInterupted) {
				Horse horse = new Horse();
				lastRunedHorse = horse.getURL();
				endOfFarm = horse.run();
				Logger.getLogger("debug").debug("runInterupted: " + runInterupted);
			}
		}
		catch(Exception e) {
			Logger.getLogger("debug").debug("HERD RUNNING ERROR");
			Logger.getLogger("debug").debug("LAST HORSE URL: " + lastRunedHorse);
			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
			return;
		}
		finally {
			Logger.getLogger("debug").debug("FINNALY");
			runInterupted = false;
			
			start.setDisable(false);
			stop.setDisable(true);
		}
	}
	
	
}
