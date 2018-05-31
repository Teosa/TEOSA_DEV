package ru.teosa.mainapp.pojo;

import ru.teosa.herdSettings.HerdRunSettings;

public class BreedingFarm {
	
	private String name;
	private String URL;
	private HerdRunSettings settings;
	

	
	

	

	
	

//*************************************************************************************************************************************
//*************************************************************************************************************************************
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
//	public boolean findFirstHorse() {
//	WebDriver driver = MainAppHolderSingleton.getInstance().getDriver();
//	
//	List<WebElement> farms = driver.findElements(By.xpath("//*[@id=\"horseList\"]/div/div[2]/ul"));
//	if(farms.size() > 0) {
//		WebElement firstHorse = driver.findElement(By.xpath("//*[@id=\"horseList\"]/div/div[2]/ul/li[1]"));
//		if(firstHorse != null) {
//			try {
//				firstHorse.findElement(By.xpath("//*[@id=\"horseList\"]/div/div[2]/ul/li[1]/div/div[1]/div/ul/li[1]"))
//				.findElement(By.className("horsename")).click();
//				return true;
//			}
//			catch(Exception e) {
//				Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
//				return false;
//			}
//		}
//		else return false;
//	}
//	else return false;
//	
//}
	
	
//	@Override
//	public void run() {
//		Button start = (Button)MainAppHolderSingleton.getInstance().getMainApp().getPrimaryStage().getScene().lookup("#startButton");
//		Button stop = (Button)MainAppHolderSingleton.getInstance().getMainApp().getPrimaryStage().getScene().lookup("#stopButton");
//		
//		start.setDisable(true);
//		stop.setDisable(false);
//		
//		Logger.getLogger("debug").debug(Thread.currentThread().getName());
//		Logger.getLogger("debug").debug("lastRunedHorse: " + lastRunedHorse);
//		Logger.getLogger("debug").debug("Breeding farm URL: " + URL);
//		
//		if(lastRunedHorse != null) driver.navigate().to(lastRunedHorse);
//		else {
//			driver.navigate().to(URL);
//			if(!findFirstHorse()) return;
//		}
//			
//		boolean endOfFarm = false;
//		
//		try {
//			while(!endOfFarm && !runInterupted) {
//				Horse horse = new Horse();
//				lastRunedHorse = horse.getURL();
//				endOfFarm = horse.run();
//				Logger.getLogger("debug").debug("runInterupted: " + runInterupted);
//			}
//		}
//		catch(Exception e) {
//			Logger.getLogger("debug").debug("HERD RUNNING ERROR");
//			Logger.getLogger("debug").debug("LAST HORSE URL: " + lastRunedHorse);
//			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
//			return;
//		}
//		finally {
//			Logger.getLogger("debug").debug("FINNALY");
//			runInterupted = false;
//			
//			start.setDisable(false);
//			stop.setDisable(true);
//		}
//	}
	
	
}
