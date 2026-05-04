package com.bnpp.kata.tictactoe.controller;

import com.bnpp.kata.tictactoe.api.model.GameResponse;
import com.bnpp.kata.tictactoe.domain.Game;
import com.bnpp.kata.tictactoe.service.TicTacToeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
public class TicTacToeController {
    private final TicTacToeService ticTacToeService;

    @PostMapping("/games")
    public ResponseEntity<GameResponse> createGame() {

        Game game = ticTacToeService.createGame();

        GameResponse response = new GameResponse();
        response.setGameId(game.getGameId());
        response.setCurrentPlayer(GameResponse.CurrentPlayerEnum.X);
        response.setStatus(GameResponse.StatusEnum.IN_PROGRESS);

        return ResponseEntity.ok(response);
    }

}
