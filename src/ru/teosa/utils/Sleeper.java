package ru.teosa.utils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

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
			Thread.sleep(getRandomNumberInRange(400, 800));
		} catch (InterruptedException e) {
			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public static void longPause(){	
		try {
			Thread.sleep(getRandomNumberInRange(1000, 2000));
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
	
	public static void turnOffImplicitWaits() {
		MainAppHolderSingleton.getInstance().getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}

	public static void turnOnImplicitWaits() {
		MainAppHolderSingleton.getInstance().getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	private static int getRandomNumberInRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
}
