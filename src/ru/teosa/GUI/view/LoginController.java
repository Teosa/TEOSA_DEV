package ru.teosa.GUI.view;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.teosa.GUI.MainApp;

public class LoginController {
	
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private Button loginButton;
	MainApp mainApp;
    /**
     * �����������.
     * ����������� ���������� ������ ������ initialize().
     */
    public LoginController() {
    }

    /**
     * ������������� ������-�����������. ���� ����� ���������� �������������
     * ����� ����, ��� fxml-���� ����� ��������.
     */
    @FXML
    private void initialize() {
    }
    
    /**
     * ���������� ������� �����������, ������� ��� �� ���� ������.
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
    	this.mainApp = mainApp;
    }
    
    @FXML
    public void testfunc(){

    	  System.setProperty("webdriver.chrome.driver", "L:\\Downloads Opera\\chromedriver_win32\\chromedriver.exe");
    	  WebDriver driver = new ChromeDriver();
    	
    	
//        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
//        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
//    	WebDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME, true);
    	  
    	  
    	driver.get("https://www.howrse.com/site/logIn?redirection=/jeu/");
    	driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    
    	driver.findElement(By.id("login")).sendKeys("Teosa");
    	driver.findElement(By.id("password")).sendKeys("3437391");
    	
    	driver.findElement(By.id("authentificationSubmit")).click();
    	

            
    } 
	
	
	
	
}
