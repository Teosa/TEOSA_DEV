package ru.teosa.GUI.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import ru.teosa.GUI.MainApp;
import ru.teosa.GUI.model.MainWindow;
import ru.teosa.site.model.BreedingFarm;
import ru.teosa.utils.Customizer;
import ru.teosa.utils.objects.SimpleComboRecord;

public class MainWindowController {

	private MainApp mainApp;
	private Customizer customizer = new Customizer();
	
	@FXML
	private ComboBox<SimpleComboRecord> farms;
	@FXML
	private Button startButton;


    @FXML
    private void initialize() {
    	customizer.CustomizeCB(farms);
    }
    
    @FXML
    public void startButtonHandler() {
    	farms.getValue().getURL();
    	new BreedingFarm().herdRun(); 
    }
//******************************************************************************************   
//******************************************************************************************   
//******************************************************************************************     
    /**
     * Инициализация окна с главной формой, кастомизация элементов и загрузка данных.
     * */
    public void initWindow(){
    	MainWindow.init(mainApp);
    	customizer.setMainApp(mainApp);
    	loadInfo();
    }
	
    //Загрузка данных в комбобоксы
    private void loadInfo(){
    	farms.getItems().addAll(MainWindow.getFarms());
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
