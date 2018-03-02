package ru.teosa.utils.objects;

import org.openqa.selenium.WebDriver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import ru.teosa.GUI.MainApp;

public class MainAppHolderSingleton {
    private static MainAppHolderSingleton _instance = null;
    private MainApp mainApp;
    private WebDriver driver ;
    NamedParameterJdbcTemplate pstmt;
 
    public MainApp getMainApp() {
		return mainApp;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		if(mainApp.getDriver() != null) setDriver(mainApp.getDriver());
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	private MainAppHolderSingleton() {}

    public static synchronized MainAppHolderSingleton getInstance() {
        if (_instance == null)
            _instance = new MainAppHolderSingleton();
        return _instance;
    }

	public NamedParameterJdbcTemplate getPstmt() {
		return pstmt;
	}

	public void setPstmt(NamedParameterJdbcTemplate pstmt) {
		this.pstmt = pstmt;
	}


}
