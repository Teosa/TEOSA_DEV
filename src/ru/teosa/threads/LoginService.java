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
			protected String call() throws Exception 
			{
				String result = null;
				try {
					// Запускаем браузер и переходим на страницу игры выбранной версии
					runWithCrome();
				  	// Устанавливаем ожидания
					System.out.println("driver " + MainAppHolderSingleton.getInstance().getDriver());
					setWaits();
					// Пытаемся авторизоваться  с введенным логопасом
					if(!loginController.accountLogin()) 
						result = "AUTH ERROR";
				} 
				catch (Exception e) {
					Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
					e.printStackTrace();
					result = e.getMessage();
				}
			
				return result;
			}
		};
	}
	
  private void runWithCrome() throws Exception 
  {
	System.setProperty("webdriver.chrome.driver", this.getClass().getResource("/chromedriver.exe").getPath());
	
//	this.mainApp = MainAppHolderSingleton.getInstance().getMainApp();
	String versionURL = loginController.getSiteVersion().getValue().getUrl();
	
	// Устанавливаем драйвер
	MainAppHolderSingleton.getInstance().setDriver(new ChromeDriver());
	
	// Переход по ссылке выбранной версии
	MainAppHolderSingleton.getInstance().getDriver().get(versionURL);
	
  	MainAppHolderSingleton.setGameURL(versionURL);

  } 
   
  private void runWithHeadlessBrowser(){}
  
  private void setWaits() 
  {
	  	Sleeper.setStandartPageLoadTimeout();
	  	Sleeper.setStandartScriptTimeout();
	  	Sleeper.setStandartImplicitlyWait();
  }
// *******************************************************************************************************************************
//*******************************************************************************************************************************
	public LoginController getLoginController() {
		return loginController;
	}
	
	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}
}
