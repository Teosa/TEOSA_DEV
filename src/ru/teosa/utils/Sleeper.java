package ru.teosa.utils;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

public class Sleeper {
	public static void pause(){	
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
		}
	}
}
