package ru.teosa.GUI.view;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ru.teosa.GUI.MainApp;

public class LoginController {
	
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private Button loginButton;
	@FXML
	private Text loginErrorMsg;
	
	private MainApp mainApp;


    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {}
    
    public void setMainApp(MainApp mainApp) {
    	this.mainApp = mainApp;
    }   
    
    @FXML
    public void login(){

    	if(!checkLogopas()) return;

    	if(mainApp.getDriver() == null) runWithCrome();
    	
    	if(accountLogin()) {
        	mainApp.showMainForm();
        	mainApp.getPrimaryStage().sizeToScene();
        	mainApp.getPrimaryStage().centerOnScreen();
    	}
    }
    
    private void runWithCrome(){
	    System.setProperty("webdriver.chrome.driver", this.getClass().getResource("/chromedriver.exe").getPath());
	    
	    mainApp.setDriver(new ChromeDriver());

    	mainApp.getDriver().get("https://www.howrse.com/site/logIn?redirection=/jeu/");

    	mainApp.getDriver().manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    	mainApp.getDriver().manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
    	mainApp.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    	
    } 
     
    private void runWithHeadlessBrowser(){}
    
    private boolean accountLogin(){

    	WebElement loginField = mainApp.getDriver().findElement(By.id("login"));
    	WebElement passwordField = mainApp.getDriver().findElement(By.id("password"));
    	
    	loginField.clear();
    	passwordField.clear();
    	
    	loginField.sendKeys(username.getText());
    	passwordField.sendKeys(password.getText());
    	
    	mainApp.getDriver().findElement(By.id("authentificationSubmit")).click();
    	
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	System.out.println(mainApp.getDriver().getCurrentUrl());
    	
    	if(!mainApp.getDriver().getCurrentUrl().contains("identification")) {
    		loginErrorMsg.setText(mainApp.getDriver().findElement(By.id("fieldError-invalidUser")).getText());
    		return false;
    	}	
    	else {
    		loginErrorMsg.setText("");
    		return true;
    	}
    }
    
    private boolean checkLogopas(){    	
    	if(username.getText().isEmpty() || password.getText().isEmpty()) {
			loginErrorMsg.setText("Введите логин и пароль");
			return false;
    	}
    	else {
    		loginErrorMsg.setText("");
    		return true;
    	}
    }
	
	
}
