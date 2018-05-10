package ru.teosa.herdSettings;

public class EC_registerSettings {
	
	private Character EC_type;          //Тип КСК для записи     Свой: "O"   Зарезервированные стойла: "R"   Любой: null
	private int regTerm;
	private Character location;         //Расположение           Лес: "F"        Горы: "M"      Пляж: "B"    Любое: null
	private Character specialization;   //Специализация          Классика: "C"   Вестерн: "W"   Любая: null
	
	//Преимущества:
	private boolean hay;
	private boolean oat;
	private boolean carrot;
	private boolean mash;
	private boolean drinker;
	private boolean shower;
	
	
	public EC_registerSettings() {
		this.EC_type = null;          
		this.regTerm = 3;
		this.location = null;        
		this.specialization = null;   
		
		this.hay     = false;
		this.oat     = false;
		this.carrot  = false;
		this.mash    = false;
		this.drinker = false;
		this.shower  = false;
	}
	
	
	public Character getEC_type() {
		return EC_type;
	}
	public void setEC_type(Character eC_type) {
		EC_type = eC_type;
	}
	public int getRegTerm() {
		return regTerm;
	}
	public void setRegTerm(int regTerm) {
		this.regTerm = regTerm;
	}
	public Character getLocation() {
		return location;
	}
	public void setLocation(Character location) {
		this.location = location;
	}
	public Character getSpecialization() {
		return specialization;
	}
	public void setSpecialization(Character specialization) {
		this.specialization = specialization;
	}
	public boolean isHay() {
		return hay;
	}
	public void setHay(boolean hay) {
		this.hay = hay;
	}
	public boolean isOat() {
		return oat;
	}
	public void setOat(boolean oat) {
		this.oat = oat;
	}
	public boolean isCarrot() {
		return carrot;
	}
	public void setCarrot(boolean carrot) {
		this.carrot = carrot;
	}
	public boolean isMash() {
		return mash;
	}
	public void setMash(boolean mash) {
		this.mash = mash;
	}
	public boolean isDrinker() {
		return drinker;
	}
	public void setDrinker(boolean drinker) {
		this.drinker = drinker;
	}
	public boolean isShower() {
		return shower;
	}
	public void setShower(boolean shower) {
		this.shower = shower;
	}
}
