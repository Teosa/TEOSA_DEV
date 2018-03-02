package ru.teosa.GUI.view;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ru.teosa.GUI.MainApp;
import ru.teosa.utils.Customizer;
import ru.teosa.utils.Sleeper;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.SimpleComboRecord;

public class LoginController {
	
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private Button loginButton;
	@FXML
	private Text loginErrorMsg;
	@FXML
	private ComboBox<SimpleComboRecord> siteVersion;
	
	private MainApp mainApp;


    /**
     * ������������� ������-�����������. ���� ����� ���������� �������������
     * ����� ����, ��� fxml-���� ����� ��������.
     */
    @FXML
    private void initialize() {
    	new Customizer().CustomizeCB(siteVersion, false);
    	siteVersion.getItems().addAll(
	    			new SimpleComboRecord("RUS", "https://www.lowadi.ru/", null),
	    			new SimpleComboRecord("INTERNATIONAL", "https://www.howrse.com/site/logIn?redirection=/jeu/", null)
    			);
    	siteVersion.setValue(siteVersion.getItems().get(0));
    }
    
    public void setMainApp(MainApp mainApp) {
    	this.mainApp = mainApp;
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

    	if(!checkLogopas()) return;
    	if(mainApp.getDriver() == null) runWithCrome();
    	if(accountLogin()) mainApp.showMainForm(); 
    	
    	MainAppHolderSingleton.getInstance().setMainApp(mainApp);
    }
    
    private void runWithCrome(){
	    System.setProperty("webdriver.chrome.driver", this.getClass().getResource("/chromedriver.exe").getPath());
	    
	    mainApp.setDriver(new ChromeDriver());

    	mainApp.getDriver().get(siteVersion.getValue().getURL());

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

	    	loginField.sendKeys(username.getText());
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
			loginErrorMsg.setText("������ �����������");
			return false;
		}
    }
    
    private boolean checkLogopas(){    	
    	if(username.getText().isEmpty() || password.getText().isEmpty()) {
			loginErrorMsg.setText("������� ����� � ������");
			return false;
    	}
    	else {
    		loginErrorMsg.setText("");
    		return true;
    	}
    }
	
	
}
