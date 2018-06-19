package ru.teosa.herdSettings;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import ru.teosa.GUI.MsgWindow;
import ru.teosa.utils.Queries;
import ru.teosa.utils.objects.MainAppHolderSingleton;

public class HerdRunSettings implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer programID;  // ID программы
	private String programName; // Название программы
	
	private CommonSettings        commonSettings;        // Общие настройки
	private BaseActionsSettings   baseActionsSettings;   // Настройки базовых действий
	private EC_Settings           EC_Settings;           // Настройки КСК
	private BreedingSettings      breedingSettings;      // Настройки случек
	
	
	public HerdRunSettings() {
		this.programID      = -1;
		this.programName    = null;
		
		this.commonSettings        = new CommonSettings();
		this.baseActionsSettings   = new BaseActionsSettings();
		this.EC_Settings           = new EC_Settings();
		this.breedingSettings      = new BreedingSettings(); 
	}

	
	public boolean save() {
		try {
			System.out.println("SAVE SETTINGS");
			System.out.println(this.toString());
			
			byte[] settings = serialize();
			
			if(settings != null && settings.length > 0) {
				
				NamedParameterJdbcTemplate pstmt = MainAppHolderSingleton.getInstance().getPstmt();
				HashMap params = new HashMap();
				
				params.put("id",       programID);
				params.put("name",     programName);
				params.put("settings", settings);
				
				
				if(programID == -1) pstmt.update(Queries.SAVE_HERD_RUN_PROGRAM, params);
				else pstmt.update(Queries.UPD_HERD_RUN_PROGRAM, params);
			}
			
			return true;
		}		
		catch(Exception e) {
			e.printStackTrace();
			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
			MsgWindow.setErrorMsg(e.getMessage());
			return false;
		}
	};
	
	@Override
	public String toString() {
		return ""
				+ "PROGRAM ID: "            + programID + ";\n"
				+ "PROGRAM NAME: "          + programName + ";\n"
				+ "COMMON SETTINGS: "       + commonSettings.toString() + "\n"
				+ "BASE ACTIONS SETTINGS: " + baseActionsSettings.toString() + "\n"
				+ "EC SETTINGS: "           + EC_Settings.toString() + "\n"
				+ "BREEDING SETTINGS: "     + breedingSettings.toString(); 
	}
	
	private byte[] serialize() {
		try {
			return  SerializationUtils.serialize(this);
		}
		catch(Exception e) {
			e.printStackTrace();
			Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
			return null;
		}
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
	public Integer getProgramID() {
		return programID;
	}
	public void setProgramID(Integer programID) {
		this.programID = programID;
	}
}
