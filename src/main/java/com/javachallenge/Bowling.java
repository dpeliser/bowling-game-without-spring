package com.javachallenge;

import lombok.var;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a bowling game.
 *
 * @author Diego Peliser
 * @version 1.0.0
 * @since 1.0.0
 */
public class Bowling {

    private final Map<String, Player> players = new HashMap<>();

    /**
     * Calculates the last score of the frame.
     *
     * @param frame to get the last score
     * @return The last score of the frame.
     */
    private Integer getLastScore(final Frame frame) {
        var totalScore = frame.getFirstScore() + frame.getSecondScore();
        if (frame.isStrike() || frame.isSpare()) {
            totalScore += frame.getThirdScore();
        }
        return totalScore;
    }

    /**
     * Calculates the score of the frame that has a strike.
     *
     * @param frame           that has a strike to get the score
     * @param nextFrame       to calculate the strike score
     * @param secondNextFrame to calculate the strike score
     * @return The total score of the frame that has a strike.
     */
    private Integer getStrikeScore(final Frame frame, final Frame nextFrame, final Frame secondNextFrame) {
        var totalScore = frame.getFirstScore() + nextFrame.getFirstScore();
        if (nextFrame.isLast()) {
            totalScore += nextFrame.getSecondScore();
        } else {
            totalScore += nextFrame.isStrike() ? secondNextFrame.getFirstScore() : nextFrame.getSecondScore();
        }
        return totalScore;
    }

    /**
     * Calculates the score of the frame that has a spare.
     *
     * @param frame     that has a spare to get the score
     * @param nextFrame to calculate the spare score
     * @return The total score of the frame that has a spare.
     */
    private Integer getSpareScore(final Frame frame, final Frame nextFrame) {
        return frame.getFirstScore() + frame.getSecondScore() + nextFrame.getFirstScore();
    }

    /**
     * Calculates the score of the frame that has neither strike nor spare.
     *
     * @param frame to get the score
     * @return The total score of the frame.
     */
    private Integer getScore(final Frame frame) {
        return frame.getFirstScore() + frame.getSecondScore();
    }

    /**
     * Calculates the score of the bowling game.
     */
    public void calculateScore() {
        getPlayers().forEach(player -> {
            var frames = player.getFrames();
            var size = frames.size();
            for (int i = 0; i < size; i++) {
                var frame = frames.get(i);
                var totalScore = 0;
                if (frame.isLast()) {
                    totalScore = getLastScore(frame);
                } else if (frame.isStrike()) {
                    var nextFrame = frames.get(i + 1);
                    var secondNextFrameIndex = i + 2;
                    var secondNextFrame = secondNextFrameIndex < size ? frames.get(secondNextFrameIndex) : null;
                    totalScore = getStrikeScore(frame, nextFrame, secondNextFrame);
                } else if (frame.isSpare()) {
                    var nextFrame = frames.get(i + 1);
                    totalScore = getSpareScore(frame, nextFrame);
                } else {
                    totalScore = getScore(frame);
                }
                frame.setTotalScore(totalScore);
            }
        });
    }

    /**
     * Add a score to a player of the bowling game.
     *
     * @param playerName to add the score
     * @param score      to add
     */
    public void addScore(final String playerName, final Integer score) {
        var player = getPlayer(playerName);
        var currentFrame = player.getCurrentFrame();
        var roll = currentFrame.getRoll();

        currentFrame.finishCurrentRoll();

        if (currentFrame.isLast()) {
            if (roll.isFirst()) {
                currentFrame.setFirstScore(score);
            } else if (roll.isSecond()) {
                currentFrame.setSecondScore(score);
                if (!currentFrame.hasAdditionalRoll()) {
                    player.finishCurrentFrame();
                }
            } else {
                currentFrame.setThirdScore(score);
                player.finishCurrentFrame();
            }
        } else {
            if (roll.isFirst()) {
                currentFrame.setFirstScore(score);
                if (currentFrame.isStrike()) {
                    player.finishCurrentFrame();
                }
            } else {
                currentFrame.setSecondScore(score);
                player.finishCurrentFrame();
            }
        }
    }

    /**
     * Get a player of the bowling game by name.
     *
     * @param playerName to get the player
     * @return A player of the bowling game.
     */
    public Player getPlayer(final String playerName) {
        var player = this.players.get(playerName);
        if (player == null) {
            player = new Player(playerName);
            this.players.put(playerName, player);
        }
        return player;
    }

    /**
     * Get the players of the bowling game.
     *
     * @return A list of the players of the bowling game.
     */
    public List<Player> getPlayers() {
        return this.players.values().stream().collect(Collectors.toList());
    }

}
