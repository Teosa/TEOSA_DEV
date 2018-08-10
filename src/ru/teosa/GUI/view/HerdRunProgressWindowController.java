package ru.teosa.GUI.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.TextFlow;
import ru.teosa.utils.objects.MainAppHolderSingleton;

/** ���� ���������� ����� ������� */
public class HerdRunProgressWindowController extends AbstractController{

	@FXML private Label planStatusLabel;
	@FXML private Label currentFarmLabel;
	@FXML private Label currentProgramLabel;
	
	@FXML private ProgressIndicator progressIndicator;
	
	@FXML private TextFlow logArea;
	
	@FXML private Button startRun;
	
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void customizeContent() {
		// TODO Auto-generated method stub
		
	}
	
	@FXML private void startRunHandler() 
	{
    	//���� ���� ������� - ������������� ��� � ������ ����� ��� ��������� ��������� READY( ���������� ��� ���������� ������� )
    	if(MainAppHolderSingleton.getHerdRunService().isRunning()) 
    	{
    		MainAppHolderSingleton.getHerdRunService().cancel();
    		MainAppHolderSingleton.getHerdRunService().reset();
    	}
    	//����� - ��������� ����	
    	else 
    	{
    		if(MainAppHolderSingleton.getHerdRunService().getState().toString() == "SUCCEEDED") 
    			MainAppHolderSingleton.getHerdRunService().restart();
    		else MainAppHolderSingleton.getHerdRunService().start();
    	}
	}
}
