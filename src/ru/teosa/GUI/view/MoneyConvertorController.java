package ru.teosa.GUI.view;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import ru.teosa.account.Account;
import ru.teosa.account.Resources;
import ru.teosa.utils.Msgs;
import ru.teosa.utils.Tools;
import ru.teosa.utils.objects.MainAppHolderSingleton;

/** Контроллер модуля Конвертация */
public class MoneyConvertorController  {

	@FXML private Text moneyCurBalText;       // Текущий баланс, экю
	@FXML private Text wheatCurBalText;       // Текущий баланс, зерно
	@FXML private TextField moneyExpBalField; // Ожидаемый баланс, экю
	@FXML private Text wheatExpBalText;       // Ожидаемый баланс, зерно
	@FXML private Button convertBtn;
	@FXML private Button copyMoneyCurBal;
	
	public static final String BUY_STORE_URL  = "marche/boutique";
	public static final String SELL_STORE_URL = "marche/boutiqueVendre";
	
    @FXML
    private void initialize() {
    	//Конфиг поля ввода (целевой баланс экю)
    	initTextField();
    	
    	//Переход на страницу магазина
    	MainAppHolderSingleton.getInstance().getDriver().navigate().to(MainAppHolderSingleton.getGameURL() + BUY_STORE_URL);
    	
    	//Подгружаем информацию о колличестве ресурсов в форму
    	setResourcesQty();
    	
    	//Добавляем всплывающую подсказку для кнопки копирования текщего баланса
    	Tooltip tooltip = new Tooltip();
    	tooltip.setText(Msgs.COPY_TEXT);
    	copyMoneyCurBal.setTooltip(tooltip);
    }
    
    /** Запуск/остановка конвертации экю->зерно / зерно->экю*/
    @FXML  
    public void convertButtonHandler() {
    	//Если тред запущен - останавливаем его и делаем ресет для установки состояния READY( необходимо для повторного запуска )
    	if(MainAppHolderSingleton.getMoneyConverterHandler().isRunning()) 
    	{
    		MainAppHolderSingleton.getMoneyConverterHandler().cancel();
    		MainAppHolderSingleton.getMoneyConverterHandler().reset();
    		convertBtn.setText(Msgs.CONVERT_TEXT);
    	}
    	//Иначе - запускаем тред	
    	else 
    	{
    		if(MainAppHolderSingleton.getMoneyConverterHandler().getState().toString() == "SUCCEEDED") 
    			MainAppHolderSingleton.getMoneyConverterHandler().restart();
    		else MainAppHolderSingleton.getMoneyConverterHandler().start();
    		
    		convertBtn.setText(Msgs.STOP_CONVERT_TEXT);
    	}
    }
    
    @FXML  
    public void copyMoneyCurBalHandler() {
    	moneyExpBalField.setText(Resources.getMoneyBalFromForm().toString());
    }

    private void initTextField() {   	
    	//Вешаем обработчик введенных значений. Ввод ограничен только цифрами и если поле не заполнено, блокируем кнопку конвертации
    	moneyExpBalField.textProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
    	    {
    	    	String nV = newValue.replace(" ", "");
    	    	
    	    	//Если переданное значение слишком велико, заменяем его на максимально возможное для Integer
    	    	if(nV.length() > 0) 
    	    	{
        	    	try { Integer.parseInt(nV); }
        	    	catch(NumberFormatException e) { nV = Integer.MAX_VALUE + "";}	
    	    	}
    	    	
    	    	// Удаляем все символы, которые не являются цифрой
    	        if (!newValue.matches("\\d*")) nV = nV.replaceAll("[^\\d]", "");
    	        
    	        //Блочим кнопку, если поле пустое
    	        convertBtn.setDisable(nV.length() == 0 || Integer.parseInt(nV) == 0 || Integer.parseInt(nV) == Resources.getMoneyBalFromForm());
    	        
    	        // Расчет целевого баланса зерна
    	        nV = precalculate(nV);
    	        
    	        moneyExpBalField.setText(nV);
    	    }
    	});
    }
    
    private void setResourcesQty() {
    	try {
    	    WebDriver driver = MainAppHolderSingleton.getInstance().getDriver();
    		
    	    MainAppHolderSingleton.getAccount();
			//Получаем баланс экю, отображаем на форме и обновляем информацию в объекте Аккаунт
    	    Account.getResources().setMoney(Resources.getMoneyBalFromForm());
        	moneyCurBalText.setText(Tools.numStringWithSpaces(Account.getResources().getMoney()));
        	
        	//Выбираем вкладку ресурсы
        	driver.findElement(By.xpath("//*[@id=\"tab-ressources\"]")).findElement(By.className("tab-action")).click();
        	
        	//Получаем баланс зерна, отображаем на форме и обновляем информацию в объекте Аккаунт
        	 Account.getResources().setWheat(Resources.getWheatBalFromForm());
        	 wheatCurBalText.setText(Tools.numStringWithSpaces(Account.getResources().getWheat()));
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    // Рассчитываем и отображаем на форме ожидаемый баланс зерна, в зависимости от введенного значения  в поле "Целевой баланс экю".
    private String precalculate(String moneyExpBal) {
    	/*Current money balance*/  Integer cmb = Account.getResources().getMoney();
    	/*Expected money balance*/ Integer emb = moneyExpBal.length() > 0 ? Integer.parseInt(moneyExpBal) : 0;
    	/*Current wheat balance*/  Integer cwb = Account.getResources().getWheat();
    	/*Expected wheat balance*/ Integer ewb = -1;   	
    	
    	if(emb > cmb) 
    	{
    		if(cwb >= (emb - cmb)) ewb = cwb - (emb - cmb);
    		else emb = cmb + cwb;
    	}
    	else 
    	{
    		ewb = cwb + (cmb - emb);
    	}
    	
    	if(ewb == -1) 
    	{
    		ewb = cmb + cwb;
    	}
    	
    	wheatExpBalText.setText(Tools.numStringWithSpaces(ewb.toString()));
    	
    	return emb.toString();

    }
    
//******************************************************************************************************************************  
//******************************************************************************************************************************
	public Text getMoneyCurBalText() {
		return moneyCurBalText;
	}
	public void setMoneyCurBalText(Text moneyCurBalText) {
		this.moneyCurBalText = moneyCurBalText;
	}
	public Text getWheatCurBalText() {
		return wheatCurBalText;
	}
	public void setWheatCurBalText(Text wheatCurBalText) {
		this.wheatCurBalText = wheatCurBalText;
	}
	public TextField getMoneyExpBalField() {
		return moneyExpBalField;
	}
	public void setMoneyExpBalField(TextField moneyExpBalField) {
		this.moneyExpBalField = moneyExpBalField;
	}
	public Text getWheatExpBalText() {
		return wheatExpBalText;
	}
	public void setWheatExpBalText(Text wheatExpBalText) {
		this.wheatExpBalText = wheatExpBalText;
	}
}
