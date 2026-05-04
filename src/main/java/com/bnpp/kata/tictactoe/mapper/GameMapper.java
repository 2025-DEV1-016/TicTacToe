package com.bnpp.kata.tictactoe.mapper;

import com.bnpp.kata.tictactoe.api.model.GameResponse;
import com.bnpp.kata.tictactoe.domain.Game;
import com.bnpp.kata.tictactoe.domain.GameStatus;
import com.bnpp.kata.tictactoe.domain.Player;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.IntStream;

@Mapper(componentModel = "spring")
public interface GameMapper {

    GameResponse toResponse(Game game);

    default GameResponse.CurrentPlayerEnum map(Player player) {
        return player == null ? null :
                GameResponse.CurrentPlayerEnum.fromValue(player.name());
    }

    default GameResponse.StatusEnum map(GameStatus status) {
        return status == null ? null :
                GameResponse.StatusEnum.fromValue(status.name());
    }

    default List<GameResponse.BoardEnum> mapBoard(char[] board) {
        if (board == null) return null;

        return IntStream.range(0, board.length)
                .mapToObj(i -> GameResponse.BoardEnum.fromValue(String.valueOf(board[i])))
                .toList();
    }
}
