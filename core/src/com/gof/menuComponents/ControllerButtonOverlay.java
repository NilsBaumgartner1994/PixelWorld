package com.gof.menuComponents;

import com.gof.inputs.Button;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gof.game.CameraControllerInterface;

public class ControllerButtonOverlay {

	private Button b;
	private TextureRegion glyphAndSymbol;
	private Color topBackground;
	private Color bottomBackGround;
	private Color glyph;
	private Color glyphActiv;

	public ControllerButtonOverlay(TextureRegion glyphAndSymbol, Button b, Color topBackground, Color glyph, Color glyphActiv) {
		this.glyphAndSymbol = glyphAndSymbol;
		this.b = b;
		this.topBackground = topBackground;
		this.bottomBackGround = this.topBackground.cpy().mul(0.5f, 0.5f, 0.5f, 1);
		this.glyph = glyph;
		this.glyphActiv = glyphActiv;
	}
	
	public ControllerButtonOverlay(TextureRegion glyphAndSymbol, Button b, Color topBackground){
		this(glyphAndSymbol,b,topBackground,Color.WHITE,new Color(172/255f,16/255f,48/255f,255/255f));
	}
	
//	Color topColor = new Color(154/255f,235/255f,0,255/255f); //Hell Gruen
//	Color bodyColor = new Color(81/255f,162/255f,0,255/255f); //Dunkel Gruen

	public void draw(CameraControllerInterface display, float xpos, float ypos, float scale) {
		Sprite body = new Sprite(GlyphAndSymbols.EMPTY);

		float xPosition = xpos - body.getWidth() * scale / 2;
		float yPosition = ypos - body.getHeight() * scale / 2;

		body.setOrigin(0, 0);
		body.setScale(scale);
		body.setPosition(xPosition, yPosition);
		
		display.drawSprite(body,this.bottomBackGround);
		
		int heightDiff = this.b.isPressed() ? 4 : 3;

		float yPositionTop = yPosition+body.getHeight()*scale/heightDiff;
		
		Sprite topBackground = new Sprite(GlyphAndSymbols.EMPTY);
		topBackground.setOrigin(0, 0);
		topBackground.setScale(scale);
		topBackground.setPosition(xPosition, yPositionTop);
		display.drawSprite(topBackground,this.topBackground);
		
		Sprite spriteButtonTop = new Sprite(this.glyphAndSymbol);
		
		Color glyphDraw = this.b.isPressed() ? this.glyphActiv : this.glyph;

		spriteButtonTop.setOrigin(0, 0);
		spriteButtonTop.setScale(scale);
		spriteButtonTop.setPosition(xPosition,yPositionTop);
		display.drawSprite(spriteButtonTop,glyphDraw);

	}
}
