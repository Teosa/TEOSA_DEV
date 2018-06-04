package ru.teosa.GUI.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import ru.teosa.GUI.MainApp;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.RedirectingComboRecordExt;


public class MainWindowController {
	
	private static AccountInfoPanelController   accountInfoController;
	private static FarmsTreeViewController      farmsTreeController;
	private static FarmProgramPanelController   farmProgramPanelController;
	private static RunProgrammController        runProgramPanelController;
	
	private static ProgramWindowController      programWindowController;

	private MainApp mainApp;
	
	@FXML private BorderPane mainForm;
	

    @FXML
    private void initialize() {
    	try 
    	{
    		MainAppHolderSingleton.getInstance().getMainApp().setController(this);
    		setMainApp(MainAppHolderSingleton.getInstance().getMainApp());
    		
    		//Загружаем панели в форму
        	loadAccountInfoPanel();    		
        	loadFarmsTreeView();
        	loadFramProgramPanel();
        	loadRunProgrammPanel();       
        	     	
        	MainApp.getController().getFarmsTreeController().customizeContent();
        	MainApp.getController().getFarmsTreeController().getTree().getSelectionModel().select(0);;
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
    private void loadFarmsTreeView() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/FarmsTreeViewl.fxml"));
        mainForm.setLeft((TreeView<RedirectingComboRecordExt>) loader.load());
    }
    
    // Загрузка панели программы для выбраного завода
    private void loadFramProgramPanel() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/FarmProgramInfoPanel.fxml"));
        mainForm.setCenter((ScrollPane) loader.load());
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
	public static RunProgrammController getRunProgramPanelController() {
		return runProgramPanelController;
	}
	public static void setRunProgramPanelController(RunProgrammController runProgramPanelController) {
		MainWindowController.runProgramPanelController = runProgramPanelController;
	}
	public static FarmsTreeViewController getFarmsTreeController() {
		return farmsTreeController;
	}
	public static void setFarmsTreeController(FarmsTreeViewController farmsTreeController) {
		MainWindowController.farmsTreeController = farmsTreeController;
	}
	public static FarmProgramPanelController getFarmProgramPanelController() {
		return farmProgramPanelController;
	}
	public static void setFarmProgramPanelController(FarmProgramPanelController farmProgramPanelController) {
		MainWindowController.farmProgramPanelController = farmProgramPanelController;
	}
	public static ProgramWindowController getProgramWindowController() {
		return programWindowController;
	}
	public static void setProgramWindowController(ProgramWindowController programWindowController) {
		MainWindowController.programWindowController = programWindowController;
	}
}
