package ru.teosa.utils;

import org.apache.log4j.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.StringConverter;
import ru.teosa.GUI.MainApp;
import ru.teosa.utils.objects.SimpleComboRecord;

/**
 * ����� ��� ������������ ��������� �����.
 * */

public class Customizer{

	private MainApp mainApp;
	
	public MainApp getMainApp() {
		return mainApp;
	}
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}


	/**
	 * ����� ��� ������������ ����������, ����������� SimpleComboRecord � �������� �������.<br>
	 * ������������� ��� ����������� � ���������� ������ ���� Name. ����� ������ ������ - ������� �� ������ �� ���� URL.
	 * @param combo ��������� ��� ������������
	 * @param URLRedirect ������� �� ������ ��� ������ �������� � ����������
	 * @return void
	 * */
	public void CustomizeCB(ComboBox<SimpleComboRecord> combo, boolean URLRedirect) {		
		if(URLRedirect) {
			combo.valueProperty().addListener((obs, oldV, newV) -> {
				if(newV != null) mainApp.getDriver().navigate().to(newV.getURL());		
			});
		}
  	
		combo.setConverter(new StringConverter<SimpleComboRecord>() {
    	    @Override
    	    public String toString(SimpleComboRecord object) {
    	        return object.getName();
    	    }

			@Override
			public SimpleComboRecord fromString(String string) {
				return combo.getItems().stream().filter(record -> record.getName().equals(string)).findFirst().orElse(null);
			}
    	});
    	
	}
	
	public void customizeTree(TreeView<SimpleComboRecord> treeView) {
		 treeView.getSelectionModel().selectedItemProperty().addListener( new ChangeListener() {
				@Override
				public void changed(ObservableValue observable, Object oldValue, Object newValue) {
		            TreeItem<SimpleComboRecord> selectedItem = (TreeItem<SimpleComboRecord>) newValue;
		            SimpleComboRecord value = selectedItem.getValue();
		            
		            Logger.getLogger("debug").debug("SELECTED NAME: " + value.getName());
		            Logger.getLogger("debug").debug("SELECTED URL: " + value.getURL());
		            Logger.getLogger("debug").debug("SELECTED DATA: " + value.getData() != null);
		            
				}
		      });
	}
	
}
