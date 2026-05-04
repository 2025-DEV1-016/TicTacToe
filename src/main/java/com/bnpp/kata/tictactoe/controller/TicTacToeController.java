package com.bnpp.kata.tictactoe.controller;

import com.bnpp.kata.tictactoe.api.model.GameResponse;
import com.bnpp.kata.tictactoe.mapper.GameMapper;
import com.bnpp.kata.tictactoe.service.TicTacToeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TicTacToeController {
    private final TicTacToeService ticTacToeService;

    private final GameMapper gameMapper;

    @PostMapping("/games")
    public ResponseEntity<GameResponse> createGame() {
        return ResponseEntity.ok(gameMapper.toResponse(ticTacToeService.createGame()));
    }

}
