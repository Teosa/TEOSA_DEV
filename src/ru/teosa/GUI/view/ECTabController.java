package ru.teosa.GUI.view;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import ru.teosa.utils.objects.SimpleComboRecord;

public class ECTabController extends AbstractController{

	// Тип КСК для записи
	      private ToggleGroup ECType;
	@FXML private RadioButton ECType_owner;
	@FXML private RadioButton ECType_reserved;
	@FXML private RadioButton ECType_any;
	
	@FXML private ComboBox<SimpleComboRecord> registrationTerm;
	
	// Расположение КСК
          private ToggleGroup location;
    @FXML private RadioButton location_forest;
    @FXML private RadioButton location_mountains;
    @FXML private RadioButton location_beach;
    @FXML private RadioButton location_any;
	
	// Специализация КСК
          private ToggleGroup specialization;
    @FXML private RadioButton specialization_classic;
    @FXML private RadioButton specialization_western;
    @FXML private RadioButton specialization_any;
	
    // Преимущества
    @FXML private CheckBox hay;
    @FXML private CheckBox oat;
    @FXML private CheckBox carrot;
    @FXML private CheckBox mash;
    @FXML private CheckBox drinker;
    @FXML private CheckBox shower;
	
	
	
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void customizeContent() {
		// TODO Auto-generated method stub
		
	}

}
