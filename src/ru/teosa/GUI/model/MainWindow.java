package ru.teosa.GUI.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import ru.teosa.GUI.MainApp;
import ru.teosa.utils.Sleeper;
import ru.teosa.utils.objects.RedirectingComboRecord;
import ru.teosa.utils.objects.RedirectingComboRecordExt;

public class MainWindow {

	private static MainApp mainApp;
	private static WebDriver driver;
	
	private static List<RedirectingComboRecordExt> farms = new ArrayList<RedirectingComboRecordExt>();

	public MainApp getMainApp() {
		return mainApp;
	}
	public static WebDriver getDriver() {
		return driver;
	}
	public static List<RedirectingComboRecordExt> getFarms() {
		return farms;
	}
	public void setFarms(List<RedirectingComboRecordExt> farms) {
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
		
		Logger.getLogger("debug").debug("FARMS QTY: " + farms.size());	
		
		for(int i = 0; i < farms.size(); ++i) {
			WebElement farm = driver.findElement(By.xpath("//*[@id=\"tab-all-breeding\"]/li[" + (i+1) + "]"));
			Logger.getLogger("debug").debug("FARM " + i + " : " + farm);
			
			Actions builder = new Actions(driver);
			builder.moveToElement(farm).build().perform();

			RedirectingComboRecordExt record = new RedirectingComboRecordExt();

			String farmURL = "";
			String name = "";
			Logger.getLogger("debug").debug("04");
			if(i != farms.size() -1) {
				Sleeper.turnOffImplicitWaits();
				List<WebElement> subFarms = farm.findElements(By.tagName("li"));
				Logger.getLogger("debug").debug("SUB FARMS QTY: " + subFarms.size());
				Sleeper.turnOnImplicitWaits();
				
				if(subFarms.size() > 0) {
					
					List<RedirectingComboRecordExt> subFarmsRecordsList = new ArrayList<RedirectingComboRecordExt>();
					for(int k = 0; k < subFarms.size(); ++k) {
						RedirectingComboRecordExt subFarmRecord = new RedirectingComboRecordExt();
						String subname = "";
						String subURL = "";
						
						subname = subFarms.get(k).findElement(By.tagName("a")).getText();
						subURL = subFarms.get(k).findElement(By.tagName("a")).getAttribute("href");
						subURL = subURL.replace("#tab-", "elevage=");
						
						subFarmRecord.setName(subname);
						subFarmRecord.setURL(subURL);
						
						Logger.getLogger("debug").debug("SUB FARM " + k + " : " + subname);
						Logger.getLogger("debug").debug("SUB FARM " + k + " : " + subURL);
						
						subFarmsRecordsList.add(subFarmRecord);
					}
					name = farm.findElement(By.className("groupes")).getText();
					record.setData(subFarmsRecordsList);
				}
				else {
					name = farm.findElement(By.className("tab-action")).getText();
					farmURL = farm.findElement(By.className("tab-action")).getAttribute("href");
					farmURL = farmURL.replace("#tab-", "elevage=");
				}
			}
			else {
				name = farm.findElement(By.tagName("a")).getText();
				farmURL = driver.getCurrentUrl();
			}
			
			record.setName(name);
			record.setURL(farmURL);
			
			Logger.getLogger("debug").debug("FARM " + i + " : " + name);
			Logger.getLogger("debug").debug("FARM " + i + " : " + farmURL);
			
			MainWindow.getFarms().add(record);	
		}
		
		Logger.getLogger("debug").debug(MainWindow.getFarms().size());	
	}
	
}
