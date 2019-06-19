package ru.teosa.site.model;

import java.security.Timestamp;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import ru.teosa.herdSettings.EC_Settings;
import ru.teosa.herdSettings.HerdRunSettings;
import ru.teosa.utils.JSScriptsConstants;
import ru.teosa.utils.Msgs;
import ru.teosa.utils.ResultObject;
import ru.teosa.utils.Sleeper;
import ru.teosa.utils.XPathConstants;
import ru.teosa.utils.objects.MainAppHolderSingleton;

public class HorsePage {
	private WebDriver driver;
	private Horse horse;
	private String URL;
	private HerdRunSettings programm;
	
//	private WebElement carePanel;
//	private WebElement nightPanel;

	private WebElement ridesPanel;
	private WebElement trainingPanel;
	private WebElement competitionsPanel;
	private WebElement breedingPanel;
	
	private WebElement characteristicsTab;
	private WebElement characteristicsPanel;
	private WebElement geneticTab;
	private WebElement geneticPanel;
	private WebElement infoPanel;  // Панель с кличкой, аффиксом и заводом
//	private WebElement statusPanel;
//	private WebElement skillsPanel;
	
	private WebElement nextHorseButton;
	private WebElement prevHorseButton;

	

	
	public HorsePage() {
		driver = MainAppHolderSingleton.getInstance().getDriver();
		
		
		try {
				infoPanel 				= getInfoPanel();
			
			
			
			
			
			
			
//			 carePanel 				= driver.findElement(By.xpath("//*[@id=\"care\"]")); 
//			 nightPanel 			= driver.findElement(By.xpath("//*[@id=\"night\"]"));
			 ridesPanel 			= driver.findElement(By.xpath("//*[@id=\"col-right\"]/div[1]/div/div"));
			 trainingPanel 			= driver.findElement(By.xpath("//*[@id=\"training\"]"));
			 competitionsPanel 		= driver.findElement(By.xpath("//*[@id=\"competition\"]"));
			 breedingPanel 			= driver.findElement(By.xpath("//*[@id=\"reproduction\"]"));
			
			 characteristicsTab 	= driver.findElement(By.xpath("//*[@id=\"tab-characteristics-title\"]"));
			 geneticTab 			= driver.findElement(By.xpath("//*[@id=\"tab-genetics-title\"]"));
			 
//			 statusPanel 			= driver.findElement(By.xpath("//*[@id=\"module-2\"]/div[2]/div/div/div"));
//			 skillsPanel 			= driver.findElement(By.xpath("//*[@id=\"skills-body-content\"]"));
			
			 nextHorseButton 		= driver.findElement(By.id("nav-next"));

			 prevHorseButton 		= driver.findElement(By.xpath("//*[@id=\"nav-previous\"]"));
		}
		catch(Exception e) {
			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
		}
		
		setURL( getPageURL() );
		this.horse = new Horse( this );

	}
	
	/**
	 * Начать выполнение программы для текущей лошади
	 * @param programm Программа для выполнения
	 * @param result Объект, результирующий выполнение функции
	 * */
	public void startProgramm( HerdRunSettings programm, ResultObject result ) 
	{
		this.programm = programm;
		
		//Logger.getLogger("debug").debug("HORSE " + horse.getName() + " WITH URL " + getURL());
		
		// Регистрация в КСК
		if( programm.getCommonSettings().isRegisterInEC() ) 
		{
			registerInAnEquestrianCenter( result );
			if( !result.isSuccess() ) return;
		}
		
		if( MainAppHolderSingleton.getHerdRunService().isStopped() ) return;
		
		
		return;
	}
	
