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
import ru.teosa.utils.Tools;
import ru.teosa.utils.objects.MainAppHolderSingleton;

public class MoneyConvertorController  {

	@FXML
	private Text moneyCurBalText;       // ������� ������, ���
	@FXML                           
	private Text wheatCurBalText;       // ������� ������, �����
	@FXML                          
	private TextField moneyExpBalField; // ��������� ������, ���
	@FXML                          
	private Text wheatExpBalText;       // ��������� ������, �����
	@FXML  
	private Button convertBtn;
	@FXML  
	private Button copyMoneyCurBal;
	
	private static final String BUY_STORE_URL  = "marche/boutique";
	private static final String SELL_STORE_URL = "marche/boutiqueVendre";

	
	private static final String CONVERT_TEXT = "�����������";
	private static final String STOP_CONVERT_TEXT = "����������";
	private static final String COPY_TEXT = "���������� ������� ������";
	

	
    @FXML
    private void initialize() {
    	//������ ���� ����� (������� ������ ���)
    	initTextField();
    	
    	//������� �� �������� ��������
    	MainAppHolderSingleton.getInstance().getDriver().navigate().to(MainAppHolderSingleton.getGameURL() + BUY_STORE_URL);
    	
    	//���������� ���������� � ����������� �������� � �����
    	setResourcesQty();
    	
    	//��������� ����������� ��������� ��� ������ ����������� ������� �������
    	Tooltip tooltip = new Tooltip();
    	tooltip.setText(COPY_TEXT);
    	copyMoneyCurBal.setTooltip(tooltip);
    }
    
    /** ������/��������� ����������� ���->����� / �����->���*/
    @FXML  
    public void convertButtonHandler() {
    	//���� ���� ������� - ������������� ��� � ������ ����� ��� ��������� ��������� READY( ���������� ��� ���������� ������� )
    	if(MainAppHolderSingleton.getMoneyConverterHandler().isRunning()) 
    	{
    		MainAppHolderSingleton.getMoneyConverterHandler().cancel();
    		MainAppHolderSingleton.getMoneyConverterHandler().reset();
    		convertBtn.setText(CONVERT_TEXT);
    	}
    	//����� - ��������� ����	
    	else 
    	{
    		MainAppHolderSingleton.getMoneyConverterHandler().start();
    		convertBtn.setText(STOP_CONVERT_TEXT);
    	}
    }
    
    @FXML  
    public void copyMoneyCurBalHandler() {
    	moneyExpBalField.setText(Resources.getMoneyBalFromForm().toString());
    }

    private void initTextField() {   	
    	//������ ���������� ��������� ��������. ���� ��������� ������ ������� � ���� ���� �� ���������, ��������� ������ �����������
    	moneyExpBalField.textProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
    	    {
    	    	String nV = newValue.replace(" ", "");
    	    	
    	    	//���� ���������� �������� ������� ������, �������� ��� �� ����������� ��������� ��� Integer
    	    	if(nV.length() > 0) 
    	    	{
        	    	try { Integer.parseInt(nV); }
        	    	catch(NumberFormatException e) { nV = Integer.MAX_VALUE + "";}	
    	    	}
    	    	
    	    	// ������� ��� �������, ������� �� �������� ������
    	        if (!newValue.matches("\\d*")) nV = nV.replaceAll("[^\\d]", "");
    	        
    	        //������ ������, ���� ���� ������
    	        convertBtn.setDisable(nV.length() == 0 || Integer.parseInt(nV) == 0 || Integer.parseInt(nV) == Resources.getMoneyBalFromForm());
    	        
    	        // ������ �������� ������� �����
    	        nV = precalculate(nV);
    	        
    	        moneyExpBalField.setText(nV);
    	    }
    	});
    }
    
    private void setResourcesQty() {
    	try {
    	    WebDriver driver = MainAppHolderSingleton.getInstance().getDriver();
    		
    	    MainAppHolderSingleton.getAccount();
			//�������� ������ ���, ���������� �� ����� � ��������� ���������� � ������� �������
    	    Account.getResources().setMoney(Resources.getMoneyBalFromForm());
        	moneyCurBalText.setText(Tools.numStringWithSpaces(Account.getResources().getMoney()));
        	
        	//�������� ������� �������
        	driver.findElement(By.xpath("//*[@id=\"tab-ressources\"]")).findElement(By.className("tab-action")).click();
        	
        	//�������� ������ �����, ���������� �� ����� � ��������� ���������� � ������� �������
        	String wheatCurBal = driver.findElement(By.xpath("//*[@id=\"ressources-inventory-body-content\"]/div/table[1]/tbody/tr/td[2]/span/span/span[2]/div/span"))
        			             .getText();
        	wheatCurBalText.setText(Tools.numStringWithSpaces(wheatCurBal.replace("x", "")));
        	Account.getResources().setWheat(Integer.parseInt(wheatCurBalText.getText().replaceAll(" ", "")));
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
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
