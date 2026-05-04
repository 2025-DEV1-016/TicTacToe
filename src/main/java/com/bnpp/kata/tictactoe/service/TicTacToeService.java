package com.bnpp.kata.tictactoe.service;

import com.bnpp.kata.tictactoe.domain.Game;
import com.bnpp.kata.tictactoe.domain.GameStatus;
import com.bnpp.kata.tictactoe.strategy.WinStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicTacToeService {

    private final WinStrategy winStrategy;

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
        if (winStrategy.isWin(game.getBoard(), symbol)) {
            game.setStatus(GameStatus.WIN);
        }
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
