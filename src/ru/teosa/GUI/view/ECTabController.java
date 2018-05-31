package ru.teosa.GUI.view;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import ru.teosa.utils.ComboStores;
import ru.teosa.utils.objects.MainAppHolderSingleton;

public class ECTabController extends AbstractController{

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

	      
// *** БЛОК ПРОДЛЕНИЯ ПОСТОЯ ***
	@FXML private Spinner<Integer>  daysBeforeExtend;    // Оставшиеся дни
	@FXML private ComboBox<Integer> extendTerm;          // Длительность продления
	@FXML private Label             extendTermLabel;      
	@FXML private CheckBox          onlyOwnerExtend;     // Продлевать только в своем КСК
	
	
	@Override
	protected void initialize() {
		MainAppHolderSingleton.getInstance().getMainApp().getController().getHerdRunSettingsTabController().setECTabController(this);
		
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
		registrationTerm.getItems().addAll(ComboStores.EC_REGISTRATION_TERM);
		
		// Инициализация спинера Оставшиеся дни
		SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 14, 3);
		daysBeforeExtend.setValueFactory(svf);
		
		// Загрузка стора длительности продления 
		extendTerm.getItems().addAll(ComboStores.EC_EXTEND_TERM);
	}

	@Override
	public void customizeContent() {
		// TODO Auto-generated method stub
		
	}

}
