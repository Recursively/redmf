package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import gui.GuiRenderer;
import gui.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import objParser.ModelData;
import objParser.OBJFileLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    public static void main(String[] args) {
        DisplayManager.createDisplay();

        Random random = new Random();
        Loader loader = new Loader();

        // THIS CODE IS UGLY PLEASE DON'T JUDGE ME!!


        // Terrain creation
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("textures/grass"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("textures/mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("textures/grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("textures/path"));

        // Bundle terrains into pack
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        // Blend map for mixing terrains
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("terrains/blendMap"));

        // Create the new terrain object, using pack blendermap and heightmap
        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "terrains/heightMap");

        // Multiple light sources
        // This is a test and makes shit look weird
        //TODO remove this
        Light light = new Light(new Vector3f(-3000, 2000, -3000), new Vector3f(1, 1, 1));
        List<Light> lights = new ArrayList<>();
        lights.add(light);
        lights.add(new Light(new Vector3f(-200, 10, -200), new Vector3f(10, 0, 0)));
//        lights.add(new Light(new Vector3f(200, 10, 200), new Vector3f(0, 0, 10)));
//        lights.add(new Light(new Vector3f(100, 100, 100), new Vector3f(0, 10, 0)));

        // BEGIN UGLY MODEL LOADING
        // TODO should use factory design pattern fro this

        ModelData data = OBJFileLoader.loadOBJ("models/lowPolyTree");
        RawModel lowPolyTreeModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(),
                data.getIndices());

        TexturedModel lowPolyTreeTexturedModel = new TexturedModel(lowPolyTreeModel,
                new ModelTexture(loader.loadTexture("textures/lowPolyTree")));
        lowPolyTreeTexturedModel.getTexture().setNumberOfRows(2);
        lowPolyTreeTexturedModel.getTexture().setShineDamper(10);
        lowPolyTreeTexturedModel.getTexture().setReflectivity(1);

        List<Entity> allPolyTrees = new ArrayList<>();

        for (int i = 0; i < 200; i++) {
            float x = random.nextFloat() * 1000;
            float z = random.nextFloat() * -1000;
            float y = terrain.getTerrainHeight(x, z);
            if ((x > 400 && x < 600) && (z > -600 && z < -400)) {
                continue;
            }
            allPolyTrees.add(new Entity(lowPolyTreeTexturedModel, new Vector3f(x, y, z), 0,
                    0, 0f, 1f, random.nextInt(4)));
        }

        ModelData data2 = OBJFileLoader.loadOBJ("models/grassClumps");
        RawModel grassModel = loader.loadToVAO(data2.getVertices(), data2.getTextureCoords(), data2.getNormals(),
                data2.getIndices());

        TexturedModel grassModelTexturedModel = new TexturedModel(grassModel,
                new ModelTexture(loader.loadTexture("textures/grassClumps")));
        grassModelTexturedModel.getTexture().setShineDamper(10);
        grassModelTexturedModel.getTexture().setReflectivity(1);
        grassModelTexturedModel.getTexture().setHasTransparency(true);
        grassModelTexturedModel.getTexture().setUseFakeLighting(true);

        List<Entity> allGrass = new ArrayList<>();

        for (int i = 0; i < 500; i++) {
            float x = random.nextFloat() * 1000;
            float z = random.nextFloat() * -1000;
            float y = terrain.getTerrainHeight(x, z);
            if ((x > 400 && x < 600) && (z > -600 && z < -400)) {
                continue;
            }
            allGrass.add(new Entity(grassModelTexturedModel, new Vector3f(x, y, z), 0,
                    0, 0f, 1f));
        }

        ModelData data3 = OBJFileLoader.loadOBJ("models/stall");
        RawModel stallModel = loader.loadToVAO(data3.getVertices(), data3.getTextureCoords(), data3.getNormals(),
                data3.getIndices());

        TexturedModel stallTexturedModel = new TexturedModel(stallModel,
                new ModelTexture(loader.loadTexture("textures/stall")));
        stallTexturedModel.getTexture().setShineDamper(10);
        stallTexturedModel.getTexture().setReflectivity(1);

        List<Entity> allStalls = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            float x = random.nextFloat() * 1000;
            float z = random.nextFloat() * -1000;
            float y = terrain.getTerrainHeight(x, z);
            if ((x > 400 && x < 600) && (z > -600 && z < -400)) {
                continue;
            }
            allStalls.add(new Entity(stallTexturedModel, new Vector3f(x, y, z), 0,
                    0, 0f, 4f));
        }

        TexturedModel treeModel = new TexturedModel(OBJLoader.loadObjModel("models/tree", loader),
                new ModelTexture(loader.loadTexture("textures/tree")));
        ModelTexture treeTexture = treeModel.getTexture();
        // TODO This is broken
        treeTexture.setShineDamper(10);
        treeTexture.setReflectivity(1);
        treeTexture.setHasTransparency(false);

        List<Entity> allTrees = new ArrayList<>();

        for (int i = 0; i < 200; i++) {
            float x = random.nextFloat() * 1000;
            float z = random.nextFloat() * -1000;
            float y = terrain.getTerrainHeight(x, z);
            if ((x > 400 && x < 600) && (z > -600 && z < -400)) {
                continue;
            }
            allTrees.add(new Entity(treeModel, new Vector3f(x, y, z), 0,
                    0, 0f, 10f));
        }

        TexturedModel fernModel = new TexturedModel(OBJLoader.loadObjModel("models/fern", loader),
                new ModelTexture(loader.loadTexture("textures/fern")));
        ModelTexture fernTexture = fernModel.getTexture();
        fernTexture.setNumberOfRows(2);
        fernTexture.setShineDamper(10);
        fernTexture.setReflectivity(1);
        fernTexture.setHasTransparency(true);

        List<Entity> allFerns = new ArrayList<>();

        for (int i = 0; i < 300; i++) {
            float x = random.nextFloat() * 1000;
            float z = random.nextFloat() * -1000;
            float y = terrain.getTerrainHeight(x, z);
            allFerns.add(new Entity(fernModel, new Vector3f(x, y, z), 0,
                    0, 0f, 1f, random.nextInt(4)));
        }

        // create single models
        // TODO again should use factory design pattern here

        TexturedModel dragonModel = new TexturedModel(OBJLoader.loadObjModel("models/dragon", loader),
                new ModelTexture(loader.loadTexture("textures/white")));
        ModelTexture dragonTexture = dragonModel.getTexture();
        dragonTexture.setShineDamper(10);
        dragonTexture.setReflectivity(1);

        TexturedModel bunnyModel = new TexturedModel(OBJLoader.loadObjModel("models/bunny", loader),
                new ModelTexture(loader.loadTexture("textures/white")));
        ModelTexture bunnyTexture = bunnyModel.getTexture();
        bunnyTexture.setShineDamper(10);
        bunnyTexture.setReflectivity(1);

        TexturedModel playerModel = new TexturedModel(OBJLoader.loadObjModel("models/player", loader),
                new ModelTexture(loader.loadTexture("textures/white")));
        ModelTexture playerTexture = playerModel.getTexture();
        playerTexture.setShineDamper(10);
        playerTexture.setReflectivity(1);


        // Create gui elements

        List<GuiTexture> guiImages = new ArrayList<>();
        GuiTexture gui = new GuiTexture(loader.loadTexture("gui/panel_brown"), new Vector2f(-0.75f, 0.75f), new Vector2f(0.25f, 0.25f));
        guiImages.add(gui);

        // gui renderer which handles rendering an infinite amount of gui elements
        GuiRenderer guiRenderer = new GuiRenderer(loader);
        ///

        // New player and camera to follow the player
        Player player = new Player(playerModel, new Vector3f(50, 0, -50), 0, 180f, 0, 1);
        Camera camera = new Camera(player);

        //TODO do we want the mouse to be captured?
        // It makes sense to be captured if game is first person, not so much for third person
        //Mouse.setGrabbed(true);

        // This renders all the goodies
        MasterRenderer renderer = new MasterRenderer();

        while (!Display.isCloseRequested()) {
            camera.move();
            renderer.processTerrain(terrain);

            // Again ugly and needs work

            player.move(terrain);
            renderer.processEntity(player);

            renderer.processEntity(new Entity(dragonModel, new Vector3f(500, terrain.getTerrainHeight(500, -500), -500),
                    0, 0, 0f, 10f));
            renderer.processEntity(new Entity(bunnyModel, new Vector3f(250, terrain.getTerrainHeight(250, -500), -500),
                    0, 0, 0f, 10f));


            for (Entity tree : allTrees) {
                renderer.processEntity(tree);
            }

            for (Entity fern : allFerns) {
                renderer.processEntity(fern);
            }

            for (Entity polyTree : allPolyTrees) {
                renderer.processEntity(polyTree);
            }

            for (Entity stall : allStalls) {
                renderer.processEntity(stall);
            }

            for (Entity grass : allGrass) {
                renderer.processEntity(grass);
            }

            renderer.render(lights, camera);

            guiRenderer.render(guiImages);

            DisplayManager.updateDisplay();
        }

        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
