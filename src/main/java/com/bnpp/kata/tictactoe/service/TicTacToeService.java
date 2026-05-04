package com.bnpp.kata.tictactoe.service;

import com.bnpp.kata.tictactoe.domain.Game;
import com.bnpp.kata.tictactoe.engine.GameEngine;
import com.bnpp.kata.tictactoe.validator.TicTacToeMoveValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicTacToeService {

    public static final String GAME_NOT_FOUND = "Game not found: ";

    private final TicTacToeMoveValidator validator;

    private final GameEngine gameEngine;

    private final Map<String, Game> store = new ConcurrentHashMap<>();

    public Game createGame() {

        String gameId = UUID.randomUUID().toString();

        log.info("Creating new game with id={}", gameId);

        Game newGame = new Game(gameId);

        store.put(gameId, newGame);

        return newGame;
    }

    public Game move(String gameId, int position) {

        Game game = getGame(gameId);

        validator.validate(game, position);

        gameEngine.applyMove(game, position);

        return game;
    }

    public Game reset(String gameId) {
        log.info("Resetting game with id={}", gameId);
        Game existingGame = getGame(gameId);

        Game newGame = new Game(gameId);
        store.put(gameId, newGame);
        return newGame;
    }

    public Game getGame(String gameId) {
        return Optional.ofNullable(store.get(gameId))
                .orElseThrow(() -> new NoSuchElementException(GAME_NOT_FOUND + gameId));
    }

}
