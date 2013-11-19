import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;
import org.junit.runners.JUnit4;


public class MULEGameEngineTest {
	private MULEGameEngine engine;
	Random rand = new Random();
	private int timeleft;

	
	
	@Test
	public final void testGetGambleMoney() {
		timeleft = rand.nextInt(50);
		if ((timeleft >= 37 && timeleft <= 50) && (engine.getCurrentRound() <= 3 && engine.getCurrentRound() >= 0))
			assertTrue(engine.getGambleMoney(timeleft)<=250);
		else if ((timeleft >= 37 && timeleft <= 50) && (engine.getCurrentRound() <= 7 && engine.getCurrentRound() >= 4))
			assertTrue(engine.getGambleMoney(timeleft)<=300);
		else if ((timeleft >= 37 && timeleft <= 50) && (engine.getCurrentRound() <= 11 && engine.getCurrentRound() >= 8))
			assertTrue(engine.getGambleMoney(timeleft)<=350);
		else if ((timeleft >= 37 && timeleft <= 50) && (engine.getCurrentRound() >= 12))
			assertTrue(engine.getGambleMoney(timeleft)<=400);
		else if ((timeleft >= 25 && timeleft < 37) && (engine.getCurrentRound() <= 3 && engine.getCurrentRound() >= 0))
			assertTrue(engine.getGambleMoney(timeleft)<=200);
		else if ((timeleft >= 25 && timeleft < 37) && (engine.getCurrentRound() <= 7 && engine.getCurrentRound() >= 4))
			assertTrue(engine.getGambleMoney(timeleft)<=250);
		else if ((timeleft >= 25 && timeleft < 37) && (engine.getCurrentRound() <= 11 && engine.getCurrentRound() >= 8))
			assertTrue(engine.getGambleMoney(timeleft)<=300);
		else if ((timeleft >= 25 && timeleft < 37) && (engine.getCurrentRound() >= 12))
			assertTrue(engine.getGambleMoney(timeleft)<=350);
		else if ((timeleft >= 12 && timeleft < 25) && (engine.getCurrentRound() <= 3 && engine.getCurrentRound() >= 0))
			assertTrue(engine.getGambleMoney(timeleft)<=150);
		else if ((timeleft >= 12 && timeleft < 25) && (engine.getCurrentRound() <= 7 && engine.getCurrentRound() >= 4))
			assertTrue(engine.getGambleMoney(timeleft)<=200);
		else if ((timeleft >= 12 && timeleft < 25) && (engine.getCurrentRound() <= 11 && engine.getCurrentRound() >= 8))
			assertTrue(engine.getGambleMoney(timeleft)<=250);
		else if ((timeleft >= 12 && timeleft < 25) && (engine.getCurrentRound() >= 12))
			assertTrue(engine.getGambleMoney(timeleft)<=300);
		else if ((timeleft >= 0 && timeleft < 12) && (engine.getCurrentRound() <= 3 && engine.getCurrentRound() >= 0))
			assertTrue(engine.getGambleMoney(timeleft)<=100);
		else if ((timeleft >= 0 && timeleft < 12) && (engine.getCurrentRound() <= 7 && engine.getCurrentRound() >= 4))
			assertTrue(engine.getGambleMoney(timeleft)<=150);
		else if ((timeleft >= 0 && timeleft < 12) && (engine.getCurrentRound() <= 11 && engine.getCurrentRound() >= 8))
			assertTrue(engine.getGambleMoney(timeleft)<=200);
		else if ((timeleft >= 0 & timeleft < 12) && (engine.getCurrentRound() >= 12))
			assertTrue(engine.getGambleMoney(timeleft)<=250);
			
	}

}
