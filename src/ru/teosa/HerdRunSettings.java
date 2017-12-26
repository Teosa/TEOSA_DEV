package ru.teosa;

import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TreeView;
import ru.teosa.GUI.MainApp;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.SimpleComboRecord;

public class HerdRunSettings {

	private static MainApp mainApp;
	
	private String name;
	private String URL;
	
	private int ECtoWrite = 1;
	private int days = 30;
	
	
	public HerdRunSettings() {
		if(mainApp == null) mainApp = MainAppHolderSingleton.getInstance().getMainApp();
		Scene scene = mainApp.getPrimaryStage().getScene();
		
		setName(((TreeView<SimpleComboRecord>) scene.lookup("#tree")).getSelectionModel().getSelectedItem().getValue().getName());
		setURL(((TreeView<SimpleComboRecord>) scene.lookup("#tree")).getSelectionModel().getSelectedItem().getValue().getURL());
		
		setECtoWrite(((RadioButton) scene.lookup("#myEC")).isSelected() ? 0 : 1);
		setDays(((ComboBox<Integer>) scene.lookup("#daysEC")).getValue());
	}
	
	
	
	
//*******************************************************************************************************************************
//*******************************************************************************************************************************
//*******************************************************************************************************************************
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public int getECtoWrite() {
		return ECtoWrite;
	}
	public void setECtoWrite(int eCtoWrite) {
		ECtoWrite = eCtoWrite;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
}
