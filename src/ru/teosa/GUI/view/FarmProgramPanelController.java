package ru.teosa.GUI.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.SimpleComboRecordExt;

public class FarmProgramPanelController extends AbstractController{

	
	@FXML private ComboBox<SimpleComboRecordExt> programs; // Программы
	@FXML private Label selectedFarmName;                  // Выбранный завод
	@FXML private Button addProgramToRun;                  // Добавить в прогон
	
	
	
	
	
	
	@Override
	protected void initialize() {
		MainAppHolderSingleton.getInstance().getMainApp().getController().setFarmProgramPanelController(this);
		
	}

	@Override
	public void customizeContent() {
		// TODO Auto-generated method stub
		
	}

	public ComboBox<SimpleComboRecordExt> getPrograms() {
		return programs;
	}
	public void setPrograms(ComboBox<SimpleComboRecordExt> programs) {
		this.programs = programs;
	}
	public Label getSelectedFarmName() {
		return selectedFarmName;
	}
	public void setSelectedFarmName(Label selectedFarmName) {
		this.selectedFarmName = selectedFarmName;
	}
	public Button getAddProgramToRun() {
		return addProgramToRun;
	}
	public void setAddProgramToRun(Button addProgramToRun) {
		this.addProgramToRun = addProgramToRun;
	}
}
