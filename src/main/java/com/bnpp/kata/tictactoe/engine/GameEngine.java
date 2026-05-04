package com.bnpp.kata.tictactoe.engine;

import com.bnpp.kata.tictactoe.domain.Game;
import com.bnpp.kata.tictactoe.domain.GameStatus;
import com.bnpp.kata.tictactoe.strategy.WinStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class GameEngine {

    public static final int START_INCLUSIVE = 0;
    private final WinStrategy winStrategy;

    public void applyMove(Game game, int position) {

        char symbol = game.getCurrentPlayer().symbol();

        game.update(position, symbol);

        if (winStrategy.isWin(game.getBoard(), symbol)) {
            game.setStatus(GameStatus.WIN);
            return;
        }

        if (isDraw(game)) {
            game.setStatus(GameStatus.DRAW);
            return;
        }

        game.setCurrentPlayer(game.getCurrentPlayer().next());
    }

    private boolean isDraw(Game game) {

        return IntStream.range(START_INCLUSIVE, game.getBoard().length)
                .noneMatch(boardPosition -> game.getBoard()[boardPosition] == Game.EMPTY);

    }
}
