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
import ru.teosa.herdSettings.HerdRunSettings;
import ru.teosa.herdSettings.SettingTabsInterface;
import ru.teosa.utils.objects.MainAppHolderSingleton;

/** ������ ���������; ������� �������� ��������� */
public class HerdRunSettingsPaneController extends AbstractController implements SettingTabsInterface<HerdRunSettings>{

	@FXML private TabPane tabPane;     // ������ �������
	@FXML private Tab mainInfoTab;     // ������� �������� ��������
	@FXML private Tab baseActionsTab;  // ������� ������� ��������
	@FXML private Tab ECTab;           // ������� ���
	@FXML private Tab breedingTab;     // ������� ����������
	
	private static InfoTabController       infoTabController;       // ���������� ������� �������� ��������
	private static BaseActionTabController baseActionTabController; // ���������� ������� ������� ��������
	private static ECTabController         ECTabController;         // ���������� ������� ���
	private static BreedingTabController   breedingTabController;   // ���������� ������� ����������
	
	
    @FXML
    protected void initialize() {
    	try {
    		MainAppHolderSingleton.getInstance().getMainApp().getController().getProgramWindowController().setHerdRunSettingsController(this);
    		
        	loadTabsIntoForm();
        	   	
        	infoTabController.customizeContent();
        	ECTabController.customizeContent();
        	breedingTabController.customizeContent();	
    		
        	loadSettings();
    	}
    	catch(Exception e) {
    		Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
    	}
    }
	
	@Override
	public void customizeContent() {}
	
	@Override
	public void loadSettings() {
		loadSettings(new HerdRunSettings());
	}

	@Override
	public void loadSettings(HerdRunSettings settings) 
	{
		infoTabController.loadSettings(settings.getCommonSettings());
		baseActionTabController.loadSettings(settings.getBaseActionsSettings());
		ECTabController.loadSettings(settings.getEC_Settings());
		breedingTabController.loadSettings(settings.getBreedingSettings());	
	}
	
	@Override
	public HerdRunSettings getTabSettings(HerdRunSettings settings) 
	{	
		infoTabController.getTabSettings(settings.getCommonSettings());
		baseActionTabController.getTabSettings(settings.getBaseActionsSettings());
		ECTabController.getTabSettings(settings.getEC_Settings());
		breedingTabController.getTabSettings(settings.getBreedingSettings());

		return settings;
	}
    
    // �������� ������� � ������
    private void loadTabsIntoForm() throws Exception{
        FXMLLoader loader = new FXMLLoader();

        //�������� ����������
        loader.setLocation(MainApp.class.getResource("view/InfoTab.fxml"));
        AnchorPane infoTab = (AnchorPane) loader.load();
        ((ScrollPane)tabPane.getTabs().get(0).getContent()).setContent(infoTab);
        
        //������� ��������
        loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/BaseActionsTab.fxml"));
        AnchorPane baseActionTab = (AnchorPane) loader.load();
        ((ScrollPane)tabPane.getTabs().get(1).getContent()).setContent(baseActionTab);

        //���
        loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/ECTab.fxml"));
        AnchorPane ECTab = (AnchorPane) loader.load();
        ((ScrollPane)tabPane.getTabs().get(2).getContent()).setContent(ECTab);
             
        //����������
        loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/BreedingTab.fxml"));
        AnchorPane breedingTab = (AnchorPane) loader.load();
        ((ScrollPane)tabPane.getTabs().get(3).getContent()).setContent(breedingTab);
    }
    
//*****************************************************************************************************************  
//*****************************************************************************************************************  
	public static InfoTabController getInfoTabController() {
		return infoTabController;
	}
	public static void setInfoTabController(InfoTabController infoTabController) {
		HerdRunSettingsPaneController.infoTabController = infoTabController;
	}
	public TabPane getTabPane() {
		return tabPane;
	}
	public void setTabPane(TabPane tabPane) {
		this.tabPane = tabPane;
	}
	public Tab getMainInfoTab() {
		return mainInfoTab;
	}
	public void setMainInfoTab(Tab mainInfoTab) {
		this.mainInfoTab = mainInfoTab;
	}
	public Tab getECTab() {
		return ECTab;
	}
	public void setECTab(Tab eCTab) {
		ECTab = eCTab;
	}
	public Tab getBreedingTab() {
		return breedingTab;
	}
	public void setBreedingTab(Tab breedingTab) {
		this.breedingTab = breedingTab;
	}
	public static ECTabController getECTabController() {
		return ECTabController;
	}
	public static void setECTabController(ECTabController eCTabController) {
		ECTabController = eCTabController;
	}
	public static BreedingTabController getBreedingTabController() {
		return breedingTabController;
	}
	public static void setBreedingTabController(BreedingTabController breedingTabController) {
		HerdRunSettingsPaneController.breedingTabController = breedingTabController;
	}
	public Tab getBaseActionsTab() {
		return baseActionsTab;
	}
	public void setBaseActionsTab(Tab baseActionsTab) {
		this.baseActionsTab = baseActionsTab;
	}
	public static BaseActionTabController getBaseActionTabController() {
		return baseActionTabController;
	}
	public static void setBaseActionTabController(BaseActionTabController baseActionTabController) {
		HerdRunSettingsPaneController.baseActionTabController = baseActionTabController;
	}	
}
