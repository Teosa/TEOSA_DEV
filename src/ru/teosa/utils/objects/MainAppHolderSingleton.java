package ru.teosa.utils.objects;

import org.openqa.selenium.WebDriver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import ru.teosa.GUI.MainApp;
import ru.teosa.account.Account;
import ru.teosa.threads.MoneyConverterSercice;

public class MainAppHolderSingleton {
    private static MainAppHolderSingleton _instance = null;
    
    private MainApp mainApp;                               //Ссылка на 
    private WebDriver driver;                              //Драйвер
    
    private static final Account account = new Account();  // Информация о юзере, аккаунте и т.д
    private static final String VER = "Version 1.2.3-dev";     // Версия приложения
    private static String gameURL;                         // Ссылка на главную страницу игры выбранной версии
    
    // --
    private static MoneyConverterSercice moneyConverterHandler = new MoneyConverterSercice();
    
    NamedParameterJdbcTemplate pstmt;
    TransactionTemplate tmpl;
 
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
	public static String getVer() {
		return VER;
	}
	public TransactionTemplate getTmpl() {
		return tmpl;
	}
	public void setTmpl(TransactionTemplate tmpl) {
		this.tmpl = tmpl;
	}
	public static MoneyConverterSercice getMoneyConverterHandler() {
		return moneyConverterHandler;
	}
	public static String getGameURL() {
		return gameURL;
	}
	public static void setGameURL(String gameURL) {
		MainAppHolderSingleton.gameURL = gameURL;
	}
	public static Account getAccount() {
		return account;
	}
}
