package com.javachallenge;

import lombok.var;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.javachallenge.Constants.FOUL;
import static com.javachallenge.Constants.SPARE;
import static com.javachallenge.Constants.STRIKE;
import static com.javachallenge.Constants.TAB;

public class JavaChallenge {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            throw new RuntimeException("You need to pass a filename as parameter!");
        }

        var fileName = args[0];
        var file = new File(fileName);

        if (!file.exists()) {
            throw new RuntimeException("The filename you passed as parameter does not exist!");
        }

        var bowling = new Bowling();

        try (var lines = Files.lines(file.toPath())) {
            lines.forEach(line -> {
                var columns = line.split(TAB);
                var playerName = columns[0];
                var score = parseScore(columns[1]);
                bowling.addScore(playerName, score);
            });
        }

        bowling.calculateScore();

        System.out.println("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10");
        bowling.getPlayers().forEach(player -> {
            System.out.println(player.getName());
            var frames = player.getFrames();

            var pinfalls = new StringBuilder("Pinfalls").append(TAB);
            for (int i = 0; i < frames.size(); i++) {
                var frame = frames.get(i);
                if (frame.isLast()) {
                    if (frame.isStrike()) {
                        pinfalls.append(STRIKE);
                        pinfalls.append(TAB);
                        pinfalls.append(frame.getSecondScore() == 10 ? STRIKE : frame.getSecondScore());
                        pinfalls.append(TAB);
                        pinfalls.append(frame.getThirdScore());
                    } else if (frame.isSpare()) {
                        pinfalls.append(frame.getFirstScore());
                        pinfalls.append(TAB);
                        pinfalls.append(SPARE);
                        pinfalls.append(TAB);
                        pinfalls.append(frame.getThirdScore());
                    } else {
                        pinfalls.append(frame.getFirstScore());
                        pinfalls.append(TAB);
                        pinfalls.append(frame.getSecondScore());
                    }
                } else if (frame.isStrike()) {
                    pinfalls.append(TAB);
                    pinfalls.append(STRIKE);
                } else if (frame.isSpare()) {
                    pinfalls.append(frame.getFirstScore());
                    pinfalls.append(TAB);
                    pinfalls.append(SPARE);
                } else {
                    pinfalls.append(frame.getFirstScore());
                    pinfalls.append(TAB);
                    pinfalls.append(frame.getSecondScore());
                }
                if (i < 9) {
                    pinfalls.append(TAB);
                }
            }
            System.out.println(pinfalls);

            var totalScore = 0;
            var score = new StringBuilder("Score").append(TAB).append(TAB);
            for (int i = 0; i < frames.size(); i++) {
                var frame = frames.get(i);
                totalScore += frame.getTotalScore();
                score.append(totalScore);
                if (i < 9) {
                    score.append(TAB);
                    score.append(TAB);
                }
            }
            System.out.println(score);
        });
    }

    private static Integer parseScore(final String score) {
        return score.equals(FOUL) ? 0 : Integer.parseInt(score);
    }

}