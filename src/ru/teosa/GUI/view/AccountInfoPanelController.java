package ru.teosa.GUI.view;

import java.io.IOException;

import org.apache.log4j.Logger;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.teosa.GUI.MainApp;
import ru.teosa.account.Account;
import ru.teosa.account.Resources;
import ru.teosa.utils.Tools;
import ru.teosa.utils.objects.MainAppHolderSingleton;

public class AccountInfoPanelController extends AbstractController{

	private MainApp mainApp;
	
	@FXML private MenuItem convertMoney;  // Модуль конвертации
	@FXML private MenuItem exit;          // Выход
	
	@FXML private MenuItem addProgram;   // Новая программа
	@FXML private MenuItem editProgram;  // Редактировать/удалить программу
	
//	@FXML
	// TODO флаг версии
	
	@FXML private Text breederName;
	@FXML private Text moneyBallance;
	
	private final static String ADD_PROGRAMM_WIN_HEADER  = "Новая программа";
	private final static String EDIT_PROGRAMM_WIN_HEADER = "Редактировать/удалить программу";
//************************************************************************************************************************************	
//************************************************************************************************************************************	
    @FXML
    protected void initialize() {
    	mainApp = MainAppHolderSingleton.getInstance().getMainApp();
    	
    	breederName.setText(Account.getUser().getUsername());
    	Account.getResources().setMoney(Resources.getMoneyBalFromForm());
    	moneyBallance.setText(Tools.numStringWithSpaces(Account.getResources().getMoney()));
    }
    
	@Override
	public void customizeContent() {
		// TODO Auto-generated method stub
		
	}
	
    //Поднятие диалога конвертации
    @FXML
    public void convertMoneyHandler() throws Exception{ 	

    	Stage dialog = new Stage();
    	
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/MoneyConvertor.fxml"));
        
        Scene scene = new Scene((VBox) loader.load());
       
        dialog.setResizable(false);
        dialog.setScene(scene);
        dialog.setTitle("Конвертация");
    	dialog.initOwner(mainApp.getPrimaryStage());
    	dialog.initModality(Modality.APPLICATION_MODAL);
    	
    	dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {		
			@Override
			public void handle(WindowEvent event) {
				if (MainAppHolderSingleton.getMoneyConverterHandler().isRunning()) 		
				{   
		    		MainAppHolderSingleton.getMoneyConverterHandler().cancel();
		    		MainAppHolderSingleton.getMoneyConverterHandler().reset();
		    	}
			}
		});
    	
    	MainAppHolderSingleton.getMoneyConverterHandler().setConvertorWin(dialog);
    	
    	dialog.showAndWait();
    }
    
    //
    @FXML
    public void addProgramHandler() throws Exception{ showProgramDialog(ADD_PROGRAMM_WIN_HEADER, 0); }
    
    //
    @FXML
    public void editProgramHandler() throws Exception{ showProgramDialog(EDIT_PROGRAMM_WIN_HEADER, 1); }
    
    private void showProgramDialog(String dialogTitle, int mode) throws IOException {
    	Stage dialog = new Stage();
    	
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/ProgramWindow.fxml"));
   
        ProgramWindowController.setMode(mode);
        
        Scene scene = new Scene((BorderPane) loader.load());

       
//        dialog.setResizable(false);
        dialog.setScene(scene);
        dialog.setTitle(dialogTitle);
    	dialog.initOwner(mainApp.getPrimaryStage());
    	dialog.initModality(Modality.APPLICATION_MODAL);
   	
    	dialog.showAndWait();
    }
}
