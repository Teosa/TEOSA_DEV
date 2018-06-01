package ru.teosa.herdSettings;

import java.io.Serializable;

public class FoalsSettings implements Serializable{

	private String name_M;
	private String name_F;
	private int affixID;
	private String affixName;
	private int ECID;
	private String ECName;
	
	
	
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
	public int getAffixID() {
		return affixID;
	}
	public void setAffixID(int affixID) {
		this.affixID = affixID;
	}
	public String getAffixName() {
		return affixName;
	}
	public void setAffixName(String affixName) {
		this.affixName = affixName;
	}
	public int getECID() {
		return ECID;
	}
	public void setECID(int eCID) {
		ECID = eCID;
	}
	public String getECName() {
		return ECName;
	}
	public void setECName(String eCName) {
		ECName = eCName;
	}
}
