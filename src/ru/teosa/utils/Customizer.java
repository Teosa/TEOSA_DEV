package ru.teosa.utils;

import javafx.scene.control.ComboBox;
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
	 * @return void
	 * */
	public void CustomizeCB(ComboBox<SimpleComboRecord> combo) {		
		combo.valueProperty().addListener((obs, oldV, newV) -> {
			if(newV != null) mainApp.getDriver().navigate().to(newV.getURL());		
		});
    	
    	
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
	
}
