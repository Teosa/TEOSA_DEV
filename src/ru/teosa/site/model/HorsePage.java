package ru.teosa.site.model;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ru.teosa.utils.Sleeper;
import ru.teosa.utils.objects.MainAppHolderSingleton;

public class HorsePage {
	private WebDriver driver;
	private Horse horse;
	
//	private WebElement carePanel;
//	private WebElement nightPanel;
//	private WebElement equestrianCenterPanel;
	private WebElement ridesPanel;
	private WebElement trainingPanel;
	private WebElement competitionsPanel;
	private WebElement breedingPanel;
	
	private WebElement characteristicsTab;
	private WebElement characteristicsPanel;
	private WebElement geneticTab;
	private WebElement geneticPanel;
	private WebElement infoPanel;
//	private WebElement statusPanel;
//	private WebElement skillsPanel;
	
	private WebElement nextHorseButton;
	private WebElement prevHorseButton;

	
	//Ufo_0  -> //*[@id="agi-10950100001514048813"]  class - close-popup
	
	public HorsePage(Horse horse) {
		driver = MainAppHolderSingleton.getInstance().getDriver();
		this.horse = horse;
		
		try {
//			 carePanel 				= driver.findElement(By.xpath("//*[@id=\"care\"]")); 
//			 nightPanel 			= driver.findElement(By.xpath("//*[@id=\"night\"]"));
//			 equestrianCenterPanel 	= driver.findElement(By.xpath("//*[@id=\"col-left\"]/div[3]/div/div/div/div"));
			 ridesPanel 			= driver.findElement(By.xpath("//*[@id=\"col-right\"]/div[1]/div/div"));
			 trainingPanel 			= driver.findElement(By.xpath("//*[@id=\"training\"]"));
			 competitionsPanel 		= driver.findElement(By.xpath("//*[@id=\"competition\"]"));
			 breedingPanel 			= driver.findElement(By.xpath("//*[@id=\"reproduction\"]"));
			
			 characteristicsTab 	= driver.findElement(By.xpath("//*[@id=\"tab-characteristics-title\"]"));
			 geneticTab 			= driver.findElement(By.xpath("//*[@id=\"tab-genetics-title\"]"));
			 infoPanel 				= driver.findElement(By.xpath("//*[@id=\"module-2\"]/div[1]/div"));
//			 statusPanel 			= driver.findElement(By.xpath("//*[@id=\"module-2\"]/div[2]/div/div/div"));
//			 skillsPanel 			= driver.findElement(By.xpath("//*[@id=\"skills-body-content\"]"));
			
			 nextHorseButton 		= driver.findElement(By.id("nav-next"));

			 prevHorseButton 		= driver.findElement(By.xpath("//*[@id=\"nav-previous\"]"));
		}
		catch(Exception e) {
			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
		}
	}
	
	
	
	public boolean isHorseProcessed(){		
		return 
				driver.findElement(By.xpath("//*[@id=\"boutonPanser\"]")).getAttribute("class").contains("action-disabled")
			 && driver.findElement(By.xpath("//*[@id=\"boutonCoucher\"]")).getAttribute("class").contains("action-disabled");
	}
	
	public String getName() {		
		return driver.findElement(By.xpath("//*[@id=\"module-2\"]/div[1]/div/div[2]/h1/a")).getText();
	}
	
	public double getAge() {		
		String ageElementText = driver.findElement(By.xpath("//*[@id=\"characteristics-body-content\"]/table/tbody/tr[1]/td[2]")).getText();	
		String[] ageElParts = ageElementText.split("\\s");
		String result = "";
		
		for(int i = 0; i < ageElParts.length; ++i) {
			try {
				result += Integer.parseInt(ageElParts[i]) + ".";
			}
			catch(Exception e) {}
		}
		
		if(result.length() > 0) return Double.parseDouble(result.substring(0, result.length()-1));
		else return 0;
		
	}
	
	public char getGender() {		
		String genderElText = driver.findElement(By.xpath("//*[@id=\"characteristics-body-content\"]/table/tbody/tr[3]/td[1]")).getText();
		return genderElText.contains("female") || genderElText.contains("кобыла") ? 'F' : 'M';
	}
	
	/** 
	 * Кормить<br> 
	 * Вычисляет необходимое количество корма. Если животное слишком толстое - не кормит.
	 * */
	public void feed() throws Exception{
		
		if(horse.getAge() < 0.6) feedByMilk();
		else{
			driver.findElement(By.xpath("//*[@id=\"boutonNourrir\"]")).click();
			Sleeper.longPause();
			
			boolean overfeedMsg = driver.findElement(By.xpath("//*[@id=\"messageBoxInline\"]")).isDisplayed();
			boolean isFeed = false;
			
			if(!overfeedMsg){
				isFeed = feedByHay();
				if(horse.getAge() >= 1.6) isFeed = feedByOat() ? true : isFeed;				
			}
			Logger.getLogger("debug").debug("feed IS FEED: " + isFeed);			
			if(isFeed) driver.findElement(By.xpath("//*[@id=\"feed-button\"]")).click();	
			else driver.findElement(By.xpath("//*[@id=\"care-tab-feed\"]/table/tbody/tr[1]/td[2]/a")).click();
		}
	}
	
