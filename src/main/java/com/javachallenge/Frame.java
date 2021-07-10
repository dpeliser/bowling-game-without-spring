package com.javachallenge;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Represents a bowling frame.
 *
 * @author Diego Peliser
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@Setter
@RequiredArgsConstructor
public class Frame {

    @NonNull
    private Integer index;

    private Integer firstScore;

    private Integer secondScore;

    private Integer thirdScore;

    private Integer totalScore;

    private Roll roll = Roll.FIRST;

    /**
     * Finished the current roll moving to the next one.
     */
    public void finishCurrentRoll() {
        this.roll = this.roll.next();
    }

    /**
     * Check if this is the last roll and is a strike or a spare to have additional roll.
     *
     * @return True if this frame has additional roll, false otherwise.
     */
    public boolean hasAdditionalRoll() {
        return isLast() && (isStrike() || isSpare());
    }

    /**
     * Check if this is the last frame.
     *
     * @return True if this is the last index, false otherwise.
     */
    public boolean isLast() {
        return this.index == 10;
    }

    /**
     * Check if the first score is a strike (10 points).
     *
     * @return True if is a strike, false otherwise.
     */
    public boolean isStrike() {
        return this.firstScore == 10;
    }

    /**
     * Check if the first and second scores are a spare (10 points).
     *
     * @return True if is a spare, false otherwise.
     */
    public boolean isSpare() {
        return !isStrike() && this.firstScore + this.secondScore == 10;
    }

}
