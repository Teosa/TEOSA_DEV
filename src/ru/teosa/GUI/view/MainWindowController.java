package ru.teosa.GUI.view;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import ru.teosa.GUI.MainApp;
import ru.teosa.GUI.model.MainWindow;
import ru.teosa.utils.Customizer;
import ru.teosa.utils.objects.SimpleComboRecord;

public class MainWindowController {

	private MainApp mainApp;
	private Customizer customizer = new Customizer();

	public MainApp getMainApp() {
		return mainApp;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	

	@FXML
	private ComboBox<SimpleComboRecord> farms;
	
    @FXML
    private void initialize() {
    	customizer.CustomizeCB(farms);
    }
    
    /**
     * ������������� ���� � ������� ������, ������������ ��������� � �������� ������.
     * */
    public void initWindow(){
    	MainWindow.init(mainApp);
    	customizer.setMainApp(mainApp);
    	loadInfo();
    }
	
    //�������� ������ � ����������
    private void loadInfo(){
    	farms.getItems().addAll(MainWindow.getFarms());
    }
}
