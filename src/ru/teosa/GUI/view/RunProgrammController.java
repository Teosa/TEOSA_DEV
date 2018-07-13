package ru.teosa.GUI.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import ru.teosa.utils.objects.RunProgramRecord;
import ru.teosa.utils.objects.SimpleComboRecord;

public class RunProgrammController extends AbstractController{

	
	@FXML private Accordion accordion;     
	                                        
	@FXML private TableView<RunProgramRecord> table;                  // Таблица добавленных в прогон заводов 
	                                        
	@FXML private TableColumn<RunProgramRecord, Integer> numCol;      // Колонка Порядковй номер
	@FXML private TableColumn<RunProgramRecord, String> farmNameCol;  // Колонка Название завода
	@FXML private TableColumn<RunProgramRecord, String> programCol;   // Колонка Выбранная программа
	@FXML private TableColumn<RunProgramRecord, String>  statusCol;   // Колонка Статус прогона
	
	
    @FXML
    protected void initialize() {
    	try {
    		
    		numCol.setCellValueFactory(cellData -> cellData.getValue().getNum().asObject());
    		farmNameCol.setCellValueFactory(cellData -> cellData.getValue().getFarmName());
    		programCol.setCellValueFactory(cellData -> cellData.getValue().getProgramName());
    		statusCol.setCellValueFactory(cellData -> cellData.getValue().getStatus());
    		

    		RunProgramRecord rec = new RunProgramRecord();
    		rec.setNum(1);
    		rec.setFarmName("NAME");
    		rec.setProgramID(0);
    		rec.setProgramName("PROGRAM");
    		rec.setStatus("REDAY");
    		
    		ObservableList<RunProgramRecord> data = FXCollections.observableArrayList( rec );   		
    		table.setItems(data);
    		
    		
    		addContextMenu();
    		
    		
    		
    	}
    	catch(Exception e) {}
    }


	@Override
	public void customizeContent() {
		// TODO Auto-generated method stub
		
	}

	private void addContextMenu() {
		ContextMenu menu = new ContextMenu();
		MenuItem menuItem_delete = new MenuItem("Удалить");
		menu.getItems().add(menuItem_delete);


		table.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		    @Override
		    public void handle(MouseEvent t) {
		        if(t.getButton() == MouseButton.SECONDARY) {
		            menu.show(table, t.getScreenX(), t.getScreenY());
		        }
		    }
		});
	}
	
	
}
