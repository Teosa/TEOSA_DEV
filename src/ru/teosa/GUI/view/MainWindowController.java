package ru.teosa.GUI.view;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.teosa.GUI.MainApp;
import ru.teosa.GUI.model.MainWindow;
import ru.teosa.site.model.BreedingFarm;
import ru.teosa.utils.Customizer;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.RedirectingComboRecordExt;
import ru.teosa.utils.objects.SimpleComboRecordExt;

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
	private TreeView<RedirectingComboRecordExt> tree;

	//����
	@FXML
	private MenuItem convertMoney;
	@FXML
	private MenuItem exit;
	
	
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
    
    //�������� ������� �����������
    @FXML
    public void convertMoneyHandler() throws Exception{ 	

    	Stage dialog = new Stage();
    	
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/MoneyConvertor.fxml"));
        loader.getController();  
        
        VBox form = (VBox) loader.load();
        Scene scene = new Scene(form);
       
        dialog.setResizable(false);
        dialog.setScene(scene);
        dialog.setTitle("�����������");
    	dialog.initOwner(mainApp.getPrimaryStage());
    	dialog.initModality(Modality.APPLICATION_MODAL);
    	
    	dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {		
			@Override
			public void handle(WindowEvent event) {
				Logger.getLogger("debug").debug("CONVERSION WIN setOnCloseRequest");	
				if (MainAppHolderSingleton.getMoneyConverterHandler().isRunning()) 		
				{   
					Logger.getLogger("debug").debug("TRY TO STOP THREAD");	
		    		MainAppHolderSingleton.getMoneyConverterHandler().cancel();
		    		MainAppHolderSingleton.getMoneyConverterHandler().reset();
		    	}
			}
		});
    	
    	MainAppHolderSingleton.getMoneyConverterHandler().setConvertorWin(dialog);
    	
    	dialog.showAndWait();
    }
    
    @FXML
    public void startButtonHandler() { 	
    	
    	BreedingFarm farm;
    	RedirectingComboRecordExt record = tree.getSelectionModel().getSelectedItem().getValue();
    	
    	if(record.getFarmData() == null) {
        	farm = new BreedingFarm();
        	farm.setURL(record.getUrl());
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
     * ������������� ���� � ������� ������, ������������ ��������� � �������� ������.
     * */
    public void initWindow(){
    	MainWindow.init(mainApp);
    	loadInfo();
    }
	
    //�������� ������
    private void loadInfo(){
    	
    	//�������� ������ �������
    	loadFarmsTree();
    }
    
    private void loadFarmsTree() {
    	//��������� �������� ����
    	TreeItem<RedirectingComboRecordExt> rootItem0 = new TreeItem<RedirectingComboRecordExt> (new RedirectingComboRecordExt(-1, "Farms", "", null));
    	rootItem0.setExpanded(true);
    	
    	for(int i = 0; i < MainWindow.getFarms().size(); ++i) {
    		RedirectingComboRecordExt record = MainWindow.getFarms().get(i);
            TreeItem<RedirectingComboRecordExt> farm = new TreeItem<RedirectingComboRecordExt> (record);
            
            if(record.getData() != null) {
            	List<RedirectingComboRecordExt> subfarms = (List<RedirectingComboRecordExt>) record.getData();
            	for(int k = 0; k < subfarms.size(); ++k) {
            		farm.getChildren().add(new TreeItem<RedirectingComboRecordExt> (subfarms.get(k)));
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
