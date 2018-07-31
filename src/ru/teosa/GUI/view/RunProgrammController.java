package ru.teosa.GUI.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import ru.teosa.GUI.MsgWindow;
import ru.teosa.utils.Msgs;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.RunProgramRecord;


public class RunProgrammController extends AbstractController{

	
	@FXML private Accordion accordion;     
	                                        
	@FXML private TableView<RunProgramRecord> table;                  // ������� ����������� � ������ ������� 
	                                        
	@FXML private TableColumn<RunProgramRecord, Integer> numCol;      // ������� ��������� �����
	@FXML private TableColumn<RunProgramRecord, String> farmNameCol;  // ������� �������� ������
	@FXML private TableColumn<RunProgramRecord, String> programCol;   // ������� ��������� ���������
	@FXML private TableColumn<RunProgramRecord, String>  statusCol;   // ������� ������ �������
	
	@FXML private Button startProgram;                                // ������ ������ ������
	
    @FXML
    protected void initialize() {
    	MainAppHolderSingleton.getInstance().getMainApp().getController().setRunProgramPanelController(this);
    	try {
    		
    		numCol.setCellValueFactory(cellData -> cellData.getValue().getNum().asObject());
    		farmNameCol.setCellValueFactory(cellData -> cellData.getValue().getFarmName());
    		programCol.setCellValueFactory(cellData -> cellData.getValue().getProgramName());
    		statusCol.setCellValueFactory(cellData -> cellData.getValue().getStatus());
   		
    		ObservableList<RunProgramRecord> data = FXCollections.observableArrayList();   		
    		table.setItems(data);
    		
    		addContextMenu();	
    	}
    	catch(Exception e) {}
    }


	@Override
	public void customizeContent() {
		// TODO Auto-generated method stub
		
	}

	@FXML private void startProgramHandler() {
//    	//���� ���� ������� - ������������� ��� � ������ ����� ��� ��������� ��������� READY( ���������� ��� ���������� ������� )
//    	if(MainAppHolderSingleton.getHerdRunService().isRunning()) 
//    	{
//    		MainAppHolderSingleton.getHerdRunService().cancel();
//    		MainAppHolderSingleton.getHerdRunService().reset();
//    	}
//    	//����� - ��������� ����	
//    	else 
//    	{
    		if(MainAppHolderSingleton.getHerdRunService().getState().toString() == "SUCCEEDED") 
    			MainAppHolderSingleton.getHerdRunService().restart();
    		else MainAppHolderSingleton.getHerdRunService().start();
    		
    		startProgram.setDisable(true);
//    	}
	}
	
	private void addContextMenu() {
		ContextMenu menu = new ContextMenu();
		MenuItem menuItem_delete = new MenuItem("�������");
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
	
	public void addRecord(RunProgramRecord newRecord) 
	{	
		System.out.println(newRecord.toString());
		
		if(checkUniqness(newRecord)) 
		{
			Integer num = table.getItems().size() + 1;
			newRecord.setNum(num);
			table.getItems().add(newRecord);
		}
		else 
		{
			MsgWindow.showErrorWindow("������ ��������� ��� ���������� ������ ��� ��������� � ������.");
		}
	}
	
	private boolean checkUniqness(RunProgramRecord newRecord) 
	{
		for(RunProgramRecord record : table.getItems())
		{
			if(record.getFarmID() == newRecord.getFarmID() && record.getProgramID() == newRecord.getProgramID())
				return false;
		}
		
		return true;
	}
}
