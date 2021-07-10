package com.javachallenge;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bowling player.
 *
 * @author Diego Peliser
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor
public class Player {

    @NonNull
    private final String name;

    private final List<Frame> frames = new ArrayList<>();

    private Frame currentFrame;

    /**
     * Get the current frame of this player.
     *
     * @return The current frame of the player.
     */
    public Frame getCurrentFrame() {
        if (this.currentFrame == null) {
            this.currentFrame = new Frame(frames.size() + 1);
            this.frames.add(this.currentFrame);
        }
        return this.currentFrame;
    }

    /**
     * Finished the current frame of the player.
     */
    public void finishCurrentFrame() {
        this.currentFrame = null;
    }

}
