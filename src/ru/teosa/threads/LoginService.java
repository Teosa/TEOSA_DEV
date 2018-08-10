package ru.teosa.threads;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import ru.teosa.GUI.view.LoginController;
import ru.teosa.utils.Sleeper;
import ru.teosa.utils.objects.MainAppHolderSingleton;

public class LoginService extends Service<String>{

	private LoginController loginController;
	
	
	
	@Override
	protected Task<String> createTask() {
		return new Task<String>() {
			@Override
			protected String call() throws Exception {
				
//				Button btn = (Button) convertorWin.getScene().lookup("#convertBtn");

//				while (true) 
//				{
					try {
						runWithCrome();


//						if(isCancelled()) break;
						
					} catch (Exception e) {
						Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
					}
//				}

				return "LOGIN SERVICE: work is done";
			}
		};
	}
	
  private void runWithCrome() throws Exception 
  {
	System.setProperty("webdriver.chrome.driver", this.getClass().getResource("/chromedriver.exe").getPath());
	
	// Устанавливаем драйвер
	MainAppHolderSingleton.getInstance().getMainApp().setDriver(new ChromeDriver());
	
	String versionURL = loginController.getSiteVersion().getValue().getUrl();
	// Переход по ссылке выбранной версии
	MainAppHolderSingleton.getInstance().getMainApp().getDriver().get(versionURL);
	
  	MainAppHolderSingleton.setGameURL(versionURL);
  	
  	// Устанавливаем ожидания
  	Sleeper.setStandartPageLoadTimeout();
  	Sleeper.setStandartScriptTimeout();
  	Sleeper.setStandartImplicitlyWait();
  } 
   
  private void runWithHeadlessBrowser(){}

public LoginController getLoginController() {
	return loginController;
}

public void setLoginController(LoginController loginController) {
	this.loginController = loginController;
}
  
  
  
  
}
