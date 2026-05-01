package com.bnpp.kata.tictactoe.service;

import com.bnpp.kata.tictactoe.domain.Game;
import com.bnpp.kata.tictactoe.domain.GameStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TicTacToeService {

    public Game createGame() {
        String id = UUID.randomUUID().toString();
        Game newGame = new Game(id);

        return newGame;
    }

    public Game move(Game game, int position) {
        char symbol = game.getCurrentPlayer().symbol();
        game.update(position, symbol);
        next(game);
        return game;
    }

    private static void next(Game game) {
        if (game.getStatus() == GameStatus.IN_PROGRESS) {
            game.setCurrentPlayer(game.getCurrentPlayer().next());
        }
    }
}
