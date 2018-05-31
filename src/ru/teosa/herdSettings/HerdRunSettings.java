package ru.teosa.herdSettings;

import java.io.Serializable;

public class HerdRunSettings implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private boolean registerInEC;
	private EC_registerSettings EC_registerSettings; 
	
	private boolean extendEC;
	private EC_extendSettings EC_extendSettings;
	
	private boolean stallionMating;
	private StallonMatingSettings stallonMatingSettings;
	
	private boolean mareMating;
	private MareMatingSettings mareMatingSettings;
	
	private boolean foals;
	private FoalsSettings foalsSettings;
	
	
	public HerdRunSettings() {
		this.registerInEC   = false;
		this.extendEC       = false;
		this.stallionMating = false;
		this.mareMating     = false;
		this.foals          = false;
		
		this.EC_registerSettings   = new EC_registerSettings();
		this.EC_extendSettings     = new EC_extendSettings();
		this.stallonMatingSettings = new StallonMatingSettings();
		this.mareMatingSettings    = new MareMatingSettings();
		this.foalsSettings         = new FoalsSettings();
	}
	
	
	
	
//*******************************************************************************************************************************
//*******************************************************************************************************************************
//*******************************************************************************************************************************
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
	public EC_registerSettings getEC_registerSettings() {
		return EC_registerSettings;
	}
	public void setEC_registerSettings(EC_registerSettings eC_registerSettings) {
		EC_registerSettings = eC_registerSettings;
	}
	public EC_extendSettings getEC_extendSettings() {
		return EC_extendSettings;
	}
	public void setEC_extendSettings(EC_extendSettings eC_extendSettings) {
		EC_extendSettings = eC_extendSettings;
	}
	public StallonMatingSettings getStallonMatingSettings() {
		return stallonMatingSettings;
	}
	public void setStallonMatingSettings(StallonMatingSettings stallonMatingSettings) {
		this.stallonMatingSettings = stallonMatingSettings;
	}
	public MareMatingSettings getMareMatingSettings() {
		return mareMatingSettings;
	}
	public void setMareMatingSettings(MareMatingSettings mareMatingSettings) {
		this.mareMatingSettings = mareMatingSettings;
	}
	public FoalsSettings getFoalsSettings() {
		return foalsSettings;
	}
	public void setFoalsSettings(FoalsSettings foalsSettings) {
		this.foalsSettings = foalsSettings;
	}
}
