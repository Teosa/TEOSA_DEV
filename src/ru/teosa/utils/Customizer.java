package ru.teosa.utils;

import org.apache.log4j.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.StringConverter;
import ru.teosa.GUI.MainApp;
import ru.teosa.utils.objects.RedirectingComboRecord;
import ru.teosa.utils.objects.RedirectingComboRecordExt;
import ru.teosa.utils.objects.SimpleComboRecordExt;

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
	public void CustomizeCB(ComboBox<SimpleComboRecordExt> combo) {				
		combo.setConverter(new StringConverter<SimpleComboRecordExt>() {
    	    @Override
    	    public String toString(SimpleComboRecordExt object) {
    	        return object.getName();
    	    }

			@Override
			public SimpleComboRecordExt fromString(String string) {
				return combo.getItems().stream().filter(record -> record.getName().equals(string)).findFirst().orElse(null);
			}
    	});
    	
	}
	
	
	/**
	 * Метод для кастомизации комбобокса, содержащего RedirectingComboRecord в качестве записей.<br>
	 * Устанавливает для отображения в выпадающем списке поле Name. После выбора записи - переход по ссылке из поля URL.
	 * @param combo комбобокс для кастомизации
	 * @param URLRedirect переход по ссылке при выборе значения в комбобоксе
	 * @return void
	 * */
	public void CustomizeCB(ComboBox<RedirectingComboRecord> combo, boolean URLRedirect) {		
		if(URLRedirect) {
			combo.valueProperty().addListener((obs, oldV, newV) -> {
				if(newV != null) mainApp.getDriver().navigate().to(newV.getURL());		
			});
		}
  	
		combo.setConverter(new StringConverter<RedirectingComboRecord>() {
    	    @Override
    	    public String toString(RedirectingComboRecord object) {
    	        return object.getName();
    	    }

			@Override
			public RedirectingComboRecord fromString(String string) {
				return combo.getItems().stream().filter(record -> record.getName().equals(string)).findFirst().orElse(null);
			}
    	});   	
	}
	
	public void customizeTree(TreeView<RedirectingComboRecordExt> treeView) {
		 treeView.getSelectionModel().selectedItemProperty().addListener( new ChangeListener() {
				@Override
				public void changed(ObservableValue observable, Object oldValue, Object newValue) {
		            TreeItem<RedirectingComboRecordExt> selectedItem = (TreeItem<RedirectingComboRecordExt>) newValue;
		            RedirectingComboRecordExt value = selectedItem.getValue();
		            
		            Logger.getLogger("debug").debug("SELECTED NAME: " + value.getName());
		            Logger.getLogger("debug").debug("SELECTED URL: " + value.getURL());
		            Logger.getLogger("debug").debug("SELECTED DATA: " + value.getData() != null);
		            
				}
		      });
	}
	
}
