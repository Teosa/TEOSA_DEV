package ru.teosa.GUI.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import ru.teosa.herdSettings.CommonSettings;
import ru.teosa.herdSettings.SettingTabsInterface;
import ru.teosa.utils.objects.MainAppHolderSingleton;

public class InfoTabController extends AbstractController implements SettingTabsInterface<CommonSettings>{
	
	@FXML private CheckBox baseActions;     // Базовые действия
	@FXML private CheckBox ECRegistration;  // Запись в КСК
	@FXML private CheckBox ECExtending;     // Продление постоя
	@FXML private CheckBox breedingStallon; // Случки (жеребцы)
	@FXML private CheckBox breedingMare;    // Случки (кобылы)
	@FXML private CheckBox breedingFoal;    // Жеребята
	
	
	@Override
	protected void initialize() {
		MainAppHolderSingleton.getInstance().getMainApp().getController().getProgramWindowController().getHerdRunSettingsController().setInfoTabController(this);
	}

	@Override
	public void customizeContent() {
		// Хэндлеры для чекбоксов
		setBaseActionsHandler();
		setECRegistrationHandler();
		setECExtendingHandler();
		setBreedingStallonHandler();
		setBreedingMareHandler();
		setBreedingFoalHandler();	
	}
	
	@Override
	public void loadSettings() {	
		loadSettings(new CommonSettings());
	}
	
	@Override
	public void loadSettings(CommonSettings settings) {
		baseActions.setSelected(true);
		ECRegistration.setSelected(true);
		ECExtending.setSelected(true);
		breedingStallon.setSelected(true);
		breedingMare.setSelected(true);
		breedingFoal.setSelected(true);
		
		baseActions    .setSelected(settings.isBaseActions());
		ECRegistration .setSelected(settings.isRegisterInEC());
		ECExtending    .setSelected(settings.isExtendEC());
		breedingStallon.setSelected(settings.isStallionMating());
		breedingMare   .setSelected(settings.isMareMating());
		breedingFoal   .setSelected(settings.isFoals());
	}
	
	/** Метод снимает все чекбоксы и дисаблит вссе элементы на вкладке "Основные настройки". */
	public void disableTab(boolean disable) {
		if(disable) {
			baseActions    .setSelected(false);
			ECRegistration .setSelected(false);
			ECExtending    .setSelected(false);
			breedingStallon.setSelected(false);
			breedingMare   .setSelected(false);
			breedingFoal   .setSelected(false);			
		}

		baseActions    .setSelected(disable);
		ECRegistration .setDisable(disable);
		ECExtending    .setDisable(disable);
		breedingStallon.setDisable(disable);
		breedingMare   .setDisable(disable);
		breedingFoal   .setDisable(disable);
	}
	
	
	
	private void setBaseActionsHandler() {
		baseActions.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				Tab baseActionsTab = MainWindowController.getProgramWindowController().getHerdRunSettingsController().getBaseActionsTab();				
				baseActionsTab.setDisable(!newValue );
			}});
	}
	
	private void setECRegistrationHandler() {
		ECRegistration.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				Tab ECTab = MainWindowController.getProgramWindowController().getHerdRunSettingsController().getECTab();
				Scene scene = MainAppHolderSingleton.getInstance().getMainApp().getController().getProgramWindowController().getScene();
				
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
				Tab ECTab = MainWindowController.getProgramWindowController().getHerdRunSettingsController().getECTab();
				Scene scene = MainAppHolderSingleton.getInstance().getMainApp().getController().getProgramWindowController().getScene();
				
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
				Tab breedingTab = MainWindowController.getProgramWindowController().getHerdRunSettingsController().getBreedingTab();
				Scene scene = MainAppHolderSingleton.getInstance().getMainApp().getController().getProgramWindowController().getScene();
				
				CheckBox breedingMare = (CheckBox) scene.lookup("#breedingMare");
				CheckBox breedingFoal = (CheckBox) scene.lookup("#breedingFoal");
				breedingTab.setDisable(!newValue && !breedingMare.isSelected() && !breedingFoal.isSelected());	
				
				// Доступность полей для настройки 
				String[] fields = {"matingQty_one", "matingQty_two", "matingQty_three", "maxMatingQty", "matingPrice"};
				for(int i = 0; i < fields.length; ++i) {
					((Node) scene.lookup("#" + fields[i])).setDisable(!newValue);
				}
			}});
	}
	
	private void setBreedingMareHandler() {
		breedingMare.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				Tab breedingTab = MainWindowController.getProgramWindowController().getHerdRunSettingsController().getBreedingTab();
				Scene scene = MainAppHolderSingleton.getInstance().getMainApp().getController().getProgramWindowController().getScene();
				
				CheckBox breedingStallon = (CheckBox) scene.lookup("#breedingStallon");
				CheckBox breedingFoal = (CheckBox) scene.lookup("#breedingFoal");
				breedingTab.setDisable(!newValue && !breedingStallon.isSelected() && !breedingFoal.isSelected());
				
				// Доступность полей для настройки 
				String[] fields = {  
						"coverBy_owner", "coverBy_any", "maxCoverPrice", "stallonBreed_likeMare", "stallonBreed_any", 
						"stallonGP_likeMare", "stallonGP_custom", "stallonGP_any", "minStallonGP", "maxStallonGP"
						};
				for(int i = 0; i < fields.length; ++i) {
					((Node) scene.lookup("#" + fields[i])).setDisable(!newValue);
				}
			}});
	}
	
	private void setBreedingFoalHandler() {
		breedingFoal.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				Tab breedingTab = MainWindowController.getProgramWindowController().getHerdRunSettingsController().getBreedingTab();
				Scene scene = MainAppHolderSingleton.getInstance().getMainApp().getController().getProgramWindowController().getScene();
				
				CheckBox breedingStallon = (CheckBox) scene.lookup("#breedingStallon");
				CheckBox breedingMare = (CheckBox) scene.lookup("#breedingMare");
				breedingTab.setDisable(!newValue && !breedingStallon.isSelected() && !breedingMare.isSelected());		
				
				// Доступность полей для настройки 
				String[] fields = {"stallonNames", "mareNames", "foalsAffix", "foalsFarm"};
				for(int i = 0; i < fields.length; ++i) {
					((Node) scene.lookup("#" + fields[i])).setDisable(!newValue);
				}
			}});
	}
//*****************************************************************************************************************************
//*****************************************************************************************************************************
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
