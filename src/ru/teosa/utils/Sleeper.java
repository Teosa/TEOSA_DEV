package ru.teosa.utils;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ru.teosa.utils.objects.MainAppHolderSingleton;

public class Sleeper {
	public static void pause(){	
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public static void longPause(){	
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public static void waitForClickAndClick(String xpath) {
		WebDriverWait wait = new WebDriverWait(MainAppHolderSingleton.getInstance().getDriver(), 3);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element.click();
	}
	
	public static WebElement waitVisibility(String xpath) {
		WebDriverWait wait = new WebDriverWait(MainAppHolderSingleton.getInstance().getDriver(), 3);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}
}
