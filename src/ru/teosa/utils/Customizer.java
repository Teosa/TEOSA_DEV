package ru.teosa.utils;

import org.apache.log4j.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.StringConverter;
import ru.teosa.GUI.MainApp;
import ru.teosa.GUI.view.FarmProgramPanelController;
import ru.teosa.GUI.view.HerdRunSettingsPaneController;
import ru.teosa.GUI.view.InfoTabController;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.RedirectingComboRecord;
import ru.teosa.utils.objects.RedirectingComboRecordExt;
import ru.teosa.utils.objects.SimpleComboRecord;
import ru.teosa.utils.objects.SimpleComboRecordExt;
import javafx.scene.control.Label;

/**
 * Класс для кастомизации элементов формы.
 * */

public class Customizer{


	private Class genericClass;
	
	public Customizer() {}

	public Customizer(Class genericClass) {
		this.genericClass = genericClass;
	}
	
	
	public void CustomizeCB(ComboBox combo) {	
		if(genericClass != null)Logger.getLogger("debug").debug("CLASS COMPARE: " + genericClass.getSimpleName().equalsIgnoreCase(SimpleComboRecordExt.class.getSimpleName()));
		if(genericClass != null && genericClass.getSimpleName().equalsIgnoreCase(SimpleComboRecordExt.class.getSimpleName())) 
			if(genericClass.getSimpleName().equalsIgnoreCase(SimpleComboRecordExt.class.getSimpleName())) CustomizeCB_SimpleComboRecordExt(combo); 
		else 
			CustomizeCB_SimpleComboRecord(combo);

	}
	
	public void CustomizeCB(ComboBox combo, boolean redirectByURL) {
//		if(genericClassName != null && genericClassName.equalsIgnoreCase("RedirectingComboRecord")) 
//			if(genericClassName.equalsIgnoreCase("RedirectingComboRecordExt")) CustomizeCB_RedirectingComboRecordExt(combo, redirectByURL); 
//		else 
			CustomizeCB_RedirectingComboRecord(combo, redirectByURL);

	}
//**************************************************************************************************************************	
//**************************************************************************************************************************
	/**
	 * Метод для кастомизации комбобокса, содержащего SimpleComboRecord в качестве записей.<br>
	 * Устанавливает для отображения в выпадающем списке поле Name.
	 * @param combo комбобокс для кастомизации
	 * @return void
	 * */
	private void CustomizeCB_SimpleComboRecord(ComboBox<SimpleComboRecord> combo) {				
		combo.setConverter(new StringConverter<SimpleComboRecord>() {
    	    @Override
    	    public String toString(SimpleComboRecord object) {
    	        return object != null ? object.getName() : "";
    	    }

			@Override
			public SimpleComboRecord fromString(String string) {
				//return (SimpleComboRecord)combo.getItems().stream().filter(record -> ((SimpleComboRecord)record).getName().equals(string)).findFirst().orElse(null);
				return new SimpleComboRecord(-1, string);
			}
    	});
    	
	}
	
	/**
	 * Метод для кастомизации комбобокса, содержащего SimpleComboRecordExt в качестве записей.<br>
	 * Устанавливает для отображения в выпадающем списке поле Name.
	 * @param combo комбобокс для кастомизации
	 * @return void
	 * */
	private void CustomizeCB_SimpleComboRecordExt(ComboBox<SimpleComboRecordExt> combo) {				
		combo.setConverter(new StringConverter<SimpleComboRecordExt>() {
    	    @Override
    	    public String toString(SimpleComboRecordExt object) {
    	        return object != null ? object.getName() : "";
    	    }

			@Override
			public SimpleComboRecordExt fromString(String string) {
//				return combo.getItems().stream().filter(record -> record.getName().equals(string)).findFirst().orElse(null);
				return new SimpleComboRecordExt(-1, string, null);
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
	private void CustomizeCB_RedirectingComboRecord(ComboBox<RedirectingComboRecord> combo, boolean URLRedirect) {		
		if(URLRedirect) {
			combo.valueProperty().addListener((obs, oldV, newV) -> {
				if(newV != null) MainAppHolderSingleton.getInstance().getDriver().navigate().to(newV.getUrl());		
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
	
	/**
	 * Метод для кастомизации комбобокса, содержащего RedirectingComboRecord в качестве записей.<br>
	 * Устанавливает для отображения в выпадающем списке поле Name. После выбора записи - переход по ссылке из поля URL.
	 * @param combo комбобокс для кастомизации
	 * @param URLRedirect переход по ссылке при выборе значения в комбобоксе
	 * @return void
	 * */
	private void CustomizeCB_RedirectingComboRecordExt(ComboBox<RedirectingComboRecordExt> combo, boolean URLRedirect) {		
		if(URLRedirect) {
			combo.valueProperty().addListener((obs, oldV, newV) -> {
				if(newV != null) MainAppHolderSingleton.getInstance().getDriver().navigate().to(newV.getUrl());		
			});
		}
  	
		combo.setConverter(new StringConverter<RedirectingComboRecordExt>() {
    	    @Override
    	    public String toString(RedirectingComboRecordExt object) {
    	        return object.getName();
    	    }

			@Override
			public RedirectingComboRecordExt fromString(String string) {
				return combo.getItems().stream().filter(record -> record.getName().equals(string)).findFirst().orElse(null);
			}
    	});   	
	}
	
//	/***/
//	public  void customizeTree(TreeView<RedirectingComboRecordExt> treeView) {
//		 treeView.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<TreeItem<RedirectingComboRecordExt>>() {
//			@Override
//			public void changed(ObservableValue<? extends TreeItem<RedirectingComboRecordExt>> observable,
//					TreeItem<RedirectingComboRecordExt> oldValue, TreeItem<RedirectingComboRecordExt> newValue) {
//
//				FarmProgramPanelController controller = MainApp.getController().getFarmProgramPanelController();
//				
//				if(newValue.getValue().getId() > -1) {
//					controller.getSelectedFarmName().setText(newValue.getValue().getName());	
//
//				}
//				else {
//					controller.getSelectedFarmName().setText("<не выбрано>");
//
//				}
//
//			}
//		   });
//	}
//*********************************************************************************
//*********************************************************************************

	
}
