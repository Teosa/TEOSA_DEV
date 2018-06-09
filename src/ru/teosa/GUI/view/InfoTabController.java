package ru.teosa.GUI.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
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
	
	@Override
	public CommonSettings getTabSettings(CommonSettings settings) {
		settings.setBaseActions(baseActions.isSelected());
		settings.setRegisterInEC(ECRegistration.isSelected());
		settings.setExtendEC(ECExtending.isSelected());
		settings.setStallionMating(breedingStallon.isSelected());
		settings.setMareMating(breedingMare.isSelected());
		settings.setFoals(breedingFoal.isSelected());
		
		return settings;
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
				
				ECTab.setDisable(!newValue && !ECExtending.isSelected());
				
				// Доступность полей для настройки регистрации в КСК
				MainAppHolderSingleton.getInstance().getMainApp().getController().getProgramWindowController()
				.getHerdRunSettingsController().getECTabController().setECRegisterBlockDisabled(!newValue);
			}});
	}
	
	private void setECExtendingHandler() {
		ECExtending.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				Tab ECTab = MainWindowController.getProgramWindowController().getHerdRunSettingsController().getECTab();

				ECTab.setDisable(!newValue && !ECRegistration.isSelected());
				
				// Доступность полей для настройки продления постоя в КСК
				MainAppHolderSingleton.getInstance().getMainApp().getController().getProgramWindowController()
				.getHerdRunSettingsController().getECTabController().setECExtendingBlockDisabled(!newValue);
			}});
	}
	
	private void setBreedingStallonHandler() {
		breedingStallon.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				Tab breedingTab = MainWindowController.getProgramWindowController().getHerdRunSettingsController().getBreedingTab();

				breedingTab.setDisable(!newValue && !breedingMare.isSelected() && !breedingFoal.isSelected());	
				
				// Доступность полей для настройки случек жеребцов
				MainAppHolderSingleton.getInstance().getMainApp().getController().getProgramWindowController()
				.getHerdRunSettingsController().getBreedingTabController().setStallonBreedingBlockDisabled(!newValue);
			}});
	}
	
	private void setBreedingMareHandler() {
		breedingMare.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				Tab breedingTab = MainWindowController.getProgramWindowController().getHerdRunSettingsController().getBreedingTab();

				breedingTab.setDisable(!newValue && !breedingStallon.isSelected() && !breedingFoal.isSelected());
				
				// Доступность полей для настройки случек кобыл
				MainAppHolderSingleton.getInstance().getMainApp().getController().getProgramWindowController()
				.getHerdRunSettingsController().getBreedingTabController().setMareBreedingBlockDisabled(!newValue);
			}});
	}
	
	private void setBreedingFoalHandler() {
		breedingFoal.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				Tab breedingTab = MainWindowController.getProgramWindowController().getHerdRunSettingsController().getBreedingTab();

				breedingTab.setDisable(!newValue && !breedingStallon.isSelected() && !breedingMare.isSelected());		
				
				// Доступность полей для настройки приема родов
				MainAppHolderSingleton.getInstance().getMainApp().getController().getProgramWindowController()
				.getHerdRunSettingsController().getBreedingTabController().setFoalsBlockDisabled(!newValue);
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
