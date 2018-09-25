package com.gentlemansoftware.pixelworld.menuComponents;

import com.gentlemansoftware.pixelworld.inputs.Button;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gentlemansoftware.pixelworld.game.CameraControllerInterface;
import com.gentlemansoftware.pixelworld.helper.EasyColor;

public class ControllerButtonOverlay {

	private TextureRegion glyphAndSymbol;
	private Color topBackground;
	private Color bottomBackGround;
	private Color glyph;
	private Color glyphActiv;

	public ControllerButtonOverlay(TextureRegion glyphAndSymbol, Color topBackground, Color glyph, Color glyphActiv) {
		this.glyphAndSymbol = glyphAndSymbol;
		this.topBackground = topBackground;
		this.bottomBackGround = this.topBackground.cpy().mul(0.5f, 0.5f, 0.5f, 1);
		this.glyph = glyph;
		this.glyphActiv = glyphActiv;
	}

	public ControllerButtonOverlay(TextureRegion glyphAndSymbol, Color topBackground) {
		this(glyphAndSymbol, topBackground, Color.WHITE, EasyColor.REDACTIVE);
	}
	
	public ControllerButtonOverlay(TextureRegion glyphAndSymbol, Color topBackground, Color glyph) {
		this(glyphAndSymbol, topBackground, glyph, EasyColor.REDACTIVE);
	}

	public void draw(CameraControllerInterface display, float xpos, float ypos, float scale, Button b) {
		Sprite body = new Sprite(GlyphAndSymbols.EMPTY);

		float xPosition = xpos - body.getWidth() * scale / 2;
		float yPosition = ypos - body.getHeight() * scale / 2;

		body.setOrigin(0, 0);
		body.setScale(scale);
		body.setPosition(xPosition, yPosition);

		display.drawSprite(body, this.bottomBackGround);

		int heightDiff = b.isPressed() ? 4 : 3;

		float yPositionTop = yPosition + body.getHeight() * scale / heightDiff;

		Sprite topBackground = new Sprite(GlyphAndSymbols.EMPTY);
		topBackground.setOrigin(0, 0);
		topBackground.setScale(scale);
		topBackground.setPosition(xPosition, yPositionTop);
		display.drawSprite(topBackground, this.topBackground);

		Sprite spriteButtonTop = new Sprite(this.glyphAndSymbol);

		Color glyphDraw = b.isPressed() ? this.glyphActiv : this.glyph;

		spriteButtonTop.setOrigin(0, 0);
		spriteButtonTop.setScale(scale);
		spriteButtonTop.setPosition(xPosition, yPositionTop);
		display.drawSprite(spriteButtonTop, glyphDraw);
	}
}
