package com.javachallenge;

/**
 * Represents a roll of a bowling frame.
 *
 * @author Diego Peliser
 * @version 1.0.0
 * @since 1.0.0
 */
public enum Roll {

    FIRST,

    SECOND,

    THIRD;

    /**
     * Indicates if this roll is the first one.
     *
     * @return True if is the first roll, false otherwise.
     */
    public boolean isFirst() {
        return this == FIRST;
    }

    /**
     * Indicates if this roll is the second one.
     *
     * @return True if is the second roll, false otherwise.
     */
    public boolean isSecond() {
        return this == SECOND;
    }

    /**
     * Get the next roll.
     *
     * @return The next roll.
     */
    public Roll next() {
        return isFirst() ? SECOND : THIRD;
    }

}
