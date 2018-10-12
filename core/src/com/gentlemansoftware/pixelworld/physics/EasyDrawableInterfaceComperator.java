package com.gentlemansoftware.pixelworld.physics;

import java.util.Comparator;

import com.gentlemansoftware.pixelworld.entitys.EasyDrawableInterface;
import com.gentlemansoftware.pixelworld.entitys.Entity;

public class EasyDrawableInterfaceComperator implements Comparator<EasyDrawableInterface> {

	Direction direction;
	PositionComperator poscomp;

	public EasyDrawableInterfaceComperator(Direction direction) {
		this.direction = direction;
		poscomp = new PositionComperator(this.direction);
	}

	@Override
	public int compare(EasyDrawableInterface o1, EasyDrawableInterface o2) {
		return poscomp.compare(o1.getPosition(), o2.getPosition());
	}

}
