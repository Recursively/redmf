package tests;

import model.GameWorld;
import model.data.Data;
import model.data.Load;
import model.data.Save;
import model.entities.movableEntity.MovableEntity;

import org.junit.Test;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertTrue;

public class DataTests {

	private static TestSuite suite = new TestSuite();
	private GameWorld gameWorld;
	private Data data;

	private void initTestGame() {
		gameWorld = TestSuite.getGameWorld();
		Vector3f position = new Vector3f(10, 10, 10);
		gameWorld.addPlayer(position, 0);
		Save.saveGame(gameWorld);
		data = Load.loadGame();

	}

	@Test
	public void testCompareisProgramCompiled() {
		initTestGame();

		assertTrue("isProgramCompiled comparison",
				GameWorld.isProgramCompiled() == data.isIsProgramCompiled());
	}
		
	@Test
	public void testIsOutside(){
		initTestGame();
		
		assertTrue("isOutside comparison",
				GameWorld.isOutside() == data.isIsOutside());
	}
	
	@Test
	public void testGameState(){
		initTestGame();
		
		assertTrue("gameState comparison", gameWorld.getGameState == data.getGameState))
	}
	
	@Test
	public void testProgress(){
		initTestGame();
		
		assertTrue("progress comparison",
				gameWorld.getProgress() == data.getProgress());
	}
	
	@Test
	public void testScore(){
		initTestGame();
		
		assertTrue("score comparison", gameWorld.getScore() == data.getScore());
	}
	
	@Test
	public void testCanApplyPatch(){
		initTestGame();
		
		assertTrue("canApplyPatch comparison",
				gameWorld.isCanApplyPatch() == data.isCanApplyPatch());
	}
	
	@Test
	public void testCommitIndex(){
		initTestGame();
		
		assertTrue("commitIndex comparison",
				gameWorld.getCommitIndex() == data.getCommitIndex());
	}
	
	@Test
	public void testTimer(){
		initGame();
		
		assertTrue("timer comparison", gameWorld.getTimer() == Load.loadGame()
				.getTimer());
	}

	@Test
	public void testCompareLaptopItems() {
		initTestGame();

		assertTrue("LaptopItems comparison", gameWorld.getInventory()
				.getItems().equals(data.getInventory()));
	}

	@Test
	public void testCompareMovableEntities() {
		initTestGame();

		for (MovableEntity e : gameWorld.getMoveableEntities().values()) {
			assertTrue("MovableEntity comparison of " + e.getType(), data.getMovableEntities().contains(e));
		}
	}

	@Test
	public void testCompareSwipeCards() {
		initTestGame();

		assertTrue(
				"SwipeCards comparison",
				gameWorld.getSwipeCards().equals(
						data.getSwipeCards()));
	}

}
