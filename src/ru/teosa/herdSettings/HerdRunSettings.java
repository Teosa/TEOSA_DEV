package ru.teosa.herdSettings;

import java.io.Serializable;

import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import ru.teosa.GUI.MainApp;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.RedirectingComboRecordExt;

public class HerdRunSettings implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String URL;
	
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
		MainApp mainApp = MainAppHolderSingleton.getInstance().getMainApp();
		
		if(mainApp != null) {
			Scene scene = mainApp.getPrimaryStage().getScene();			
			TreeView<RedirectingComboRecordExt> treeView = (TreeView<RedirectingComboRecordExt>) scene.lookup("#tree");
			
			this.name = treeView.getSelectionModel().getSelectedItem().getValue().getName();
			this.URL = treeView.getSelectionModel().getSelectedItem().getValue().getURL();
		}
		
		this.registerInEC   = false;
		this.extendEC       = false;
		this.stallionMating = false;
		this.mareMating     = false;
		this.foals          = false;
		
		this.EC_registerSettings   = null;
		this.EC_extendSettings     = null;
		this.stallonMatingSettings = null;
		this.mareMatingSettings    = null;
		this.foalsSettings         = null;

	}
	
	
	
	
//*******************************************************************************************************************************
//*******************************************************************************************************************************
//*******************************************************************************************************************************
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
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
