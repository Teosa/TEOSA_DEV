package ru.teosa.GUI.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ru.teosa.GUI.MainApp;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.SimpleComboRecordExt;

public class ProgramWindowController extends AbstractController{

	@FXML private BorderPane window;
	private static int mode;
	
	@FXML private Label                          programNamelabel;     /**/
	@FXML private ComboBox<SimpleComboRecordExt> programNameCombo;     /* Название программы */
	@FXML private TextField                      programNameTextField; /**/ 
	
	@FXML private Button editProgramName; // Редактировать название программы
	@FXML private Button save;            // Сохранить программу
	@FXML private Button delete;          // Удалить программу
	@FXML private Button cancel;          // Закрыть окно без сохранения изменений
	
	private HerdRunSettingsPaneController herdRunSettingsController;
	
//*********************************************************************************************************************************	
//*********************************************************************************************************************************	
	@Override
	protected void initialize() {
		
		MainAppHolderSingleton.getInstance().getMainApp().getController().setProgramWindowController(this);;
		
		try {
			loadHerdRunSettingsTabPane();	
			customizeContent();
		}
		catch(Exception e) {}
	}

	@Override
	public void customizeContent() {

		programNameTextField.setVisible(mode == 0);
		
		programNameCombo.setVisible(mode == 1);
		editProgramName.setVisible(mode == 1);
		delete.setVisible(mode == 1);
	}
	
	
    // Загрузка панели с настройками прогона
    private void loadHerdRunSettingsTabPane() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/HerdRunSettingsTabPane.fxml"));
        window.setCenter((TabPane) loader.load());
    }
//*********************************************************************************************************************************	
//*********************************************************************************************************************************	
	public Label getProgramNamelabel() {
		return programNamelabel;
	}
	public void setProgramNamelabel(Label programNamelabel) {
		this.programNamelabel = programNamelabel;
	}
	public TextField getProgramNameTextField() {
		return programNameTextField;
	}
	public void setProgramNameTextField(TextField programNameTextField) {
		this.programNameTextField = programNameTextField;
	}
	public Button getEditProgramName() {
		return editProgramName;
	}
	public void setEditProgramName(Button editProgramName) {
		this.editProgramName = editProgramName;
	}
	public Button getSave() {
		return save;
	}
	public void setSave(Button save) {
		this.save = save;
	}
	public Button getDelete() {
		return delete;
	}
	public void setDelete(Button delete) {
		this.delete = delete;
	}
	public Button getCancel() {
		return cancel;
	}
	public void setCancel(Button cancel) {
		this.cancel = cancel;
	}
	public HerdRunSettingsPaneController getHerdRunSettingsController() {
		return herdRunSettingsController;
	}
	public void setHerdRunSettingsController(HerdRunSettingsPaneController herdRunSettingsController) {
		this.herdRunSettingsController = herdRunSettingsController;
	}
	public ComboBox<SimpleComboRecordExt> getProgramNameCombo() {
		return programNameCombo;
	}
	public void setProgramNameCombo(ComboBox<SimpleComboRecordExt> programNameCombo) {
		this.programNameCombo = programNameCombo;
	}
	public static int getMode() {
		return mode;
	}
	public static void setMode(int mode) {
		ProgramWindowController.mode = mode;
	}
	public Scene getScene() {
		return window.getScene();
	}
	
}
