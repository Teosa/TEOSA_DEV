package ru.teosa.herdSettings;

import java.io.Serializable;

public class CommonSettings implements Serializable{
	private boolean baseActions;
	private boolean registerInEC;
	private boolean extendEC;
	private boolean stallionMating;
	private boolean mareMating;
	private boolean foals;
	
	
	public CommonSettings() {
		this.baseActions    = true;
		this.registerInEC   = false;
		this.extendEC       = false;
		this.stallionMating = false;
		this.mareMating     = false;
		this.foals          = false;
	}
	
	@Override
	public String toString() {
		return ""
				+ "BASE ACTIONS: "    + baseActions    + "; "
				+ "REGISTER IN EC: "  + registerInEC   + "; "
				+ "EXTEND EC: "       + extendEC       + "; "
				+ "STALLION MATING: " + stallionMating + "; "
				+ "MARE MATING: "     + mareMating     + "; "
				+ "FOALS: "           + foals          + "; ";
	}
	
	public boolean isBaseActions() {
		return baseActions;
	}
	public void setBaseActions(boolean baseActions) {
		this.baseActions = baseActions;
	}
	public boolean isRegisterInEC() {
		return registerInEC;
	}
	public void setRegisterInEC(boolean registerInEC) {
		this.registerInEC = registerInEC;
	}
	public boolean isExtendEC() {
		return extendEC;
	}
	public void setExtendEC(boolean extendEC) {
		this.extendEC = extendEC;
	}
	public boolean isStallionMating() {
		return stallionMating;
	}
	public void setStallionMating(boolean stallionMating) {
		this.stallionMating = stallionMating;
	}
	public boolean isMareMating() {
		return mareMating;
	}
	public void setMareMating(boolean mareMating) {
		this.mareMating = mareMating;
	}
	public boolean isFoals() {
		return foals;
	}
	public void setFoals(boolean foals) {
		this.foals = foals;
	}
}
