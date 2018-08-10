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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import ru.teosa.GUI.MainApp;
import ru.teosa.herdSettings.HerdRunSettings;
import ru.teosa.utils.Queries;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.RunProgramRecord;
import ru.teosa.utils.objects.SimpleComboRecordExt;

/** Основное окно; Контроллер панели добавления записей в программу прогона */
public class FarmProgramPanelController extends AbstractController{

	
	@FXML private ComboBox<SimpleComboRecordExt> programsCombo; // Программы
	@FXML private Label selectedFarmName;                  		// Выбранный завод
	@FXML private Button addProgramToRun;                  		// Добавить в прогон
	
	
	@Override
	protected void initialize() {
		MainAppHolderSingleton.getInstance().getMainApp().getController().setFarmProgramPanelController(this);
		
		// Заполняем комбобокс программ
		loadProgramSettings();
	}

	@Override
	public void customizeContent() {

		// Слушатель изменений в комбобоксе 'Программа'
		programsCombo.valueProperty().addListener(new ChangeListener<SimpleComboRecordExt>() {
			@Override
			public void changed(ObservableValue observable, SimpleComboRecordExt oldValue, SimpleComboRecordExt newValue) {
				setAddProgramToRunButtonAvalibity();
			}
        });
	}

	// Добавление выбранной пары завод-программа в план прогона
	@FXML private void addProgramToRunHandler() 
	{
		RunProgrammController runProgramController = MainApp.getController().getRunProgramPanelController();
		FarmsTreeViewController treeViewController = MainApp.getController().getFarmsTreeController();
		
		RunProgramRecord newRecord = new RunProgramRecord();
		newRecord.setFarmID(treeViewController.getTree().getSelectionModel().getSelectedItem().getValue().getId());
		newRecord.setFarmName(treeViewController.getTree().getSelectionModel().getSelectedItem().getValue().getName());
		newRecord.setProgramID(programsCombo.getSelectionModel().getSelectedItem().getId());
		newRecord.setProgramName(programsCombo.getSelectionModel().getSelectedItem().getName());
		newRecord.setProgram((HerdRunSettings)programsCombo.getSelectionModel().getSelectedItem().getData());
		newRecord.setStatus("STATUS");

		runProgramController.addRecord(newRecord);
	}
	
	
	@SuppressWarnings("unchecked")
	public void loadProgramSettings() {
		
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

		programsCombo.getItems().addAll(programs);
	}	
	
	/** Установка доступности кнопки 'Добавить в прогон', в зависимости от условий */
	public void setAddProgramToRunButtonAvalibity() 
	{
		FarmsTreeViewController treeViewController = MainApp.getController().getFarmsTreeController();

		Boolean isFarmSelectrd = false;
		Boolean isProgramSelected = false;
		
		// Выбран завод
		isFarmSelectrd = treeViewController.getTree().getSelectionModel().getSelectedItem().getValue().getId() > -1;

		//Выбрана программа
		isProgramSelected = programsCombo.getSelectionModel().getSelectedItem() != null;

		addProgramToRun.setDisable(!(isFarmSelectrd && isProgramSelected));
	}
//*********************************************************************************************************************************	
//*********************************************************************************************************************************	
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