	// Пропустить лошадь
	public boolean skipPage() 
	{
		try 
		{
			switchToNextHorse();	
		}
		catch (Exception e) 
		{
			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
			return false;
		}
		
		return true;
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
	
	public String toString() 
	{		try {
		return ""
				+ "HORSE NAME: " + horse.getName() + "; "
				+ "PAGE URL: " + getURL() + "; "
				+ "PROGRAM NAME: " + (programm != null ? programm.getProgramName() : "null" )+ "; "
				;
	}
	catch(Exception e) {e.printStackTrace(); return null;}

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
//*****************************************************************************************************************	
//*****************************************************************************************************************	
//*****************************************************************************************************************		
	private void switchToNextHorse(){
		Sleeper.waitForClickAndClick("//*[@id=\"nav-next\"]");
	}
	
/*****************************************************************
 *                     Регистрация в КСК                        *
 *****************************************************************/
	private void registerInAnEquestrianCenter( ResultObject result ) 
	{
		Logger.getLogger("debug").debug("registerInAnEquestrianCenter");
		Logger.getLogger("debug").debug(horse.getAge());
		
		if( horse.getAge() < 1.6 ) return;
		
		// Нажимаем кнопку "Зарегестрировать в КСК"
		try 
		{
			Sleeper.setStandartImplicitlyWait(3);
			WebElement registerECButton = driver.findElement(By.xpath(XPathConstants.HORSE_PAGE_REGISTER_IN_EC_BUTTON));
			Sleeper.setStandartImplicitlyWait();
			String registerURL = registerECButton.findElement(By.tagName("a")).getAttribute("href");
			driver.navigate().to(registerURL);
			Sleeper.longPause();
		}
		catch(TimeoutException | NoSuchElementException e) 
		{
			result.setInfoMsg(Msgs.EC_REGISTER_HORSE_ALREADY_REGISTERED_MSG);
			return;
		}
		catch(Exception e)
		{
			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
			result.setErrMsg(Msgs.EC_REGISTER_ERROR_MSG);
			result.setSuccess(false);
			return;
		}
		
		
		// Так как страница записи в КСК не хочет нормально работать со стандартными средствами Selenium, пользуемся JavascriptExecutor.
		try 
		{			
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			EC_Settings settings = programm.getEC_Settings();
			
			if( settings.getEC_type() != null ) 
			{
				// Зарезервированные стойла
				if( settings.getEC_type() == 'R' ) 
				{
					// Кликаем на вкладку Зарезервированные стойла
					executor.executeScript(JSScriptsConstants.EC_PAGE_RESERVED_TAB);
				
					Sleeper.pause();
											
					WebElement stallListTable = driver.findElement(By.xpath(XPathConstants.EC_PAGE_RESERVED_TABLE));
					List<WebElement> stallList = stallListTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
					
					// Если зарезервированных стойл нет, возвращаемся на страницу лошади
					if( stallList.size() == 0 ) 
					{
						result.setInfoMsg(Msgs.EC_REGISTER_NO_RESERVED_STALLS_MSG);
						result.setSuccess(false);
						driver.navigate().to(URL);
						return;
					}
					
					// Сортируем по длительности записи				
					switch( settings.getRegTerm() ) 
					{
						case 3: 
							executor.executeScript(JSScriptsConstants.EC_PAGE_RESERVED_TABLE_DAYS_SORT_3);
						break;
						case 10:
							executor.executeScript(JSScriptsConstants.EC_PAGE_RESERVED_TABLE_DAYS_SORT_10);
						break;
						case 30: 
							executor.executeScript(JSScriptsConstants.EC_PAGE_RESERVED_TABLE_DAYS_SORT_30);
						break;
						case 60:
							executor.executeScript(JSScriptsConstants.EC_PAGE_RESERVED_TABLE_DAYS_SORT_60);
						break;				
					}
					
					Sleeper.pause();
					
					// Т.к страница обновилась после сортировки, заново получаем строки списка
					stallList = stallListTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
					
					List<WebElement> registerButtons = stallList.get(0).findElements(By.className("button"));
					
					try 
					{
						switch( settings.getRegTerm() )
						{
							case 3: 
								registerButtons.get(1).click();
							break;
							case 10:
								registerButtons.get(2).click();
							break;
							case 30: 
								registerButtons.get(3).click();
							break;
							case 60:
								registerButtons.get(4).click();
							break;	
						}					
					}
					catch(Exception e) 
					{
						Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
						result.setInfoMsg(Msgs.EC_REGISTER_NO_STALLS_MSG);
						result.setSuccess(false);
						driver.navigate().to(URL);
						return ;
					}

				}
				// Свой КСК
				else if( settings.getEC_type() == 'O' ) 
				{
					// Переключаемся на свой КСК
					WebElement filtersPanel = driver.findElement(By.xpath(XPathConstants.EC_PAGE_FILTERS_PANEL));
					filtersPanel.findElements(By.className("button")).get(1).click();
					
					Sleeper.pause();
					
					WebElement ECTable = driver.findElement(By.xpath(XPathConstants.EC_PAGE_EC_TABLE));
					List<WebElement> stallList = ECTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
					
					// Если стойл нет, возвращаемся на страницу лошади
					if( stallList.size() == 0 ) 
					{
						result.setInfoMsg(Msgs.EC_REGISTER_NO_STALLS_MSG);
						result.setSuccess(false);
						driver.navigate().to(URL);
						return;
					}
					
					List<WebElement> registerButtons = stallList.get(0).findElements(By.className("button"));
					try 
					{
						// Кнопки записи не кликабельны, скроллим страницу к ним, чтобы нажать
						switch( settings.getRegTerm() )
						{
							case 3: 
								executor.executeScript("arguments[0].scrollIntoView(true);", registerButtons.get(1));
								registerButtons.get(1).click();
							break;
							case 10:
								executor.executeScript("arguments[0].scrollIntoView(true);", registerButtons.get(2));
								registerButtons.get(2).click();
							break;
							case 30: 
								executor.executeScript("arguments[0].scrollIntoView(true);", registerButtons.get(3));
								registerButtons.get(3).click();
							break;
							case 60:
								executor.executeScript("arguments[0].scrollIntoView(true);", registerButtons.get(4));
								registerButtons.get(4).click();
							break;	
						}					
					}
					catch(Exception e) 
					{
						Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
						result.setInfoMsg(Msgs.EC_REGISTER_NO_STALLS_MSG);
						result.setSuccess(false);
						driver.navigate().to(URL);
						return;
					}
				}
			}
			// Любой КСК
			else 
			{	
				// Устанавливаем фильтры
				try 
				{
					WebElement filtersPanel = driver.findElement(By.xpath(XPathConstants.EC_PAGE_FILTERS_PANEL));
					Boolean haveFilters = false;
					
					//Расположение
					if( settings.getLocation() != null ) 
					{
						switch( settings.getLocation() ) 
						{
							case 'F': 
								filtersPanel.findElement(By.id("foretCheckbox")).click();
							break;
							case 'M': 
								filtersPanel.findElement(By.id("montagneCheckbox")).click();
							break;
							case 'B': 
								filtersPanel.findElement(By.id("plageCheckbox")).click();
							break;
						}
						
						haveFilters = true;
					}
					
					//Специализация
					if( settings.getSpecialization() != null ) 
					{
						switch( settings.getSpecialization() ) 
						{
							case 'C': 
								filtersPanel.findElement(By.id("classiqueCheckbox")).click();
							break;
							case 'W':
								filtersPanel.findElement(By.id("westernCheckbox")).click();
							break;
						}
						
						haveFilters = true;
					}
					
					// Фураж
					if( settings.isHay() ) 
					{
						filtersPanel.findElement(By.id("fourrageCheckbox")).click();
						haveFilters = true;
					}
					
					// Овес
					if( settings.isOat() ) 
					{
						filtersPanel.findElement(By.id("avoineCheckbox")).click();
						haveFilters = true;
					}
					
					// Морковь
					if( settings.isCarrot() ) 
					{
						filtersPanel.findElement(By.id("carotteCheckbox")).click();
						haveFilters = true;
					}
					
					// Комбикорм
					if( settings.isMash() ) 
					{
						filtersPanel.findElement(By.id("mashCheckbox")).click();
						haveFilters = true;
					}
					
					// Поилка
					if( settings.isDrinker() ) 
					{
						filtersPanel.findElement(By.id("abreuvoirCheckbox")).click();
						haveFilters = true;
					}
					
					// Душ
					if( settings.isShower() ) 
					{
						filtersPanel.findElement(By.id("doucheCheckbox")).click();
						haveFilters = true;
					}
					
					// Максимальный тариф за день постоя
					if( settings.getMaxTariff() > 0 ) 
					{
						WebElement tarifCombo = filtersPanel.findElement(By.id("tarif"));
						executor.executeScript("arguments[0].value = " + settings.getMaxTariff() + ";", tarifCombo);
						haveFilters = true;
					}
					
					// Если есть хоть один фильтр - нажимаем кнопку поиска
					if( haveFilters ) 
					{
						filtersPanel.findElements(By.className("button")).get(0).click();
						Sleeper.longPause();
					}
				}
				catch (Exception e) 
				{
					Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
					result.setErrMsg(Msgs.EC_REGISTER_FILTER_ERROR_MSG);
					result.setSuccess(false);
					driver.navigate().to(URL);
					return;
				}
				
				WebElement ECTable = driver.findElement(By.xpath(XPathConstants.EC_PAGE_EC_TABLE));
				List<WebElement> stallList = ECTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
				
				// Если стойл нет, возвращаемся на страницу лошади
				if( stallList.size() == 0 ) 
				{
					result.setInfoMsg(Msgs.EC_REGISTER_NO_STALLS_MSG);
					result.setSuccess(false);
					driver.navigate().to(URL);
					return;
				}
				
				List<WebElement> termSortHeader = ECTable.findElements(By.xpath("//*[@class='grid-cell spacer-small-top spacer-small-bottom']"));
				// Сортируем по длительности записи				
				switch( settings.getRegTerm() ) 
				{
					case 3: 
						termSortHeader.get(1).findElement(By.tagName("a")).click();
					break;
					case 10:
						termSortHeader.get(2).findElement(By.tagName("a")).click();
					break;
					case 30: 
						termSortHeader.get(3).findElement(By.tagName("a")).click();
					break;
					case 60:
						termSortHeader.get(4).findElement(By.tagName("a")).click();
					break;				
				}
				
				Sleeper.pause();
				
				// Т.к страница обновилась после сортировки и поиска, заново получаем таблицу и строки списка
				ECTable = driver.findElement(By.xpath(XPathConstants.EC_PAGE_EC_TABLE));
				stallList = ECTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
				
				List<WebElement> registerButtons = stallList.get(0).findElements(By.className("button"));
				try 
				{
					// Кнопки записи не кликабельны, скроллим страницу к ним, чтобы нажать
					switch( settings.getRegTerm() )
					{
						case 3: 
							executor.executeScript("arguments[0].scrollIntoView(true);", registerButtons.get(1));
							registerButtons.get(1).click();
						break;
						case 10:
							executor.executeScript("arguments[0].scrollIntoView(true);", registerButtons.get(2));
							registerButtons.get(2).click();
						break;
						case 30: 
							executor.executeScript("arguments[0].scrollIntoView(true);", registerButtons.get(3));
							registerButtons.get(3).click();
						break;
						case 60:
							executor.executeScript("arguments[0].scrollIntoView(true);", registerButtons.get(4));
							registerButtons.get(4).click();
						break;	
					}					
				}
				catch(Exception e) 
				{
					Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
					result.setInfoMsg(Msgs.EC_REGISTER_NO_STALLS_MSG);
					result.setSuccess(false);
					driver.navigate().to(URL);
					return;
				}
			}
		}
		catch(Exception e) 
		{
			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
			result.setErrMsg(Msgs.EC_REGISTER_NO_STALLS_MSG);
			result.setSuccess(false);
			return;
		}
		
		result.setInfoMsg(Msgs.EC_REGISTER_REGISTER_SUCCESS_MSG);
		return;
	}
	

	
	
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
	/**
	 * Так как при переключении между лошадьми URL генерируется динамически, получаем не тот, который забит в адресную строку, а зашитый в кличку лошади на странице.
	 * @return URL страницы лошади 
	 * */
	private String getPageURL() 
	{
		return infoPanel.findElement(By.className("horse-name")).findElement(By.tagName("a")).getAttribute("href");
	}
	
	private WebElement getInfoPanel() 
	{
		
		try 
		{
			return driver.findElement(By.xpath(XPathConstants.HORSE_PAGE_INFO_PANEL));
		}
		catch( NoSuchElementException e ) 
		{
			return driver.findElement(By.xpath(XPathConstants.HORSE_PAGE_INFO_PANEL_ALT));
		}
		catch (Exception e) 
		{
			return null;
		}
	}
//*****************************************************************************************************************	
//*****************************************************************************************************************
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}

	public Horse getHorse() {
		return horse;
	}

	public void setHorse(Horse horse) {
		this.horse = horse;
	}
}
