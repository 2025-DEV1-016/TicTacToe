package com.bnpp.kata.tictactoe.validator;

import com.bnpp.kata.tictactoe.domain.Game;
import com.bnpp.kata.tictactoe.domain.GameStatus;
import org.springframework.stereotype.Component;

@Component
public class TicTacToeMoveValidator {

    private static final String GAME_ALREADY_FINISHED = "Game already finished";
    private static final String INVALID_POSITION = "Invalid position: ";
    private static final String CELL_OCCUPIED = "Cell already occupied at position: ";

    public void validate(Game game, int position) {

        if (game.getStatus() != GameStatus.IN_PROGRESS) {
            throw new IllegalStateException(GAME_ALREADY_FINISHED);
        }

        char[] board = game.getBoard();

        if (position < 0 || position >= board.length) {
            throw new IllegalArgumentException(INVALID_POSITION + position);
        }

        if (board[position] != Game.EMPTY) {
            throw new IllegalArgumentException(CELL_OCCUPIED + position);
        }
    }
}
