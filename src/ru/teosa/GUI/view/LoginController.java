package ru.teosa.GUI.view;

import java.io.File;
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
     * Конструктор.
     * Конструктор вызывается раньше метода initialize().
     */
    public LoginController() {
    }

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
    }
    
    /**
     * Вызывается главным приложением, которое даёт на себя ссылку.
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
    	this.mainApp = mainApp;
    }
    
    
    
    @FXML
    public void login(){
//    	System.out.println(this.getClass().getResource("/chromedriver.exe").getPath());
    	runWithCrome();
//    	runWithHeadlessBrowser();
    }
    
    
    
    
    private void runWithCrome(){
//	    System.setProperty("webdriver.chrome.driver", "L:\\Downloads Opera\\chromedriver_win32\\chromedriver.exe");
	    System.setProperty("webdriver.chrome.driver", this.getClass().getResource("/chromedriver.exe").getPath());
	    WebDriver driver = new ChromeDriver();

    	driver.get("https://www.howrse.com/site/logIn?redirection=/jeu/");
    	driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    
    	driver.findElement(By.id("login")).sendKeys("Teosa");
    	driver.findElement(By.id("password")).sendKeys("3437391");    	
    	driver.findElement(By.id("authentificationSubmit")).click();    
    } 
    
    
    private void runWithHeadlessBrowser(){}
//  java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
//  java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
//	WebDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME, true);
	
	
}
