package com.bnpp.kata.tictactoe.controller;

import com.bnpp.kata.tictactoe.api.model.GameResponse;
import com.bnpp.kata.tictactoe.api.model.MoveRequest;
import com.bnpp.kata.tictactoe.domain.Game;
import com.bnpp.kata.tictactoe.mapper.GameMapper;
import com.bnpp.kata.tictactoe.service.TicTacToeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    @DisplayName("Should make a move via API")
    void shouldMakeMove() throws Exception {
        MoveRequest request = new MoveRequest();
        request.setPosition(0);

        Game game = new Game("1");

        GameResponse response = new GameResponse();
        response.setGameId("1");

        when(ticTacToeService.move("1", 0)).thenReturn(game);
        when(gameMapper.toResponse(game)).thenReturn(response);

        mockMvc.perform(post("/games/1/moves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value("1"));
    }

    @Test
    @DisplayName("should return game by id")
    void shouldGetGame() throws Exception {

        Game game = new Game("1");

        GameResponse response = new GameResponse();
        response.setGameId("1");

        when(ticTacToeService.getGame("1")).thenReturn(game);
        when(gameMapper.toResponse(game)).thenReturn(response);

        mockMvc.perform(get("/games/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value("1"));
    }

    @Test
    @DisplayName("should reset game via API")
    void shouldResetGame() throws Exception {

        Game game = new Game("1");

        GameResponse response = new GameResponse();
        response.setGameId("1");

        when(ticTacToeService.reset("1")).thenReturn(game);
        when(gameMapper.toResponse(game)).thenReturn(response);

        mockMvc.perform(post("/games/1/reset"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value("1"));
    }
}
