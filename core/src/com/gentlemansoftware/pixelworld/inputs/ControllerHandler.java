package com.gentlemansoftware.pixelworld.inputs;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gentlemansoftware.pixelworld.game.Main;
import com.gentlemansoftware.pixelworld.profiles.User;

public class ControllerHandler implements ControllerListener {

	public static String inputHandlerName;
	Map<Controller, GamePadLayout> controllerInterfaceMap;

	public ControllerHandler(InputHandler inputHandler) {
		controllerInterfaceMap = new HashMap<Controller, GamePadLayout>();
		inputHandlerName = "Keyboard";
	}

	public User getUser(Controller controller) {
		return Main.getInstance().userHandler.getUserByInput(inputHandlerName);
	}

	public void madeAnAction(Controller controller) {
		User user = getUser(controller);
		GamePadLayout padinterface = controllerInterfaceMap.get(controller);
		if (padinterface != null) {
			user.gamepad.layouttype = padinterface;
		}
	}

	public void updateInputLogic() {
		for (Controller controller : Controllers.getControllers()) {
			GamePadLayout padinterface = controllerInterfaceMap.get(controller);
			if (padinterface == null) {
				padinterface = new GamePadLayoutPlaystation4();
				controllerInterfaceMap.put(controller, padinterface);
			} else {
				User user = getUser(controller);
				updateLeftStick(user, controller);
				if (isControllerLastInput(controller)) {
					updateButtons(user, controller, padinterface);

				}
			}
		}
	}

	private boolean isControllerLastInput(Controller controller) {
		User user = getUser(controller);
		GamePadLayout padinterface = controllerInterfaceMap.get(controller);
		return (user.gamepad.layouttype == padinterface);
	}

	public void updateButtons(User user, Controller controller, GamePadLayout padinterface) {
		for (int buttonCode : padinterface.buttons) {
			GamePadButtons button = padinterface.getButton(buttonCode);
			user.gamepad.setButtonState(button, controller.getButton(buttonCode));
		}
	}

	public void updateLeftStick(User user, Controller controller) {
		float dy = -controller.getAxis(GamePadLayoutXBox360.AXIS_LEFT_Y);
		float dx = -controller.getAxis(GamePadLayoutXBox360.AXIS_LEFT_X);

		float thresholdStick = 0.7f;
		Vector2 vec = new Vector2(dx, dy).rotate(45);
		if (vec.len2() < thresholdStick) {
			vec = new Vector2(0, 0);
		} else {
			madeAnAction(controller);
		}

		if (isControllerLastInput(controller)) {
			user.gamepad.getLeftStick().setVec(vec);
		}
	}

	public void updateRightStick(User user, Controller controller) {
		float dy = controller.getAxis(GamePadLayoutXBox360.AXIS_RIGHT_Y);
		float dx = controller.getAxis(GamePadLayoutXBox360.AXIS_RIGHT_X);

		// p.setRightStick(new Vector2(dx, dy));
		// p.stickRightDown = controller.getButton(XBox360Pad.BUTTON_R3);
	}

	public void updateTrigger(User user, Controller controller) {
		float leftTrigger = controller.getAxis(GamePadLayoutXBox360.AXIS_LEFT_TRIGGER);
		if (leftTrigger < 2E-5) {
			leftTrigger = 0;
		}

		float rightTrigger = -controller.getAxis(GamePadLayoutXBox360.AXIS_RIGHT_TRIGGER);
		if (rightTrigger < 2E-5) {
			rightTrigger = 0;
		}

		// float thresholdRightTrigger = 0.7f;

	}

	// private float threshold = 0.4f; // spielraum, ab 20% wird Stick erst
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
		// setButtonStateForController(controller, buttonCode, true);
		madeAnAction(controller);
		return false;
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		// setButtonStateForController(controller, buttonCode, false);
		madeAnAction(controller);
		return false;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		// TODO Auto-generated method stub
//		 System.out.println("AxisMoved: "+axisCode+" value: "+value);
		return false;
	}

	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "pov: " + povCode + " with " + value);

		// Human p =
		// Main.getInstance().userHandler.getPlayerByInput("controller:" +
		// controller.hashCode());
		//
		// if (value == XBox360Pad.BUTTON_DPAD_DOWN) {
		// p.cameraController.distanceIncrease();
		// }
		// if (value == XBox360Pad.BUTTON_DPAD_UP) {
		// p.cameraController.distanceDecrease();
		// }

		madeAnAction(controller);
		return false;
	}

	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "xSlider: " + sliderCode + " with " + value);
		madeAnAction(controller);
		return false;
	}

	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "ySlider: " + sliderCode + " with " + value);
		madeAnAction(controller);
		return false;
	}

	@Override
	public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "accelerometer: " + accelerometerCode + " with " + value);
		return false;
	}

}
