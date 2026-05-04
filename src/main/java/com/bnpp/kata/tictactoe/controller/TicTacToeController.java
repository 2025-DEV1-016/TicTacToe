package com.bnpp.kata.tictactoe.controller;

import com.bnpp.kata.tictactoe.api.GamesApi;
import com.bnpp.kata.tictactoe.api.model.GameResponse;
import com.bnpp.kata.tictactoe.api.model.MoveRequest;
import com.bnpp.kata.tictactoe.mapper.GameMapper;
import com.bnpp.kata.tictactoe.service.TicTacToeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TicTacToeController implements GamesApi {
    private final TicTacToeService ticTacToeService;

    private final GameMapper gameMapper;

    @Override
    public ResponseEntity<GameResponse> createGame() {
        return ResponseEntity.ok(gameMapper.toResponse(ticTacToeService.createGame()));
    }

    @Override
    public ResponseEntity<GameResponse> makeMove(String gameId, MoveRequest request) {
        return ResponseEntity.ok(
                gameMapper.toResponse(ticTacToeService.move(gameId, request.getPosition()))
        );
    }

}
