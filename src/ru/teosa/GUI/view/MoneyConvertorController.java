package ru.teosa.GUI.view;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ru.teosa.utils.Tools;
import ru.teosa.utils.objects.MainAppHolderSingleton;

public class MoneyConvertorController  {

	@FXML
	private Text moneyCurBalText;       // Текущий баланс, экю
	@FXML                           
	private Text wheatCurBalText;       // Текущий баланс, зерно
	@FXML                          
	private TextField moneyExpBalField; // Ожидаемый баланс, экю
	@FXML                          
	private Text wheatExpBalText;       // Ожидаемый баланс, зерно
	@FXML  
	private Button convertBtn;
	
	private static final String STORE_URL = "marche/boutique";
	
	private static final String CONVERT_TEXT = "Конвертация";
	private static final String STOP_CONVERT_TEXT = "Остановить";
	

	
    @FXML
    private void initialize() {
    	//Конфиг поля ввода (целевой баланс экю)
    	initTextField();
    	
    	//Переход на страницу магазина
    	MainAppHolderSingleton.getInstance().getDriver().navigate().to(MainAppHolderSingleton.getGameURL() + STORE_URL);
    	
    	//Подгружаем информацию о колличестве ресурсов в форму
    	setResourcesQty();
    	
    }
    
    /** Запуск/остановка конвертации экю->зерно / зерно->экю*/
    @FXML  
    public void convertButtonHandler() {
    	//Если тред запущен - останавливаем его и делаем ресет для установки состояния READY( необходимо для повторного запуска )
    	if(MainAppHolderSingleton.getMoneyConverterHandler().isRunning()) 
    	{
    		MainAppHolderSingleton.getMoneyConverterHandler().cancel();
    		MainAppHolderSingleton.getMoneyConverterHandler().reset();
    		convertBtn.setText(CONVERT_TEXT);
    	}
    	//Иначе - запускаем тред	
    	else 
    	{
    		MainAppHolderSingleton.getMoneyConverterHandler().start();
    		convertBtn.setText(STOP_CONVERT_TEXT);
    	}
    }

    private void initTextField() {   	
    	//Вешаем обработчик введенных значений. Ввод ограничен только цифрами и если поле не заполнено, блокируем кнопку конвертации
    	moneyExpBalField.textProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
    	    {
    	        if (!newValue.matches("\\d*")) {
    	        	moneyExpBalField.setText(newValue.replaceAll("[^\\d]", ""));
    	        }
    	        
    	        convertBtn.setDisable(moneyExpBalField.getText().length() == 0);
    	    }
    	});
    }
    
    private void setResourcesQty() {
    	try {
    	    WebDriver driver = MainAppHolderSingleton.getInstance().getDriver();
    		
    	    //Получаем баланс экю и отображаем на форме
        	String moneyCurBal = driver.findElement(By.xpath("//*[@id=\"reserve\"]")).getAttribute("data-amount");
        	moneyCurBalText.setText(Tools.numStringWithSpaces(moneyCurBal));
        	
        	//Выбираем вкладку ресурсы
        	driver.findElement(By.xpath("//*[@id=\"tab-ressources\"]")).findElement(By.className("tab-action")).click();
        	
        	//Получаем баланс зерна и отображаем на форме
        	String wheatCurBal = driver.findElement(By.xpath("//*[@id=\"ressources-inventory-body-content\"]/div/table[1]/tbody/tr/td[2]/span/span/span[2]/div/span"))
        			             .getText();
        	wheatCurBalText.setText(Tools.numStringWithSpaces(wheatCurBal.replace("x", "")));
        	
   		
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
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
