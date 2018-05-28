package ru.teosa.GUI.view;

import org.apache.log4j.Logger;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
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
	
	
	
	//Меню
	@FXML
	private MenuItem convertMoney;  // Модуль конвертации
	@FXML
	private MenuItem exit;          //Выход
	
	
//	@FXML
	// TODO флаг версии
	
	@FXML
	private Text breederName;
	@FXML
	private Text moneyBallance;
	
	
	
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
}
