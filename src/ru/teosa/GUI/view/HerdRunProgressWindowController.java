package ru.teosa.GUI.view;

import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.TextFlow;
import ru.teosa.utils.objects.MainAppHolderSingleton;

/** ќкно выполнени€ плана прогона */
public class HerdRunProgressWindowController extends AbstractController{

	@FXML private Label planStatusLabel;
	@FXML private Label currentFarmLabel;
	@FXML private Label currentProgramLabel;
	
	@FXML private ProgressIndicator progressIndicator;
	
	@FXML private TextFlow logArea;
	
	@FXML private Button startRun;
	@FXML private Button pauseRun;
	@FXML private Button stopRun; 
	
	
	@Override
	protected void initialize() 
	{
    	pauseRun.setDisable(true);
    	stopRun.setDisable(true);
	}

	@Override
	public void customizeContent() {
		// TODO Auto-generated method stub
		
	}
	
	@FXML private void startRunHandler() 
	{
    	if(MainAppHolderSingleton.getHerdRunService().getState().toString() == "SUCCEEDED") 
    		MainAppHolderSingleton.getHerdRunService().restart();
    	else MainAppHolderSingleton.getHerdRunService().start();
    	
    	MainAppHolderSingleton.getHerdRunService().setStopped(false);
    	
    	startRun.setDisable(true);
    	pauseRun.setDisable(false);
    	stopRun.setDisable(false);
    	
    	Logger.getLogger("debug").debug("************ START RUN ************");
	}
	
	@FXML private void pauseRunHandler() 
	{/*
    	//≈сли тред запущен - останавливаем его и делаем ресет дл€ установки состо€ни€ READY( необходимо дл€ повторного запуска )
    	if(MainAppHolderSingleton.getHerdRunService().isRunning()) 
    	{
    		MainAppHolderSingleton.getHerdRunService().cancel();
    		MainAppHolderSingleton.getHerdRunService().reset();
    	}
    	
    	startRun.setDisable(false);
    	pauseRun.setDisable(true);
    	stopRun.setDisable(false);*/
	}
	
	@FXML private void stopRunHandler() 
	{
    	//≈сли тред запущен - останавливаем его и делаем ресет дл€ установки состо€ни€ READY( необходимо дл€ повторного запуска )
    	if(MainAppHolderSingleton.getHerdRunService().isRunning()) 
    	{
    		MainAppHolderSingleton.getHerdRunService().cancel();
    		MainAppHolderSingleton.getHerdRunService().reset();
    	}
    	
    	MainAppHolderSingleton.getHerdRunService().setStopped(true);
    	
    	startRun.setDisable(false);
    	pauseRun.setDisable(true);
    	stopRun.setDisable(true);
    	
    	Logger.getLogger("debug").debug("************ STOP RUN ************");
	}
	
}
