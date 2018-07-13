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

/** Класс для управления ожиданиями и таймаутами */
public class Sleeper {
	
	private static final int STANDART_PAGE_LOAD_TIME       = 15; // Стандартное время ожидания загрузки страницы
	private static final int STANDART_SCRIPT_TIMEOUT_TIME  = 30; // Стандартное время ожидания выполнения скриптов
	private static final int STANDART_IMPLICITLY_WAIT_TIME = 10; // Стандартное время ожиданий
	
	/**  Остановка треда на рандомный промежуток от 400 до 800 мсек. */
	public static void pause(){	
		try {
			Thread.sleep(getRandomNumberInRange(400, 800));
		} catch (InterruptedException e) {
			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
		}
	}
	
	/**  Остановка треда на рандомный промежуток от 1 до 2 сек. */
	public static void longPause(){	
		try {
			Thread.sleep(getRandomNumberInRange(1000, 2000));
		} catch (InterruptedException e) {
			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
		}
	}
	
	/**
	 * Ожидание кликабельности элемента на странице в течение 3 секунд и выполнение клика.
	 * @param xpath xpath элемента
	 * */
	public static void waitForClickAndClick(String xpath) {
		WebDriverWait wait = new WebDriverWait(MainAppHolderSingleton.getInstance().getDriver(), 3);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element.click();
	}
	

	/**
	 * Ожидание появления элемента на странице в течение 3 секунд.
	 * @param xpath xpath элемента
	 * @return найденный элемент или NULL
	 * */
	public static WebElement waitVisibility(String xpath) {
		try {
			WebDriverWait wait = (WebDriverWait) new WebDriverWait(MainAppHolderSingleton.getInstance().getDriver(), 3);
			return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
/* **********************************************************
 *               ВРЕМЯ ЗАГРУЗКИ СТРАНИЦЫ                    *
 ************************************************************/
	
	/** Включение стандартного времени ожидания загрузки страницы в 15 сек.*/
	public static void setStandartPageLoadTimeout() {
		MainAppHolderSingleton.getInstance().getDriver().manage().timeouts().pageLoadTimeout(STANDART_PAGE_LOAD_TIME, TimeUnit.SECONDS);
	}
	
	/** 
	 * Изменение стандартного времени ожидания загрузки страницы 
	 * @param time новое время ожидания в секундах
	 * */
	public static void setStandartPageLoadTimeout(int time) {
		MainAppHolderSingleton.getInstance().getDriver().manage().timeouts().pageLoadTimeout(time, TimeUnit.SECONDS);
	}
	
	/** Отключение стандартного времени ожидания загрузки страницы */
	public static void turnOffStandartPageLoadTimeout() {
		MainAppHolderSingleton.getInstance().getDriver().manage().timeouts().pageLoadTimeout(0, TimeUnit.SECONDS);
	}
	
/* **********************************************************
 *               ВРЕМЯ ВЫПОЛНЕНИЯ СКРИПТОВ                  *
 ************************************************************/
	
	/** Включение стандартного времени ожидания выполнения скриптов в 30 сек.*/
	public static void setStandartScriptTimeout() {
		MainAppHolderSingleton.getInstance().getDriver().manage().timeouts().setScriptTimeout(STANDART_SCRIPT_TIMEOUT_TIME, TimeUnit.SECONDS);
	}
	
	/** 
	 * Изменение стандартного времени ожидания выполнения скриптов 
	 * @param time новое время ожидания в секундах
	 * */
	public static void setStandartScriptTimeout(int time) {
		MainAppHolderSingleton.getInstance().getDriver().manage().timeouts().setScriptTimeout(time, TimeUnit.SECONDS);
	}
	
	/** Отключение стандартного времени ожидания выполнения скриптов */
	public static void turnOffStandartScriptTimeout() {
		MainAppHolderSingleton.getInstance().getDriver().manage().timeouts().setScriptTimeout(0, TimeUnit.SECONDS);
	}
	
/* **********************************************************
 *               ВРЕМЯ СТАНДАРТНЫХ ОЖИДАНИЙ                 *
 ************************************************************/

	/** Включение стандартных ожиданий в 10 сек.*/
	public static void setStandartImplicitlyWait() {
		MainAppHolderSingleton.getInstance().getDriver().manage().timeouts().implicitlyWait(STANDART_IMPLICITLY_WAIT_TIME, TimeUnit.SECONDS);
	}
	
	/** 
	 * Изменение стандартных ожиданий.
     * @param time новое время ожидания в секундах
	 * */
	public static void setStandartImplicitlyWait(int time) {
		MainAppHolderSingleton.getInstance().getDriver().manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}
	
	/** Отключение стандартных ожиданий */
	public static void turnOffImplicitlyWait() {
		MainAppHolderSingleton.getInstance().getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}
	
//***************************************************************************************************************************************
//***************************************************************************************************************************************
	// Получение рандомного числа из заданного промежутка.
	private static int getRandomNumberInRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
}
