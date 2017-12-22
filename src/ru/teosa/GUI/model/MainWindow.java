package ru.teosa.GUI.model;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import ru.teosa.GUI.MainApp;
import ru.teosa.utils.objects.SimpleComboRecord;

public class MainWindow {

	private static MainApp mainApp;
	private static WebDriver driver;
	
	private static List<SimpleComboRecord> farms = new ArrayList<SimpleComboRecord>();

	public MainApp getMainApp() {
		return mainApp;
	}
	public static WebDriver getDriver() {
		return driver;
	}
	public static List<SimpleComboRecord> getFarms() {
		return farms;
	}
	public void setFarms(List<SimpleComboRecord> farms) {
		MainWindow.farms = farms;
	}
	
	/**
	 * Инициализация формы.<br>
	 * 1.Установка ссылок на главное приложение и драйвер.
	 * 2. Переход на страницу заводов.
	 * 3. Запонение доступных комбобоксов и полей.
	 * */
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
			SimpleComboRecord record = new SimpleComboRecord();
			String farmURL = "";
			
			if(i != farms.size() -1) {
				farmURL = farm.findElement(By.className("tab-action")).getAttribute("href");
				farmURL = farmURL.replace("#tab-", "elevage=");
			}
			else farmURL = driver.getCurrentUrl();
			
			record.setName(farm.findElement(By.className("tab-action")).getText());
			record.setURL(farmURL);
			
			MainWindow.getFarms().add(record);
		}
	}
	
}
