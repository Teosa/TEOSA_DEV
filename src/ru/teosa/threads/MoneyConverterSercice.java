package ru.teosa.threads;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class MoneyConverterSercice extends Service<String>{
	
	
	boolean stop = false;

	@Override
	protected Task<String> createTask() {
		return new Task<String>() {
			@Override
			protected String call() throws Exception {
				
				while(!stop) {
					System.out.println("running...");
					Thread.sleep(3000);
				}
				return null;
			}};
	}

}
