package ru.teosa.utils.objects;

import org.openqa.selenium.WebDriver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import ru.teosa.GUI.MainApp;
import ru.teosa.account.Account;
import ru.teosa.threads.HerdRunService;
import ru.teosa.threads.MoneyConverterSercice;

public class MainAppHolderSingleton {
    private static MainAppHolderSingleton _instance = null;
    
    private MainApp mainApp;                               //������ �� 
    private WebDriver driver;                              //�������
    
    private static final Account account = new Account();  // ���������� � �����, �������� � �.�
    private static final String VER = "Version 1.4.0";     // ������ ����������
    private static String gameURL;                         // ������ �� ������� �������� ���� ��������� ������
    
    // �����
    private static MoneyConverterSercice moneyConverterService = new MoneyConverterSercice();
    private static HerdRunService herdRunService = new HerdRunService();
    
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
		return moneyConverterService;
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
	public static HerdRunService getHerdRunService() {
		return herdRunService;
	}
}
