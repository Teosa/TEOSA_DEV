package ru.teosa.GUI.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import ru.teosa.herdSettings.BreedingSettings;
import ru.teosa.herdSettings.SettingTabsInterface;
import ru.teosa.utils.AutoMapper;
import ru.teosa.utils.Tokens;
import ru.teosa.utils.Queries;
import ru.teosa.utils.Tools;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.SimpleComboRecord;

public class BreedingTabController extends AbstractController implements SettingTabsInterface<BreedingSettings>{

	private int gridRowHeight = 30;
	
	@FXML private GridPane breedingTabGreed ;
	
// *** БЛОК ЖЕРЕБЦЫ ***
	// Количество случек
          private ToggleGroup matingQty;
    @FXML private RadioButton matingQty_one;           // 1 
    @FXML private RadioButton matingQty_two;           // 2
    @FXML private RadioButton matingQty_three;         // 3
	@FXML private Spinner<Integer> maxMatingQty;       // Максимальное количество активных случек 
	@FXML private ComboBox<Integer> matingPrice;       // Цена за случку
	
	
// *** БЛОК КОБЫЛЫ ***
	// Крыть
          private ToggleGroup coverBy;
    @FXML private RadioButton coverBy_owner;          // Только моими 
    @FXML private RadioButton coverBy_any;            // Любыми
    @FXML private Label coverByLabel; 
    
    @FXML private ComboBox<Integer> maxCoverPrice;    // Максимальная цена за случку
    
    // Порода жеребца
          private ToggleGroup stallonBreed;
    @FXML private RadioButton stallonBreed_likeMare;  // Как у кобылы
    @FXML private RadioButton stallonBreed_any;       // Любая
    
    // ГП жеребца
          private ToggleGroup stallonGP;
	@FXML private RadioButton stallonGP_likeMare;     // Как у кобылы 
	@FXML private RadioButton stallonGP_custom;       // Указать
	@FXML private RadioButton stallonGP_any;          // Любое
	
	@FXML private RowConstraints GPRow;               // Ряд в гриде с полями ГП жеребца
	@FXML private TextField minStallonGP;             // Минимальный ГП жеребца
	@FXML private TextField maxStallonGP;             // Максимальный ГП жеребца
	@FXML private Label     maxStallonGPLabel;
	@FXML private Label     minStallonGPLabel;


// *** БЛОК ЖЕРЕБЯТА ***
	@FXML private TextField stallonNames;                 // Кличка (жеребцы)
	@FXML private TextField mareNames;                    // Кличка (кобылы)
	@FXML private ComboBox<SimpleComboRecord> foalsAffix; // Аффикс
	@FXML private ComboBox<SimpleComboRecord> foalsFarm;  // Завод
	
	
	@Override
	protected void initialize() 
	{
		MainAppHolderSingleton.getInstance().getMainApp().getController().getProgramWindowController()
		.getHerdRunSettingsController().setBreedingTabController(this);
		
		matingQty    = new ToggleGroup();
		coverBy      = new ToggleGroup();
		stallonBreed = new ToggleGroup();
		stallonGP    = new ToggleGroup();
		
		matingQty_one        .setUserData(1);
		matingQty_two        .setUserData(2);
		matingQty_three      .setUserData(3);
		coverBy_owner        .setUserData('O');
		stallonBreed_likeMare.setUserData('M');
		stallonGP_likeMare   .setUserData('M');
		stallonGP_custom     .setUserData('C');
		
		// Объединяем радиобатоны в группы
		matingQty   .getToggles().addAll(matingQty_one, matingQty_two, matingQty_three);
		coverBy     .getToggles().addAll(coverBy_owner, coverBy_any);
		stallonBreed.getToggles().addAll(stallonBreed_likeMare, stallonBreed_any);
		stallonGP   .getToggles().addAll(stallonGP_likeMare, stallonGP_custom, stallonGP_any);
		
		// Загрузка сторов цен за случки
		matingPrice.getItems().addAll(Tokens.STALLON_MATING_PRICE);
		maxCoverPrice.getItems().addAll(Tokens.STALLON_MATING_PRICE);
				
		// Инициализация спинера Количество активных случек
		SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
		maxMatingQty.setValueFactory(svf);
		
		// Загрузка списка заводов
		foalsFarm.getItems().add(new SimpleComboRecord(-1, "no selection"));
		foalsFarm.getItems().addAll(MainAppHolderSingleton.getInstance().getMainApp().getController().getFarmsTreeController().getFarms());
		
		// Загрузка списка аффиксов
		foalsAffix.getItems().add(new SimpleComboRecord(-1, "no selection"));
		foalsAffix.getItems().addAll(loadAffixesList());
	}

	@Override
	public void customizeContent() 
	{
		// Хендлер выбора ручного ввода ГП жеребца
		stallonGP_custom.selectedProperty().addListener(new ChangeListener<Boolean>() 
		{
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) 
			{
				minStallonGPLabel.setVisible(newValue);
				minStallonGP.setVisible(newValue);
				maxStallonGPLabel.setVisible(newValue);
				maxStallonGP.setVisible(newValue);
				
				GPRow.setPrefHeight(newValue ? gridRowHeight : 0);
			}
		});
		
