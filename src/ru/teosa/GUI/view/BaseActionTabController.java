package ru.teosa.GUI.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import ru.teosa.herdSettings.BaseActionsSettings;
import ru.teosa.herdSettings.SettingTabsInterface;
import ru.teosa.utils.objects.MainAppHolderSingleton;

public class BaseActionTabController extends AbstractController implements SettingTabsInterface<BaseActionsSettings>{
	
	@FXML private CheckBox feed;       private static final char FEED_CHAR    = 'F';          // �������
	@FXML private CheckBox drink;      private static final char DRINK_CHAR   = 'D';          // �����
	@FXML private CheckBox stroke;     private static final char STROKE_CHAR  = 'S';          // �����
	@FXML private CheckBox groom;      private static final char GROOM_CHAR   = 'G';          // �������
	@FXML private CheckBox carrot;     private static final char CARROT_CHAR  = 'C';          // �������
	@FXML private CheckBox mash;       private static final char MASH_CHAR    = 'M';          // ���������
	@FXML private CheckBox mission;    private static final char MISSION_CHAR = 'A';          // ������
	@FXML private CheckBox goToSleep;  private static final char SLEEP_CHAR   = 'B';          // ��������� �����
	
	@FXML private CheckBox manualActionsSeqSetting; // ������ ��������� ������� ��������
	@FXML private ListView<String> actionsSeq;      // ������ ������� ��������
	
	@FXML private Button up;
	@FXML private Button down;
	
	// ������� �������� ��-���������
	private static ArrayList<Character> defaultSeq = new ArrayList<Character>();
	
	// ������ ������� ��������
	private static ArrayList<Character> customSeq = new ArrayList<Character>();
	
	// ������ �������� � �������� ��������
	private static HashMap<Character, String> actionsValuesMapping = new HashMap<Character, String>();

	static {
		defaultSeq.add(SLEEP_CHAR); 
		defaultSeq.add(GROOM_CHAR); 
		defaultSeq.add(DRINK_CHAR); 
		defaultSeq.add(MASH_CHAR); 
		defaultSeq.add(MISSION_CHAR); 
		defaultSeq.add(STROKE_CHAR); 
		defaultSeq.add(CARROT_CHAR); 
		defaultSeq.add(FEED_CHAR); 
		
		actionsValuesMapping.put(FEED_CHAR,    "�������");
		actionsValuesMapping.put(DRINK_CHAR,   "�����");
		actionsValuesMapping.put(STROKE_CHAR,  "�����");
		actionsValuesMapping.put(GROOM_CHAR,   "�������");
		actionsValuesMapping.put(CARROT_CHAR,  "�������");
		actionsValuesMapping.put(MASH_CHAR,    "���������");
		actionsValuesMapping.put(MISSION_CHAR, "������");
		actionsValuesMapping.put(SLEEP_CHAR,   "��������� �����");
	}
	
	@Override
	protected void initialize() {
		MainAppHolderSingleton.getInstance().getMainApp().getController().getProgramWindowController().getHerdRunSettingsController().setBaseActionTabController(this);;
		
		feed     .setUserData(FEED_CHAR);
		drink    .setUserData(DRINK_CHAR);
		stroke   .setUserData(STROKE_CHAR);
		groom    .setUserData(GROOM_CHAR);
		carrot   .setUserData(CARROT_CHAR);
		mash     .setUserData(MASH_CHAR);
		mission  .setUserData(MISSION_CHAR);
		goToSleep.setUserData(SLEEP_CHAR);
		
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
			loadCustomActionsSeq(settings.getActionsSeq());
		}
	}
	
	@Override
	public BaseActionsSettings getTabSettings(BaseActionsSettings settings) {
		settings.setFeed(feed.isSelected());
		settings.setDrink(drink.isSelected());
		settings.setStroke(stroke.isSelected());
		settings.setGroom(groom.isSelected());
		settings.setCarrot(carrot.isSelected());
		settings.setMash(mash.isSelected());
		settings.setMission(mission.isSelected());
		settings.setGoToSleep(goToSleep.isSelected());
		settings.setManualActionsSeqSetting(manualActionsSeqSetting.isSelected());		
		
		collectActionsValues();
		settings.setActionsSeq(customSeq);
		
		return settings;
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
		CheckBox cb = null;
		
		for(Character value : defaultSeq) {
			cb = getCheckboxByValue(value);
			if(cb.isSelected()) actionsSeq.getItems().add(cb.getText());
		}
	}
	
	private CheckBox getCheckboxByValue(Character value) 
	{	
		switch(value) 
		{	
		  case FEED_CHAR:    return feed;   
		  case DRINK_CHAR:   return drink;
		  case STROKE_CHAR:  return stroke;
		  case GROOM_CHAR:   return groom;
		  case CARROT_CHAR:  return carrot;
		  case MASH_CHAR:    return mash;
		  case MISSION_CHAR: return mission;
		  case SLEEP_CHAR:   return goToSleep;
		  default:           return null;
		}
	}
	
	private void loadCustomActionsSeq(List<Character> customSeq) {
		for(Character actionChar : customSeq) {
			actionsSeq.getItems().add(getCheckboxByValue(actionChar).getText());
		}
	}
	
	private void collectActionsValues() 
	{		
		customSeq.clear();
		
		for(String actionName : actionsSeq.getItems()) {
			for (Map.Entry<Character, String> el : actionsValuesMapping.entrySet()) {
				if(el.getValue().equalsIgnoreCase(actionName)) customSeq.add(el.getKey());
			}
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
	public static ArrayList<Character> getCustomSeq() {
		return customSeq;
	}
	public static void setCustomSeq(ArrayList<Character> customSeq) {
		BaseActionTabController.customSeq = customSeq;
	}
}
