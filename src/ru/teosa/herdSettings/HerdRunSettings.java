package ru.teosa.herdSettings;

import java.io.Serializable;

public class HerdRunSettings implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String programName; // Название программы
	
	private CommonSettings        commonSettings;        // Общие настройки
	private BaseActionsSettings   baseActionsSettings;   // Настройки базовых действий
	private EC_Settings           EC_Settings;           // Настройки КСК
	private BreedingSettings      breedingSettings;      // Настройки случек
	
	
	public HerdRunSettings() {
		this.programName    = "Моя программа";
		
		this.commonSettings        = new CommonSettings();
		this.baseActionsSettings   = new BaseActionsSettings();
		this.EC_Settings           = new EC_Settings();
		this.breedingSettings      = new BreedingSettings(); 
	}

//*******************************************************************************************************************************
//*******************************************************************************************************************************
//*******************************************************************************************************************************
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public CommonSettings getCommonSettings() {
		return commonSettings;
	}
	public void setCommonSettings(CommonSettings commonSettings) {
		this.commonSettings = commonSettings;
	}
	public BaseActionsSettings getBaseActionsSettings() {
		return baseActionsSettings;
	}
	public void setBaseActionsSettings(BaseActionsSettings baseActionsSettings) {
		this.baseActionsSettings = baseActionsSettings;
	}
	public EC_Settings getEC_Settings() {
		return EC_Settings;
	}
	public void setEC_Settings(EC_Settings eC_Settings) {
		EC_Settings = eC_Settings;
	}
	public BreedingSettings getBreedingSettings() {
		return breedingSettings;
	}
	public void setBreedingSettings(BreedingSettings breedingSettings) {
		this.breedingSettings = breedingSettings;
	}
}
