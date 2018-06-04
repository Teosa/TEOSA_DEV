package ru.teosa.GUI.view;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import ru.teosa.herdSettings.BaseActionsSettings;
import ru.teosa.herdSettings.SettingTabsInterface;
import ru.teosa.utils.objects.MainAppHolderSingleton;

public class BaseActionTabController extends AbstractController implements SettingTabsInterface<BaseActionsSettings>{
	
	@FXML private CheckBox feed;                    // �������
	@FXML private CheckBox drink;                   // �����
	@FXML private CheckBox stroke;                  // �����
	@FXML private CheckBox groom;                   // �������
	@FXML private CheckBox carrot;                  // �������
	@FXML private CheckBox mash;                    // ���������
	@FXML private CheckBox mission;                 // ������
	@FXML private CheckBox goToSleep;               // ��������� �����
	
	@FXML private CheckBox manualActionsSeqSetting; // ������ ��������� ������� ��������
	@FXML private ListView<String> actionsSeq;      // ������ ������� ��������
	
	@FXML private Button up;
	@FXML private Button down;
	
	// ������� �������� ��-���������
	private static ArrayList<CheckBox> defaultSeq = new ArrayList<CheckBox>();


	@Override
	protected void initialize() {
		MainAppHolderSingleton.getInstance().getMainApp().getController().getProgramWindowController().getHerdRunSettingsController().setBaseActionTabController(this);;
	
		// ��������� ������ ������� �������� ��-���������
		defaultSeq.add(goToSleep);
		defaultSeq.add(groom);
		defaultSeq.add(drink);
		defaultSeq.add(mash);
		defaultSeq.add(mission);
		defaultSeq.add(stroke);
		defaultSeq.add(carrot);
		defaultSeq.add(feed);		
		
		// ������ �������� ����������/�������� �� ������ ��������
		setActionsHandlers();
		
		manualActionsSeqSetting.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) loadDefaultSeq();	
				
				actionsSeq.setDisable(!newValue);
				up.setDisable(!newValue);
				down.setDisable(!newValue);
			}
		});
		
		// ��-��������� �������� ������ � ������ ��������
		actionsSeq.setDisable(true);
		up.setDisable(true);
		down.setDisable(true);
	}

	@Override
	public void customizeContent() {}
	
	@FXML
	private void upButtonHandler() {
		int selectedIndex = actionsSeq.getSelectionModel().getSelectedIndex();
		
		String selectedAction = actionsSeq.getSelectionModel().getSelectedItem();
		String prevIndex = actionsSeq.getItems().get(selectedIndex - 1);
	
		// ������ ������� �������� ������ � ����������
		actionsSeq.getItems().set(selectedIndex - 1, selectedAction);
		actionsSeq.getItems().set(selectedIndex, prevIndex);


		// ������������� ������������ ����� ������ ���������
		actionsSeq.getSelectionModel().select(selectedIndex - 1);
		
		handleMouseClick(null);
	}
	
	@FXML
	private void downButtonHandler() {
		int selectedIndex = actionsSeq.getSelectionModel().getSelectedIndex();
		
		String selectedAction = actionsSeq.getSelectionModel().getSelectedItem();
		String nextAction = actionsSeq.getItems().get(selectedIndex + 1);
	
		// ������ ������� �������� ������ � �����������
		actionsSeq.getItems().set(selectedIndex, nextAction);
		actionsSeq.getItems().set(selectedIndex + 1, selectedAction);

		// ������������� ������������ ���� ������ ���������
		actionsSeq.getSelectionModel().select(selectedIndex + 1);
		
		handleMouseClick(null);
	}

	@FXML 
	// ����������� ������ ����� � ���� � ����������� �� ������� ��������� ������
	private void handleMouseClick(MouseEvent e) {
	    int index = actionsSeq.getSelectionModel().getSelectedIndex();
	    
	    up.setDisable(index < 1);
	    down.setDisable(index == -1 || index == actionsSeq.getItems().size() - 1);
	}
	
	
	@Override
	public void loadSettings() {
		loadSettings(new BaseActionsSettings());	
	}

	@Override
	public void loadSettings(BaseActionsSettings settings) {		
		feed      .setSelected(settings.isFeed());
		drink     .setSelected(settings.isDrink());
		stroke    .setSelected(settings.isStroke());
		groom     .setSelected(settings.isGroom());
		carrot    .setSelected(settings.isCarrot());
		mash      .setSelected(settings.isMash());
		mission   .setSelected(settings.isMission());
		goToSleep .setSelected(settings.isGoToSleep());
		
		manualActionsSeqSetting.setSelected(settings.isManualActionsSeqSetting());
		
		if(manualActionsSeqSetting.isSelected()) {
			actionsSeq.getItems().clear();
			actionsSeq.getItems().addAll(settings.getActionsSeq());
		}
	}
	
	// ���������� ������������ ��� ��������� ��������
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
