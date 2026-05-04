package com.bnpp.kata.tictactoe.controller;

import com.bnpp.kata.tictactoe.api.model.GameResponse;
import com.bnpp.kata.tictactoe.domain.Game;
import com.bnpp.kata.tictactoe.mapper.GameMapper;
import com.bnpp.kata.tictactoe.service.TicTacToeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TicTacToeController.class)
public class TicTacToeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicTacToeService ticTacToeService;

    @MockBean
    private GameMapper gameMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("should create a new game via API")
    void shouldCreateGame() throws Exception {
        Game game = new Game("1");

        GameResponse response = new GameResponse();
        response.setGameId("1");

        when(ticTacToeService.createGame()).thenReturn(game);
        when(gameMapper.toResponse(game)).thenReturn(response);

        mockMvc.perform(post("/games"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value("1"));
    }
}
