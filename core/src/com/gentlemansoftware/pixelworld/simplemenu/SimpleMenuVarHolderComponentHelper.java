package com.gentlemansoftware.pixelworld.simplemenu;

import java.util.LinkedList;
import java.util.List;

import com.gentlemansoftware.pixelworld.profiles.VarHolder;

public class SimpleMenuVarHolderComponentHelper {

	public static SimpleMenuComponent getRightComponent(VarHolder<?> var) {
		if (var.getVar().getClass()==Boolean.class) {
			return new SimpleMenuBooleanEditable((VarHolder<Boolean>) var);
		}
		if (var.getVar().getClass()==Float.class) {
			return new SimpleMenuFloatEditable((VarHolder<Float>) var);
		}
		return null;
	}

	public static List<SimpleMenuComponent> getRightComponents(VarHolder<?>[] varHolders) {
		List<SimpleMenuComponent> components = new LinkedList<SimpleMenuComponent>();
		for (VarHolder<?> var : varHolders) {
			components.add(getRightComponent(var));
		}
		return components;
	}

}
