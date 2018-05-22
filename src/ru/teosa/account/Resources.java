package ru.teosa.account;

import org.openqa.selenium.By;

import ru.teosa.utils.Tools;
import ru.teosa.utils.objects.MainAppHolderSingleton;

public class Resources {
	private Integer money;  // Экю
	private Integer wheat;  // Зерно
	
	
	
	
	
	
	
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
	
	/** Получение актуального баланса экю с формы */
	public static Integer getMoneyBalFromForm() {
		Integer result = -1;
		try {
        	String moneyCurBal = MainAppHolderSingleton.getInstance().getDriver().findElement(By.xpath("//*[@id=\"reserve\"]")).getAttribute("data-amount");
        	if(moneyCurBal != null) result = Integer.parseInt(moneyCurBal);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
}
