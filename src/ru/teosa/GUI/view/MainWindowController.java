package ru.teosa.GUI.view;

import org.apache.log4j.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import ru.teosa.GUI.MainApp;
import ru.teosa.utils.Customizer;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.RedirectingComboRecordExt;


public class MainWindowController {
	
	private static AccountInfoPanelController       accountInfoController;
	private static ECTreeViewController             ECTreeController;
	private static HerdRunSettingsTabPaneController HerdRunSettingsTabController;
	private static RunProgrammController            runProgramPanelController;

	private MainApp mainApp;
	
	@FXML
	private BorderPane mainForm;
	

    @FXML
    private void initialize() {
    	try 
    	{
    		MainAppHolderSingleton.getInstance().getMainApp().setController(this);
    		setMainApp(MainAppHolderSingleton.getInstance().getMainApp());
    		
    		//Загружаем панели в форму
        	loadAccountInfoPanel();    		
        	loadECTreeView();
//        	loadHerdRunSettingsTabPane();
        	loadRunProgrammPanel();       
        	     	
//        	MainApp.getController().getECTreeController().customizeContent();
//        	MainApp.getController().getECTreeController().getTree().getSelectionModel().select(0);;
    	}
    	catch (Exception e) {
			e.printStackTrace();
		}
    }
//******************************************************************************************   
//******************************************************************************************   
//******************************************************************************************  
    // Занрузка меню окна и панели с информащие об аккаунте
    private void loadAccountInfoPanel() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/AccountInfoPanel.fxml"));
        mainForm.setTop((AnchorPane) loader.load());
    }
   
    // Загрузка панели с деревем заводов
    private void loadECTreeView() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/ECTreeViewl.fxml"));
        mainForm.setLeft((TreeView<RedirectingComboRecordExt>) loader.load());
    }
    
    // Загрузка панели с настройками прогона
    private void loadHerdRunSettingsTabPane() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/HerdRunSettingsTabPane.fxml"));
        mainForm.setCenter((TabPane) loader.load());
    }
    
    // Загрузка панели Прогон
    private void loadRunProgrammPanel() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/RunProgrramPanel.fxml"));
        mainForm.setBottom((Accordion) loader.load());
    }
       

//******************************************************************************************   
//******************************************************************************************   
//******************************************************************************************   
	public MainApp getMainApp() {
		return mainApp;
	}
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	public static AccountInfoPanelController getAccountInfoController() {
		return accountInfoController;
	}
	public static void setAccountInfoController(AccountInfoPanelController accountInfoController) {
		MainWindowController.accountInfoController = accountInfoController;
	}
	public static ECTreeViewController getECTreeController() {
		return ECTreeController;
	}
	public static void setECTreeController(ECTreeViewController eCTreeController) {
		ECTreeController = eCTreeController;
	}
	public static HerdRunSettingsTabPaneController getHerdRunSettingsTabController() {
		return HerdRunSettingsTabController;
	}
	public static void setHerdRunSettingsTabController(HerdRunSettingsTabPaneController herdRunSettingsTabController) {
		HerdRunSettingsTabController = herdRunSettingsTabController;
	}
	public static RunProgrammController getRunProgramPanelController() {
		return runProgramPanelController;
	}
	public static void setRunProgramPanelController(RunProgrammController runProgramPanelController) {
		MainWindowController.runProgramPanelController = runProgramPanelController;
	}
}
