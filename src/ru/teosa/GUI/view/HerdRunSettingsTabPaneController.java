package ru.teosa.GUI.view;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import ru.teosa.GUI.MainApp;

public class HerdRunSettingsTabPaneController {

	
	
	@FXML
	private TabPane tabPane;
	
	@FXML
	private static Tab mainTab;
	@FXML
	private static Tab ECTab;
	@FXML
	private static Tab breedingTab;
	
	
	
    @FXML
    private void initialize() {
    	try {
    	   	loadTabsIntoForm();
    	}
    	catch(Exception e) {
    		Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
    	}
    }
	
    // Загрузка вкладок в панель
    private void loadTabsIntoForm() throws Exception{
        FXMLLoader loader = new FXMLLoader();

        //Основная информация
        loader.setLocation(MainApp.class.getResource("view/InfoTab.fxml"));
        AnchorPane infoTab = (AnchorPane) loader.load();
        ((ScrollPane)tabPane.getTabs().get(0).getContent()).setContent(infoTab);

        //КСК
        loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/ECTab.fxml"));
        AnchorPane ECTab = (AnchorPane) loader.load();
        ((ScrollPane)tabPane.getTabs().get(1).getContent()).setContent(ECTab);
             
        //Разведение
        loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/BreedingTab.fxml"));
        AnchorPane breedingTab = (AnchorPane) loader.load();
        ((ScrollPane)tabPane.getTabs().get(2).getContent()).setContent(breedingTab);
    }
    
//*****************************************************************************************************************  
//*****************************************************************************************************************  
	public static Tab getMainTab() {
		return mainTab;
	}
	public static Tab getECTab() {
		return ECTab;
	}
	public static Tab getBreedingTab() {
		return breedingTab;
	}    
}
