package com.bnpp.kata.tictactoe.domain;

public enum Player {
    X, O;

    public Player next() {
        return switch (this) {
            case X -> O;
            case O -> X;
        };
    }

    public char symbol() {
        return name().charAt(0);
    }
}
