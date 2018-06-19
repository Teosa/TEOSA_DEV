package ru.teosa.herdSettings;

import java.io.Serializable;

public class BreedingSettings implements Serializable{

	// ****** ЖЕРЕБЦЫ ******
	private Integer matingQty;        // Количество случек  1/2/3
	private Integer maxMatingQty;     // Максимальное количество активных случек 
	private Integer matingPrice;      // Цена за случку
	
	// ****** КОБЫЛЫ ******
	private Character coverBy;        // Крыть		                     Только моими: O		Любыми: null
	private Integer maxCoverPrice;    // Максимальная цена за случку
	private Character stallonBreed;   // Порода жеребца                  Как у кобылы: M		Любыми: null
	private Character StallonGP;	  // ГП жеребца                      Как у кобылы: M		Указать: C			Любыми: null
	private Integer minStallonGP;     // Минимальный ГП жеребца
	private Integer maxStallonGP;     // Максимальный ГП жеребца
	
	// ****** ЖЕРЕБЯТА ******
	private String name_M;            // Кличка (жеребцы)
	private String name_F;            // Кличка (кобылы)
	private Integer affixid;          // Аффикс
	private Integer ECID;             // ID завода
	private String ECName;            // Название завода
	
	
	public BreedingSettings() {
		matingQty     = 1;
		maxMatingQty  = 1;
		matingPrice   = 500;
		
		coverBy       = null;
		maxCoverPrice = 500;
		stallonBreed  = null;
		
		name_M        = "Жеребец";
		name_F        = "Кобыла";
		affixid       = -1;
		ECID          = -1;
		ECName        = null;
	}
	
	@Override
	public String toString() {
		return ""
				+ "MATING QTY: "       + matingQty     + "; "
				+ "MAXMATING QTY: "    + maxMatingQty  + "; "
				+ "MATING PRICE: "     + matingPrice   + "; "
				+ "COVER BY: "         + coverBy       + "; "
				+ "MAX COVER PRICE: "  + maxCoverPrice + "; "
				+ "STALLON BREED: "    + stallonBreed  + "; "
				+ "STALLONS  NAME: "   + name_M        + "; "
				+ "MARES NAME: "       + name_F        + "; "
				+ "AFFIX ID: "         + affixid       + "; "
				+ "EC ID: "            + ECID          + "; "
				+ "EC NAME: "          + ECName        + "; "	
			;
	}
	
	public Integer getMatingQty() {
		return matingQty;
	}
	public void setMatingQty(Integer matingQty) {
		this.matingQty = matingQty;
	}
	public Integer getMatingPrice() {
		return matingPrice;
	}
	public void setMatingPrice(Integer matingPrice) {
		this.matingPrice = matingPrice;
	}
	public Character getCoverBy() {
		return coverBy;
	}
	public void setCoverBy(Character coverBy) {
		this.coverBy = coverBy;
	}
	public Integer getMaxCoverPrice() {
		return maxCoverPrice;
	}
	public void setMaxCoverPrice(Integer maxCoverPrice) {
		this.maxCoverPrice = maxCoverPrice;
	}
	public Character getStallonBreed() {
		return stallonBreed;
	}
	public void setStallonBreed(Character stallonBreed) {
		this.stallonBreed = stallonBreed;
	}
	public String getName_M() {
		return name_M;
	}
	public void setName_M(String name_M) {
		this.name_M = name_M;
	}
	public String getName_F() {
		return name_F;
	}
	public void setName_F(String name_F) {
		this.name_F = name_F;
	}
	public Integer getECID() {
		return ECID;
	}
	public void setECID(Integer eCID) {
		ECID = eCID;
	}
	public String getECName() {
		return ECName;
	}
	public void setECName(String eCName) {
		ECName = eCName;
	}
	public Character getStallonGP() {
		return StallonGP;
	}
	public void setStallonGP(Character stallonGP) {
		StallonGP = stallonGP;
	}
	public Integer getMinStallonGP() {
		return minStallonGP;
	}
	public void setMinStallonGP(Integer minStallonGP) {
		this.minStallonGP = minStallonGP;
	}
	public Integer getMaxStallonGP() {
		return maxStallonGP;
	}
	public void setMaxStallonGP(Integer maxStallonGP) {
		this.maxStallonGP = maxStallonGP;
	}
	public Integer getMaxMatingQty() {
		return maxMatingQty;
	}
	public void setMaxMatingQty(Integer maxMatingQty) {
		this.maxMatingQty = maxMatingQty;
	}
	public Integer getAffixid() {
		return affixid;
	}
	public void setAffixid(Integer affixid) {
		this.affixid = affixid;
	}
}
