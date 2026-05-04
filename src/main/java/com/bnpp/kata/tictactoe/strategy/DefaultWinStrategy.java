package com.bnpp.kata.tictactoe.strategy;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DefaultWinStrategy implements WinStrategy{
    private static final int[][] PATTERNS = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    public boolean isWin(char[] board, char symbol) {
        return Arrays.stream(PATTERNS)
                .anyMatch(boardArray -> Arrays.stream(boardArray)
                        .mapToObj(boardPosition -> board[boardPosition])
                        .allMatch(player -> player == symbol));
    }
}
