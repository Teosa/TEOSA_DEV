package ru.teosa.account;

import ru.teosa.utils.objects.User;

public class Account {

	private static User user;
	private static Resources resources;
	
	static {
		user = new User();
		resources = new Resources();
	}
	
	
	public static User getUser() {
		return user;
	}
	public static void setUser(User user) {
		Account.user = user;
	}
	public static Resources getResources() {
		return resources;
	}
	public static void setResources(Resources resources) {
		Account.resources = resources;
	}
}
