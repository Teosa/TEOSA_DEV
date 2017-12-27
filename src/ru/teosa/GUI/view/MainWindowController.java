package ru.teosa.GUI.view;

import java.util.List;

import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import ru.teosa.GUI.MainApp;
import ru.teosa.GUI.model.MainWindow;
import ru.teosa.site.model.BreedingFarm;
import ru.teosa.utils.Customizer;
import ru.teosa.utils.objects.SimpleComboRecord;

public class MainWindowController {

	private MainApp mainApp;
	private Customizer customizer = new Customizer();
	
	
	@FXML
	private RadioButton myEC;
	@FXML
	private RadioButton anyEC;
	@FXML
	private ComboBox<Integer> daysEC;
	
	
	@FXML
	private Button startButton;
	@FXML
	private Button stopButton;
	@FXML
	private TreeView<SimpleComboRecord> tree;


    @FXML
    private void initialize() {
//    	customizer.customizeTree(tree);
//    	stopButton.setDisable(true);
//    	
//    	ToggleGroup group = new ToggleGroup();
//    	myEC.setToggleGroup(group);
//    	anyEC.setSelected(true);
//    	anyEC.setToggleGroup(group);
//    	
//    	daysEC.getItems().addAll(1,3,10,30,60);
//    	daysEC.setValue(30);
    }
    
    @FXML
    public void startButtonHandler() { 	
    	
    	BreedingFarm farm;
    	SimpleComboRecord record = tree.getSelectionModel().getSelectedItem().getValue();
    	
    	if(record.getFarmData() == null) {
        	farm = new BreedingFarm();
        	farm.setURL(record.getURL());
        	record.setFarmData(farm);
    	}
    	else farm = record.getFarmData();    	
    	
        new Thread(farm).start();
    }
    
    @FXML
    public void stopButtonHandler() throws Exception{ 	
    	Logger.getLogger("debug").debug("TRY TO INTERRUPT");		
    	BreedingFarm.setRunInterupted(true);
    }
    
    
//******************************************************************************************   
//******************************************************************************************   
//******************************************************************************************     
    /**
     * Инициализация окна с главной формой, кастомизация элементов и загрузка данных.
     * */
    public void initWindow(){
//    	MainWindow.init(mainApp);
//    	customizer.setMainApp(mainApp);
//    	loadInfo();
    }
	
    //Загрузка данных
    private void loadInfo(){
    	loadFarmsTree();
    }
    
    private void loadFarmsTree() {
    	TreeItem<SimpleComboRecord> rootItem0 = new TreeItem<SimpleComboRecord> (new SimpleComboRecord("Farms", "", ""));
    	rootItem0.setExpanded(true);
    	
    	for(int i = 0; i < MainWindow.getFarms().size(); ++i) {
    		SimpleComboRecord record = MainWindow.getFarms().get(i);
            TreeItem<SimpleComboRecord> farm = new TreeItem<SimpleComboRecord> (record);
            
            if(record.getData() != null) {
            	List<SimpleComboRecord> subfarms = (List<SimpleComboRecord>) record.getData();
            	for(int k = 0; k < subfarms.size(); ++k) {
            		farm.getChildren().add(new TreeItem<SimpleComboRecord> (subfarms.get(k)));
            	}
            }
            
            rootItem0.getChildren().add(farm);
    	}
    	
    	tree.setRoot(rootItem0);
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
