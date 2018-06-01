package ru.teosa.GUI.view;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import ru.teosa.utils.objects.MainAppHolderSingleton;

public class BaseActionTabController extends AbstractController{
	
	@FXML private CheckBox feed;                    // Кормить
	@FXML private CheckBox drink;                   // Поить
	@FXML private CheckBox stroke;                  // Ласка
	@FXML private CheckBox groom;                   // Чистить
	@FXML private CheckBox carrot;                  // Морковь
	@FXML private CheckBox mash;                    // Комбикорм
	@FXML private CheckBox mission;                 // Миссия
	@FXML private CheckBox goToSleep;               // Отправить спать
	
	@FXML private CheckBox manualActionsSeqSetting; // Ручная настройка порядка действий
	@FXML private ListView<String> actionsSeq;      // Список порядка действий
	
	@FXML private Button up;
	@FXML private Button down;
	
	// Порядок действий по-умолчанию
	private static ArrayList<CheckBox> defaultSeq = new ArrayList<CheckBox>();


	@Override
	protected void initialize() {
		MainAppHolderSingleton.getInstance().getMainApp().getController().getProgramWindowController().getHerdRunSettingsController().setBaseActionTabController(this);;
	
		// Заполняем массив порядка действий по-умолчанию
		defaultSeq.add(goToSleep);
		defaultSeq.add(groom);
		defaultSeq.add(drink);
		defaultSeq.add(mash);
		defaultSeq.add(mission);
		defaultSeq.add(stroke);
		defaultSeq.add(carrot);
		defaultSeq.add(feed);		
		
		// Вешаем хендлеры добавления/удаления из списка действий
		setActionsHandlers();
				
		manualActionsSeqSetting.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) loadDefaultSeq();	
			}
		});
	}

	@Override
	public void customizeContent() {}
	
	@FXML
	private void upButtonHandler() {
		String selectedAction = actionsSeq.getSelectionModel().getSelectedItem();
		
		System.out.println(selectedAction);
		if(selectedAction != null) {}
	}
	
	@FXML
	private void downButtonHandler() {
		String selectedAction = actionsSeq.getSelectionModel().getSelectedItem();
		
		System.out.println(selectedAction);
		if(selectedAction != null) {}
	}

	private void setActionsHandlers() {
		feed.selectedProperty().addListener(setListener(feed.getText())); 
		drink.selectedProperty().addListener(setListener(drink.getText())); 
		stroke.selectedProperty().addListener(setListener(stroke.getText())); 
		groom.selectedProperty().addListener(setListener(groom.getText())); 
		carrot.selectedProperty().addListener(setListener(carrot.getText())); 
		mash.selectedProperty().addListener(setListener(mash.getText())); 
		mission.selectedProperty().addListener(setListener(mission.getText())); 
		goToSleep.selectedProperty().addListener(setListener(goToSleep.getText())); 
	}
	
	
	private ChangeListener<Boolean> setListener(String text){
		return new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(manualActionsSeqSetting.isSelected()){
					if(newValue) actionsSeq.getItems().add(text);
					else actionsSeq.getItems().remove(text);
				}
				else loadDefaultSeq();
			}};
	}

	private void loadDefaultSeq() {
		actionsSeq.getItems().clear();
		for(CheckBox cb : defaultSeq) {
			if(cb.isSelected()) actionsSeq.getItems().add(cb.getText());
		}
	}
//***************************************************************************************************************************************	
//***************************************************************************************************************************************	
	public CheckBox getFeed() {
		return feed;
	}
	public void setFeed(CheckBox feed) {
		this.feed = feed;
	}
	public CheckBox getDrink() {
		return drink;
	}
	public void setDrink(CheckBox drink) {
		this.drink = drink;
	}
	public CheckBox getStroke() {
		return stroke;
	}
	public void setStroke(CheckBox stroke) {
		this.stroke = stroke;
	}
	public CheckBox getGroom() {
		return groom;
	}
	public void setGroom(CheckBox groom) {
		this.groom = groom;
	}
	public CheckBox getCarrot() {
		return carrot;
	}
	public void setCarrot(CheckBox carrot) {
		this.carrot = carrot;
	}
	public CheckBox getMash() {
		return mash;
	}
	public void setMash(CheckBox mash) {
		this.mash = mash;
	}
	public CheckBox getMission() {
		return mission;
	}
	public void setMission(CheckBox mission) {
		this.mission = mission;
	}
	public CheckBox getGoToSleep() {
		return goToSleep;
	}
	public void setGoToSleep(CheckBox goToSleep) {
		this.goToSleep = goToSleep;
	}
	public ListView<String> getActionsSeq() {
		return actionsSeq;
	}
	public void setActionsSeq(ListView<String> actionsSeq) {
		this.actionsSeq = actionsSeq;
	}
	public CheckBox getManualActionsSeqSetting() {
		return manualActionsSeqSetting;
	}
	public void setManualActionsSeqSetting(CheckBox manualActionsSeqSetting) {
		this.manualActionsSeqSetting = manualActionsSeqSetting;
	}
}
