package com.bnpp.kata.tictactoe.service;

import com.bnpp.kata.tictactoe.domain.Game;
import com.bnpp.kata.tictactoe.domain.GameStatus;
import com.bnpp.kata.tictactoe.domain.Player;
import com.bnpp.kata.tictactoe.engine.GameEngine;
import com.bnpp.kata.tictactoe.strategy.DefaultWinStrategy;
import com.bnpp.kata.tictactoe.validator.TicTacToeMoveValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeServiceTest {

    public static final int BOARD_LENGTH = 9;
    public static final String UNKNOWN_GAME_ID = "unknown-id";
    public static final String GAME_NOT_FOUND = "Game not found: ";
    public static final String INVALID_GAME_ID = "invalid-id";
    public static final String EMPTY_BOARD = "---------";

    private TicTacToeService ticTacToeService;

    @BeforeEach
    public void setUp() {

        ticTacToeService = new TicTacToeService(new TicTacToeMoveValidator(), new GameEngine(new DefaultWinStrategy()));

    }

    @Test
    @DisplayName("createGame initializes empty board with Player X and IN_PROGRESS status")
    void shouldCreateGame() {

        Game newGame = ticTacToeService.createGame();

        assertNotNull(newGame.getGameId());
        assertEquals(Player.X, newGame.getCurrentPlayer());
        assertEquals(GameStatus.IN_PROGRESS, newGame.getStatus());
        assertEquals(BOARD_LENGTH, newGame.getBoard().length);
    }

    @Test
    @DisplayName("move should place X at the given position on first turn")
    void shouldPlaceXFirst() {
        Game game = ticTacToeService.createGame();

        Game updated = ticTacToeService.move(game.getGameId(), 0);

        assertEquals('X', updated.getBoard()[0]);
    }

    @Test
    @DisplayName("Should alternate players between X and O")
    void shouldAlternatePlayers() {
        Game game = ticTacToeService.createGame();

        ticTacToeService.move(game.getGameId(), 0);

        Game updated = ticTacToeService.move(game.getGameId(), 1);

        assertEquals('O', updated.getBoard()[1]);
    }

    @Test
    @DisplayName("should set game status to WIN on a winning move")
    void shouldDetectWin() {
        Game game = ticTacToeService.createGame();

        ticTacToeService.move(game.getGameId(), 0);
        ticTacToeService.move(game.getGameId(), 3);
        ticTacToeService.move(game.getGameId(), 1);
        ticTacToeService.move(game.getGameId(), 4);

        Game result = ticTacToeService.move(game.getGameId(), 2);

        assertEquals(GameStatus.WIN, result.getStatus());
        assertEquals('X', result.getBoard()[2]);
    }

    @Test
    @DisplayName("should set game status to DRAW when board is full with no winner")
    void shouldDetectDraw() {
        Game game = ticTacToeService.createGame();

        ticTacToeService.move(game.getGameId(), 0);
        ticTacToeService.move(game.getGameId(), 1);
        ticTacToeService.move(game.getGameId(), 2);
        ticTacToeService.move(game.getGameId(), 4);
        ticTacToeService.move(game.getGameId(), 3);
        ticTacToeService.move(game.getGameId(), 5);
        ticTacToeService.move(game.getGameId(), 7);
        ticTacToeService.move(game.getGameId(), 6);

        Game result = ticTacToeService.move(game.getGameId(), 8);

        assertEquals(GameStatus.DRAW, result.getStatus());
    }

    @Test
    @DisplayName("should not allow moves after a game is won")
    void shouldNotAllowMoveAfterWin() {
        Game game = ticTacToeService.createGame();

        ticTacToeService.move(game.getGameId(), 0);
        ticTacToeService.move(game.getGameId(), 3);
        ticTacToeService.move(game.getGameId(), 1);
        ticTacToeService.move(game.getGameId(), 4);
        ticTacToeService.move(game.getGameId(), 2);

        assertThrows(IllegalStateException.class,
                () -> ticTacToeService.move(game.getGameId(), 5));
    }

    @Test
    @DisplayName("should not allow moves after a game is drawn")
    void shouldNotAllowMoveAfterDraw() {
        Game game = ticTacToeService.createGame();

        ticTacToeService.move(game.getGameId(), 0);
        ticTacToeService.move(game.getGameId(), 1);
        ticTacToeService.move(game.getGameId(), 2);
        ticTacToeService.move(game.getGameId(), 4);
        ticTacToeService.move(game.getGameId(), 3);
        ticTacToeService.move(game.getGameId(), 5);
        ticTacToeService.move(game.getGameId(), 7);
        ticTacToeService.move(game.getGameId(), 6);
        ticTacToeService.move(game.getGameId(), 8);

        assertThrows(IllegalStateException.class,
                () -> ticTacToeService.move(game.getGameId(), 0));
    }

    @ParameterizedTest
    @ValueSource(ints = {9, -1})
    @DisplayName("should not allow invalid board positions")
    void shouldNotAllowInvalidPositions(int position) {
        Game game = ticTacToeService.createGame();

        assertThrows(IllegalArgumentException.class,
                () -> ticTacToeService.move(game.getGameId(), position));
    }

    @Test
    @DisplayName("should not allow placing a move on an already occupied cell")
    void shouldNotAllowSameCellTwice() {
        Game game = ticTacToeService.createGame();

        ticTacToeService.move(game.getGameId(), 0);

        assertThrows(IllegalArgumentException.class,
                () -> ticTacToeService.move(game.getGameId(), 0));
    }

    @Test
    @DisplayName("should not switch player after game ends")
    void shouldNotSwitchPlayerAfterGameEnds() {
        Game game = ticTacToeService.createGame();

        ticTacToeService.move(game.getGameId(), 0);
        ticTacToeService.move(game.getGameId(), 3);
        ticTacToeService.move(game.getGameId(), 1);
        ticTacToeService.move(game.getGameId(), 4);

        Game result = ticTacToeService.move(game.getGameId(), 2);

        assertEquals(Player.X, result.getCurrentPlayer());
    }

    @Test
    @DisplayName("Should reset the game to its initial state")
    void shouldResetGameToInitialState() {
        Game game = ticTacToeService.createGame();

        ticTacToeService.move(game.getGameId(), 0);

        Game resetGame = ticTacToeService.reset(game.getGameId());

        assertEquals(game.getGameId(), resetGame.getGameId());
        assertEquals(GameStatus.IN_PROGRESS, resetGame.getStatus());
        assertEquals(Player.X, resetGame.getCurrentPlayer());
        assertArrayEquals(EMPTY_BOARD.toCharArray(), resetGame.getBoard());
    }

    @Test
    @DisplayName("should reset existing game")
    void shouldResetExistingGame() {

        Game game = ticTacToeService.createGame();

        Game resetGame = ticTacToeService.reset(game.getGameId());

        assertEquals(game.getGameId(), resetGame.getGameId());
    }

    @Test
    @DisplayName("should throw when resetting non-existing game")
    void shouldThrowWhenResetGameNotFound() {

        assertThrows(NoSuchElementException.class, () ->
                ticTacToeService.reset(INVALID_GAME_ID)
        );
    }

    @Test
    @DisplayName("should throw exception when game not found")
    void shouldThrowWhenGameNotFound() {

        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> ticTacToeService.getGame(UNKNOWN_GAME_ID)
        );

        assertEquals(GAME_NOT_FOUND + UNKNOWN_GAME_ID, exception.getMessage());
    }

    @Test
    @DisplayName("should create game and store it retrievable by id")
    void shouldCreateAndStoreGame() {

        Game game = ticTacToeService.createGame();

        assertNotNull(game.getGameId());

        Game fetched = ticTacToeService.getGame(game.getGameId());

        assertEquals(game.getGameId(), fetched.getGameId());
    }

}
