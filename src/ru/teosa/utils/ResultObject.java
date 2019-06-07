package ru.teosa.utils;

public class ResultObject 
{
	private boolean success;
	private String infoMsg;
	private String errMsg;
	
	private String separator = "\n*************************************************\n";
	
	public ResultObject() 
	{
		this.success = true;
		this.infoMsg = "";
		this.errMsg = "";
	}

	public boolean haveInfo() 
	{
		return infoMsg.length() > 0;
	}
	
	public boolean haveErrors() 
	{
		return errMsg.length() > 0;
	}
	
	public void appendInfoMsg( String msg ) 
	{
		if( haveInfo() ) 
		{
			infoMsg += separator + msg;
		}
		else 
		{
			infoMsg = msg;
		}
	}
	
	public void appendErrorMsg( String msg ) 
	{
		if( haveErrors() ) 
		{
			errMsg += separator + msg;
		}
		else 
		{
			errMsg = msg;
		}
	}
	
	
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getInfoMsg() {
		return infoMsg;
	}
	public void setInfoMsg(String infoMsg) {
		this.infoMsg = infoMsg;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
