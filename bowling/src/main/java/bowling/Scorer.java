package bowling;

import java.util.ArrayList;
import java.util.List;

public class Scorer {
	private int rollingFrame;
	private int totalScore;
	private List<Integer> frameScores;
	private int state;
	private static final int ROLLING_FIRST_BALL = 0;
	private static final int ROLLING_SECOND_BALL = 1;
	private static final int STRIKE_LAST_BALL = 2;
	private static final int TWO_CONSEC_STRIKES = 3;
	private static final int STRIKE_2_BALLS_AGO = 4;
	private static final int SPARE_LAST_BALL = 5;
	private static int MAX_SCORE_ONE_BALL = 10;
	private int firstBallInFrame;
	private int lastFrameNumber;

	public Scorer() {
		this(10);
	}

	public Scorer(int frameCount) {
		lastFrameNumber = frameCount;
		rollingFrame = 1;
		totalScore = 0;
		frameScores = new ArrayList<Integer>();
		state = ROLLING_FIRST_BALL;
	}

	public int frameNumber() {
		if (frameScores.size() == lastFrameNumber) {
			return lastFrameNumber + 1;
		} else if (rollingFrame > lastFrameNumber) {
			return lastFrameNumber;
		} else {
			return rollingFrame;
		}
	}

	public int scoreSoFar() {
		if (frameScores.size() == lastFrameNumber) {
			return frameScores.get(lastFrameNumber - 1);
		} else {
			return totalScore;
		}
	}

	public boolean gameIsOver() {
		return frameNumber() > lastFrameNumber;
	}

	public List<Integer> roll(int ball) {
		if (ball > 10 || ball < 0) {
			throw new IllegalArgumentException("Invalid ball score");
		}
		if (state == ROLLING_FIRST_BALL) {
			if (ball == MAX_SCORE_ONE_BALL) {
				rollingFrame++;
				state = STRIKE_LAST_BALL;
			} else {
				firstBallInFrame = ball;
				state = ROLLING_SECOND_BALL;
			}
		} else if (state == ROLLING_SECOND_BALL) {
			if (firstBallInFrame + ball == MAX_SCORE_ONE_BALL) {
				rollingFrame++;
				state = SPARE_LAST_BALL;
			} else {
				rollingFrame++;
				addFrame(firstBallInFrame + ball);
				state = ROLLING_FIRST_BALL;
			}
		} else if (state == SPARE_LAST_BALL) {
			addFrame(MAX_SCORE_ONE_BALL + ball);
			if (ball == MAX_SCORE_ONE_BALL) {
				rollingFrame++;
				state = STRIKE_LAST_BALL;
			} else {
				firstBallInFrame = ball;
				state = ROLLING_SECOND_BALL;
			}
		} else if (state == STRIKE_LAST_BALL) {
			if (ball == MAX_SCORE_ONE_BALL) {
				rollingFrame++;
				state = TWO_CONSEC_STRIKES;
			} else {
				firstBallInFrame = ball;
				state = STRIKE_2_BALLS_AGO;
			}
		} else if (state == TWO_CONSEC_STRIKES) {
			addFrame(2 * MAX_SCORE_ONE_BALL + ball);
			if (ball == MAX_SCORE_ONE_BALL) {
				rollingFrame++;
			} else {
				firstBallInFrame = ball;
				state = STRIKE_2_BALLS_AGO;
			}
		} else if (state == STRIKE_2_BALLS_AGO) {
			addFrame(MAX_SCORE_ONE_BALL + firstBallInFrame + ball);
			if (firstBallInFrame + ball == MAX_SCORE_ONE_BALL) {
				rollingFrame++;
				state = SPARE_LAST_BALL;
			} else {
				rollingFrame++;
				addFrame(firstBallInFrame + ball);
				state = ROLLING_FIRST_BALL;
			}
		} else {
			throw new IllegalArgumentException("Invalid State number");
		}
		return frameScores;
	}

	private void addFrame(int toAdd) {
		totalScore = totalScore + toAdd;
		if (frameScores.size() < lastFrameNumber) {
			frameScores.add(totalScore);
		}
	}
}
