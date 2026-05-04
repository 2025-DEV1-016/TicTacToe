package com.bnpp.kata.tictactoe.mapper;

import com.bnpp.kata.tictactoe.api.model.GameResponse;
import com.bnpp.kata.tictactoe.domain.Game;
import com.bnpp.kata.tictactoe.domain.GameStatus;
import com.bnpp.kata.tictactoe.domain.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GameMapperTest {

    public static final int BOARD_LENGTH = 9;
    public static final String EMPTY = "-";

    private final GameMapper gameMapper = new GameMapperImpl();

    @Test
    @DisplayName("should map Game to GameResponse with all fields correctly populated")
    void shouldMapGameToResponse() {
        Game game = new Game("test-id");

        GameResponse response = gameMapper.toResponse(game);

        assertEquals("test-id", response.getGameId());
        assertEquals("X", response.getCurrentPlayer().getValue());
        assertEquals("IN_PROGRESS", response.getStatus().getValue());
        assertEquals(BOARD_LENGTH, response.getBoard().size());
        assertEquals(EMPTY, response.getBoard().get(0).getValue());
        assertEquals(EMPTY, response.getBoard().get(8).getValue());
    }

    @Test
    @DisplayName("should map board correctly when game has moves")
    void shouldMapBoardCorrectly() {
        Game game = new Game("test-id");
        game.update(0, 'X');
        game.update(1, 'O');

        GameResponse response = gameMapper.toResponse(game);

        assertEquals("X", response.getBoard().get(0).getValue());
        assertEquals("O", response.getBoard().get(1).getValue());
    }

    @Test
    @DisplayName("should return null when input game is null")
    void shouldReturnNullWhenGameIsNull() {
        assertNull(gameMapper.map((Player) null));
    }

    @Test
    @DisplayName("should return null when status is null")
    void shouldReturnNullWhenStatusIsNull() {

        GameMapper mapper = new GameMapperImpl();

        assertNull(mapper.map((GameStatus) null));
    }

    @Test
    @DisplayName("should return null when board is null")
    void shouldReturnNullWhenBoardIsNull() {

        GameMapper mapper = new GameMapperImpl();

        assertEquals(List.of(), mapper.mapBoard(null));
    }

    @Test
    @DisplayName("should map player correctly")
    void shouldMapPlayer() {

        GameMapper mapper = new GameMapperImpl();

        assertEquals(
                GameResponse.CurrentPlayerEnum.X,
                mapper.map(Player.X)
        );
    }

    @Test
    @DisplayName("should map game status correctly")
    void shouldMapGameStatus() {

        GameMapper mapper = new GameMapperImpl();

        assertEquals(
                GameResponse.StatusEnum.IN_PROGRESS,
                mapper.map(GameStatus.IN_PROGRESS)
        );
    }

    @Test
    @DisplayName("should map board correctly")
    void shouldMapBoard() {

        GameMapper mapper = new GameMapperImpl();

        char[] board = {'X', 'O', 'X'};

        List<GameResponse.BoardEnum> result = mapper.mapBoard(board);

        assertEquals(3, result.size());
    }
}
