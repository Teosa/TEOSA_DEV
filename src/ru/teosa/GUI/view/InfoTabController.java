package ru.teosa.GUI.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import ru.teosa.utils.objects.MainAppHolderSingleton;

public class InfoTabController extends AbstractController{

	
	@FXML private Label selectedFarmName;
	
	@FXML private CheckBox ECRegistration;
	@FXML private CheckBox ECExtending;
	@FXML private CheckBox breedingStallon;
	@FXML private CheckBox breedingMare;
	@FXML private CheckBox breedingFoal;
	
	
	
	
	
	
	
	@Override
	protected void initialize() {
		MainAppHolderSingleton.getInstance().getMainApp().getController().getHerdRunSettingsTabController().setInfoTabController(this);
	}

	@Override
	public void customizeContent() {
		//Хэндлер для признака регистрации в КСК
		ECRegistration.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				Tab ECTab = MainWindowController.getHerdRunSettingsTabController().getECTab();
				Scene scene = MainAppHolderSingleton.getInstance().getMainApp().getPrimaryStage().getScene();
				
				CheckBox ECExt = (CheckBox) scene.lookup("#ECExtending");
				ECTab.setDisable(!newValue && !ECExt.isSelected());
				
				
				
				
			}});
		
		//Хэндлер для признака продления постоя в КСК
		ECExtending.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				Tab ECTab = MainWindowController.getHerdRunSettingsTabController().getECTab();
				Scene scene = MainAppHolderSingleton.getInstance().getMainApp().getPrimaryStage().getScene();
				
				CheckBox ECReg = (CheckBox) scene.lookup("#ECRegistration");
				ECTab.setDisable(!newValue && !ECReg.isSelected());
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
