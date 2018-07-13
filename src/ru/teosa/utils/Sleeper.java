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

/** ����� ��� ���������� ���������� � ���������� */
public class Sleeper {
	
	private static final int STANDART_PAGE_LOAD_TIME       = 15; // ����������� ����� �������� �������� ��������
	private static final int STANDART_SCRIPT_TIMEOUT_TIME  = 30; // ����������� ����� �������� ���������� ��������
	private static final int STANDART_IMPLICITLY_WAIT_TIME = 10; // ����������� ����� ��������
	
	/**  ��������� ����� �� ��������� ���������� �� 400 �� 800 ����. */
	public static void pause(){	
		try {
			Thread.sleep(getRandomNumberInRange(400, 800));
		} catch (InterruptedException e) {
			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
		}
	}
	
	/**  ��������� ����� �� ��������� ���������� �� 1 �� 2 ���. */
	public static void longPause(){	
		try {
			Thread.sleep(getRandomNumberInRange(1000, 2000));
		} catch (InterruptedException e) {
			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
		}
	}
	
	/**
	 * �������� �������������� �������� �� �������� � ������� 3 ������ � ���������� �����.
	 * @param xpath xpath ��������
	 * */
	public static void waitForClickAndClick(String xpath) {
		WebDriverWait wait = new WebDriverWait(MainAppHolderSingleton.getInstance().getDriver(), 3);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element.click();
	}
	

	/**
	 * �������� ��������� �������� �� �������� � ������� 3 ������.
	 * @param xpath xpath ��������
	 * @return ��������� ������� ��� NULL
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
 *               ����� �������� ��������                    *
 ************************************************************/
	
	/** ��������� ������������ ������� �������� �������� �������� � 15 ���.*/
	public static void setStandartPageLoadTimeout() {
		MainAppHolderSingleton.getInstance().getDriver().manage().timeouts().pageLoadTimeout(STANDART_PAGE_LOAD_TIME, TimeUnit.SECONDS);
	}
	
	/** 
	 * ��������� ������������ ������� �������� �������� �������� 
	 * @param time ����� ����� �������� � ��������
	 * */
	public static void setStandartPageLoadTimeout(int time) {
		MainAppHolderSingleton.getInstance().getDriver().manage().timeouts().pageLoadTimeout(time, TimeUnit.SECONDS);
	}
	
	/** ���������� ������������ ������� �������� �������� �������� */
	public static void turnOffStandartPageLoadTimeout() {
		MainAppHolderSingleton.getInstance().getDriver().manage().timeouts().pageLoadTimeout(0, TimeUnit.SECONDS);
	}
	
/* **********************************************************
 *               ����� ���������� ��������                  *
 ************************************************************/
	
	/** ��������� ������������ ������� �������� ���������� �������� � 30 ���.*/
	public static void setStandartScriptTimeout() {
		MainAppHolderSingleton.getInstance().getDriver().manage().timeouts().setScriptTimeout(STANDART_SCRIPT_TIMEOUT_TIME, TimeUnit.SECONDS);
	}
	
	/** 
	 * ��������� ������������ ������� �������� ���������� �������� 
	 * @param time ����� ����� �������� � ��������
	 * */
	public static void setStandartScriptTimeout(int time) {
		MainAppHolderSingleton.getInstance().getDriver().manage().timeouts().setScriptTimeout(time, TimeUnit.SECONDS);
	}
	
	/** ���������� ������������ ������� �������� ���������� �������� */
	public static void turnOffStandartScriptTimeout() {
		MainAppHolderSingleton.getInstance().getDriver().manage().timeouts().setScriptTimeout(0, TimeUnit.SECONDS);
	}
	
/* **********************************************************
 *               ����� ����������� ��������                 *
 ************************************************************/

	/** ��������� ����������� �������� � 10 ���.*/
	public static void setStandartImplicitlyWait() {
		MainAppHolderSingleton.getInstance().getDriver().manage().timeouts().implicitlyWait(STANDART_IMPLICITLY_WAIT_TIME, TimeUnit.SECONDS);
	}
	
	/** 
	 * ��������� ����������� ��������.
     * @param time ����� ����� �������� � ��������
	 * */
	public static void setStandartImplicitlyWait(int time) {
		MainAppHolderSingleton.getInstance().getDriver().manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}
	
	/** ���������� ����������� �������� */
	public static void turnOffImplicitlyWait() {
		MainAppHolderSingleton.getInstance().getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}
	
//***************************************************************************************************************************************
//***************************************************************************************************************************************
	// ��������� ���������� ����� �� ��������� ����������.
	private static int getRandomNumberInRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
}
