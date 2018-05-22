package ru.teosa.threads;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.teosa.account.Resources;


public class MoneyConverterSercice extends Service<String>{
	
	private Stage convertorWin;           // Окно конвертации
	
	private Text moneyCurBalText;         // Поле текущий баланс, экю                         
	private Text wheatCurBalText;         // Поле текущий баланс, зерно
	
	private Integer targetMoneyBallance;  // Целевой баланс экю
	
	private boolean sellWheat;

	@Override
	protected Task<String> createTask() {
		return new Task<String>() {
			@Override
			protected String call() throws Exception {
				
				moneyCurBalText = (Text)convertorWin.getScene().lookup("moneyCurBalText");
				wheatCurBalText = (Text)convertorWin.getScene().lookup("wheatCurBalText");
						
				targetMoneyBallance = Integer.parseInt(((TextField)convertorWin.getScene().lookup("moneyExpBalField")).getText());
				
				sellWheat = Integer.parseInt(moneyCurBalText.getText()) - targetMoneyBallance < 0;
				
				while(!isTargetBalanceReached()) {
					if(sellWheat) sell();
					else buy();
				}
				return null;
			}};
	}
	
	private boolean isTargetBalanceReached() {
		return sellWheat ? Resources.getMoneyBalFromForm() >=  targetMoneyBallance : Resources.getMoneyBalFromForm() <=  targetMoneyBallance;
	}
	
	
	private void buy() {
		
	};
	
	private void sell() {};
	
//******************************************************************************************
//******************************************************************************************
	public Stage getConvertorWin() {
		return convertorWin;
	}
	public void setConvertorWin(Stage convertorWin) {
		this.convertorWin = convertorWin;
	}
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
	public Integer getTargetMoneyBallance() {
		return targetMoneyBallance;
	}
	public void setTargetMoneyBallance(Integer targetMoneyBallance) {
		this.targetMoneyBallance = targetMoneyBallance;
	}

}
