package com.bnpp.kata.tictactoe.service;

import com.bnpp.kata.tictactoe.domain.Game;
import com.bnpp.kata.tictactoe.domain.GameStatus;
import com.bnpp.kata.tictactoe.domain.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TicTacToeServiceTest {

    @Test
    @DisplayName("createGame initializes empty board with Player X and IN_PROGRESS status")
    void shouldCreateGame() {

        TicTacToeService ticTacToeService = new TicTacToeService();

        Game newGame = ticTacToeService.createGame();

        assertNotNull(newGame.getGameId());
        assertEquals(Player.X, newGame.getCurrentPlayer());
        assertEquals(GameStatus.IN_PROGRESS, newGame.getStatus());
        assertEquals(9, newGame.getBoard().length);
    }
}
