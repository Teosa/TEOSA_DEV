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

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ru.teosa.GUI.MainApp;
import ru.teosa.utils.AutoMapper;
import ru.teosa.utils.Customizer;
import ru.teosa.utils.Queries;
import ru.teosa.utils.Sleeper;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.RedirectingComboRecord;
import ru.teosa.utils.objects.SimpleComboRecord;
import ru.teosa.utils.objects.SimpleComboRecordExt;
import ru.teosa.utils.objects.User;

public class LoginController {
	
	@FXML private ComboBox<RedirectingComboRecord>  siteVersion;
	@FXML private ComboBox<SimpleComboRecordExt>    username;
	@FXML private TextField  password;
	@FXML private Button     loginButton;
	@FXML private Text       loginErrorMsg;

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
    	
    	//������������� �����������
    	initGameVersionsCombo();
    	initLoginCombo();

    	//�������� ������ ������ � ���������� ������
    	siteVersion.setValue(siteVersion.getItems().get(0));
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
    	
    	//������ ���� ����������� � �� ������������� ��� ��������� ������
    	HashMap params = new HashMap();
    	params.put("versionid", siteVersion.getValue().getId());
    	List<User> users = pstmt.query(Queries.GET_USERS_BY_VERSION, params, new AutoMapper(User.class, null));

    	//��������� ��������� ���������� ��������������
    	for(User user : users) 
    		username.getItems().add(new SimpleComboRecordExt(user.getId(), user.getUsername(), user));
    	
    	//���� ������ �� ������, �������� �� ���� ������� ������������
    	if(username.getItems().size() > 0) username.setValue(username.getItems().get(0));
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
    	if(accountLogin()) mainApp.showMainForm(); 
    	
    	MainAppHolderSingleton.getInstance().setMainApp(mainApp);
    }
    
    private void runWithCrome(){
	    System.setProperty("webdriver.chrome.driver", this.getClass().getResource("/chromedriver.exe").getPath());
	    
	    mainApp.setDriver(new ChromeDriver());

    	mainApp.getDriver().get(siteVersion.getValue().getUrl());

    	mainApp.getDriver().manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
    	mainApp.getDriver().manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
    	mainApp.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    	
    	MainAppHolderSingleton.getInstance().setDriver(mainApp.getDriver());
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
	    	if(!mainApp.getDriver().getCurrentUrl().contains("identification")) {
	    		loginErrorMsg.setText(mainApp.getDriver().findElement(By.id("fieldError-invalidUser")).getText());
	    		return false;
	    	}	
	    	else {
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
    	
    	//���� ������������ ����������, ������ ��� ������ ����� ����
    	try {
    		Logger.getLogger("debug").debug("TRY TO GET LOGIN VALUE");
    		Logger.getLogger("debug").debug(username.getValue().getClass().getName());
    		if(!username.getValue().isValueEmpty(username.getValue().getName()))
    			usernameVal = username.getValue().getName();
    		userid = username.getValue().getId();
    	}
    	catch(ClassCastException e) {
    		Logger.getLogger("debug").debug("COUGHT EXCEPTION WHEN TRY TO GET LOGIN VALUE");
    		usernameVal = username.getEditor().getText();
    	}
    	
    	//��������� ������������� ����� ����� � ������
    	if(usernameVal.length() == 0 || password.getText().isEmpty()) {
			loginErrorMsg.setText("������� ����� � ������");
			return false;
    	}
    	//���� ��� ������ ����� ����, ��������� ��� � ��
    	else {
    		Logger.getLogger("debug").debug("USERID: " + userid + " " + usernameVal);
    		loginErrorMsg.setText("");
    		if(userid == -1) saveLogopas(usernameVal);
    		else changeLastusedProp();
    		return true;
    	}
    }
    
    //���������� ������ �����
    private void saveLogopas(String alias) {
    	NamedParameterJdbcTemplate pstmt = MainAppHolderSingleton.getInstance().getPstmt();
    	
    	HashMap params = new HashMap();
    	params.put("login", alias);
    	params.put("password", password.getText());
    	params.put("gamever", siteVersion.getValue().getId());
    	
    	try {
    		Logger.getLogger("debug").debug("TRY TO SAVE NEW USER: " + alias);
        	pstmt.update(Queries.SAVE_USER, params);
    	}
    	catch(Exception e) {
    		Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
    	}
    }
    
    //
    private void changeLastusedProp() {
    	Logger.getLogger("debug").debug("USER LASTUSED:  " + ((User)username.getValue().getData()).getLastused());
    	Logger.getLogger("debug").debug("USER LASTUSED:  " + ((User)username.getValue().getData()).getLastused().isLetterOrDigit('N'));
    	if(((User)username.getValue().getData()).getLastused().isLetterOrDigit('N')) {
        	NamedParameterJdbcTemplate pstmt = MainAppHolderSingleton.getInstance().getPstmt();
        	
        	HashMap params = new HashMap();
        	params.put("id", username.getValue().getId());
        	
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
    		
    		HashMap params = new HashMap();
    		params.put("id", siteVersion.getValue().getId());

    		pstmt.update("UPDATE GAMEVERSIONS SET LASTUSED = 'Y' WHERE ID = :id", params);
    	}
    	catch(Exception e) {
    		Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
    	}
    }
	
	
}
