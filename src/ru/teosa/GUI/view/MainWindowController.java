package ru.teosa.GUI.view;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import ru.teosa.GUI.MainApp;

public class MainWindowController {

	private MainApp mainApp;

	public MainApp getMainApp() {
		return mainApp;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
    @FXML
    private void initialize() {}

    @FXML
	private ComboBox farms;
	
	
	
}
