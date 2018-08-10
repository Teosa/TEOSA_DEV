package ru.teosa.utils.objects;

public class ResultObject {

	private int succseed = -1;
	private String errorMsg = "";
	
	
	public int getSuccseed() {
		return succseed;
	}
	public void setSuccseed(int succseed) {
		this.succseed = succseed;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
