package ru.teosa.GUI.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.teosa.GUI.MainApp;
import ru.teosa.GUI.MsgWindow;
import ru.teosa.herdSettings.HerdRunSettings;
import ru.teosa.utils.Queries;
import ru.teosa.utils.Tools;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.SimpleComboRecordExt;

/** Контроллер модуля Программы прогона */
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
	
	private static HerdRunSettingsPaneController herdRunSettingsController;
	
	private final static int EDIT_NAME_ON  = 1;
	private final static int EDIT_NAME_OFF = 0;
	
//*********************************************************************************************************************************	
//*********************************************************************************************************************************	
	@Override
	protected void initialize() {
		
		MainAppHolderSingleton.getInstance().getMainApp().getController().setProgramWindowController(this);;
		
		try {
			loadHerdRunSettingsTabPane();	
			customizeContent();
			loadProgramSettings();
		}
		catch(Exception e) {
			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
		}
	}

	@Override
	public void customizeContent() {

		programNameTextField.setVisible(mode == 0);
		
		programNameCombo.setVisible(mode == 1);
		editProgramName.setVisible(mode == 1);
		
		delete.setUserData(EDIT_NAME_OFF);
		delete.setVisible(mode == 1);	
		
		programNameCombo.valueProperty().addListener(new ChangeListener<SimpleComboRecordExt>() {
			@Override
			public void changed(ObservableValue observable, SimpleComboRecordExt oldValue, SimpleComboRecordExt newValue) {
				programNameTextField.setText(newValue.getName());
				herdRunSettingsController.loadSettings((HerdRunSettings)newValue.getData());
			}
        });
	}
	
	@SuppressWarnings("unchecked")
	public void loadProgramSettings() {
		// Если модуль открыт в режиме редактирования - подгружаем список сохраненных программ в комбобокс
		if(mode == 1) {
			NamedParameterJdbcTemplate pstmt = MainAppHolderSingleton.getInstance().getPstmt();
			final ArrayList<SimpleComboRecordExt> programs = new ArrayList<SimpleComboRecordExt>();
			
			try 
			{
				pstmt.query(Queries.GET_HERD_RUN_PROGRAMS, new HashMap(), new RowMapper() 
				{			
					@Override
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException 
					{
						byte[] bytes = rs.getBytes("settings");
						HerdRunSettings settings  = SerializationUtils.deserialize(bytes);
						
						settings.setProgramID(rs.getInt("id"));
						settings.setProgramName(rs.getString("name"));
						System.out.println(settings.toString());
						SimpleComboRecordExt record = new SimpleComboRecordExt(settings.getProgramID(), settings.getProgramName(), settings);
						
						programs.add(record);
						return null;
					}
				});
			}
			catch(Exception e) 
			{
				e.printStackTrace();
				Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
			}
			
			programNameCombo.getItems().addAll(programs);
		}
	}
	
    // Загрузка панели с настройками прогона
    private void loadHerdRunSettingsTabPane() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/HerdRunSettingsTabPane.fxml"));
        window.setCenter((TabPane) loader.load());
    }
    
    @FXML
    private void saveButtonHandler() {
    	// Выполняем проверку данных перед сохранением
    	if(!makeChecks()) {
    		//TODO showErrorMsg()
    		return;
    	}
    	
    	// Собираем данные с формы и сохраняем в БД.
    	// Если сохранение прошло успешно - закрываем окно, иначе - поднимаем окно с ошибкой
    	final HerdRunSettings settings = new HerdRunSettings();
    	settings.setProgramName(Tools.replaceEmtyText(programNameTextField.getText()));
    	
    	if(mode == 1) settings.setProgramID(programNameCombo.getSelectionModel().getSelectedItem().getId());
    	
    	if(herdRunSettingsController.getTabSettings(settings).save()) closeButtonHandler();
    	else MsgWindow.showErrorWindow("Ошибка сохранения настроек.\n" + MsgWindow.getErrorMsg());
    }
    
    @FXML
    private void closeButtonHandler() 
    {
    	((Stage)window.getScene().getWindow()).close();
    }
    
    @FXML
    private void removeButton() {
    	final HerdRunSettings settings = new HerdRunSettings();
    	
    	if(mode == 1) settings.setProgramID(programNameCombo.getSelectionModel().getSelectedItem().getId());
    	
    	if(herdRunSettingsController.getTabSettings(settings).remove()) closeButtonHandler();
    	else MsgWindow.showErrorWindow("Ошибка удаления настроек.\n" + MsgWindow.getErrorMsg());
    }
    
    @FXML
    private void changeProgramNameButtonHandler() {
    	
    	switch(Integer.parseInt(delete.getUserData().toString())) {
	    	case EDIT_NAME_ON: 
	    		delete.setUserData(EDIT_NAME_OFF);
	    		
	    		programNameCombo.setVisible(true);
	    		programNameTextField.setVisible(false);
	    		
	    		int index = programNameCombo.getSelectionModel().getSelectedIndex();
	    		SimpleComboRecordExt item = programNameCombo.getSelectionModel().getSelectedItem();
	    		item.setName(programNameTextField.getText());
	    			    		
	    		programNameCombo.getItems().set(index, item);
	    		break;
	    		
	    	case EDIT_NAME_OFF: 
	    		delete.setUserData(EDIT_NAME_ON);
	    		
	    		programNameCombo.setVisible(false);
	    		programNameTextField.setVisible(true);
	    		break;
    	}
    	
    	
    }
    
    private boolean makeChecks() {
    	
//    	ValidationSupport validationSupport = new ValidationSupport();
    	
    	return true;
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
	public static void setHerdRunSettingsController(HerdRunSettingsPaneController herdRunSettingsController) {
		ProgramWindowController.herdRunSettingsController = herdRunSettingsController;
	}
	
}
