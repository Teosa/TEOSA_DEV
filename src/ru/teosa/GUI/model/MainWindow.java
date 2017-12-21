package ru.teosa.GUI.model;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import ru.teosa.GUI.MainApp;

public class MainWindow {

	private static MainApp mainApp;
	private static WebDriver driver;
	
	private static List<String> farms = new ArrayList<String>();

	public MainApp getMainApp() {
		return mainApp;
	}
	public static WebDriver getDriver() {
		return driver;
	}
	public static List<String> getFarms() {
		return farms;
	}
	public void setFarms(List<String> farms) {
		MainWindow.farms = farms;
	}
	
	
	public static void init(MainApp mainApp) {
		MainWindow.mainApp = mainApp;
		MainWindow.driver = mainApp.getDriver();
		
		goToBreedingFarm();
		getBreedibgFarms();
		
	}
																	
	private static void goToBreedingFarm(){	
		WebElement breedingFarmMenu = driver.findElement(By.xpath("//*[@id=\"header-menu\"]/div[1]/ul/li[1]"));

		Actions builder = new Actions(driver);
		builder.moveToElement(breedingFarmMenu).build().perform();
	
		driver.findElement(By.xpath("//*[@id=\"header-menu\"]/div[1]/ul/li[1]/ul/li[2]/a")).click();
	}
	
	private static void getBreedibgFarms(){
		List<WebElement> farms = driver.findElements(By.xpath("//*[@id=\"tab-all-breeding\"]/li"));
		MainWindow.farms.clear();
		
		for(int i = 0; i < farms.size(); ++i) {
			WebElement farm = farms.get(i);
			MainWindow.farms.add(farm.findElement(By.className("tab-action")).getText());
		}
	}
	
}
