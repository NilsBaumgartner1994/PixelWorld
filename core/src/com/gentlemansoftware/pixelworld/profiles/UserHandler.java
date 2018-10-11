package com.gentlemansoftware.pixelworld.profiles;

import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.gentlemansoftware.pixelworld.game.Main;
import com.gentlemansoftware.pixelworld.helper.SplitScreenDimension;
import com.gentlemansoftware.pixelworld.helper.SplitscreenHelper;

public class UserHandler {

	HashMap<String, User> localUsers;

	public User getUser(int id) {
		User[] users = getUsers();
		return users[id];
	}

	public User[] getUsers() {
		User[] users = new User[localUsers.values().size()];
		localUsers.values().toArray(users);
		return users;
	}

	public int getUserNumber(User u) {
		User[] users = getUsers();
		for (int i = 0; i < users.length; i++) {
			if (u.equals(users[i]))
				return i;
		}
		return -1;
	}

	public int getUserAmount() {
		return getUsers().length;
	}

	public UserHandler() {
		localUsers = new HashMap<String, User>();
	}

	public User getUserByInput(String inputHandlerName) {
		boolean found = localUsers.containsKey(inputHandlerName);

		if (!found) {
			Main.log(getClass(), "New User found");
			addNewUser(inputHandlerName);
			adaptScreenSizeForAllPlayers();
		}

		return localUsers.get(inputHandlerName);
	}

	private void addNewUser(String inputHandlerName) {
		User u = new User();
		localUsers.put(inputHandlerName, u);
	}

	public void adaptScreenSizeForAllPlayers() {
		int playerAmounts = getUserAmount();
		List<SplitScreenDimension> dimensions = SplitscreenHelper.getDimensionsForAmountOfPlayers(playerAmounts,
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		User[] users = getUsers();
		for(int i=0;i<playerAmounts;i++){
			SplitScreenDimension dimension = dimensions.get(i);
			User user = users[i];
			user.cameraController.resize(dimension);
		}

	}

	public void updateUserInputs() {
		User[] users = getUsers();

		for (User u : users) {
			u.updateUserInputs();
		}
	}

}
