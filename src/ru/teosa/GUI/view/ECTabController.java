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

/** ������ ���������; ������� ��� */
public class ECTabController extends AbstractController implements SettingTabsInterface<EC_Settings>{

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
	
	@FXML private ComboBox<Integer> maxTariffCombo;      // ������������ ����� �� ���� ������
	@FXML private TextField         maxPayEntry;         // ������������ ����� �� ���� ���� ������ ( ������������ * ����� )

	      
// *** ���� ��������� ������ ***
	@FXML private Spinner<Integer>  daysBeforeExtend;    // ���������� ���
	@FXML private ComboBox<Integer> extendTerm;          // ������������ ���������
	@FXML private Label             extendTermLabel;      
	@FXML private CheckBox          onlyOwnerExtend;     // ���������� ������ � ����� ���
	
	
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
		
		// ���������� ����������� � ������
		ECType.getToggles().addAll(ECType_reserved, ECType_owner, ECType_any);
		location.getToggles().addAll(location_forest, location_mountains, location_beach, location_any);
		specialization.getToggles().addAll(specialization_classic, specialization_western, specialization_any);
		
		// �������� ����� ������������ ������ 
		registrationTerm.getItems().addAll(Tokens.EC_REGISTRATION_TERM);
		
		// ������������� ������� ���������� ���
		SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 14, 3);
		daysBeforeExtend.setValueFactory(svf);
		
		// �������� ����� ������������ ��������� 
		extendTerm.getItems().addAll(Tokens.EC_EXTEND_TERM);
		
		// �������� ����� ������������� ������ �� ���� ������
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
	 * ��������� ����������� ����� ����� ������ � ���
	 * @param disable ������� �����������
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
	 * ��������� ����������� ����� ����� ��������� ������ � ���
	 * @param disable ������� �����������
	 * */
	public void setECExtendingBlockDisabled(boolean disable) {
		daysBeforeExtend.setDisable(disable);
		extendTerm      .setDisable(disable);
		extendTermLabel .setDisable(disable);
		onlyOwnerExtend .setDisable(disable);
	}
}