	/** Поить */
	public void drink() {
		Logger.getLogger("debug").debug("drink");
		Sleeper.waitForClickAndClick("//*[@id=\"boutonBoire\"]");
	}
	
	/** Ласка */
	public void stroke() {
		Logger.getLogger("debug").debug("stroke");
		Sleeper.waitForClickAndClick("//*[@id=\"boutonCaresser\"]");
	}
	
	/** Чистить */
	public void groom() {
		Sleeper.pause();
		Logger.getLogger("debug").debug("groom");
		driver.findElement(By.xpath("//*[@id=\"boutonPanser\"]")).click();
	}
	
	/** Морковь */
	public void carrot() {
		Sleeper.pause();
		Logger.getLogger("debug").debug("carrot");
		driver.findElement(By.xpath("//*[@id=\"boutonCarotte\"]")).click();
	}
	
	/** Комбикорм */
	public void mash() {
		Sleeper.pause();
		Logger.getLogger("debug").debug("mash");
		driver.findElement(By.xpath("//*[@id=\"boutonMash\"]")).click();
	}
	
	/** Отправить спать */
	public void putToBed() {
		Sleeper.pause();
		Logger.getLogger("debug").debug("putToBed");
		driver.findElement(By.xpath("//*[@id=\"boutonCoucher\"]")).click();
	}
	
	/** Вырастить при помощи ОР */
	public void age() {}
	
	/** Миссия */
	public void mission() {
		Logger.getLogger("debug").debug("mission");
		if(horse.getAge() >= 2){
			Sleeper.waitForClickAndClick("//*[@id=\"mission-tab-0\"]/div/div/div[2]/a");
			Sleeper.pause();
		}
	}
	
	public void switchToNextHorse(){
		Sleeper.waitForClickAndClick("//*[@id=\"nav-next\"]");
	}
	
	public void registerInAnEquestrianCenter() {
		Logger.getLogger("debug").debug("registerInAnEquestrianCenter");
		
		try {
			Sleeper.pause();
			Sleeper.waitForClickAndClick("//*[@id=\"cheval-inscription\"]/a/span[1]");
//			WebElement searchECPanel = Sleeper.waitVisibility("//*[@id=\"cheval-centre-inscription\"]");
//			List<WebElement> buttons = searchECPanel.findElements(By.tagName("button-inner-0"));
//			buttons.get(1).click();
		}
		catch(TimeoutException e) {
			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
		}
	}
	
//*****************************************************************************************************************	
//*****************************************************************************************************************	
//*****************************************************************************************************************	
	private void feedByMilk(){
		Logger.getLogger("debug").debug("feedByMilk");
		driver.findElement(By.xpath("//*[@id=\"boutonAllaiter\"]")).click();		
	}
	
	private boolean feedByHay(){
		Logger.getLogger("debug").debug("feedByHay");
		Logger.getLogger("debug").debug(driver.findElement(By.xpath("//*[@id=\"feeding\"]/table[1]/tbody/tr[2]/td[1]/span[2]")).getText());
		Logger.getLogger("debug").debug(driver.findElement(By.xpath("//*[@id=\"feeding\"]/table[1]/tbody/tr[2]/td[1]/span[2]")).getText().split("/")[0]);
		boolean isFeed = false;
		int eatenHay = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"feeding\"]/table[1]/tbody/tr[2]/td[1]/span[2]")).getText().split("/")[0].trim());
		int reqHay = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"feeding\"]/table[1]/tbody/tr[2]/td[1]/span[2]/strong")).getText());
		
		if(eatenHay < reqHay) {
			driver.findElement(By.xpath("//*[@id=\"haySlider\"]/ol/li[" + (reqHay - eatenHay + 1) + "]")).click();
			isFeed = true;
		}
		Logger.getLogger("debug").debug("FOOD QTY: " + (reqHay - eatenHay + 1));
		Logger.getLogger("debug").debug("IS FEED: " + isFeed);
		return isFeed;
	}
	
	private boolean feedByOat(){
		Logger.getLogger("debug").debug("feedByOat");
		boolean isFeed = false;
		
		WebElement eatenOatEl = Sleeper.waitVisibility("//*[@id=\"feeding\"]/table[1]/tbody/tr[4]/td[1]/span[2]");
		WebElement reqOatEl = Sleeper.waitVisibility("//*[@id=\"feeding\"]/table[1]/tbody/tr[4]/td[1]/span[2]/strong");
		
		int eatenOat = Integer.parseInt(eatenOatEl.getText().split("/")[0].trim());
		int reqOat = Integer.parseInt(reqOatEl.getText().trim());
				
//		int eatenOat = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"feeding\"]/table[1]/tbody/tr[4]/td[1]/span[2]")).getText().split("/")[0].trim());
//		int reqOat = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"feeding\"]/table[1]/tbody/tr[4]/td[1]/span[2]/strong")).getText());
			
		if(eatenOat < reqOat) {
			driver.findElement(By.xpath("//*[@id=\"oatsSlider\"]/ol/li[" + (reqOat - eatenOat + 1) + "]")).click();
			isFeed = true;
		}
		Logger.getLogger("debug").debug("FOOD QTY: " + (reqOat - eatenOat + 1));
		Logger.getLogger("debug").debug("IS FEED: " + isFeed);
		return isFeed;
	}
	
	
}
