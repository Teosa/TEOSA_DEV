package ru.teosa.threads;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ru.teosa.GUI.MsgWindow;
import ru.teosa.site.model.HorsePage;
import ru.teosa.utils.Msgs;
import ru.teosa.utils.ResultObject;
import ru.teosa.utils.Sleeper;
import ru.teosa.utils.Tokens;
import ru.teosa.utils.XPathConstants;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.RunProgramRecord;

public class HerdRunService extends Service<String>{
	
	private Stage window;     
	private boolean isStopped;
	
	
	@Override
	protected Task<String> createTask() {
		return new Task<String>() {
			@Override
			protected String call() throws Exception 
			{	
				// �������� ������� �� ������� ��� �������
				TableView<RunProgramRecord> runProgrammTable = (TableView)MainAppHolderSingleton.getInstance().getMainApp().getPrimaryStage().getScene().lookup("#table");
				
				if( runProgrammTable == null ) 
				{
					MsgWindow.showErrorWindow(Msgs.HERD_RUN_NO_LIST_ERROR_MSG);
					return null;
				}
				
				// �������� ���-�� ������� � �������
				int listSize = runProgrammTable.getItems().size();
				if( listSize == 0 ) 
				{
					MsgWindow.showErrorWindow(Msgs.HERD_RUN_EMPTY_LIST_ERROR_MSG);
					return null;
				}
				
				for( int i = 0; i < listSize; ++i ) 
				{
					final RunProgramRecord record = runProgrammTable.getItems().get(i);
					
					if( !farmRun(record) ) 
					{
						break;
					}
				}
				
				
				

				/*while (true) 
				{
					try {

						Logger.getLogger("debug").debug("HerdRunService thread: WORK");
						Sleeper.pause(10000);
						
						//Sleeper.pause();
						if(isCancelled()) break;
						
					} catch (Exception e) {
						Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
						break;
					}
				}*/
							
				return null;
			}
		};
	}

	/**
	 * ������ ��������� ������� ��� ���������� ������
	 * @param record ����� ��� �������. �������� ������ ���� ��������� ��� ����������.
	 * @return ��������� ���� ���������� ����������.
	 * */
	private boolean farmRun(RunProgramRecord record) 
	{
		WebDriver driver = MainAppHolderSingleton.getInstance().getDriver();
		Integer errorsCounter = 0;
		
		// ��������� �� �������� ������
		driver.navigate().to(record.getFarmURL());
		Sleeper.waitVisibility(XPathConstants.FARM_LIST);
		
		// ���� ����� ������, �������
		if(!driver.findElement(By.xpath(XPathConstants.FARM_LIST_FIRST_HORSE)).isDisplayed()) 
		{
			record.setStatus(Tokens.herdRunStatuses.EMPTYFARM.getID());
			return true;
		}
		
		// ��������� �� �������� ������
		driver.findElement(By.xpath(XPathConstants.FARM_LIST_FIRST_HORSE)).click();
		
		HorsePage firstHorsePage = null;
		
		while( !isStopped() ) 
		{
			HorsePage horsePage = new HorsePage();
		
			// ��������� ������ ������ ���� ��� ������
			if( firstHorsePage == null ) 
			{
				firstHorsePage = horsePage;
			}
			// ���� �� ����� ������ � ������ ������ ������ - ������� �� �����
			else if( firstHorsePage.getURL().equalsIgnoreCase(horsePage.getURL()) ) 
			{
				break;
			}
			
			ResultObject result = new ResultObject();
			
			// ��������� ��������
			try 
			{
				horsePage.startProgramm( record.getProgram(), result );	
			}
			catch (Exception e) 
			{
				Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
				MsgWindow.showErrorWindow(e.getMessage());
				return false;
			}
			
			// ���� �������� ��������� ������
			if( !result.isSuccess() && result.haveErrors() ) 
			{
				MsgWindow.showErrorWindow( result.getErrMsg() );	
				return false;
			}
			// ���� �� ������, �� ��� ��������� ������
			else if( !result.isSuccess() && !result.haveErrors() ) 
			{
				Sleeper.longPause();
				// ��������� ���������� ��������
				if( !horsePage.skipPage() ) 
				{
					MsgWindow.showErrorWindow( result.getErrMsg() );	
					//record.setStatus(Tokens.herdRunStatuses.ERROR.getID());
					return false;
				}
				
				//record.setStatus(Tokens.herdRunStatuses.DONEWITHERRORS.getID());
			}
			// ���� ���� �������������� ���������, ������� ��� � ���
			else if( result.haveInfo() ) 
			{
				Logger.getLogger("debug").debug( result.getInfoMsg() );	
			}
			
			// ���� ���������� ������ ��������, ����������� ������� ������
			if( !result.isSuccess() ) 
			{
				errorsCounter++;
				// ���� ������� ������ �������� ������������ ����� - ������������� ���������� �������
				if( errorsCounter > MainAppHolderSingleton.getMaxRuntimeErrors() ) 
				{
					MsgWindow.showErrorWindow(Msgs.MAX_ERRORS_OCCURED);	
					return false;
				}
			}
			
			//return true;
			// ������������� �� ��������� ������
			Sleeper.longPause();
			horsePage.skipPage();
		}
		
		//record.setStatus(Tokens.herdRunStatuses.DONE.getID());
		return true;			
	}
	
	
//**********************************************************************************************************************************************************
//**********************************************************************************************************************************************************
	public Stage getWindow() {
		return window;
	}
	public void setWindow(Stage window) {
		this.window = window;
	}
	public boolean isStopped() {
		return isStopped;
	}
	public void setStopped(boolean isStopped) {
		this.isStopped = isStopped;
	}
}
