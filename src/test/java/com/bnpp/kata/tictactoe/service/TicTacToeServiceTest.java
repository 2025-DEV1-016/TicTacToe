package com.bnpp.kata.tictactoe.service;

import com.bnpp.kata.tictactoe.domain.Game;
import com.bnpp.kata.tictactoe.domain.GameStatus;
import com.bnpp.kata.tictactoe.domain.Player;
import com.bnpp.kata.tictactoe.strategy.DefaultWinStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeServiceTest {

    public static final int BOARD_LENGTH = 9;

    private TicTacToeService ticTacToeService;

    @BeforeEach
    public void setUp() {
        ticTacToeService = new TicTacToeService(new DefaultWinStrategy());

    }

    @Test
    @DisplayName("createGame initializes empty board with Player X and IN_PROGRESS status")
    void shouldCreateGame() {

        Game newGame = ticTacToeService.createGame();

        assertNotNull(newGame.getGame());
        assertEquals(Player.X, newGame.getCurrentPlayer());
        assertEquals(GameStatus.IN_PROGRESS, newGame.getStatus());
        assertEquals(BOARD_LENGTH, newGame.getBoard().length);
    }

    @Test
    @DisplayName("move should place X at the given position on first turn")
    void shouldPlaceXFirst() {
        Game game = ticTacToeService.createGame();

        Game updated = ticTacToeService.move(game, 0);

        assertEquals('X', updated.getBoard()[0]);
    }

    @Test
    @DisplayName("Should alternate players between X and O")
    void shouldAlternatePlayers() {
        Game game = ticTacToeService.createGame();

        ticTacToeService.move(game, 0); // X
        Game updated = ticTacToeService.move(game, 1); // O

        assertEquals('O', updated.getBoard()[1]);
    }

    @Test
    @DisplayName("should set game status to WIN on a winning move")
    void shouldDetectWin() {
        Game game = ticTacToeService.createGame();

        ticTacToeService.move(game, 0);
        ticTacToeService.move(game, 3);
        ticTacToeService.move(game, 1);
        ticTacToeService.move(game, 4);
        Game result = ticTacToeService.move(game, 2); // win

        assertEquals(GameStatus.WIN, result.getStatus());
        assertEquals('X', result.getBoard()[2]);
    }

    @Test
    @DisplayName("should set game status to DRAW when board is full with no winner")
    void shouldDetectDraw() {
        Game game = ticTacToeService.createGame();

        ticTacToeService.move(game, 0);
        ticTacToeService.move(game, 1);
        ticTacToeService.move(game, 2);
        ticTacToeService.move(game, 4);
        ticTacToeService.move(game, 3);
        ticTacToeService.move(game, 5);
        ticTacToeService.move(game, 7);
        ticTacToeService.move(game, 6);
        Game result = ticTacToeService.move(game, 8);

        assertEquals(GameStatus.DRAW, result.getStatus());
    }

    @Test
    @DisplayName("should not allow moves after a game is won")
    void shouldNotAllowMoveAfterWin() {
        Game game = ticTacToeService.createGame();

        ticTacToeService.move(game, 0);
        ticTacToeService.move(game, 3);
        ticTacToeService.move(game, 1);
        ticTacToeService.move(game, 4);
        ticTacToeService.move(game, 2); // win

        assertThrows(IllegalStateException.class,
                () -> ticTacToeService.move(game, 5));
    }

    @Test
    @DisplayName("should not allow moves after a game is drawn")
    void shouldNotAllowMoveAfterDraw() {
        Game game = ticTacToeService.createGame();

        ticTacToeService.move(game, 0);
        ticTacToeService.move(game, 1);
        ticTacToeService.move(game, 2);
        ticTacToeService.move(game, 4);
        ticTacToeService.move(game, 3);
        ticTacToeService.move(game, 5);
        ticTacToeService.move(game, 7);
        ticTacToeService.move(game, 6);
        ticTacToeService.move(game, 8); // draw

        assertThrows(IllegalStateException.class,
                () -> ticTacToeService.move(game, 0));
    }

    @ParameterizedTest
    @ValueSource(ints = {9, -1})
    @DisplayName("should not allow invalid board positions")
    void shouldNotAllowInvalidPositions(int position) {
        Game game = ticTacToeService.createGame();

        assertThrows(IllegalArgumentException.class,
                () -> ticTacToeService.move(game, position));
    }

    @Test
    @DisplayName("should not allow placing a move on an already occupied cell")
    void shouldNotAllowSameCellTwice() {
        Game game = ticTacToeService.createGame();

        ticTacToeService.move(game, 0);

        assertThrows(IllegalArgumentException.class,
                () -> ticTacToeService.move(game, 0));
    }

    @Test
    @DisplayName("should not switch player after game ends")
    void shouldNotSwitchPlayerAfterGameEnds() {
        Game game = ticTacToeService.createGame();

        ticTacToeService.move(game, 0);
        ticTacToeService.move(game, 3);
        ticTacToeService.move(game, 1);
        ticTacToeService.move(game, 4);
        Game result = ticTacToeService.move(game, 2); // win

        assertEquals(Player.X, result.getCurrentPlayer()); // stays same
    }
}
