package com.bnpp.kata.tictactoe.service;

import com.bnpp.kata.tictactoe.domain.Game;
import com.bnpp.kata.tictactoe.engine.GameEngine;
import com.bnpp.kata.tictactoe.validator.TicTacToeMoveValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicTacToeService {

    private final TicTacToeMoveValidator ticTacToeMoveValidator;

    private final GameEngine gameEngine;

    public Game createGame() {

        String id = UUID.randomUUID().toString();

        Game newGame = new Game(id);

        return newGame;
    }

    public Game move(Game game, int position) {

        ticTacToeMoveValidator.validate(game, position);

        gameEngine.applyMove(game, position);

        return game;
    }
}
