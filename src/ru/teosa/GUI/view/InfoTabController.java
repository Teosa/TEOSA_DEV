package ru.teosa.GUI.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.SimpleComboRecordExt;

public class InfoTabController extends AbstractController{
	
	@FXML private ComboBox<SimpleComboRecordExt> programs; // Программы
	@FXML private Label selectedFarmName;   // Выбранный завод
	@FXML private Button addProgramToRun;   // Добавить в прогон
    
	@FXML private CheckBox ECRegistration;  // Запись в КСК
	@FXML private CheckBox ECExtending;     // Продление постоя
	@FXML private CheckBox breedingStallon; // Случки (жеребцы)
	@FXML private CheckBox breedingMare;    // Случки (кобылы)
	@FXML private CheckBox breedingFoal;    // Жеребята
	
	
	@Override
	protected void initialize() {
		MainAppHolderSingleton.getInstance().getMainApp().getController().getHerdRunSettingsTabController().setInfoTabController(this);
	}

	@Override
	public void customizeContent() {
		// Хэндлеры для чекбоксов
		setECRegistrationHandler();
		setECExtendingHandler();
		setBreedingStallonHandler();
		setBreedingMareHandler();
		setBreedingFoalHandler();	
	}
	
	/** Метод снимает все чекбоксы и дисаблит вссе элементы на вкладке "Основные настройки". */
	public void disableTab(boolean disable) {
		if(disable) {
			ECRegistration .setSelected(false);
			ECExtending    .setSelected(false);
			breedingStallon.setSelected(false);
			breedingMare   .setSelected(false);
			breedingFoal   .setSelected(false);			
		}

		ECRegistration .setDisable(disable);
		ECExtending    .setDisable(disable);
		breedingStallon.setDisable(disable);
		breedingMare   .setDisable(disable);
		breedingFoal   .setDisable(disable);
		
	}
	
	private void setECRegistrationHandler() {
		ECRegistration.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				Tab ECTab = MainWindowController.getHerdRunSettingsTabController().getECTab();
				Scene scene = MainAppHolderSingleton.getInstance().getMainApp().getPrimaryStage().getScene();
				
				CheckBox ECExt = (CheckBox) scene.lookup("#ECExtending");
				ECTab.setDisable(!newValue && !ECExt.isSelected());
				
				// Доступность полей для настройки регистрации в КСК
				String[] fields = {
						"ECType_owner", "ECType_reserved", "ECType_any",
						"registrationTerm", "registrationTermLabel",
						"location_forest", "location_mountains", "location_beach", "location_any",
						"specialization_classic", "specialization_western", "specialization_any",
						"hay", "oat", "carrot", "mash", "drinker", "shower"
				};
				
				for(int i = 0; i < fields.length; ++i) {
					((Node) scene.lookup("#" + fields[i])).setDisable(!newValue);
				}
			}});
	}
	
	private void setECExtendingHandler() {
		ECExtending.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				Tab ECTab = MainWindowController.getHerdRunSettingsTabController().getECTab();
				Scene scene = MainAppHolderSingleton.getInstance().getMainApp().getPrimaryStage().getScene();
				
				CheckBox ECReg = (CheckBox) scene.lookup("#ECRegistration");
				ECTab.setDisable(!newValue && !ECReg.isSelected());
				
				// Доступность полей для настройки продления постоя в КСК
				String[] fields = {"daysBeforeExtend", "extendTerm", "extendTermLabel", "onlyOwnerExtend"};
				
				for(int i = 0; i < fields.length; ++i) {
					((Node) scene.lookup("#" + fields[i])).setDisable(!newValue);
				}
			}});
	}
	
	private void setBreedingStallonHandler() {
		breedingStallon.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				Tab breedingTab = MainWindowController.getHerdRunSettingsTabController().getBreedingTab();
				Scene scene = MainAppHolderSingleton.getInstance().getMainApp().getPrimaryStage().getScene();
				
				CheckBox breedingMare = (CheckBox) scene.lookup("#breedingMare");
				CheckBox breedingFoal = (CheckBox) scene.lookup("#breedingFoal");
				breedingTab.setDisable(!newValue && !breedingMare.isSelected() && !breedingFoal.isSelected());								
			}});
	}
	
	private void setBreedingMareHandler() {
		breedingMare.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				Tab breedingTab = MainWindowController.getHerdRunSettingsTabController().getBreedingTab();
				Scene scene = MainAppHolderSingleton.getInstance().getMainApp().getPrimaryStage().getScene();
				
				CheckBox breedingStallon = (CheckBox) scene.lookup("#breedingStallon");
				CheckBox breedingFoal = (CheckBox) scene.lookup("#breedingFoal");
				breedingTab.setDisable(!newValue && !breedingStallon.isSelected() && !breedingFoal.isSelected());
			}});
	}
	
	private void setBreedingFoalHandler() {
		breedingFoal.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				Tab breedingTab = MainWindowController.getHerdRunSettingsTabController().getBreedingTab();
				Scene scene = MainAppHolderSingleton.getInstance().getMainApp().getPrimaryStage().getScene();
				
				CheckBox breedingStallon = (CheckBox) scene.lookup("#breedingStallon");
				CheckBox breedingMare = (CheckBox) scene.lookup("#breedingMare");
				breedingTab.setDisable(!newValue && !breedingStallon.isSelected() && !breedingMare.isSelected());				
			}});
	}
//*****************************************************************************************************************************
//*****************************************************************************************************************************
	public Label getSelectedFarmName() {
		return selectedFarmName;
	}
	public void setSelectedFarmName(Label selectedFarmName) {
		this.selectedFarmName = selectedFarmName;
	}
	public CheckBox getECRegistration() {
		return ECRegistration;
	}
	public void setECRegistration(CheckBox eCRegistration) {
		ECRegistration = eCRegistration;
	}
	public CheckBox getECExtending() {
		return ECExtending;
	}
	public void setECExtending(CheckBox eCExtending) {
		ECExtending = eCExtending;
	}
	public CheckBox getBreedingStallon() {
		return breedingStallon;
	}
	public void setBreedingStallon(CheckBox breedingStallon) {
		this.breedingStallon = breedingStallon;
	}
	public CheckBox getBreedingMare() {
		return breedingMare;
	}
	public void setBreedingMare(CheckBox breedingMare) {
		this.breedingMare = breedingMare;
	}
	public CheckBox getBreedingFoal() {
		return breedingFoal;
	}
	public void setBreedingFoal(CheckBox breedingFoal) {
		this.breedingFoal = breedingFoal;
	}
}
