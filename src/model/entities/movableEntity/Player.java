package model.entities.movableEntity;

import model.GameWorld;
import model.entities.Camera;
import model.models.TexturedModel;
import model.terrains.Terrain;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import view.DisplayManager;

public class Player extends MovableEntity {

    private static final float RUN_SPEED = 1;
    private static final float JUMP_POWER = 30;

    private static float terrainHeight = 0;
    private Camera camera;

    private float verticalVelocity = 0;
    
    private GameWorld gameWorld;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, int id, Camera camera, GameWorld game) {
        super(model, position, rotX, rotY, rotZ, scale, id);
        this.camera = camera;
        this.gameWorld = game;
    }

    public void move(Terrain terrain) {
        updateTerrainHeight(terrain);
        gravityPull();
        firstPersonMove();
        camera.update(super.getPosition());
    }

    private void gravityPull() {
        verticalVelocity += GRAVITY * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(0, verticalVelocity * DisplayManager.getFrameTimeSeconds(), 0);
        if (super.getPosition().y < terrainHeight) {
            verticalVelocity = 0;
            super.getPosition().y = terrainHeight;
        }
    }

    private void updateTerrainHeight(Terrain terrain) {
        terrainHeight = terrain.getTerrainHeight(super.getPosition().x, super.getPosition().z);
    }

    private void jump() {
        verticalVelocity += JUMP_POWER;
    }

    private void firstPersonMove() {

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            moveFromLook(0, 0, -1 * RUN_SPEED);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            moveFromLook(0, 0, 1 * RUN_SPEED);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            moveFromLook(1 * RUN_SPEED, 0, 0);

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            moveFromLook(-1 * RUN_SPEED, 0, 0);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            if (super.getPosition().y == terrainHeight) {
                jump();
            }
        }
        
        
        // TODO Merge pickup/interact....
        
        // ensures single reaction to a key press event when dealing with items
        while(Keyboard.next()){
        	// carry out methods when key is pressed (not released)
        	if(Keyboard.getEventKeyState()){
        		if(Keyboard.getEventKey() == Keyboard.KEY_E){
        			interactWithItem();
        		}
        		if(Keyboard.getEventKey() == Keyboard.KEY_R){
        			//TODO should drop be in the gui controller as delete?
        			//dropItem();
        		}
        		// TODO have 'C' or something to interact/copy code from npc?
                // TODO have 'U' or something for unlock door method
        	}
        }

        /* Prevents the camera from turning over 360 or under -360 */
        camera.changeYaw(Mouse.getDX() / 2);
        camera.changePitch(-(Mouse.getDY() / 2));
        if (camera.getPitch() > 60) {
            camera.setPitch(60);
        } else if (camera.getPitch() < -30) {
            camera.setPitch(-30);
        }
    }

	public void moveFromLook(float dx, float dy, float dz) {

        Vector3f position = super.getPosition();

        position.z += dx * (float) Math.cos(Math.toRadians(camera.getYaw() - 90)) + dz * Math.cos(Math.toRadians(camera.getYaw()));
        position.x -= dx * (float) Math.sin(Math.toRadians(camera.getYaw() - 90)) + dz * Math.sin(Math.toRadians(camera.getYaw()));

        super.setPosition(position);
    }

    public Camera getCamera() {
        return camera;
    }
    
    // TODO pickup just deals with 
    
    /**
     * Find item that player is trying to interact with 
     * and then carry out interaction
     */
    private void interactWithItem() {
    	Item item = gameWorld.findItem(this.getPosition()); 
    	if(item != null){
    		// picking up items affect game differently depending on item
    		// so allow the item to deal with changing game state accordingly
    		item.interact(this.gameWorld); 
    	}
	}
}
