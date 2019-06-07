package ru.teosa.herdSettings;

import java.io.Serializable;

public class EC_Settings implements Serializable{
	private static final long serialVersionUID = -2806583952726248864L;
	
	// ****** ЗАПИСЬ В КСК ******
	private Character EC_type;          //Тип КСК для записи     Свой: "O"   Зарезервированные стойла: "R"   Любой: null
	private Integer regTerm;
	private Character location;         //Расположение           Лес: "F"        Горы: "M"      Пляж: "B"    Любое: null
	private Character specialization;   //Специализация          Классика: "C"   Вестерн: "W"   Любая: null
	
	//Преимущества:
	private boolean hay;
	private boolean oat;
	private boolean carrot;
	private boolean mash;
	private boolean drinker;
	private boolean shower;
	
	private Integer maxTariff;         // Максимальный тариф за день постоя
	
	
	// ****** ПРОДЛЕНИЕ ПОСТОЯ ******
	private Integer daysBeforeCheckout;
	private Integer extendTerm;
	private boolean onlyMyECExtend;
	
	
	public EC_Settings() {
		
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
		
		this.maxTariff = 0;
		
		this.daysBeforeCheckout = 1;
		this.extendTerm = 3;
		this.onlyMyECExtend = true;
	}

	@Override
	public String toString() {
		return ""
			+ "EC_TYPE: "            + EC_type            + "; "
			+ "REGTERM: "            + regTerm            + "; "
			+ "LOCATION: "           + location           + "; "
			+ "SPECIALIZATION: "     + specialization     + "; "
			+ "HAY: "                + hay                + "; "
			+ "OAT: "                + oat                + "; "
			+ "CARROT: "             + carrot             + "; "
			+ "MASH: "               + mash               + "; "
			+ "DRINKER: "            + drinker            + "; "
			+ "SHOWER: "             + shower             + "; "
			+ "DAYSBEFORECHECKOUT: " + daysBeforeCheckout + "; "
			+ "EXTENDTERM: "         + extendTerm         + "; "
			+ "ONLYMYECEXTEND: "     + onlyMyECExtend     + "; "
			+ "MAXTARIFF: "          + maxTariff          + "; "
		;
	}
	
	public Character getEC_type() {
		return EC_type;
	}
	public void setEC_type(Character eC_type) {
		EC_type = eC_type;
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
	public boolean isOnlyMyECExtend() {
		return onlyMyECExtend;
	}
	public void setOnlyMyECExtend(boolean onlyMyECExtend) {
		this.onlyMyECExtend = onlyMyECExtend;
	}
	public Integer getRegTerm() {
		return regTerm;
	}
	public void setRegTerm(Integer regTerm) {
		this.regTerm = regTerm;
	}
	public Integer getDaysBeforeCheckout() {
		return daysBeforeCheckout;
	}
	public void setDaysBeforeCheckout(Integer daysBeforeCheckout) {
		this.daysBeforeCheckout = daysBeforeCheckout;
	}
	public Integer getExtendTerm() {
		return extendTerm;
	}
	public void setExtendTerm(Integer extendTerm) {
		this.extendTerm = extendTerm;
	}
	public Integer getMaxTariff() {
		return maxTariff;
	}
	public void setMaxTariff(Integer maxTariff) {
		this.maxTariff = maxTariff;
	}
}
