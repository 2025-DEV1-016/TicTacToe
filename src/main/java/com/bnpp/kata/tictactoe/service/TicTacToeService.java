package com.bnpp.kata.tictactoe.service;

import com.bnpp.kata.tictactoe.domain.Game;
import com.bnpp.kata.tictactoe.engine.GameEngine;
import com.bnpp.kata.tictactoe.validator.TicTacToeMoveValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class TicTacToeService {

    private final TicTacToeMoveValidator ticTacToeMoveValidator;

    private final GameEngine gameEngine;

    private final Map<String, Game> store = new ConcurrentHashMap<>();

    public Game createGame() {

        String gameId = UUID.randomUUID().toString();

        Game newGame = new Game(gameId);

        store.put(gameId, newGame);

        return newGame;
    }

    public Game move(Game game, int position) {

        ticTacToeMoveValidator.validate(game, position);

        gameEngine.applyMove(game, position);

        return game;
    }

    public Game reset(Game game) {

        String gameId = game.getGameId();

        if (!store.containsKey(gameId)) {
            throw new NoSuchElementException("Game not found: " + gameId);
        }

        Game newGame = new Game(gameId);
        store.put(gameId, newGame);
        return newGame;
    }



}
