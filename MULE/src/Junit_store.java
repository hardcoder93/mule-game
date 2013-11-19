/**
 * Sung Hye Jeon
 */

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Test;


public class Junit_store {
	MULEGameEngine engine;
	MULEMap map;
 	
	public void init(int numPlayers, String difficulty, String mapType){
		engine = new MULEGameEngine(difficulty, mapType, numPlayers);
		engine.addPlayer("dummy Player", "Red", "Flapper");
	}

	@Test
	public void purchaseTileTest() {
		int cost;
	
		// Beginner
		init(1,"Beginner", "Standard");
		engine.nextRound();
		assertEquals(engine.getCurrentRound(), 1);
		assertEquals(engine.getActivePlayer().getMoney(), 1600);
		assertEquals(engine.getActivePlayer().getMule(), 0);
		assertEquals(engine.getActivePlayer().getFood(), 8);
		assertEquals(engine.getActivePlayer().getEnergy(), 4);
		assertEquals(engine.getActivePlayer().getOre(), 0);
		
		assertEquals(engine.getStore().getQuantity("Food"), 16);
		assertEquals(engine.getStore().getQuantity("Energy"), 16);
		assertEquals(engine.getStore().getQuantity("Smithore"), 0);
		assertEquals(engine.getStore().getQuantity("Mules"), 25);

		assertEquals(engine.getStore().getCurrentPrice("Food"), 30);
		assertEquals(engine.getStore().getCurrentPrice("Energy"), 25);
		assertEquals(engine.getStore().getCurrentPrice("Smithore"), 50);
		assertEquals(engine.getStore().getCurrentPrice("Mules"), 100);
		
		assertEquals(engine.getActivePlayer().getMoney(), 1600);	

		
		engine.storeTransaction(true, "Mules", "Food Mule"); 	//100
		assertEquals(engine.getActivePlayer().getMoney(), 1500);	//1475

		
		engine.storeTransaction(true, "Food", "5");	//150
		engine.storeTransaction(true, "Smithore", "2"); // 50 there was no Smithore to buy
		engine.storeTransaction(true, "Energy", "2");	//50
		
		assertEquals(engine.getActivePlayer().getFood(), 13);
		
		assertEquals(engine.getActivePlayer().getEnergy(), 6);
		assertEquals(engine.getActivePlayer().getOre(), 2);	// this should be 0 or 2
		assertEquals(engine.getActivePlayer().getMule(), 1);
		assertEquals(engine.getActivePlayer().getMoney(), 1200);	//1175

		engine.storeTransaction(false, "Food", "1");
		assertEquals(engine.getActivePlayer().getFood(), 8);	
		assertEquals(engine.getActivePlayer().getMoney(), 1150);

		// engine.storeTransaction(false, "Food", "10");
		// should give an error msg, cannot sell more than I have
		
		engine.storeTransaction(true, "Food", "110");
		assertEquals(engine.getActivePlayer().getFood(), 8);

		
		init(1,"Standard", "Standard");
		engine.nextRound();
		assertEquals(engine.getCurrentRound(), 1);
		assertEquals(engine.getActivePlayer().getMoney(), 1600);
		assertEquals(engine.getActivePlayer().getMule(), 0);
		assertEquals(engine.getActivePlayer().getFood(), 4);
		assertEquals(engine.getActivePlayer().getEnergy(), 2);
		assertEquals(engine.getActivePlayer().getOre(), 0);
		
		assertEquals(engine.getStore().getQuantity("Food"), 8);
		assertEquals(engine.getStore().getQuantity("Energy"), 8);
		assertEquals(engine.getStore().getQuantity("Smithore"), 8);
		assertEquals(engine.getStore().getQuantity("Crystite"), 0);
		assertEquals(engine.getStore().getQuantity("Mules"), 14);

		assertEquals(engine.getStore().getCurrentPrice("Food"), 30);
		assertEquals(engine.getStore().getCurrentPrice("Energy"), 25);
		assertEquals(engine.getStore().getCurrentPrice("Smithore"), 50);
		assertEquals(engine.getStore().getCurrentPrice("Crystite"), 100);
		assertEquals(engine.getStore().getCurrentPrice("Mules"), 100);
		
		
		init(1,"Tournament", "Standard");
		engine.nextRound();
		assertEquals(engine.getCurrentRound(), 1);
		assertEquals(engine.getActivePlayer().getMoney(), 1600);
		assertEquals(engine.getActivePlayer().getMule(), 0);
		assertEquals(engine.getActivePlayer().getFood(), 4);
		assertEquals(engine.getActivePlayer().getEnergy(), 2);
		assertEquals(engine.getActivePlayer().getOre(), 0);
		
		assertEquals(engine.getStore().getQuantity("Food"), 8);
		assertEquals(engine.getStore().getQuantity("Energy"), 8);
		assertEquals(engine.getStore().getQuantity("Smithore"), 8);
		assertEquals(engine.getStore().getQuantity("Crystite"), 0);
		assertEquals(engine.getStore().getQuantity("Mules"), 14);

		assertEquals(engine.getStore().getCurrentPrice("Food"), 30);
		assertEquals(engine.getStore().getCurrentPrice("Energy"), 25);
		assertEquals(engine.getStore().getCurrentPrice("Smithore"), 50);
		assertEquals(engine.getStore().getCurrentPrice("Crystite"), 100);
		assertEquals(engine.getStore().getCurrentPrice("Mules"), 100);
		
	}
	
	

}
