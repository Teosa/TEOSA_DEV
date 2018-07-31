package ru.teosa.threads;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.teosa.GUI.view.MoneyConvertorController;
import ru.teosa.account.Account;
import ru.teosa.account.Resources;
import ru.teosa.utils.Tokens;
import ru.teosa.utils.Msgs;
import ru.teosa.utils.Sleeper;
import ru.teosa.utils.Tools;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import javafx.scene.control.Button;


public class MoneyConverterSercice extends Service<String>{

	private Stage convertorWin;           // Окно конвертации
	
	private Text moneyCurBalText;         // Поле текущий баланс, экю                         
	private Text wheatCurBalText;         // Поле текущий баланс, зерно
	
	private Integer targetMoneyBallance;  // Целевой баланс экю
	
	private boolean sellWheat;

	@Override
	protected Task<String> createTask() {
		return new Task<String>() {
			@Override
			protected String call() throws Exception {
				
				Button btn = (Button) convertorWin.getScene().lookup("#convertBtn");

				moneyCurBalText = (Text) convertorWin.getScene().lookup("#moneyCurBalText");
				wheatCurBalText = (Text) convertorWin.getScene().lookup("#wheatCurBalText");

				targetMoneyBallance = Integer.parseInt(((TextField) convertorWin.getScene().lookup("#moneyExpBalField")).getText());

				sellWheat = Integer.parseInt(moneyCurBalText.getText().replace(" ", "")) - targetMoneyBallance < 0;

				while (!isTargetBalanceReached()) 
				{
					try {
						if (sellWheat) {
							switchToStorePage(MoneyConvertorController.SELL_STORE_URL);
							sell();
						} else {
							switchToStorePage(MoneyConvertorController.BUY_STORE_URL);
							buy();
						}

						Sleeper.pause();
						if(isCancelled()) break;
						
					} catch (Exception e) {
						Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
						break;
					}
				}
				
				btn.setText(Msgs.CONVERT_TEXT);
				btn.setDisable(true);
				
				return null;
			}
		};
	}
	
	private boolean isTargetBalanceReached() {
		return sellWheat ? Resources.getMoneyBalFromForm() >=  targetMoneyBallance : Resources.getMoneyBalFromForm() <=  targetMoneyBallance;
	}
	
	//Проверяем находимся ли на нужной странице магазина, если нет - переключаемся
	private void switchToStorePage(String urlPart) {
		WebDriver driver = MainAppHolderSingleton.getInstance().getDriver();
		String storeURL = MainAppHolderSingleton.getGameURL() + urlPart;
		
		if(!driver.getCurrentUrl().trim().equalsIgnoreCase(storeURL)){
			driver.navigate().to(storeURL);
			if(urlPart == MoneyConvertorController.BUY_STORE_URL) {
				//Выбираем вкладку ресурсы
				Sleeper.waitVisibility("//*[@id=\"tab-ressources\"]");
	        	driver.findElement(By.xpath("//*[@id=\"tab-ressources\"]")).findElement(By.className("tab-action")).click();	
			}
		}
	}
	
	private void buy() {
		WebDriver driver = MainAppHolderSingleton.getInstance().getDriver();
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		
		String buyWheatBtnXpath = "//*[@id=\"achat-produit-son-ble\"]";
		String comboXpath = "//*[@id=\"purchase97\"]";
		String buyXpath = "//*[@id=\"soumettre97\"]";
		
		//Нажимаем на кнопку покупки зерна в магазине
		Sleeper.waitVisibility(buyWheatBtnXpath);
		executor.executeScript("document.getElementById(\"achat-produit-son-ble\").click();");
		
		Sleeper.waitVisibility(comboXpath);
		Select combo = new Select(driver.findElement(By.xpath(comboXpath)));	
		
		Integer mcb = Resources.getMoneyBalFromForm();
		
		//Находим максимально возможное количество зерна для покупки и селектим в комбобоксе
		for(int i = Tokens.WHEAT_STORE.length -1; i >= 0; --i ) {		
			if(mcb - Tokens.WHEAT_STORE[i] >= targetMoneyBallance) {
				combo.selectByValue(Tokens.WHEAT_STORE[i] + "");
				break;
			}
		}
		
		//Покупаем выбранное количество
		Sleeper.waitVisibility(buyXpath);
		driver.findElement(By.xpath(buyXpath)).click();
		
		Sleeper.pause();
		
		//Обновляем информацию в аккаунте
		Account.getResources().setMoney(Resources.getMoneyBalFromForm());
		Account.getResources().setWheat(Resources.getWheatBalFromForm());

		//Обновляем информацию на форме конвертации
		moneyCurBalText.setText(Tools.numStringWithSpaces(Account.getResources().getMoney()));
		wheatCurBalText.setText(Tools.numStringWithSpaces(Account.getResources().getWheat()));
	};
	
	private void sell() {
		WebDriver driver = MainAppHolderSingleton.getInstance().getDriver();
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		String sellWheatBtnXpath = "//*[@id=\"vendre97\"]";
		String comboXpath = "//*[@id=\"sale97\"]";
		String sellXpath = "//*[@id=\"soumettre97\"]";
		
		//Нажимаем на кнопку продажи зерна в магазине
		Sleeper.waitVisibility(sellWheatBtnXpath);
		executor.executeScript("document.getElementById(\"vendre97\").click();");
		
		Sleeper.waitVisibility(comboXpath);
		Select combo = new Select(driver.findElement(By.xpath(comboXpath)));	
		
		Integer mcb = Resources.getMoneyBalFromForm();

		int tosell = targetMoneyBallance - mcb;
		  
		//Если мы не хотим продавать все, вычисляем количество зерна, которое необходимо выбрать в выпадающем списке
		if(Account.getResources().getWheat() != tosell) tosell = Tools.getQtyForSale(tosell);
		
		//Выставляем количество
		combo.selectByValue(tosell + "");
		
		//Покупаем выбранное количество
		Sleeper.waitVisibility(sellXpath);
		driver.findElement(By.xpath(sellXpath)).click();
		
		Sleeper.pause();
		
		//Обновляем информацию в аккаунте
		Account.getResources().setMoney(Resources.getMoneyBalFromForm());
		Account.getResources().setWheat(Resources.getWheatBalFromForm());

		//Обновляем информацию на форме конвертации
		moneyCurBalText.setText(Tools.numStringWithSpaces(Account.getResources().getMoney()));
		wheatCurBalText.setText(Tools.numStringWithSpaces(Account.getResources().getWheat()));
	};
	
//******************************************************************************************
//******************************************************************************************
	public Stage getConvertorWin() {
		return convertorWin;
	}
	public void setConvertorWin(Stage convertorWin) {
		this.convertorWin = convertorWin;
	}
	public Text getMoneyCurBalText() {
		return moneyCurBalText;
	}
	public void setMoneyCurBalText(Text moneyCurBalText) {
		this.moneyCurBalText = moneyCurBalText;
	}
	public Text getWheatCurBalText() {
		return wheatCurBalText;
	}
	public void setWheatCurBalText(Text wheatCurBalText) {
		this.wheatCurBalText = wheatCurBalText;
	}
	public Integer getTargetMoneyBallance() {
		return targetMoneyBallance;
	}
	public void setTargetMoneyBallance(Integer targetMoneyBallance) {
		this.targetMoneyBallance = targetMoneyBallance;
	}

}
