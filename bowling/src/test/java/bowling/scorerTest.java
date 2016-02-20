package bowling;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Set;

import org.junit.After;
import org.junit.Test;

import static org.mockito.Mockito.*;
import junit.framework.TestCase;

public class scorerTest 
    extends TestCase
{
	private Scorer scorer;
	private final int STRIKE = 10;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	@Test
	public void testConstructor() {
		scorer = new Scorer();
		assertEquals(1, scorer.frameNumber());
		assertEquals(0, scorer.scoreSoFar());
		assertFalse(scorer.gameIsOver());
	}

	@Test
    public void testStrikeStrikeSpare()
    {
        scorer = new Scorer();
        scorer.roll(STRIKE);
        assertEquals(2, scorer.frameNumber());
		assertFalse(scorer.gameIsOver());
		assertEquals(0, scorer.scoreSoFar());
		
		scorer.roll(STRIKE);
        assertEquals(3, scorer.frameNumber());
		assertFalse(scorer.gameIsOver());
		assertEquals(0, scorer.scoreSoFar());

		scorer.roll(4);
        assertEquals(3, scorer.frameNumber());
		assertFalse(scorer.gameIsOver());
		assertEquals(24, scorer.scoreSoFar());

		scorer.roll(6);
        assertEquals(4, scorer.frameNumber());
		assertFalse(scorer.gameIsOver());
		assertEquals(44, scorer.scoreSoFar());
    }
    
    @Test
    public void testStrikeStrikeMiss()
    {
        scorer = new Scorer();
        scorer.roll(STRIKE);
        assertEquals(2, scorer.frameNumber());
		assertFalse(scorer.gameIsOver());
		assertEquals(0, scorer.scoreSoFar());
		
		scorer.roll(STRIKE);
        assertEquals(3, scorer.frameNumber());
		assertFalse(scorer.gameIsOver());
		assertEquals(0, scorer.scoreSoFar());
		
        scorer.roll(4);
        assertEquals(3, scorer.frameNumber());
		assertFalse(scorer.gameIsOver());
		assertEquals(24, scorer.scoreSoFar());
		
		scorer.roll(4);
        assertEquals(4, scorer.frameNumber());
		assertFalse(scorer.gameIsOver());
		assertEquals(50, scorer.scoreSoFar());
 
    }
    
    @Test
    public void testMiss()
    {
        scorer = new Scorer();

		scorer.roll(4);
        assertEquals(1, scorer.frameNumber());
		assertFalse(scorer.gameIsOver());
		assertEquals(0, scorer.scoreSoFar());

		scorer.roll(4);
        assertEquals(2, scorer.frameNumber());
		assertFalse(scorer.gameIsOver());
		assertEquals(8, scorer.scoreSoFar());
    }
    
    @Test
    public void testSpareStrike()
    {
        scorer = new Scorer();

		scorer.roll(4);
        assertEquals(1, scorer.frameNumber());
		assertFalse(scorer.gameIsOver());
		assertEquals(0, scorer.scoreSoFar());

		scorer.roll(6);
        assertEquals(2, scorer.frameNumber());
		assertFalse(scorer.gameIsOver());
		assertEquals(0, scorer.scoreSoFar());

		scorer.roll(10);
        assertEquals(3, scorer.frameNumber());
		assertFalse(scorer.gameIsOver());
		assertEquals(20, scorer.scoreSoFar());
    }
    
    @Test
    public void testSpareMiss()
    {
        scorer = new Scorer();

		scorer.roll(4);
        assertEquals(1, scorer.frameNumber());
		assertFalse(scorer.gameIsOver());
		assertEquals(0, scorer.scoreSoFar());

		scorer.roll(6);
        assertEquals(2, scorer.frameNumber());
		assertFalse(scorer.gameIsOver());
		assertEquals(0, scorer.scoreSoFar());

		scorer.roll(4);
        assertEquals(2, scorer.frameNumber());
		assertFalse(scorer.gameIsOver());
		assertEquals(14, scorer.scoreSoFar());
    }

    @Test
    public void testBowlingGame() throws IOException {
    	BufferedReader bufferedReaderMock = mock(BufferedReader.class);
		when(bufferedReaderMock.readLine()).thenReturn("Kate Paul");
		
		System.setOut(new PrintStream(outContent));
    	Set<String> players = BowlingGame.readIntpuData(bufferedReaderMock);
    	assertEquals(2, players.size());
    	assertTrue(players.contains("Kate"));
    	assertTrue(players.contains("Paul"));
    	
    	BowlingGame.runGame(bufferedReaderMock, System.out); 
    	assertFalse(outContent.toString().isEmpty());
    }
    
    @Test (expected = IOException.class)
    public void testBowlingGameException() throws IOException {
    	BufferedReader bufferedReaderMock = mock(BufferedReader.class);
    	doThrow(new IOException()).when(bufferedReaderMock).readLine();
		BowlingGame.readIntpuData(bufferedReaderMock); 	
    }
    
    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

}
