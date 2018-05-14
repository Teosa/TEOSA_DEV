package ru.teosa.GUI.view;

import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import javafx.concurrent.*;

public class MoneyConvertorController  {

	@FXML
	private Text moneyCurBal;      // Текущий баланс, экю
	@FXML                           
	private Text wheatCurBal;      // Текущий баланс, зерно
	@FXML                          
	private TextField moneyExpBal; // Ожидаемый баланс, экю
	@FXML                          
	private Text wheatExpBal;      // Ожидаемый баланс, зерно
	@FXML  
	private Button convertBtn;
	
	private static final String storeURL = "https://www.howrse.com/marche/boutique";
	
    @FXML
    private void initialize() {
    	System.out.println("ddddddddd");
    	//MainAppHolderSingleton.getInstance().getDriver()
    	
    	
    }
    
    @FXML  
    public void convertButtonHandler() {}

//******************************************************************************************************************************  
//******************************************************************************************************************************
	public Text getMoneyCurBal() {
		return moneyCurBal;
	}
	public void setMoneyCurBal(Text moneyCurBal) {
		this.moneyCurBal = moneyCurBal;
	}
	public Text getWheatCurBal() {
		return wheatCurBal;
	}
	public void setWheatCurBal(Text wheatCurBal) {
		this.wheatCurBal = wheatCurBal;
	}
	public TextField getMoneyExpBal() {
		return moneyExpBal;
	}
	public void setMoneyExpBal(TextField moneyExpBal) {
		this.moneyExpBal = moneyExpBal;
	}
	public Text getWheatExpBal() {
		return wheatExpBal;
	}
	public void setWheatExpBal(Text wheatExpBal) {
		this.wheatExpBal = wheatExpBal;
	}
}
