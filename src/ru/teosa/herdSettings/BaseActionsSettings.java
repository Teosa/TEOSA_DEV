package ru.teosa.herdSettings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BaseActionsSettings implements Serializable{
	
	private boolean feed;      // 
	private boolean drink;     // 
	private boolean stroke;    // 
	private boolean groom;     // 
	private boolean carrot;    // 
	private boolean mash;      // 
	private boolean mission;   // 
	private boolean goToSleep; // 
	
	private boolean manualActionsSeqSetting; // 
    private List<String> actionsSeq;         // 
	
	
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
    	actionsSeq =  new ArrayList<String>();
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
	public List<String> getActionsSeq() {
		return actionsSeq;
	}
	public void setActionsSeq(List<String> actionsSeq) {
		this.actionsSeq = actionsSeq;
	}	
}
