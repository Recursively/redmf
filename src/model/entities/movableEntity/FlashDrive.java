package model.entities.movableEntity;

import model.GameWorld;
import model.models.TexturedModel;

import org.lwjgl.util.vector.Vector3f;

/**
 * Represents a flash drive item in the game. Stores the specific attributes of 
 * the item that make the item different from other laptop items. 
 * 
 * @author Divya
 *
 */
public class FlashDrive extends LaptopItem {
	private static final int FLASH_DRIVE_SCORE = 20;
	private static final int FLASH_DRIVE_SIZE = 40;
	
	//TODO field to store image?
	
	public FlashDrive(TexturedModel model, Vector3f position, float rotX, float rotY,
			float rotZ, float scale, int id, String name) {
		super(model, position, rotX, rotY, rotZ, scale, id, name);
	}
	
	public FlashDrive(TexturedModel model, Vector3f position, float rotX, float rotY,
			float rotZ, float scale, int textureIndex, int id, String name) {
		super(model, position, rotX, rotY, rotZ, scale, textureIndex, id, name);
	}
	
	@Override
	public int getScore() {
		return FLASH_DRIVE_SCORE;
	}

	@Override
	public String viewInfo() {
		return "Flash Drives store special items. Interact with them and see what happens.";
	}

	@Override
	public int getSize() {
		return FLASH_DRIVE_SIZE;
	}
}
