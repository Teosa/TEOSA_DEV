package ru.teosa.GUI.view;

import org.apache.log4j.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import ru.teosa.GUI.MainApp;
import ru.teosa.utils.objects.RedirectingComboRecordExt;


public class MainWindowController {

	private MainApp mainApp;
	
	@FXML
	private BorderPane mainForm;
	

    @FXML
    private void initialize() {
    	try 
    	{
    		//Загружаем панели в форму
        	loadAccountInfoPanel();    		
        	loadECTreeView();
        	loadHerdRunSettingsTabPane();
        	loadRunProgrammPanel();       
    	}
    	catch (Exception e) {
			e.printStackTrace();
		}
	
    	
   	
//    	ToggleGroup group = new ToggleGroup();
//    	myEC.setToggleGroup(group);
//    	anyEC.setSelected(true);
//    	anyEC.setToggleGroup(group);
//    	
//    	daysEC.getItems().addAll(1,3,10,30,60);
//    	daysEC.setValue(30);
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
}
