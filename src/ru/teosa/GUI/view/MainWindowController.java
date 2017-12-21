package ru.teosa.GUI.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import ru.teosa.GUI.MainApp;
import ru.teosa.GUI.model.MainWindow;

public class MainWindowController {

	private MainApp mainApp;

	public MainApp getMainApp() {
		return mainApp;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	

    @FXML
	private ComboBox farms;
	
    @FXML
    private void initialize() {
    	farms.valueProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				System.out.println(observable);
				System.out.println(oldValue);
				System.out.println(newValue);			
			}
		});
    }
    
    public void initWindow(){
    	MainWindow.init(mainApp);
    	loadInfo();
    }
	
    private void loadInfo(){
    	farms.getItems().addAll(MainWindow.getFarms());
    }
}
