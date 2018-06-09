package ru.teosa.herdSettings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BaseActionsSettings implements Serializable{
	
	private boolean feed;      // Кормить
	private boolean drink;     // Поить
	private boolean stroke;    // Ласка
	private boolean groom;     // Чистить
	private boolean carrot;    // Морковь
	private boolean mash;      // Комбикорм
	private boolean mission;   // Миссия
	private boolean goToSleep; // Отправить спать
	
	private boolean manualActionsSeqSetting; // Ручная настройка порядка действий
    private List<Character> actionsSeq;         // Порядок действий
	
	
    public BaseActionsSettings() {
    	feed      = true;
    	drink     = true;
    	stroke    = false;
    	groom     = true;
    	carrot    = false;
    	mash      = false;
    	mission   = true;
    	goToSleep = true;
    	
    	manualActionsSeqSetting = false;
    	actionsSeq =  new ArrayList<Character>();
    }
    
    @Override
    public String toString() {
    	return ""
    		+ "FEED: "             + feed                    + "; "
    		+ "DRINK: "            + drink                   + "; "
    		+ "STROKE: "           + stroke                  + "; "
    		+ "GROOM: "            + groom                   + "; "
    		+ "CARROT: "           + carrot                  + "; "
    		+ "MASH: "             + mash                    + "; "
    		+ "MISSION: "          + mission                 + "; "
    		+ "GOTOSLEEP: "        + goToSleep               + "; "
    		+ "MANUALACTIONSSEQ: " + manualActionsSeqSetting + "; "
    	    + "ACTIONSSEQ: "       + actionsSeq.toString()   + "; "
		;
    }
    
	public boolean isFeed() {
		return feed;
	}
	public void setFeed(boolean feed) {
		this.feed = feed;
	}
	public boolean isDrink() {
		return drink;
	}
	public void setDrink(boolean drink) {
		this.drink = drink;
	}
	public boolean isStroke() {
		return stroke;
	}
	public void setStroke(boolean stroke) {
		this.stroke = stroke;
	}
	public boolean isGroom() {
		return groom;
	}
	public void setGroom(boolean groom) {
		this.groom = groom;
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
	public boolean isMission() {
		return mission;
	}
	public void setMission(boolean mission) {
		this.mission = mission;
	}
	public boolean isGoToSleep() {
		return goToSleep;
	}
	public void setGoToSleep(boolean goToSleep) {
		this.goToSleep = goToSleep;
	}
	public boolean isManualActionsSeqSetting() {
		return manualActionsSeqSetting;
	}
	public void setManualActionsSeqSetting(boolean manualActionsSeqSetting) {
		this.manualActionsSeqSetting = manualActionsSeqSetting;
	}
	public List<Character> getActionsSeq() {
		return actionsSeq;
	}
	public void setActionsSeq(List<Character> actionsSeq) {
		this.actionsSeq = actionsSeq;
	}
}