		// Ограничиваем ввод в поле Мин. ГП только цифрами
		minStallonGP.textProperty().addListener(new ChangeListener<String>() 
		{
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
    	    {
    	    	// Удаляем все символы, которые не являются цифрой
    	        if (!newValue.matches("\\d*")) minStallonGP.setText(newValue.replaceAll("[^\\d]", ""));
    	    }
    	});
		
		// Ограничиваем ввод в поле Макс. ГП только цифрами
		maxStallonGP.textProperty().addListener(new ChangeListener<String>() 
		{
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
    	    {
    	    	// Удаляем все символы, которые не являются цифрой
    	        if (!newValue.matches("\\d*")) maxStallonGP.setText(newValue.replaceAll("[^\\d]", ""));
    	    }
    	});
		
	}

	@Override
	public void loadSettings() 
	{
		loadSettings(new BreedingSettings());	
	}

	@Override
	public void loadSettings(BreedingSettings settings) 
	{
		Tools.setRadioButtonGroupValue(matingQty, settings.getMatingQty());
		matingPrice.getSelectionModel().select(settings.getMatingPrice());
		
		maxMatingQty.getValueFactory().setValue(settings.getMatingQty());
		
		Tools.setRadioButtonGroupValue(coverBy, settings.getCoverBy());
		maxCoverPrice.getSelectionModel().select(settings.getMaxCoverPrice());
		
		Tools.setRadioButtonGroupValue(stallonBreed, settings.getStallonBreed());
		Tools.setRadioButtonGroupValue(stallonGP, settings.getStallonGP());
		
		minStallonGP.setText(Tools.writeText(settings.getMinStallonGP()));
		maxStallonGP.setText(Tools.writeText(settings.getMaxStallonGP()));
		
		stallonNames.setText(settings.getName_M());
		mareNames.setText(settings.getName_F());
		foalsAffix.getSelectionModel().select(Tools.findSimpleComboRecordById(/*settings.getAffixid()*/));
		foalsFarm.getSelectionModel().select(Tools.findSimpleComboRecordById(/*settings.getECID()*/));
	}
	
	@Override
	public BreedingSettings getTabSettings(BreedingSettings settings) 
	{
		settings.setMatingQty((Integer)Tools.getRadioButtonGroupValue(matingQty));
		settings.setMaxMatingQty(maxMatingQty.getValue());
		settings.setMatingPrice(matingPrice.getSelectionModel().getSelectedItem());
		
		settings.setCoverBy((Character)Tools.getRadioButtonGroupValue(coverBy));
		settings.setMaxCoverPrice(maxCoverPrice.getSelectionModel().getSelectedItem());
		settings.setStallonBreed((Character)Tools.getRadioButtonGroupValue(stallonBreed));
		settings.setStallonGP((Character)Tools.getRadioButtonGroupValue(stallonGP));
		settings.setMinStallonGP(Tools.writeInteger(minStallonGP.getText()));
		settings.setMaxStallonGP(Tools.writeInteger(maxStallonGP.getText()));
		
		settings.setName_M(stallonNames.getText());
		settings.setName_F(mareNames.getText());
		settings.setAffixid(foalsAffix.getSelectionModel().getSelectedItem().getId());
		settings.setECID(foalsFarm.getSelectionModel().getSelectedItem().getId());
		settings.setECName(foalsFarm.getSelectionModel().getSelectedItem().getName());
		
		return settings;
	};
	
	/**
	 * Установка доступности полей блока случек жеребцов
	 * @param disable признак доступности
	 * */
	public void setStallonBreedingBlockDisabled(boolean disable) 
	{
		matingQty_one  .setDisable(disable);
		matingQty_two  .setDisable(disable);
		matingQty_three.setDisable(disable);
		maxMatingQty   .setDisable(disable);
		matingPrice    .setDisable(disable);
	}
	
	/**
	 * Установка доступности полей блока случек кобыл
	 * @param disable признак доступности
	 * */
	public void setMareBreedingBlockDisabled(boolean disable) 
	{
		coverBy_owner        .setDisable(disable);
		coverBy_any          .setDisable(disable);
		maxCoverPrice        .setDisable(disable);
		stallonBreed_likeMare.setDisable(disable);
		stallonBreed_any     .setDisable(disable);
		stallonGP_likeMare   .setDisable(disable);
		stallonGP_custom     .setDisable(disable);
		stallonGP_any        .setDisable(disable);
		minStallonGP         .setDisable(disable);
		maxStallonGP         .setDisable(disable);
	}
	
	/**
	 * Установка доступности полей блока жеребят
	 * @param disable признак доступности
	 * */
	public void setFoalsBlockDisabled(boolean disable) 
	{
		stallonNames.setDisable(disable);
		mareNames   .setDisable(disable);
		foalsAffix  .setDisable(disable);
		foalsFarm   .setDisable(disable);
	}
	
	private List<SimpleComboRecord> loadAffixesList() 
	{
		NamedParameterJdbcTemplate pstmt = MainAppHolderSingleton.getInstance().getPstmt();
		HashMap params = new HashMap();
		params.put("accountid", MainAppHolderSingleton.getAccount().getUser().getAccountid());
		
		try {
			return pstmt.query(Queries.GET_AFFIXES, params, new AutoMapper(SimpleComboRecord.class, null));
		}
		catch (EmptyResultDataAccessException e) { return new ArrayList<SimpleComboRecord>(); }
		catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<SimpleComboRecord>();
		}
	}
}
