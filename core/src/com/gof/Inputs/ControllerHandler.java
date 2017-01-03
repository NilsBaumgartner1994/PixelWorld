package com.redagent.Inputs;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.redagent.entitys.LocalPlayer;
import com.redagent.game.Main;

public class ControllerHandler implements ControllerListener {

	public ControllerHandler(InputHandler inputHandler) {

	}

	public void updateInputLogic() {
		for (Controller controller : Controllers.getControllers()) {
			LocalPlayer p = Main.getInstance().playerHandler.getPlayerByInput("controller:" + controller.hashCode());
			updateLeftStick(p,controller);
			updateRightStick(p,controller);
			updateABXY(p,controller);
			updateTrigger(p,controller);
		}
	}
	
	public void updateLeftStick(LocalPlayer p, Controller controller) {
		float dy = -controller.getAxis(XBox360Pad.AXIS_LEFT_Y);
		float dx = controller.getAxis(XBox360Pad.AXIS_LEFT_X);
		
//		Vector2 dir = ;
		p.setLeftStick(new Vector2(dx, dy));
		p.stickLeftDown = controller.getButton(XBox360Pad.BUTTON_L3);
	}
	
	public void updateRightStick(LocalPlayer p, Controller controller) {
		float dy = controller.getAxis(XBox360Pad.AXIS_RIGHT_Y);
		float dx = controller.getAxis(XBox360Pad.AXIS_RIGHT_X);

		p.setRightStick(new Vector2(dx, dy));
		p.stickRightDown = controller.getButton(XBox360Pad.BUTTON_R3);
	}
	
	public void updateTrigger(LocalPlayer p, Controller controller){
		float leftTrigger = controller.getAxis(XBox360Pad.AXIS_LEFT_TRIGGER);
		if(leftTrigger<2E-5){
			leftTrigger=0;
		}
		
		float rightTrigger = -controller.getAxis(XBox360Pad.AXIS_RIGHT_TRIGGER);
		if(rightTrigger<2E-5){
			rightTrigger=0;
		}
		
//		float thresholdRightTrigger = 0.7f;
		
	}

	public void updateABXY(LocalPlayer p, Controller controller) {
			
	}

//	private float threshold = 0.4f; // spielraum, ab 20% wird Stick erst
	// gemessen




	@Override
	public void connected(Controller controller) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "Controller connected");
	}

	@Override
	public void disconnected(Controller controller) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "Controller disconnected");
	}

	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "ButtonDown: " + buttonCode);
		return false;
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "ButtonUp: " + buttonCode);
		return false;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		// TODO Auto-generated method stub
//		System.out.println("AxisMoved: "+axisCode+" value: "+value);
		return false;
	}

	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "pov: " + povCode + " with " + value);
		
		LocalPlayer p = Main.getInstance().playerHandler.getPlayerByInput("controller:" + controller.hashCode());

		if (value == XBox360Pad.BUTTON_DPAD_DOWN) {
			p.cameraController.distanceIncrease();
		}
		if (value == XBox360Pad.BUTTON_DPAD_UP) {
			p.cameraController.distanceDecrease();
		}

		return false;
	}

	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "xSlider: " + sliderCode + " with " + value);
		return false;
	}

	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "ySlider: " + sliderCode + " with " + value);
		return false;
	}

	@Override
	public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "accelerometer: " + accelerometerCode + " with " + value);
		return false;
	}

}
