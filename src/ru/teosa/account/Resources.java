package ru.teosa.account;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import ru.teosa.GUI.view.MoneyConvertorController;
import ru.teosa.utils.Sleeper;
import ru.teosa.utils.Tools;
import ru.teosa.utils.objects.MainAppHolderSingleton;

public class Resources {
	private Integer money;  // ���
	private Integer wheat;  // �����
	
	
	
	
	
	
	
//***************************************************************************	
//***************************************************************************
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public Integer getWheat() {
		return wheat;
	}
	public void setWheat(Integer wheat) {
		this.wheat = wheat;
	}
	
	/** ��������� ����������� ������� ��� � �����.<br>
	 *  � ������ ������������� ����������, ����� ������ -1.  
	 *  @return ������ ��� (int)*/
	public static Integer getMoneyBalFromForm() {
		Integer result = -1;
		try {
			Sleeper.waitVisibility("//*[@id=\"reserve\"]");
			String moneyCurBal = MainAppHolderSingleton.getInstance().getDriver().findElement(By.xpath("//*[@id=\"reserve\"]")).getText();
			if(moneyCurBal != null) result = Integer.parseInt(moneyCurBal.replace(",", "").trim());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
	/** ��������� ����������� ������� ����� � �����. ���� ���������� �� ��������, ����� ���������� -1 */
	public static Integer getWheatBalFromForm() {
		Integer result = -1;
		try {
			WebDriver driver = MainAppHolderSingleton.getInstance().getDriver();
			String wheatCurBal = null;


			//���� �� �� �������� ������� �������
			if(driver.getCurrentUrl().trim().equalsIgnoreCase(MainAppHolderSingleton.getGameURL() + MoneyConvertorController.BUY_STORE_URL))
				wheatCurBal = (driver
				        		.findElement(By.className("inventory-item-son-ble"))
				        		.findElement(By.className("value"))
        			          ).getText();
			//���� �� �������� �������
			else if(driver.getCurrentUrl().trim().equalsIgnoreCase(MainAppHolderSingleton.getGameURL() + MoneyConvertorController.SELL_STORE_URL))
				wheatCurBal = driver.findElement(By.xpath("//*[@id=\"inventaire97\"]")).getText();
				
        	if(wheatCurBal != null) result = Integer.parseInt(wheatCurBal.replace("x", "").trim());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
