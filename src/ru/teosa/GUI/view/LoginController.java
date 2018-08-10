package ru.teosa.GUI.view;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ru.teosa.GUI.LoadingWindow;
import ru.teosa.GUI.MainApp;
import ru.teosa.GUI.MsgWindow;
import ru.teosa.lang.Lang;
import ru.teosa.mainapp.pojo.User;
import ru.teosa.threads.ServiceStarter;
import ru.teosa.utils.AutoMapper;
import ru.teosa.utils.Customizer;
import ru.teosa.utils.Queries;
import ru.teosa.utils.Sleeper;
import ru.teosa.utils.XPathConstants;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.RedirectingComboRecord;
import ru.teosa.utils.objects.ResultObject;
import ru.teosa.utils.objects.SimpleComboRecord;
import ru.teosa.utils.objects.SimpleComboRecordExt;

/** Контроллер окна авторизации */
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
     * Инициализация класса-контроллера для формы авторизации. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
    	
		//Отображаем версию программы
    	version.setText(MainAppHolderSingleton.getVer());
    	
    	//Инициализация комбобоксов
    	initGameVersionsCombo();
    	initLoginCombo();

    	//Выбираем первую запись в комбобоксе версий
    	siteVersion.setValue(siteVersion.getItems().get(0));
    }
    
    /**
     * Метод вызывается после нажатия кнопки 'Подключение'.<br>
     * 1. Проверка заполненности логина и пароля. В случае неудачи - выход из метода.<br>
     * 2. Проверка наличия открытого браузера (открытие нового при неудаче) и переход на главную страницу Lowadi.<br>
     * 3. Попытка авторизации с введенными логином и паролем. В случае неудачи - отображение ошибки в окне авторизации приложения.
     * 4. Инициализация MainAppHolderSingleton.
     * */
    @FXML
    public void login()
    {

       saveSelectedVersion();
       
       // Проверяем логин и пароль
       if(checkLogopas()) 
       {
    	   MainAppHolderSingleton.getInstance().setMainApp(mainApp);

    	   startBrowserAndLogin();
       }
        	
        	// Запускаем браузер
//        	if(mainApp.getDriver() == null) runWithCrome();
        	
//        	// Пытаемся авторизоваться с введенным логином и паролем
//        	if(accountLogin()) 
//        		mainApp.showMainForm(); 
//        	else LoadingWindow.hide();
//        	
//        	MainAppHolderSingleton.getInstance().setMainApp(mainApp);

    }
        
    private void initGameVersionsCombo() {
    	NamedParameterJdbcTemplate pstmt = MainAppHolderSingleton.getInstance().getPstmt();

    	//Конфиг комбобокса версий сайта
    	new Customizer(RedirectingComboRecord.class).CustomizeCB(siteVersion, false);
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
    }
    
    private void initLoginCombo() {
    	//Конфиг комбобокса логина
    	new Customizer(SimpleComboRecordExt.class).CustomizeCB(username);
    	//Слушатель, меняющий значение в поле Пароль
    	username.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				Logger.getLogger("debug").debug("CHANGE LOGIN COMBO VALUE");
				/* После потери фокуса в поле Логин отрабатывает данный слушатель.
				*  Даже если была выбрана запись из выпадающего списка (а не введено новое значение), 
				*  слушатель принимает за newValue текст из комбобокса с ID = -1, что в дальнейшем приводит к созданию дубликата юзера в БД. 
				*  Чтобы избежать этого, делаем проверку на совпадение алиасов старого и нового значений и проверяем ID на равенство -1. 
				*  В случае Успешности обеих проверок - откатываем изменение значения в комбобоксе.
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
    
    //Заполнение комбобокса Логин пользователями для выбранной версии сайта
    private void fillUsernameCombo() {
    	NamedParameterJdbcTemplate pstmt = MainAppHolderSingleton.getInstance().getPstmt();
    	
    	username.getItems().clear();
    	
    	//Получаем список всех сохраненных в БД пользователей для выбранной версии
    	HashMap<String, Integer> params = new HashMap<String, Integer>();
    	params.put("versionid", siteVersion.getValue().getId());
    	List<User> users = pstmt.query(Queries.GET_USERS_BY_VERSION, params, new AutoMapper(User.class, null));

    	//Заполняем комбобокс доступными пользователями
    	for(User user : users) 
    		username.getItems().add(new SimpleComboRecordExt(user.getId(), user.getUsername(), user));
    	
    	//Если список не пустой, выбираем из него первого пользователя
    	if(username.getItems().size() > 0) username.setValue(username.getItems().get(0));
    }

    
    private boolean startBrowserAndLogin() 
    {
    	MainAppHolderSingleton.getLoginService().setLoginController(this);	
    	MainAppHolderSingleton.getLoginService().addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
    		    new EventHandler<WorkerStateEvent>() {
    		    @Override
    		    public void handle(WorkerStateEvent t) {
    		        String result = MainAppHolderSingleton.getLoginService().getValue();
    		        System.out.println("TASK RESULT " + result);
    		        LoadingWindow.hide();
    		    }
    		});

    	ServiceStarter.start(MainAppHolderSingleton.getLoginService());
    	
    	LoadingWindow.show();
    	
    	return true;
    }
 
    private boolean accountLogin(){
    	try {
    		//Если на странице отсутствует поле Логин, ждем появления кнопки Войти для отображения формы авторизации
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

	    	//Заполняем Логин и Пароль
	    	loginField.sendKeys(username.getValue().getName());
	    	passwordField.sendKeys(password.getText());

	    	//Сабмитим
	    	mainApp.getDriver().findElement(By.id("authentificationSubmit")).click();
	    	
	    	// Увеличиваем время ожидания загрузки страницы 
//			mainApp.getDriver().manage().timeouts().pageLoadTimeout(1, TimeUnit.MINUTES);
	    	Sleeper.setStandartPageLoadTimeout(60);
		
			// Ждем появления кнопки Коневодство на панели кнопок главной страницы игры
			WebDriverWait wait = (WebDriverWait) new WebDriverWait(MainAppHolderSingleton.getInstance().getDriver(), 60);
//			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"header-menu\"]/div[1]/ul/li[1]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPathConstants.MAIN_HEADER)));
		
			// Получаем кнопку Коневодство
			WebElement el = mainApp.getDriver().findElement(By.xpath(XPathConstants.MAIN_HEADER_BREEDING_FARM));

			// Возвращаем время ожидания загрузки  страницы к стандартному значению
			Sleeper.setStandartPageLoadTimeout();
			
			if(el != null) {
				// Загружаем языковой мапинг в зависимости от выбранной версии игры
				Lang.loadMap();
				
	    		loginErrorMsg.setText("");  		
	    		return true;
			}
			else {
				// TODO реализовать в этом блоке вывод текста ошибки авторизации из браузера
	    		loginErrorMsg.setText("Ошибка авторизации1");
	    		return false;
			}
		} catch (Exception e) {
			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
			loginErrorMsg.setText("Ошибка авторизации");
			return false;
		}
    }
    
    //Проверка Логина и Пароля
    private boolean checkLogopas(){    	    	
    	Integer userid = -1;
    	String usernameVal = "";
    	
    	//Если выбрасывется исключение, значит был введен новый юзер (возможно уже не выбрасывается...)
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
    	else {
    		loginErrorMsg.setText("");
    		//Если был введен новый юзер, сохраняем его в БД
    		if(userid == -1) userid = saveLogopas(usernameVal.trim());
    		//Иначе - выставляем признак последнего исрльзования выбранному аккаунту юзера
    		else changeLastusedProp();
    		
    		//Заполняем объект информацией об авторизовавшемся пользователе
    		initAccount(userid);
    		return true;
    	}
    }
    
    //Сохранение нового юзера
    @SuppressWarnings("unchecked")
	private Integer saveLogopas(String alias) {
    	NamedParameterJdbcTemplate pstmt = MainAppHolderSingleton.getInstance().getPstmt();
    	TransactionTemplate tmpl = MainAppHolderSingleton.getInstance().getTmpl();
    	Integer userID = -1;

    	HashMap params = new HashMap();
    	params.put("login", alias);
    	params.put("password", password.getText());
    	params.put("gamever", siteVersion.getValue().getId());
    	
    	//Получаем список существующих в БД алиасов
    	List<SimpleComboRecord> aliases = pstmt.query("SELECT ID, ALIAS AS name FROM USERS", params,  new AutoMapper(SimpleComboRecord.class, null));
    	Integer existingAlias = null;
    	
    	//Проверяем существование алиаса в БД
    	for(SimpleComboRecord user : aliases) if(alias.equalsIgnoreCase(user.getName())) existingAlias = user.getId();
    	
    	try {
    		//Если алиас существует, то создаем новый аккаунт и привязываем его к алиасу
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
    		//Иначе создаем нового юзера и аккаунт
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
    
    private void saveSelectedVersion()
    {
    		NamedParameterJdbcTemplate pstmt = MainAppHolderSingleton.getInstance().getPstmt();
    		
    		HashMap<String, Integer> params = new HashMap<String, Integer>();
    		params.put("id", siteVersion.getValue().getId());

    		pstmt.update("UPDATE GAMEVERSIONS SET LASTUSED = 'Y' WHERE ID = :id", params);   	
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
//*******************************************************************************************************************************************
//*******************************************************************************************************************************************
	public ComboBox<RedirectingComboRecord> getSiteVersion() {
		return siteVersion;
	}
	public void setSiteVersion(ComboBox<RedirectingComboRecord> siteVersion) {
		this.siteVersion = siteVersion;
	}
	public ComboBox<SimpleComboRecordExt> getUsername() {
		return username;
	}
	public void setUsername(ComboBox<SimpleComboRecordExt> username) {
		this.username = username;
	}
	public TextField getPassword() {
		return password;
	}
	public void setPassword(TextField password) {
		this.password = password;
	}
	public Button getLoginButton() {
		return loginButton;
	}
	public void setLoginButton(Button loginButton) {
		this.loginButton = loginButton;
	}
	public Text getLoginErrorMsg() {
		return loginErrorMsg;
	}
	public void setLoginErrorMsg(Text loginErrorMsg) {
		this.loginErrorMsg = loginErrorMsg;
	}
	public Text getVersion() {
		return version;
	}
	public void setVersion(Text version) {
		this.version = version;
	}	
}
