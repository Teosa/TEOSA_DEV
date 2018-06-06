package ru.teosa.GUI.view;

import java.util.HashMap;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.teosa.GUI.MainApp;
import ru.teosa.utils.AutoMapper;
import ru.teosa.utils.Queries;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.SimpleComboRecord;

public class AffixesWindowController extends AbstractController{

	private static Stage thisWindow;
	
	@FXML private ListView<SimpleComboRecord> affixesList;
	
	@FXML private Button addButton;
	@FXML private Button editButton;
	@FXML private Button removeButton;
	
	@FXML private Button saveButton;
	@FXML private Button closeButton;
		
	private static final String ADD_AFFIX_TITLE = "Добавить аффикс";
	private static final String EDIT_AFFIX_TITLE = "Редактировать аффикс";
	
	
	@Override
	protected void initialize() {		
		// Устанавливаем в качестве объектов списка SimpleComboRecord
		affixesList.setCellFactory(param -> new ListCell<SimpleComboRecord>() {
		    @Override
		    protected void updateItem(SimpleComboRecord item, boolean empty) {
		        super.updateItem(item, empty);

		        if (empty || item == null || item.getName() == null) {
		            setText(null);
		        } else {
		            setText(item.getName());
		        }
		    }
		});
		
		// Заполняем список данными из БД
		fillListFromDB();
		
		// Устанавливаем иконки на кнопки
		setIcons();
	}

	@Override
	public void customizeContent() {
		// TODO Auto-generated method stub
		
	}

	@FXML
	private void addButtonHandler() { showAddEditWindow(0); }
	
	@FXML
	private void editButtonHandler() { showAddEditWindow(1); }
	
	@FXML
	private void removeButtonHandler() {
		SimpleComboRecord record = affixesList.getSelectionModel().getSelectedItem();
		
		if(record != null) {
			NamedParameterJdbcTemplate pstmt = MainAppHolderSingleton.getInstance().getPstmt();
			HashMap params = new HashMap();
			params.put("affixid", record.getId());
			params.put("accountid", MainAppHolderSingleton.getAccount().getUser().getAccountid());
			
			try {
				pstmt.update(Queries.UNATTACH_AFFIX_FROM_ACCOUNT, params);
				pstmt.update(Queries.REMOVE_AFFIX, params);
				reloadList();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void saveButtonHandler() {}
	
	@FXML
	private void closeButtonHandler() {
		thisWindow.close();
	}
	
	@FXML 
	// Доступность кнопок взаимодействия со списком
	private void onListClickHandler(MouseEvent e) {
	    int index = affixesList.getSelectionModel().getSelectedIndex();
	    
	    editButton.setDisable(index == -1);
	    removeButton.setDisable(index == -1);
	}
	
	public void reloadList() {
		fillListFromDB();
	}
	
	// Заполнение списка аффиксов из БД
	private void fillListFromDB() {
		
		NamedParameterJdbcTemplate pstmt = MainAppHolderSingleton.getInstance().getPstmt();
		HashMap params = new HashMap();
		params.put("accountid", MainAppHolderSingleton.getAccount().getUser().getAccountid());
		
		try {
			affixesList.getItems().clear();
			affixesList.getItems().addAll( pstmt.query(Queries.GET_AFFIXES, params, new AutoMapper(SimpleComboRecord.class, null)) );
		}
		catch (EmptyResultDataAccessException e) {}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void showAddEditWindow(int mode) {
    	Stage dialog = new Stage();
    	
    	AffixAddEditWindowController.setThisWindow(dialog);
    	AffixAddEditWindowController.setMODE(mode);
    	AffixAddEditWindowController.setParentController(this);
    	
        dialog.setResizable(false);
        dialog.setTitle(mode == 0 ? ADD_AFFIX_TITLE : EDIT_AFFIX_TITLE);
        dialog.initOwner(thisWindow);
    	dialog.initModality(Modality.APPLICATION_MODAL);
    	
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/AffixAddEditWindow.fxml"));
        
        try {
            Scene scene = new Scene((AnchorPane) loader.load());
            dialog.setScene(scene);
       	    dialog.showAndWait();	
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
	private void setIcons() {
		// Кнопка Добавить
        ImageView addICO = new ImageView(new Image("/icons/add_icon.png"));
        addICO.setFitHeight(25);
        addICO.setFitWidth(25);
		addButton.setGraphic(addICO);
		
		// Кнопка Редактировать
        ImageView editICO = new ImageView(new Image("/icons/edit_icon.png"));
        editICO.setFitHeight(25);
        editICO.setFitWidth(25);
		editButton.setGraphic(editICO);
		
		// Кнопка Удалить
        ImageView removeICO = new ImageView(new Image("/icons/remove_icon.png"));
        removeICO.setFitHeight(25);
        removeICO.setFitWidth(25);
		removeButton.setGraphic(removeICO);
	}
	
	public static void setThisWindow(Stage thisWindow) {
		AffixesWindowController.thisWindow = thisWindow;
	}
}
