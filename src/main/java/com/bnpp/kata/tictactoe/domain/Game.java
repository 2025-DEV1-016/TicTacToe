package com.bnpp.kata.tictactoe.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Game {

    private final String gameId;
    private final char[] board;
    private Player currentPlayer;
    private GameStatus status;
    public static final char EMPTY = '-';
    public static final int START_INCLUSIVE = 0;

    public Game(String gameId) {
        this.gameId = gameId;
        this.board = "---------".toCharArray();
        this.currentPlayer = Player.X;
        this.status = GameStatus.IN_PROGRESS;
    }

    public void update(int position, char symbol) {
        board[position] = symbol;
    }

}
