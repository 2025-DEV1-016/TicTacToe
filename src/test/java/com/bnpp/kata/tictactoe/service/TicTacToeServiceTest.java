package com.bnpp.kata.tictactoe.service;

import com.bnpp.kata.tictactoe.domain.Game;
import com.bnpp.kata.tictactoe.domain.GameStatus;
import com.bnpp.kata.tictactoe.domain.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TicTacToeServiceTest {

    public static final int BOARD_LENGTH = 9;

    private TicTacToeService ticTacToeService;

    @BeforeEach
    public void setUp() {
        ticTacToeService = new TicTacToeService();
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
}
