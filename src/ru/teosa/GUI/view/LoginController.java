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


    /**
     * Инициализация класса-контроллера для формы авторизации. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
    	NamedParameterJdbcTemplate pstmt = MainAppHolderSingleton.getInstance().getPstmt();

    	//Конфиг комбобокса версий сайта
    	new Customizer(RedirectingComboRecord.class.getSimpleName()).CustomizeCB(siteVersion, false);
    	//Слушатель, меняющий значение в полях Логин и Пароль
    	siteVersion.valueProperty().addListener(new ChangeListener<RedirectingComboRecord>() {
			@Override
			public void changed(ObservableValue observable, RedirectingComboRecord oldValue, RedirectingComboRecord newValue) {
				fillUsernameCombo();
			}
        });
    	//Список версий
    	List<RedirectingComboRecord> records = pstmt.query(Queries.GET_GAME_VERSIONS, new HashMap(), new AutoMapper(RedirectingComboRecord.class, null));
    	//Заполняем комбобокс версиями
    	for(RedirectingComboRecord record : records) {
    		siteVersion.getItems().add(record);
    	}
    	
    	//Конфиг комбобокса логина
    	new Customizer(SimpleComboRecordExt.class.getSimpleName()).CustomizeCB(username);
    	//Слушатель, меняющий значение в поле Пароль
    	username.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				//Если предыдущий алиас равен новому - выходим из функции
				if(oldValue != null && ((SimpleComboRecord)oldValue).getName().equalsIgnoreCase(((SimpleComboRecord)newValue).getName()))
					return;
				
				if(newValue != null && ((SimpleComboRecord)newValue).getId() > -1) 
					password.setText(((User)((SimpleComboRecordExt)newValue).getData()).getPassword()); 
				else password.setText("");
			}
        });
    	
    	
    	//Выбираем первую версию в списке
    	siteVersion.setValue(siteVersion.getItems().get(0));

    }
    
    //Заполнение комбобокса Логин пользователями для выбранной версии сайта
    private void fillUsernameCombo() {
    	NamedParameterJdbcTemplate pstmt = MainAppHolderSingleton.getInstance().getPstmt();
    	
    	username.getItems().clear();
    	
    	//Список всех сохраненных в БД пользователей для выбранной версии
    	HashMap params = new HashMap();
    	params.put("versionid", siteVersion.getValue().getId());
    	List<User> users = pstmt.query(Queries.GET_USERS_BY_VERSION, params, new AutoMapper(User.class, null));

    	//Заполняем комбобокс доступными пользователями
    	for(User user : users) 
    		username.getItems().add(new SimpleComboRecordExt(user.getId(), user.getUsername(), user));
    	
    	//Если список не пустой, выбираем из него первого пользователя
    	if(username.getItems().size() > 0) username.setValue(username.getItems().get(0));
    }
    
    
    public void setMainApp(MainApp mainApp) {
    	this.mainApp = mainApp;
    }   
    
    /**
     * Метод вызывается после нажатия кнопки 'Подключение'.<br>
     * 1. Проверка заполненности логина и пароля. В случае неудачи - выход из метода.<br>
     * 2. Проверка наличия открытого браузера (открытие нового при неудаче) и переход на главную страницу Lowadi.<br>
     * 3. Попытка авторизации с введенными логином и паролем. В случае неудачи - отображение ошибки в окне авторизации приложения.
     * 4. Инициализация MainAppHolderSingleton.
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
    		try {
    			Sleeper.waitForClickAndClick("//*[@id=\"header\"]/nav/div");
    		}
    		catch(NoSuchElementException | TimeoutException e) {
    			Logger.getLogger("error").error(e);
    		}

	    	WebElement loginField = mainApp.getDriver().findElement(By.id("login"));
	    	WebElement passwordField = mainApp.getDriver().findElement(By.id("password"));

	    	loginField.clear();
	    	passwordField.clear();

	    	loginField.sendKeys(username.getValue().getName());
	    	passwordField.sendKeys(password.getText());

	    	mainApp.getDriver().findElement(By.id("authentificationSubmit")).click();
	    	
			Thread.sleep(2000);

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
			loginErrorMsg.setText("Ошибка авторизации");
			return false;
		}
    }
    
    //Проверка Логина и Пароля
    private boolean checkLogopas(){    	    	
    	Integer userid = -1;
    	String usernameVal = "";
    	
    	//Если выбрасывется исключение, значит был введен новый юзер
    	try {
    		if(!username.getValue().isValueEmpty(username.getValue().getName()))
    			usernameVal = username.getValue().getName();
    		userid = username.getValue().getId();
    	}
    	catch(ClassCastException e) {
    		usernameVal = username.getEditor().getText();
    	}
    	
    	//Проверяем заполненность полей Логин и Пароль
    	if(usernameVal.length() == 0 || password.getText().isEmpty()) {
			loginErrorMsg.setText("Введите логин и пароль");
			return false;
    	}
    	//Если был введен новый юзер, сохраняем его в БД
    	else {
    		loginErrorMsg.setText("");
    		if(userid == -1) saveLogopas(usernameVal);
    		else changeLastusedProp();
    		return true;
    	}
    }
    
    //Сохранение нового юзера
    private void saveLogopas(String alias) {
    	NamedParameterJdbcTemplate pstmt = MainAppHolderSingleton.getInstance().getPstmt();
    	
    	HashMap params = new HashMap();
    	params.put("login", alias);
    	params.put("password", password.getText());
    	params.put("gamever", siteVersion.getValue().getId());
    	
    	try {
        	pstmt.update(Queries.SAVE_USER, params);
    	}
    	catch(Exception e) {
    		Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
    	}
    }
    
    //
    private void changeLastusedProp() {
    	if(((User)username.getValue().getData()).getLastused().isDigit('N')) {
        	NamedParameterJdbcTemplate pstmt = MainAppHolderSingleton.getInstance().getPstmt();
        	
        	HashMap params = new HashMap();
        	params.put("id", username.getValue().getId());
        	
        	try {
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

    		
    		pstmt.update("UPDATE GAMEVERSIONS SET LASTUSED = 'N' WHERE LASTUSED = 'Y'", params);
    		pstmt.update("UPDATE GAMEVERSIONS SET LASTUSED = 'Y' WHERE ID = :id", params);
    	}
    	catch(Exception e) {
    		Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
    	}
    }
	
	
}
