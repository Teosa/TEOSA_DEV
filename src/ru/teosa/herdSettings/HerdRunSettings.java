package ru.teosa.herdSettings;

import java.io.Serializable;

public class HerdRunSettings implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String programName; // �������� ���������
	
	private CommonSettings        commonSettings;        // ����� ���������
	private BaseActionsSettings   baseActionsSettings;   // ��������� ������� ��������
	private EC_Settings           EC_Settings;           // ��������� ���
	private StallonMatingSettings stallonMatingSettings; // ��������� ������ ��� ��������
	private MareMatingSettings    mareMatingSettings;    // ��������� ������ ��� �����
	private FoalsSettings         foalsSettings;         // ��������� �������� �������
	
	
	public HerdRunSettings() {
		this.programName    = "��� ���������";
		
		this.baseActionsSettings   = new BaseActionsSettings();
		this.EC_Settings           = new EC_Settings();
		this.stallonMatingSettings = new StallonMatingSettings();
		this.mareMatingSettings    = new MareMatingSettings();
		this.foalsSettings         = new FoalsSettings();
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
