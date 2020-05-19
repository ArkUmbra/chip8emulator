package com.arkumbra.startchip8.gdx;


import com.arkumbra.chip8.Chip8;
import com.arkumbra.chip8.external.ScreenOutputter;
import com.arkumbra.chip8.machine.KeyPressListener;
import com.arkumbra.chip8.machine.ScreenMemory;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import java.io.File;

public class GdxOutputter extends ApplicationAdapter implements ScreenOutputter {

	SpriteBatch batch;
	Texture img;

	TextureAttribute diffuseTextureAttribute;
	TextureAttribute emissiveTextureAttribute;
	ColorAttribute emissiveColorAttribute;
	BlendingAttribute blendingAttribute;

	private Environment environment;

	private PerspectiveCamera cam;
	private CameraInputController camController;

	private ModelBatch modelBatch;
	private Model model;
	private ModelInstance instance;

//	public Shader shader;
	public RenderContext renderContext;
	public Renderable renderable;

	private ScreenMemory screenMemory;
	private KeyPressListener keyPressListener;

	private ModelInstance[][] pixelInstances = new ModelInstance[ScreenMemory.WIDTH][ScreenMemory.HEIGHT];
	private Array<ModelInstance> modelInstances = new Array<>();
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
//		emissiveTexture = new Texture(Gdx.files.internal("data/particle-star.png"), true);


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

		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);


		// Create material attributes. Each material can contain x-number of attributes.
//		diffuseTextureAttribute = new TextureAttribute(TextureAttribute.Diffuse, img);
//		emissiveTextureAttribute = new TextureAttribute(TextureAttribute.Emissive, img);
		emissiveColorAttribute = new ColorAttribute(ColorAttribute.Emissive, Color.ORANGE);
		blendingAttribute = new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

//		Material material = new Material(diffuseTextureAttribute, emissiveTextureAttribute, emissiveColorAttribute);
		Material material = new Material(emissiveColorAttribute);



		ModelBuilder modelBuilder = new ModelBuilder();
		model = modelBuilder.createBox(5f, 5f, 5f,
//				new Material(ColorAttribute.createDiffuse(Color.GREEN), TextureAttribute.createReflection(img)),
//				new Material(TextureAttribute.createReflection(img)),
				material,
				Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		model.manageDisposable(img);
		instance = new ModelInstance(model);
//		instance.transform.translate()
//		NodePart blockPart = instance.nodes.get(0).parts.get(0);
//
//		renderable = new Renderable();
//		renderable.meshPart.primitiveType = GL20.GL_POINTS;
//
//		blockPart.setRenderable(renderable);
//		renderable.environment = environment;
//		renderable.worldTransform.idt();


//		renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED, 1));
//		shader = new DefaultShader(renderable);
//		shader.init();

		createBoxPerPixel();


		String relativePath = "chip8/src/main/resources/BLINKY.ch8";
		String absolutePath = new File(relativePath).getAbsolutePath();

		Chip8 chip8 = new Chip8(this);
		chip8.loadGame(absolutePath);
		chip8.runAsync();
	}

	private void createBoxPerPixel() {
//		ColorAttribute materialColour = new ColorAttribute(ColorAttribute.Emissive, Color.GREEN);
		ColorAttribute diffuse = new ColorAttribute(ColorAttribute.Diffuse, Color.valueOf("22CB43"));
		ColorAttribute ambient = new ColorAttribute(ColorAttribute.Ambient, Color.valueOf("AA66CC"));
		Material material = new Material(diffuse, ambient);
//		Material material = new Material(diffuse);

		ModelBuilder modelBuilder = new ModelBuilder();
		Model model = modelBuilder.createBox(1f, 1f, 1f,
				material,
				Usage.Position | Usage.Normal | Usage.TextureCoordinates);

		for (int y = 0; y < pixelInstances[0].length; y++) {
			for (int x = 0; x < pixelInstances.length; x++) {
				ModelInstance modelInstance = createInstanceAndTransform(model, x, y);
				pixelInstances[x][y] = modelInstance;
				modelInstances.add(modelInstance);
			}
		}

		model.manageDisposable(img);
		instance = new ModelInstance(model);
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
		camController.update();
		blendingAttribute.opacity = 0.25f + Math.abs(0.5f);

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


//		modelBatch.render(instance, environment);
		modelBatch.render(modelInstancesForFrame, environment);
//
//		shader.begin(cam, renderContext);
//		shader.render(renderable);
//		shader.end();

		modelBatch.end();
	}
	
	@Override
	public void dispose () {
//		shader.dispose();
		model.dispose();
		modelBatch.dispose();
	}

	@Override
	public void init(ScreenMemory screenMemory, KeyPressListener keyPressListener) {
		this.screenMemory = screenMemory;
		this.keyPressListener = keyPressListener;
	}

}
