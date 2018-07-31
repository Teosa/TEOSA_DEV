package ru.teosa.threads;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import ru.teosa.utils.Sleeper;

public class HerdRunService extends Service<String>{

	@Override
	protected Task<String> createTask() {
		return new Task<String>() {
			@Override
			protected String call() throws Exception {
				
//				Button btn = (Button) convertorWin.getScene().lookup("#convertBtn");



				while (true) 
				{
					try {


						Sleeper.pause();
						if(isCancelled()) break;
						
					} catch (Exception e) {
						Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
						break;
					}
				}
							
				return null;
			}
		};
	}
}
