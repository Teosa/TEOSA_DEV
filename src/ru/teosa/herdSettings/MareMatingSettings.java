package ru.teosa.herdSettings;

public class MareMatingSettings {

	private Character coverBy;
	private int maxCoverPrice;
	private int stallonBreedID;
	private String stallonBreedName;
	private int minStallonGP;
	
	
	
	public Character getCoverBy() {
		return coverBy;
	}
	public void setCoverBy(Character coverBy) {
		this.coverBy = coverBy;
	}
	public int getMaxCoverPrice() {
		return maxCoverPrice;
	}
	public void setMaxCoverPrice(int maxCoverPrice) {
		this.maxCoverPrice = maxCoverPrice;
	}
	public int getStallonBreedID() {
		return stallonBreedID;
	}
	public void setStallonBreedID(int stallonBreedID) {
		this.stallonBreedID = stallonBreedID;
	}
	public String getStallonBreedName() {
		return stallonBreedName;
	}
	public void setStallonBreedName(String stallonBreedName) {
		this.stallonBreedName = stallonBreedName;
	}
	public int getMinStallonGP() {
		return minStallonGP;
	}
	public void setMinStallonGP(int minStallonGP) {
		this.minStallonGP = minStallonGP;
	}
}
