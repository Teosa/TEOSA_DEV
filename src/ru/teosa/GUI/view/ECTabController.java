package ru.teosa.GUI.view;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import ru.teosa.herdSettings.EC_Settings;
import ru.teosa.herdSettings.SettingTabsInterface;
import ru.teosa.utils.Tokens;
import ru.teosa.utils.Tools;
import ru.teosa.utils.objects.MainAppHolderSingleton;

/** Модуль Программы; Вкладка КСК */
public class ECTabController extends AbstractController implements SettingTabsInterface<EC_Settings>{

// *** БЛОК ЗАПИСИ В КСК ***
	// Тип КСК для записи
	      private ToggleGroup ECType;
	@FXML private RadioButton ECType_owner;                // Свой
	@FXML private RadioButton ECType_reserved;             // Зарезервированные стойла
	@FXML private RadioButton ECType_any;                  // Любой

	@FXML private ComboBox<Integer> registrationTerm;      // Длительность записи
	@FXML private Label             registrationTermLabel; 

	// Расположение КСК 
	      private ToggleGroup location;
	@FXML private RadioButton location_forest;           // Лес
	@FXML private RadioButton location_mountains;        // Горы
	@FXML private RadioButton location_beach;            // Пляж
	@FXML private RadioButton location_any;              // Любое
	      
	// Специализация КСК
	      private ToggleGroup specialization;
	@FXML private RadioButton specialization_classic;    // Классика
	@FXML private RadioButton specialization_western;    // Вестерн
	@FXML private RadioButton specialization_any;        // Любая

	// Преимущества
	@FXML private CheckBox hay;                          // Фураж
	@FXML private CheckBox oat;                          // Овес
	@FXML private CheckBox carrot;                       // Морковь
	@FXML private CheckBox mash;                         // Комбикорм
	@FXML private CheckBox drinker;                      // Поилка
	@FXML private CheckBox shower;                       // Душ
	
	@FXML private ComboBox<Integer> maxTariffCombo;      // Максимальный тариф за день постоя
	@FXML private TextField         maxPayEntry;         // Максимальная сумма за весь срок постоя ( длительность * тариф )

	      
// *** БЛОК ПРОДЛЕНИЯ ПОСТОЯ ***
	@FXML private Spinner<Integer>  daysBeforeExtend;    // Оставшиеся дни
	@FXML private ComboBox<Integer> extendTerm;          // Длительность продления
	@FXML private Label             extendTermLabel;      
	@FXML private CheckBox          onlyOwnerExtend;     // Продлевать только в своем КСК
	
	
	@Override
	protected void initialize() {
		MainAppHolderSingleton.getInstance().getMainApp().getController().getProgramWindowController()
		.getHerdRunSettingsController().setECTabController(this);
		
		ECType = new ToggleGroup();
		location = new ToggleGroup();
		specialization = new ToggleGroup();
		
		
		ECType_owner.setUserData('O');
		ECType_reserved.setUserData('R');
		location_forest.setUserData('F');
		location_mountains.setUserData('M');
		location_beach.setUserData('B');
		specialization_classic.setUserData('C');
		specialization_western.setUserData('W');
		
		// Объединяем радиобатоны в группы
		ECType.getToggles().addAll(ECType_reserved, ECType_owner, ECType_any);
		location.getToggles().addAll(location_forest, location_mountains, location_beach, location_any);
		specialization.getToggles().addAll(specialization_classic, specialization_western, specialization_any);
		
		// Загрузка стора длительности записи 
		registrationTerm.getItems().addAll(Tokens.EC_REGISTRATION_TERM);
		
		// Инициализация спинера Оставшиеся дни
		SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 14, 3);
		daysBeforeExtend.setValueFactory(svf);
		
		// Загрузка стора длительности продления 
		extendTerm.getItems().addAll(Tokens.EC_EXTEND_TERM);
		
		// Загрузка стора максимального тарифа за день постоя
		ArrayList<Integer> tariff = new ArrayList<Integer>();
		tariff.add(0);
		for( int i = 0; i < 181; ++i ) 
		{
			tariff.add(i + 20);
		}
		maxTariffCombo.getItems().addAll(tariff);
	}

	@Override
	public void customizeContent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadSettings() {
		loadSettings(new EC_Settings());
	}
	
	@Override
	public void loadSettings(EC_Settings settings) {
		
		Tools.setRadioButtonGroupValue(ECType, settings.getEC_type());
		Tools.setRadioButtonGroupValue(location, settings.getLocation());
		Tools.setRadioButtonGroupValue(specialization, settings.getSpecialization());
		
		registrationTerm.getSelectionModel().select(settings.getRegTerm());
		
		hay.setSelected(settings.isHay());
		oat.setSelected(settings.isOat());
		carrot.setSelected(settings.isCarrot());
		mash.setSelected(settings.isMash());
		drinker.setSelected(settings.isDrinker());
		shower.setSelected(settings.isShower());
		
		maxTariffCombo.getSelectionModel().select(settings.getMaxTariff());

		daysBeforeExtend.getValueFactory().setValue(settings.getDaysBeforeCheckout());
		extendTerm.getSelectionModel().select(settings.getExtendTerm());
		onlyOwnerExtend.setSelected(settings.isOnlyMyECExtend());
	}
	
	@Override
	public EC_Settings getTabSettings(EC_Settings settings) {

		settings.setEC_type((Character)Tools.getRadioButtonGroupValue(ECType));
		settings.setRegTerm(registrationTerm.getSelectionModel().getSelectedItem());
		settings.setLocation((Character)Tools.getRadioButtonGroupValue(location));
		settings.setSpecialization((Character)Tools.getRadioButtonGroupValue(specialization));
		
		settings.setHay(hay.isSelected());
		settings.setOat(oat.isSelected());
		settings.setCarrot(carrot.isSelected());
		settings.setMash(mash.isSelected());
		settings.setDrinker(drinker.isSelected());
		settings.setShower(shower.isSelected());
		
		settings.setMaxTariff(maxTariffCombo.getSelectionModel().getSelectedItem());
		
		settings.setDaysBeforeCheckout(daysBeforeExtend.getValue());
		settings.setExtendTerm(extendTerm.getSelectionModel().getSelectedItem());
		settings.setOnlyMyECExtend(onlyOwnerExtend.isSelected());
		
		return settings;
	}
	
	/**
	 * Установка доступности полей блока записи в КСК
	 * @param disable признак доступности
	 * */
	public void setECRegisterBlockDisabled(boolean disable) {		
		ECType_owner          .setDisable(disable);
		ECType_reserved       .setDisable(disable);
		ECType_any            .setDisable(disable);
		registrationTerm      .setDisable(disable);
		location_forest       .setDisable(disable);
		location_mountains    .setDisable(disable);
		location_beach        .setDisable(disable);
		location_any          .setDisable(disable);
		specialization_classic.setDisable(disable);
		specialization_western.setDisable(disable);
		specialization_any    .setDisable(disable);
		hay                   .setDisable(disable);
		oat                   .setDisable(disable);
		carrot                .setDisable(disable);
		mash                  .setDisable(disable);
		drinker               .setDisable(disable);
		shower                .setDisable(disable);		
		maxTariffCombo        .setDisable(disable);	
	}
	
	/**
	 * Установка доступности полей блока продления постоя в КСК
	 * @param disable признак доступности
	 * */
	public void setECExtendingBlockDisabled(boolean disable) {
		daysBeforeExtend.setDisable(disable);
		extendTerm      .setDisable(disable);
		extendTermLabel .setDisable(disable);
		onlyOwnerExtend .setDisable(disable);
	}
}
