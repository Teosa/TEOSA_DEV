package ru.teosa.threads;

import javafx.concurrent.Service;

/** ����� ��� ���������� ��������� */
public class ServiceStarter {

	/**
	 * ������������ ��������� �������. (��� -> ���� / ���� -> ���)
	 * @param service ������ ��� ������������
	 * @return 
	 * 1  - �������; <br>
	 * 0  - ��������� �� ����������; <br>
	 * -1 - ������; 
	 * */
	public static int shift(Service<String> service) 
	{
		if(service.isRunning()) 
			return stop(service);
		else 
			return start(service);
	}
	
	/**
	 * ������ �������.
	 * @param service ������ ��� ������������
	 * @return 
	 * 1  - �������; <br>
	 * 0  - ��������� �� ����������; <br>
	 * -1 - ������; 
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
	 * ��������� �������. 
	 * @param service ������ ��� ������������
	 * @return 
	 * 1  - �������; <br>
	 * 0  - ��������� �� ����������; <br>
	 * -1 - ������; 
	 * */
	public static int stop(Service<String> service) 
	{
		try 
		{
			//���� ���� ������� - ������������� ��� � ������ ����� ��� ��������� ��������� READY( ���������� ��� ���������� ������� )
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
