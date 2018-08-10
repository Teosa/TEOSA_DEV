package ru.teosa.GUI.view;

import java.util.HashMap;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.teosa.utils.Queries;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.SimpleComboRecord;

/** Модуль Аффиксы; Контроллер окна добавления нового аффикса */
public class AffixAddEditWindowController extends AbstractController{

	private static int MODE;
	private static Stage thisWindow;
	private static AffixesWindowController parentController;
	
	@FXML private TextField affixName;
	@FXML private Button okButton;
	
	private int affID;
	private String affName;
	
	@Override
	protected void initialize() {
		if(MODE == 1) {
			ListView<SimpleComboRecord> affixList = (ListView<SimpleComboRecord> )thisWindow.getOwner().getScene().lookup("#affixesList");
			
			affID   = affixList.getSelectionModel().getSelectedItem().getId();
			affName = affixList.getSelectionModel().getSelectedItem().getName();
			
		}
		else affID = -1;
			
		// Хендлер, блочащий кнопку ОК если поле Аффикс пустое
		affixName.textProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
    	    {
    	    	okButton.setDisable(newValue == null || newValue.trim().length() == 0);
    	    	affName = newValue;
    	    }
    	});
				
		affixName.setText(affName);
	}

	@Override
	public void customizeContent() {}

    @SuppressWarnings("unchecked")
	@FXML 
	private void okButtonHandler() {
		NamedParameterJdbcTemplate pstmt = MainAppHolderSingleton.getInstance().getPstmt();
		
		HashMap params = new HashMap();
		params.put("affixid", affID);
		params.put("name", affName);
		
		try {
			switch(MODE) 
			{
				case 0: 
			    	TransactionTemplate tmpl = MainAppHolderSingleton.getInstance().getTmpl();
			    	
	    			tmpl.execute(new TransactionCallback(){
		    		    public Object doInTransaction(TransactionStatus ts){
		    		        try{
		    					pstmt.update(Queries.SAVE_AFFIX, params);
		    					Integer affixid = pstmt.queryForObject("SELECT MAX(ID) FROM AFFIXES", params, Integer.class);
		    					
		    					params.put("affixid", affixid);
		    					params.put("accountid", MainAppHolderSingleton.getAccount().getUser().getAccountid());
		    					pstmt.update(Queries.ATTACH_AFFIX_TO_ACCOUNT, params);
		    		        }
		    		        catch(Exception ex){
		    		            ts.setRollbackOnly(); 
		    		            Logger.getLogger("error").error(ExceptionUtils.getStackTrace(ex));
		    		        }
		    		        return null;
		    		    }
		    		});
					break;
				case 1: 
					pstmt.update(Queries.UPD_AFFIX, params);
					break;
			}	
			
			// Обновляем список аффиксов
			parentController.reloadList();
			
			thisWindow.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int getMODE() {
		return MODE;
	}
	public static void setMODE(int mODE) {
		MODE = mODE;
	}
	public TextField getAffixName() {
		return affixName;
	}
	public void setAffixName(TextField affixName) {
		this.affixName = affixName;
	}
	public Button getOkButton() {
		return okButton;
	}
	public void setOkButton(Button okButton) {
		this.okButton = okButton;
	}
	public static Stage getThisWindow() {
		return thisWindow;
	}
	public static void setThisWindow(Stage thisWindow) {
		AffixAddEditWindowController.thisWindow = thisWindow;
	}
	public static AffixesWindowController getParentController() {
		return parentController;
	}
	public static void setParentController(AffixesWindowController parentController) {
		AffixAddEditWindowController.parentController = parentController;
	}
}
