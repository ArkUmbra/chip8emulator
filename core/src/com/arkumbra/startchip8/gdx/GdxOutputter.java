package com.arkumbra.startchip8.gdx;

import com.arkumbra.chip8.Chip8;
import com.arkumbra.chip8.external.ScreenOutputter;
import com.arkumbra.chip8.machine.KeyPressListener;
import com.arkumbra.chip8.machine.ScreenMemory;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GdxOutputter extends ApplicationAdapter implements ScreenOutputter {
	SpriteBatch batch;
	Texture img;

	private ScreenMemory screenMemory;
	private KeyPressListener keyPressListener;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		Chip8 chp8 = new Chip8(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Gdx.gl30.draw

		batch.begin();
		batch.draw(img, 0, 0);
		batch.

		Texture texture = new Texture();
		batch.draw(texture, x, y);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	@Override
	public void init(ScreenMemory screenMemory, KeyPressListener keyPressListener) {
		this.screenMemory = screenMemory;
		this.keyPressListener = keyPressListener;
	}

}
