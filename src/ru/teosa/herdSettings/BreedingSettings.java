package ru.teosa.herdSettings;

import java.io.Serializable;

public class BreedingSettings implements Serializable{

	// ****** ������� ******
	private Integer matingQty;        // ���������� ������  1/2/3
	private Integer maxMatingQty;     // ������������ ���������� �������� ������ 
	private Integer matingPrice;      // ���� �� ������
	
	// ****** ������ ******
	private Character coverBy;        // �����		                     ������ �����: O		������: null
	private Integer maxCoverPrice;    // ������������ ���� �� ������
	private Character stallonBreed;   // ������ �������                  ��� � ������: M		������: null
	private Character StallonGP;	  // �� �������                      ��� � ������: M		�������: C			������: null
	private Integer minStallonGP;     // ����������� �� �������
	private Integer maxStallonGP;     // ������������ �� �������
	
	// ****** �������� ******
	private String name_M;            // ������ (�������)
	private String name_F;            // ������ (������)
	private Integer affixid;          // ������
	private Integer ECID;             // ID ������
	private String ECName;            // �������� ������
	
	
	public BreedingSettings() {
		matingQty     = 1;
		maxMatingQty  = 1;
		matingPrice   = 500;
		
		coverBy       = null;
		maxCoverPrice = 500;
		stallonBreed  = null;
		
		name_M        = "�������";
		name_F        = "������";
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
