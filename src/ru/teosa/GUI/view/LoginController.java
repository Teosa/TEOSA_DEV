package ru.teosa.GUI.view;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ru.teosa.GUI.MainApp;
import ru.teosa.mainapp.pojo.User;
import ru.teosa.utils.AutoMapper;
import ru.teosa.utils.Customizer;
import ru.teosa.utils.Queries;
import ru.teosa.utils.Sleeper;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.RedirectingComboRecord;
import ru.teosa.utils.objects.SimpleComboRecord;
import ru.teosa.utils.objects.SimpleComboRecordExt;

public class LoginController {
	
	@FXML private ComboBox<RedirectingComboRecord>  siteVersion;
	@FXML private ComboBox<SimpleComboRecordExt>    username;
	@FXML private TextField  password;
	@FXML private Button     loginButton;
	@FXML private Text       loginErrorMsg;
	@FXML private Text       version;

	      private MainApp    mainApp;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}   
	
	
    /**
     * ������������� ������-����������� ��� ����� �����������. ���� ����� ���������� �������������
     * ����� ����, ��� fxml-���� ����� ��������.
     */
    @FXML
    private void initialize() {
    	
		//���������� ������ ���������
    	version.setText(MainAppHolderSingleton.getVer());
    	
    	//������������� �����������
    	initGameVersionsCombo();
    	initLoginCombo();

    	//�������� ������ ������ � ���������� ������
    	siteVersion.setValue(siteVersion.getItems().get(0));
    }
    
    /**
     * ����� ���������� ����� ������� ������ '�����������'.<br>
     * 1. �������� ������������� ������ � ������. � ������ ������� - ����� �� ������.<br>
     * 2. �������� ������� ��������� �������� (�������� ������ ��� �������) � ������� �� ������� �������� Lowadi.<br>
     * 3. ������� ����������� � ���������� ������� � �������. � ������ ������� - ����������� ������ � ���� ����������� ����������.
     * 4. ������������� MainAppHolderSingleton.
     * */
    @FXML
    public void login(){

    	saveSelectedVersion();
    	
    	if(!checkLogopas()) return;
    	if(mainApp.getDriver() == null) runWithCrome();
    	if(accountLogin()) 
    		mainApp.showMainForm(); 
    	
    	MainAppHolderSingleton.getInstance().setMainApp(mainApp);
    }
        
    private void initGameVersionsCombo() {
    	NamedParameterJdbcTemplate pstmt = MainAppHolderSingleton.getInstance().getPstmt();

    	//������ ���������� ������ �����
    	new Customizer(RedirectingComboRecord.class).CustomizeCB(siteVersion, false);
    	//���������, �������� �������� � ����� ����� � ������
    	siteVersion.valueProperty().addListener(new ChangeListener<RedirectingComboRecord>() {
			@Override
			public void changed(ObservableValue observable, RedirectingComboRecord oldValue, RedirectingComboRecord newValue) {
				fillUsernameCombo();
			}
        });
    	//������ ������
    	List<RedirectingComboRecord> records = pstmt.query(Queries.GET_GAME_VERSIONS, new HashMap(), new AutoMapper(RedirectingComboRecord.class, null));
    	//��������� ��������� ��������
    	for(RedirectingComboRecord record : records) {
    		siteVersion.getItems().add(record);
    	}
    }
    
    private void initLoginCombo() {
    	//������ ���������� ������
    	new Customizer(SimpleComboRecordExt.class).CustomizeCB(username);
    	//���������, �������� �������� � ���� ������
    	username.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				Logger.getLogger("debug").debug("CHANGE LOGIN COMBO VALUE");
				/* ����� ������ ������ � ���� ����� ������������ ������ ���������.
				*  ���� ���� ���� ������� ������ �� ����������� ������ (� �� ������� ����� ��������), 
				*  ��������� ��������� �� newValue ����� �� ���������� � ID = -1, ��� � ���������� �������� � �������� ��������� ����� � ��. 
				*  ����� �������� �����, ������ �������� �� ���������� ������� ������� � ������ �������� � ��������� ID �� ��������� -1. 
				*  � ������ ���������� ����� �������� - ���������� ��������� �������� � ����������.
				*  */
				if(oldValue != null && newValue != null
			      && ((SimpleComboRecord)oldValue).getName().equalsIgnoreCase(((SimpleComboRecord)newValue).getName())
			      && ((SimpleComboRecord)newValue).getId() == -1) 
				{
		            Platform.runLater(new Runnable(){
		                @Override
		                public void run() {
		                	username.setValue((SimpleComboRecordExt)oldValue);
		                	return;
		                }});
				}
				else 
				{
					if(newValue != null && ((SimpleComboRecord)newValue).getId() > -1) {
						Logger.getLogger("debug").debug("COMBO RECORD ID: " + ((SimpleComboRecord)newValue).getId());
						password.setText(((User)((SimpleComboRecordExt)newValue).getData()).getPassword()); 
					}
					else password.setText("");					
				}
			}
        });
    }
    
    //���������� ���������� ����� �������������� ��� ��������� ������ �����
    private void fillUsernameCombo() {
    	NamedParameterJdbcTemplate pstmt = MainAppHolderSingleton.getInstance().getPstmt();
    	
    	username.getItems().clear();
    	
    	//�������� ������ ���� ����������� � �� ������������� ��� ��������� ������
    	HashMap<String, Integer> params = new HashMap<String, Integer>();
    	params.put("versionid", siteVersion.getValue().getId());
    	List<User> users = pstmt.query(Queries.GET_USERS_BY_VERSION, params, new AutoMapper(User.class, null));

    	//��������� ��������� ���������� ��������������
    	for(User user : users) 
    		username.getItems().add(new SimpleComboRecordExt(user.getId(), user.getUsername(), user));
    	
    	//���� ������ �� ������, �������� �� ���� ������� ������������
    	if(username.getItems().size() > 0) username.setValue(username.getItems().get(0));
    }
    
    private void runWithCrome(){
	    System.setProperty("webdriver.chrome.driver", this.getClass().getResource("/chromedriver.exe").getPath());
	    
	    mainApp.setDriver(new ChromeDriver());

    	mainApp.getDriver().get(siteVersion.getValue().getUrl());

    	mainApp.getDriver().manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
    	mainApp.getDriver().manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
    	mainApp.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    	
    	MainAppHolderSingleton.getInstance().setDriver(mainApp.getDriver()); 	
    	MainAppHolderSingleton.setGameURL(siteVersion.getValue().getUrl());
    } 
     
    private void runWithHeadlessBrowser(){}
 
    private boolean accountLogin(){
    	try {
    		//���� �� �������� ����������� ���� �����, ���� ��������� ������ ����� ��� ����������� ����� �����������
    		if(!mainApp.getDriver().findElement(By.id("login")).isDisplayed()) {
	    		try {
	    			Sleeper.waitForClickAndClick("//*[@id=\"header\"]/nav/div");
	    		}
	    		catch(NoSuchElementException | TimeoutException e) {
	    			Logger.getLogger("error").error(e);
	    		}
    		}
    		
	    	WebElement loginField = mainApp.getDriver().findElement(By.id("login"));
	    	WebElement passwordField = mainApp.getDriver().findElement(By.id("password"));

	    	loginField.clear();
	    	passwordField.clear();

	    	//��������� ����� � ������
	    	loginField.sendKeys(username.getValue().getName());
	    	passwordField.sendKeys(password.getText());

	    	//��������
	    	mainApp.getDriver().findElement(By.id("authentificationSubmit")).click();
	    	
	    	//���� �������� ��������
			Thread.sleep(2000);

			//���� �� ��������������, ������� ������ � ����������
			try {
				WebElement el = Sleeper.waitVisibility("//*[@id=\"fieldError-invalidUser\"]");
				
	    		loginErrorMsg.setText(el.getText());
	    		return false;
			}
			catch(NoSuchElementException | TimeoutException ex) {
	    		loginErrorMsg.setText("");  		
	    		return true;
			}
		} catch (Exception e) {
			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
			loginErrorMsg.setText("������ �����������");
			return false;
		}
    }
    
    //�������� ������ � ������
    private boolean checkLogopas(){    	    	
    	Integer userid = -1;
    	String usernameVal = "";
    	
    	//���� ������������ ����������, ������ ��� ������ ����� ���� (�������� ��� �� �������������...)
    	try {
    		if(!username.getValue().isValueEmpty(username.getValue().getName()))
    			usernameVal = username.getValue().getName();
    		userid = username.getValue().getId();
    	}
    	catch(ClassCastException e) {
    		usernameVal = username.getEditor().getText();
    	}
    	
    	//��������� ������������� ����� ����� � ������
    	if(usernameVal.length() == 0 || password.getText().isEmpty()) {
			loginErrorMsg.setText("������� ����� � ������");
			return false;
    	}
    	else {
    		loginErrorMsg.setText("");
    		//���� ��� ������ ����� ����, ��������� ��� � ��
    		if(userid == -1) userid = saveLogopas(usernameVal.trim());
    		//����� - ���������� ������� ���������� ������������ ���������� �������� �����
    		else changeLastusedProp();
    		
    		//��������� ������ ����������� �� ���������������� ������������
    		initAccount(userid);
    		return true;
    	}
    }
    
    //���������� ������ �����
    @SuppressWarnings("unchecked")
	private Integer saveLogopas(String alias) {
    	NamedParameterJdbcTemplate pstmt = MainAppHolderSingleton.getInstance().getPstmt();
    	TransactionTemplate tmpl = MainAppHolderSingleton.getInstance().getTmpl();
    	Integer userID = -1;

    	HashMap params = new HashMap();
    	params.put("login", alias);
    	params.put("password", password.getText());
    	params.put("gamever", siteVersion.getValue().getId());
    	
    	//�������� ������ ������������ � �� �������
    	List<SimpleComboRecord> aliases = pstmt.query("SELECT ID, ALIAS AS name FROM USERS", params,  new AutoMapper(SimpleComboRecord.class, null));
    	Integer existingAlias = null;
    	
    	//��������� ������������� ������ � ��
    	for(SimpleComboRecord user : aliases) if(alias.equalsIgnoreCase(user.getName())) existingAlias = user.getId();
    	
    	try {
    		//���� ����� ����������, �� ������� ����� ������� � ����������� ��� � ������
    		if(existingAlias != null) {
	    		final Integer existingAlias_ = existingAlias;
    			tmpl.execute(new TransactionCallback(){
	    		    public Object doInTransaction(TransactionStatus ts){
	    		        try{
	    		    		pstmt.update(Queries.SAVE_ACCOUNT, params);
	    		    		Integer newAccountID = pstmt.queryForObject("SELECT MAX(ID) FROM ACCOUNTS", params, Integer.class);
	    		    			
	    		    		params.put("userid", existingAlias_);
	    		    		params.put("accid", newAccountID);
	    		    			
	    		    		pstmt.update(Queries.ATTACH_ACCOUNT_TO_USER, params);	
	    		        }
	    		        catch(Exception ex){
	    		            ts.setRollbackOnly(); 
	    		            Logger.getLogger("error").error(ExceptionUtils.getStackTrace(ex));
	    		        }
	    		        return null;
	    		    }
	    		});
    			
        		userID = existingAlias_;
    		}
    		//����� ������� ������ ����� � �������
    		else {
    			tmpl.execute(new TransactionCallback(){
	    		    public Object doInTransaction(TransactionStatus ts){
	    		        try{
	    	    			pstmt.update(Queries.SAVE_USER, params);
	    	    			pstmt.update(Queries.SAVE_ACCOUNT, params);
	    	    			
	    	    			Integer newAccountID = pstmt.queryForObject("SELECT MAX(ID) FROM ACCOUNTS", params, Integer.class);
	    	    			Integer newUserID = pstmt.queryForObject("SELECT MAX(ID) FROM USERS", params, Integer.class);
	    	    			
	    	    			params.put("userid", newUserID);
	    	    			params.put("accid", newAccountID);
	    	    			
	    	    			pstmt.update(Queries.ATTACH_ACCOUNT_TO_USER, params);	    	
	    		        }
	    		        catch(Exception ex){
	    		            ts.setRollbackOnly(); 
	    		            Logger.getLogger("error").error(ExceptionUtils.getStackTrace(ex));
	    		        }
	    		        return null;
	    		    }
	    		});
    			
    			userID = pstmt.queryForObject("SELECT MAX(ID) FROM USERS", params, Integer.class);
    		} 
    	}
    	catch(Exception e) {
    		Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
    	}
    	
		return userID;
    }
    
    //
    private void changeLastusedProp() {

    	if(((User)username.getValue().getData()).getLastused().isLetterOrDigit('N')) {
        	NamedParameterJdbcTemplate pstmt = MainAppHolderSingleton.getInstance().getPstmt();
        	
        	HashMap<String, Integer> params = new HashMap<String, Integer>();
        	params.put("id", username.getValue().getId());
        	params.put("versionid", siteVersion.getValue().getId());
        	
        	try {
        		Logger.getLogger("debug").debug("TRY TO UPDATE LASTUSED FOR ID " + username.getValue().getId());
            	pstmt.update(Queries.UPD_USER_LASTUSED, params);
        	}
        	catch(Exception e) {
        		Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
        	}
    	}
    }
    
    private void saveSelectedVersion() {
    	try {
    		NamedParameterJdbcTemplate pstmt = MainAppHolderSingleton.getInstance().getPstmt();
    		
    		HashMap<String, Integer> params = new HashMap<String, Integer>();
    		params.put("id", siteVersion.getValue().getId());

    		pstmt.update("UPDATE GAMEVERSIONS SET LASTUSED = 'Y' WHERE ID = :id", params);
    	}
    	catch(Exception e) {
    		Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
    	}
    }
	
	private void initAccount(Integer userid) {
		try {
			HashMap<String, Integer> params = new HashMap<String, Integer>();
			params.put("userid", userid);
			params.put("versionid", siteVersion.getValue().getId());
			
			MainAppHolderSingleton.getAccount().setUser
			(
			 (User)MainAppHolderSingleton.getInstance().getPstmt().queryForObject(Queries.GET_USER, params, new AutoMapper(User.class, null))	
			);
		}
		catch(Exception e) {
			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
		}
	}
}
