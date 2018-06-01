package ru.teosa.herdSettings;

import java.io.Serializable;

public class EC_extendSettings implements Serializable{

	private int daysBeforeCheckout;
	private int extendTerm;
	private boolean onlyMyECExtend;
	
	
	public EC_extendSettings() {
		this.daysBeforeCheckout = 1;
		this.extendTerm = 3;
		this.onlyMyECExtend = true;
	}
	
	
	public int getDaysBeforeCheckout() {
		return daysBeforeCheckout;
	}
	public void setDaysBeforeCheckout(int daysBeforeCheckout) {
		this.daysBeforeCheckout = daysBeforeCheckout;
	}
	public int getExtendTerm() {
		return extendTerm;
	}
	public void setExtendTerm(int extendTerm) {
		this.extendTerm = extendTerm;
	}
	public boolean isOnlyMyECExtend() {
		return onlyMyECExtend;
	}
	public void setOnlyMyECExtend(boolean onlyMyECExtend) {
		this.onlyMyECExtend = onlyMyECExtend;
	}	
}
