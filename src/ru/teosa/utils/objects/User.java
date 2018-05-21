package ru.teosa.utils.objects;

public class User {
	private Integer id;
	private String username;
	private String password;
	private Character lastused;
	private Integer accountid;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Character getLastused() {
		return lastused;
	}
	public void setLastused(Character lastused) {
		this.lastused = lastused;
	}
	public Integer getAccountid() {
		return accountid;
	}
	public void setAccountid(Integer accountid) {
		this.accountid = accountid;
	}
}
