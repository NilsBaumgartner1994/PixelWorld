package com.gentlemansoftware.pixelworld.profiles;

import java.util.HashMap;

import com.gentlemansoftware.pixelworld.entitys.Human;
import com.gentlemansoftware.pixelworld.game.Main;

public class UserHandler {

	HashMap<String, User> localUsers;

	public User getUser(int id){
		User[] users = getUsers();
		return users[id];
	}
	
	public User[] getUsers(){
		User[] users = new User[localUsers.values().size()];
		localUsers.values().toArray(users);
		return users;
	}
	
	public int getUserNumber(User u){
		User[] users = getUsers();
		for(int i=0; i<users.length;i++){
			if(u.equals(users[i])) return i;
		}
		return -1;
	}
	
	public int getUserAmount(){
		return getUsers().length;
	}
	
	public UserHandler() {
		localUsers = new HashMap<String, User>();
	}

	public User getUserByInput(String inputHandlerName) {
		boolean found = localUsers.containsKey(inputHandlerName);

		if (!found) {
			Main.log(getClass(), "New User found");
			User u = new User();
			localUsers.put(inputHandlerName, u);
		}

		return localUsers.get(inputHandlerName);
	}
	
	public void updateUserInputs() {
		User[] users = getUsers();

		for (User u : users) {
			u.updateUserInputs();
		}
	}

}
