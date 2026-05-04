package com.bnpp.kata.tictactoe.service;

import com.bnpp.kata.tictactoe.domain.Game;
import com.bnpp.kata.tictactoe.domain.GameStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
public class TicTacToeService {

    private static final int[][] PATTERNS = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    public Game createGame() {
        String id = UUID.randomUUID().toString();
        Game newGame = new Game(id);

        return newGame;
    }

    public Game move(Game game, int position) {
        char symbol = game.getCurrentPlayer().symbol();
        game.update(position, symbol);
        win(game, symbol);
        draw(game);
        next(game);
        return game;
    }

    private static void next(Game game) {
        if (game.getStatus() == GameStatus.IN_PROGRESS) {
            game.setCurrentPlayer(game.getCurrentPlayer().next());
        }
    }

    private void win(Game game, char symbol) {
        if (isWin(game.getBoard(), symbol)) {
            game.setStatus(GameStatus.WIN);
        }
    }

    public boolean isWin(char[] board, char symbol) {
        return Arrays.stream(PATTERNS)
                .anyMatch(boardArray -> Arrays.stream(boardArray)
                        .mapToObj(boardPosition -> board[boardPosition])
                        .allMatch(player -> player == symbol));
    }

    private void draw(Game game) {
        if (game.getStatus() == GameStatus.IN_PROGRESS &&
                isBoardFull(game.getBoard())) {
            game.setStatus(GameStatus.DRAW);
        }
    }

    private boolean isBoardFull(char[] board) {
        for (char c : board) {
            if (c == '-') { // assuming '-' is your empty marker
                return false;
            }
        }
        return true;
    }
}
