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

// *** ���� ������ � ��� ***
	// ��� ��� ��� ������
	      private ToggleGroup ECType;
	@FXML private RadioButton ECType_owner;                // ����
	@FXML private RadioButton ECType_reserved;             // ����������������� ������
	@FXML private RadioButton ECType_any;                  // �����

	@FXML private ComboBox<Integer> registrationTerm;      // ������������ ������
	@FXML private Label             registrationTermLabel; 

	// ������������ ��� 
	      private ToggleGroup location;
	@FXML private RadioButton location_forest;           // ���
	@FXML private RadioButton location_mountains;        // ����
	@FXML private RadioButton location_beach;            // ����
	@FXML private RadioButton location_any;              // �����
	      
	// ������������� ���
	      private ToggleGroup specialization;
	@FXML private RadioButton specialization_classic;    // ��������
	@FXML private RadioButton specialization_western;    // �������
	@FXML private RadioButton specialization_any;        // �����

	// ������������
	@FXML private CheckBox hay;                          // �����
	@FXML private CheckBox oat;                          // ����
	@FXML private CheckBox carrot;                       // �������
	@FXML private CheckBox mash;                         // ���������
	@FXML private CheckBox drinker;                      // ������
	@FXML private CheckBox shower;                       // ���

	      
// *** ���� ��������� ������ ***
	@FXML private Spinner<Integer>  daysBeforeExtend;    // ���������� ���
	@FXML private ComboBox<Integer> extendTerm;          // ������������ ���������
	@FXML private Label             extendTermLabel;      
	@FXML private CheckBox          onlyOwnerExtend;     // ���������� ������ � ����� ���
	
	
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
		
		// ���������� ����������� � ������
		ECType.getToggles().addAll(ECType_reserved, ECType_owner, ECType_any);
		location.getToggles().addAll(location_forest, location_mountains, location_beach, location_any);
		specialization.getToggles().addAll(specialization_classic, specialization_western, specialization_any);
		
		// �������� ����� ������������ ������ 
		registrationTerm.getItems().addAll(ComboStores.EC_REGISTRATION_TERM);
		
		// ������������� ������� ���������� ���
		SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 14, 3);
		daysBeforeExtend.setValueFactory(svf);
		
		// �������� ����� ������������ ��������� 
		extendTerm.getItems().addAll(ComboStores.EC_EXTEND_TERM);
	}

	@Override
	public void customizeContent() {
		// TODO Auto-generated method stub
		
	}

}
