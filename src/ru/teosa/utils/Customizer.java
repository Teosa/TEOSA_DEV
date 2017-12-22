package ru.teosa.utils;

import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import ru.teosa.GUI.MainApp;
import ru.teosa.utils.objects.SimpleComboRecord;

/**
 * Класс для кастомизации элементов формы.
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
	 * Метод для кастомизации комбобокса, содержащего SimpleComboRecord в качестве записей.<br>
	 * Устанавливает для отображения в выпадающем списке поле Name. После выбора записи - переход по ссылке из поля URL.
	 * @param combo комбобокс для кастомизации
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
