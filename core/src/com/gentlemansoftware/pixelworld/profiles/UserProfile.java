package com.gentlemansoftware.pixelworld.profiles;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.badlogic.gdx.files.FileHandle;
import com.gentlemansoftware.pixelworld.game.FileController;
import com.gentlemansoftware.pixelworld.game.Main;
import com.gentlemansoftware.pixelworld.game.SaveAndLoadable;

public class UserProfile extends SaveAndLoadable {

	public UserDebugProfile debugProfile;
	public UserSoundProfile soundProfile;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7248397811160277883L;
	public VarHolder<String> name;
	public VarHolder<String> uuid;

	public UserProfile() {
		this.name = new VarHolder<String>("Default","Name");
		this.uuid = new VarHolder<String>(UUID.randomUUID().toString(),"UUID");
		this.debugProfile = new UserDebugProfile();
		this.soundProfile = new UserSoundProfile();
	}

	public static final transient String DATA = "data/";
	public static final transient String PROFILES = DATA + "profiles/";
	public static final transient String ENDING = ".profile";

	public static UserProfile load(String name) {
		return (UserProfile) SaveAndLoadable.loadFromInternal(PROFILES + name + ENDING, UserProfile.class);
	}
	
	public void delete(){
		FileController.getInstance().deleteLocalFile(PROFILES + name.getVar() + ENDING);
	}
	
	public static List<UserProfile> getAllUserProfiles(){
		List<UserProfile> profiles = new LinkedList<UserProfile>();
		FileHandle dictionary = FileController.getInstance().getLocalFileHandle(PROFILES);
		if(dictionary.isDirectory()){
			for(FileHandle file : dictionary.list()){
				String name = file.nameWithoutExtension();
				if(!name.equals("")){
					Main.log(UserProfile.class, "Search for UserProfile: "+name);
					UserProfile profile = load(name);
					profiles.add(profile);
				}
			}
		}
		return profiles;
	}

	public void save() {
		super.saveToInternal(PROFILES + name.getVar() + ENDING);
	}

}
