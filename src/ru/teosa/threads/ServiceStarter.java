package ru.teosa.threads;

import javafx.concurrent.Service;

/** Класс для управления сервисами */
public class ServiceStarter {

	/**
	 * Переключение состояния сервиса. (Вкл -> Выкл / Выкл -> Вкл)
	 * @param service сервис для переключения
	 * @return 
	 * 1  - успешно; <br>
	 * 0  - состояние не изменилось; <br>
	 * -1 - ошибка; 
	 * */
	public static int shift(Service<String> service) 
	{
		if(service.isRunning()) 
			return stop(service);
		else 
			return start(service);
	}
	
	/**
	 * Запуск сервиса.
	 * @param service сервис для переключения
	 * @return 
	 * 1  - успешно; <br>
	 * 0  - состояние не изменилось; <br>
	 * -1 - ошибка; 
	 * */
	public static int start(Service<String> service) 
	{
		try 
		{
	    	if(!service.isRunning()) 
	    	{
	    		if(service.getState().toString() == "SUCCEEDED") 
	    			 service.restart();
	    		else service.start();
	    		
	    		return 1;
	    	}
	    	
	    	return 0;
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Остановка сервиса. 
	 * @param service сервис для переключения
	 * @return 
	 * 1  - успешно; <br>
	 * 0  - состояние не изменилось; <br>
	 * -1 - ошибка; 
	 * */
	public static int stop(Service<String> service) 
	{
		try 
		{
			//Если тред запущен - останавливаем его и делаем ресет для установки состояния READY( необходимо для повторного запуска )
	    	if(service.isRunning()) 
	    	{
	    		service.cancel();
	    		service.reset();
	    		
	    		return 1;
	    	}
	    	
	    	return 0;
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			return -1;
		}
	}
}
