package model.factories;

import model.entities.movableEntity.LaptopItem;
import model.guiComponents.Inventory;
import model.textures.GuiTexture;
import model.toolbox.Loader;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory Game for creating Gui Components
 *
 * @author Marcel van Workum
 * @author Ellie
 * @author Divya
 */
public class GuiFactory {

	private static final String GUI_PATH = "gui/";
	private final Loader loader;

	/**
	 * Create the Gui factory passing in the object loader
	 *
	 * @param loader
	 *            Object loader
	 */
	public GuiFactory(Loader loader) {
		this.loader = loader;
	}

	/**
	 * Creates a {@link GuiTexture} with the specified texture, position and
	 * scale.
	 *
	 * @param textureName
	 *            Texture
	 * @param position
	 *            Position on the display x(0-1) y(0-1)
	 * @param scale
	 *            scale of the texture on display x(0-1) y(0-1)
	 *
	 * @return The GuiTexture created
	 */

	public GuiTexture makeGuiTexture(String textureName, Vector2f position, Vector2f scale) {
		return new GuiTexture(loader.loadTexture(GUI_PATH + textureName), position, scale);
	}

	public ArrayList<GuiTexture> makeInventory(Inventory inventory) {
		ArrayList<GuiTexture> inventoryImages = new ArrayList<GuiTexture>();
		inventoryImages.add(makeGuiTexture("blankInventoryScreen", new Vector2f(0f, 0f), new Vector2f(0.8f, 1f)));
		if (inventory.getLaptopDisplay() != null) {
			LaptopItem[][] items = inventory.getLaptopDisplay();
			for(int x = 0; x < items.length; x++){
				for(int y = 0; y < items[0].length; y++){
					if(items[x][y] != null){
						// TODO fix vector calulations depending on x y values
						GuiTexture img = makeGuiTexture(items[x][y].getName(), new Vector2f(0f + 100 * x, 0f), new Vector2f(1f, 1f));
						inventoryImages.add(img);
					}
				}
			}
		}

		return inventoryImages;

	}
	
	public ArrayList<GuiTexture> makeLostScreen(){
		ArrayList<GuiTexture> lostScreen = new ArrayList<GuiTexture>();
		lostScreen.add(makeGuiTexture("youLostScreen", new Vector2f(0f, 0f), new Vector2f(1f, 1f)));
		return lostScreen;
	}

	public List<GuiTexture> makePopUpInteract(Vector3f position) {
		List<GuiTexture> message = new ArrayList<GuiTexture>();
		// TODO fix calculations
		Vector3f norm = new Vector3f(0,0,0);
		position.normalise(norm);
		Vector2f pos = new Vector2f(norm.getX()*2 - 1, norm.getY());
		message.add(makeGuiTexture("pressEToInteract", pos, new Vector2f(0.5f, 0.5f)));
		return message;
	}
}
