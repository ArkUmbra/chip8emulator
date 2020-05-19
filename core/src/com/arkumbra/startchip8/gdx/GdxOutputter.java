package com.arkumbra.startchip8.gdx;


import com.arkumbra.chip8.Chip8;
import com.arkumbra.chip8.external.ScreenOutputter;
import com.arkumbra.chip8.machine.KeyPressListener;
import com.arkumbra.chip8.machine.ScreenMemory;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import java.io.File;

public class GdxOutputter extends ApplicationAdapter implements ScreenOutputter {

	private Environment environment;

	private PerspectiveCamera cam;
	private CameraInputController camController;
	private InputProcessor inputProcessor;

	private ModelBatch modelBatch;

	private ScreenMemory screenMemory;

	private ModelInstance[][] pixelInstances = new ModelInstance[ScreenMemory.WIDTH][ScreenMemory.HEIGHT];
	private Array<ModelInstance> modelInstances = new Array<>();
	
	@Override
	public void create () {
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
//		environment.add(new DirectionalLight().set(1f, 1f, 1f, 1f, 1f, 0.5f)); // Decent
		environment.add(new PointLight().set(Color.valueOf("CCAACC"), new Vector3(32, 16, 10), 500f));


		modelBatch = new ModelBatch();

		// Set up camera to look at the game screen face on
		cam = new PerspectiveCamera(80, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(ScreenMemory.WIDTH / 2, ScreenMemory.HEIGHT / 2, -30f);
		cam.lookAt(ScreenMemory.WIDTH / 2,ScreenMemory.HEIGHT / 2,0);
		cam.near = 1f;
		cam.far = 300f;
		cam.rotate(180, 0, 0, -1);
		cam.update();


		createBoxPerPixel();


		String relativePath = "chip8/src/main/resources/BLINKY.ch8";
		String absolutePath = new File(relativePath).getAbsolutePath();

		Chip8 chip8 = new Chip8(this);
		chip8.loadGame(absolutePath);
		chip8.runAsync();
	}

	private void createBoxPerPixel() {
		ColorAttribute emissive = new ColorAttribute(ColorAttribute.Emissive, Color.valueOf("001100"));
		ColorAttribute diffuse = new ColorAttribute(ColorAttribute.Diffuse, Color.valueOf("22CB43"));
		ColorAttribute ambient = new ColorAttribute(ColorAttribute.Ambient, Color.valueOf("AA66CC"));
		ColorAttribute reflective = new ColorAttribute(ColorAttribute.Reflection, Color.valueOf("00CCAA"));
		Material material = new Material(emissive, diffuse, ambient, reflective);

		ModelBuilder modelBuilder = new ModelBuilder();
		Model model = modelBuilder.createBox(0.9f, 0.9f, 0.5f,
				material,
				Usage.Position | Usage.Normal | Usage.TextureCoordinates);

		for (int y = 0; y < pixelInstances[0].length; y++) {
			for (int x = 0; x < pixelInstances.length; x++) {
				ModelInstance modelInstance = createInstanceAndTransform(model, x, y);
				pixelInstances[x][y] = modelInstance;
				modelInstances.add(modelInstance);
			}
		}
	}

	private ModelInstance createInstanceAndTransform(Model model, int x, int y) {
		ModelInstance instance = new ModelInstance(model);
		instance.transform.translate(
				x,
				y,
				0
		);

		return instance;
	}

	@Override
	public void render () {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		boolean[][] screenPixels = screenMemory.getPixels();
		Array<ModelInstance> modelInstancesForFrame = new Array<>();
		for (int y = 0; y < screenPixels[0].length; y++) {
			for (int x = 0; x < screenPixels.length; x++) {
				if (screenPixels[x][y]) {
					modelInstancesForFrame.add(pixelInstances[x][y]);
				}
			}
		}

		modelBatch.begin(cam);
		modelBatch.render(modelInstancesForFrame, environment);
		modelBatch.end();
	}
	
	@Override
	public void dispose () {
		modelBatch.dispose();
	}

	@Override
	public void init(ScreenMemory screenMemory, KeyPressListener keyPressListener) {
		this.screenMemory = screenMemory;

		inputProcessor = new GdxInputProcessor(keyPressListener);
		Gdx.input.setInputProcessor(inputProcessor);
	}

}
