package bowling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BowlingGame {
	
	public static Set<String> readIntpuData(BufferedReader bufferedReader) {
	    try {
		  return new HashSet<String>(Arrays.asList(bufferedReader.readLine().split(" ")));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	    return Collections.emptySet();
	}
	
	public static void runGame(BufferedReader bufferedReader, PrintStream outputStream) {
		Set<String> players = readIntpuData(bufferedReader);
		Random randomGenerator = new Random();
		for (String player: players) {
			Scorer scorer = new Scorer();
			while (!scorer.gameIsOver()) {
				int ball = randomGenerator.nextInt(11);
				scorer.roll(ball);
			}
			outputResult(scorer.scoreSoFar(), player, outputStream);
		}
	}
	
	public static void main(String[] args) {
		runGame(new BufferedReader(new InputStreamReader(System.in)), System.out);
	}

	private static void outputResult(int scoreSoFar, String player, PrintStream outputStream) {
		outputStream.print("Player " + player + " has " + scoreSoFar + " points."
				+ "\n");	
	}
}
